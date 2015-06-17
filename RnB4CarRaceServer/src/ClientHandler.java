import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Luca Nerlich on 15.06.2015.
 * Verwaltet die einzelnen Clienten.
 */
public class ClientHandler implements Runnable {
    //hier muessen alle autos gesammelt werden?

    private final Socket client;
    private final ServerSocket serverSocket;
    private static int clientId;
    private static ArrayList<PrintWriter> clients;
    BufferedReader bufferedReader;

    ClientHandler(ServerSocket serverSocket, Socket client, ArrayList<PrintWriter> clients) { //Server/Client-Socket
        this.client = client;
        this.serverSocket = serverSocket;
        clientId++;
        this.clients = clients;
    }

    @Override
    public void run() {
        // Iterator<DataOutputStream> it = NetworkHandler.getClients().iterator();

        StringBuffer sb = new StringBuffer();
        PrintWriter out = null;
        System.out.println("running service, " + Thread.currentThread());
        try {
            while (true) {
                // read and service request on client
                out = new PrintWriter(client.getOutputStream(), true);

                bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        client.getInputStream()));
                char[] buffer = new char[100];
                int anzahlZeichen = bufferedReader.read(buffer, 0, 100); // blockiert bis Nachricht empfangen
                String nachricht = new String(buffer, 0, anzahlZeichen);
                //nachricht.split("\\s");
                System.out.println("Client_ID_'" + clientId + "'_" + client.getInetAddress() + ":" + client.getLocalPort() + " _ " + nachricht);

                String answer = RaceProtocol.getInstance().processInput(nachricht);
                out.println("Server " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() + " _ " + answer);  //R�ckgabe Ergebnis an den Client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getClientId() {
        return clientId;
    }
}
