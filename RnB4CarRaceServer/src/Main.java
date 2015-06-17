import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luca Nerlich on 15.06.2015.
 * orientiert an: https://de.wikibooks.org/wiki/Java_Standard:_Socket_ServerSocket_(java.net)_UDP_und_TCP_IP#Ein_etwas_komfortableres_Client.2FServer-Beispiel
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Ich bin ein Race Server!");

        try {
            final ExecutorService pool = Executors.newCachedThreadPool();
            int port = 3141;
            final ServerSocket serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(60000); // Timeout nach 1 Minute

            Thread t1 = new Thread(new NetworkHandler(pool, serverSocket));
            System.out.println("Start NetworkService(Multiplikation), Thread: " + Thread.currentThread());
            //Start der run-Methode von NetworkService: warten auf Client-request
            t1.start();

            //reagiert auf Strg+C, der Thread(Parameter) darf nicht gestartet sein
            Runtime.getRuntime().addShutdownHook(
                    new Thread() {
                        public void run() {
                            System.out.println("Strg+C, pool.shutdown");
                            pool.shutdown();  //keine Annahme von neuen Anforderungen
                            try {
                                //warte maximal 4 Sekunden auf Beendigung aller Anforderungen
                                pool.awaitTermination(4L, TimeUnit.SECONDS);
                                if (!serverSocket.isClosed()) {
                                    System.out.println("ServerSocket close");
                                    serverSocket.close();
                                }
                            } catch (IOException e) {
                            } catch (InterruptedException ei) {
                            }
                        }
                    }
            );

        } catch (InterruptedIOException iEex) {
            System.err.println("Timeout nach einer Minute!");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
