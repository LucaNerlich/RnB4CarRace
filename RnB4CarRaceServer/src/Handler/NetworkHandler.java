package Handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luca Nerlich on 15.06.2015.
 * Verwaltet die Verbindungen des Servers.
 */
public class NetworkHandler implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private static ArrayList<PrintWriter> clients;
    private MessageHandler messageHandler;

    //for future use
    private static Set<Socket> clientSockets = new HashSet<>();

    public NetworkHandler(ExecutorService pool,
                          ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = pool;
        clients = new ArrayList();
        messageHandler = new MessageHandler();
    }

    public void run() {

        /*
         Zunaechst wird eine Client-Anforderung entgegengenommen(accept-Methode).
         Der ExecutorService pool liefert einen Thread, dessen run-Methode
         durch die run-Methode der Handler-Instanz realisiert wird.
         Dem Handler werden als Parameter uebergeben:
         der ServerSocket und der Socket des anfordernden Clients.
        */

        System.out.println("Starting Timer, accepting clients");

        //danke Julian E. fuer den Lambda Ausdruck:
        Runnable acceptingClients = () -> {
            acceptClients();
        };
        new Thread(acceptingClients).start();

        try {
            //WAIT FOR SERVER TO ACCEPT CLIENTS, KACK LOESUNG
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Timertask done - cannot accept new Clients");
        System.out.println("Calculating Race and Winner... Please stand by.");

        try {
            //execute race:
            Race.RaceCar winner = Race.RaceCalculator.calculateRace();

            //send winner to all clients
            if (winner != null) {
                String inetaddress = winner.getSocket().getLocalAddress().toString();
                int port = winner.getSocket().getPort();
                messageHandler.sendMessageToClients(clients, "Race Over, the registered cars are: " + Race.RaceProtocol.carNames);
                messageHandler.sendMessageToClients(clients, "The Winner is: " + winner.getName() +
                        " at the Address: " + inetaddress +":" + port + " with a time of: " + winner.getTimeFinished());
            } else {
                messageHandler.sendMessageToClients(clients, "Es nahmen keine Autos am Rennen teil.");
            }

        } finally {
            System.out.println("--- Ende NetworkService(pool.shutdown)");
            try {
                pool.shutdown();  //keine Annahme von neuen Anforderungen
                //warte maximal 4 Sekunden auf Beendigung aller Anforderungen
                pool.awaitTermination(4L, TimeUnit.SECONDS);
                if (!serverSocket.isClosed()) {
                    System.out.println("--- Ende NetworkService:ServerSocket close");
                    serverSocket.close();
                }
            } catch (IOException e) {
            } catch (InterruptedException ei) {
            }
        }
    }

    private void acceptClients() {
        System.out.println("Race starts in 30 seconds.");
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        Socket cs = null;  //warten auf Client-Anforderung
                        System.out.println("Accepting Clients...");
                        cs = serverSocket.accept();

                        //list of clients to send messages to
                        clientSockets.add(cs);
                        clients.add(new PrintWriter(cs.getOutputStream(), true));

                        //starte den Handler-Thread zur Realisierung der Client-Anforderung
                        ClientHandler client = new ClientHandler(serverSocket, cs);
                        Race.RaceCalculator.addClientHandler(client);
                        pool.execute(client);
                        System.out.println("Client_" + ClientHandler.getClientId() + " added");
                    }
                } catch (IOException e) {
                    System.out.println("--- Interrupt NetworkService-run");
                }
            }
        }, 30_000);
    }


    public static ArrayList<PrintWriter> getClients() {
        return clients;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public static void setClients(ArrayList<PrintWriter> clients) {
        NetworkHandler.clients = clients;
    }
}
