import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * Created by Luca Nerlich on 17.06.2015.
 * Timerklasse die fuer eine gegebene Zeit neue Clientenverbindungen akzeptiert.
 */
public class RaceTimer extends TimerTask {

    Thread t;
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private static ArrayList<PrintWriter> clients;

    RaceTimer(Thread t, ServerSocket ss, ExecutorService pool, ArrayList<PrintWriter> clients) {
        this.t = t;
        this.serverSocket = ss;
        this.pool = pool;
        this.clients = clients;
    }

    @Override
    public void run() {
        Socket cs = null;  //warten auf Client-Anforderung
        try {
            System.out.println("Accepting Clients...");
            cs = serverSocket.accept();

            //list of clients to send messages to
            clients.add(new PrintWriter(cs.getOutputStream(), true));

            //starte den Handler-Thread zur Realisierung der Client-Anforderung
            ClientHandler client = new ClientHandler(serverSocket, cs, clients);
            RaceCalculator.addClientHandler(client);
            pool.execute(client);
            System.out.println("Client_" + ClientHandler.getClientId() + " added");
        } catch (IOException e) {
            System.out.println("--- Interrupt NetworkService-run");
        }
    }
}

