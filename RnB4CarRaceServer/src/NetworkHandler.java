import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
                Socket cs = serverSocket.accept();  //warten auf Client-Anforderung

                //list of clients to send messages to
                clients.add(new PrintWriter(cs.getOutputStream(), true));

                //starte den Handler-Thread zur Realisierung der Client-Anforderung
                pool.execute(new ClientHandler(serverSocket, cs));
            }
        } catch (IOException ex) {
            System.out.println("--- Interrupt NetworkService-run");
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
