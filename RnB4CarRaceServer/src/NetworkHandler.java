import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luca Nerlich on 15.06.2015.
 */
public class NetworkHandler implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private static ArrayList<PrintWriter> clients;

    public NetworkHandler(ExecutorService pool,
                          ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = pool;
        clients = new ArrayList();
    }

    public void run() { // run the service
        try {
            while (true) {
        /*
         Zunaechst wird eine Client-Anforderung entgegengenommen(accept-Methode).
         Der ExecutorService pool liefert einen Thread, dessen run-Methode
         durch die run-Methode der Handler-Instanz realisiert wird.
         Dem Handler werden als Parameter uebergeben:
         der ServerSocket und der Socket des anfordernden Clients.
        */
                


                Timer t = new Timer();
                t.schedule(new TimerTask() {

                    //run for 30 seconds
                    @Override
                    public void run() {                   //timer
                        Socket cs = null;  //warten auf Client-Anforderung
                        try {
                            cs = serverSocket.accept();

                            //list of clients to send messages to
                            clients.add(new PrintWriter(cs.getOutputStream(), true));

                            //starte den Handler-Thread zur Realisierung der Client-Anforderung
                            ClientHandler client = new ClientHandler(serverSocket, cs, clients);
                            RaceCalculator.addClientHandler(client);
                            pool.execute(client);
                        } catch (IOException e) {
                            System.out.println("--- Interrupt NetworkService-run");
                        }
                    }
                }, 30000L);

                //execute race:

                RaceCar winner = RaceCalculator.calculateRace();
                PrintWriter out;

                //send winner to all clients
                for(int i = 0; i < clients.size(); i++){
                    out = clients.get(i);
                    out.println("The Winner is: " + winner.getName() + " with a time of: " + winner.getTimeFinished());
                    break;
                }

                //atm wird das rennen nur einmal ausgefuehrt.
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
    }

    public static ArrayList<PrintWriter> getClients() {
        return clients;
    }
}
