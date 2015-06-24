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
    private static Set<Socket> clientSockets = new HashSet<>();
    private static Timer timer = new Timer(true); // set daemon
    private long currenttime = System.currentTimeMillis();
    private long endtime = currenttime + 30000;

    public NetworkHandler(ExecutorService pool,
                          ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = pool;
        clients = new ArrayList();
    }

    public void run() { // run the service
        // try {
        //while (System.currentTimeMillis() < endtime) {

        /*
         Zunaechst wird eine Client-Anforderung entgegengenommen(accept-Methode).
         Der ExecutorService pool liefert einen Thread, dessen run-Methode
         durch die run-Methode der Handler-Instanz realisiert wird.
         Dem Handler werden als Parameter uebergeben:
         der ServerSocket und der Socket des anfordernden Clients.
        */

        System.out.println("Starting Timer, accepting clients");

        Runnable acceptingClients = () -> {
            acceptClients();
        };
        new Thread(acceptingClients).start();

        /*
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Socket cs = null;  //warten auf Client-Anforderung
                    System.out.println("Accepting Clients...");
                    cs = serverSocket.accept();

                    //list of clients to send messages to
                    clients.add(new PrintWriter(cs.getOutputStream(), true));

                    //starte den Handler-Thread zur Realisierung der Client-Anforderung
                    ClientHandler client = new ClientHandler(serverSocket, cs, clients);
                    RaceCalculator.addClientHandler(client);
                    pool.execute(client);
                    System.out.println("Client_" + ClientHandler.getClientId() + " added");
                    System.out.println("Remaining Time: " + (endtime - System.currentTimeMillis()));
                } catch (IOException e) {
                    System.out.println("--- Interrupt NetworkService-run");
                }
            }
        },30_000);

        */

        // }

        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Timertask done - cannot accept new Clients");
        System.out.println("Calculating Race and Winner... Please stand by.");

        try {
            //execute race:
            //erstmal kein ende, zum testen

            RaceCar winner = RaceCalculator.calculateRace();
            PrintWriter out;

            //send winner to all clients
            if (winner != null) {
                for (int i = 0; i < clients.size(); i++) {
                    out = clients.get(i);
                    out.println("The Winner is: " + winner.getName() + " with a time of: " + winner.getTimeFinished());
                    System.out.println("Winner: " + winner.getName());
                }
            } else {
                for (int i = 0; i < clients.size(); i++) {
                    out = clients.get(i);
                    out.println("Es nahmen keine Autos am Rennen teil.");
                    System.out.println("Error, keine Autos.");
                }
            }

        } finally {
            System.out.println("--- Ende NetworkService(pool.shutdown)");
            pool.shutdown();  //keine Annahme von neuen Anforderungen
            try {
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
        // }
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
                        ClientHandler client = new ClientHandler(serverSocket, cs, clients);
                        RaceCalculator.addClientHandler(client);
                        pool.execute(client);
                        System.out.println("Client_" + ClientHandler.getClientId() + " added");
                        System.out.println("Remaining Time: " + (endtime - System.currentTimeMillis()));
                    }
                    }catch(IOException e){
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
