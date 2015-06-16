import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Luca Nerlich on 15.06.2015.
 */
public class ClientHandler implements Runnable {
    //hier muessen alle autos gesammelt werden?

    private final Socket client;
    private final ServerSocket serverSocket;

    ClientHandler(ServerSocket serverSocket, Socket client) { //Server/Client-Socket
        this.client = client;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        /*
        try {

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inputLine;
            String outputLine;

            // Initiate conversation with client
            RaceProtocol rP = new RaceProtocol();
            outputLine = rP.processInput(null);
            out.println(outputLine);


            //todo client input verwalten.
            while ((inputLine = in.readLine()) != null) {
                outputLine = rP.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("/EXIT"))
                    break;
            }
*/
        // Iterator<DataOutputStream> it = NetworkHandler.getClients().iterator();

        StringBuffer sb = new StringBuffer();
        PrintWriter out = null;
        System.out.println("running service, " + Thread.currentThread());
        try {
            while (true) {
                // read and service request on client
                out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        client.getInputStream()));
                char[] buffer = new char[100];
                int anzahlZeichen = bufferedReader.read(buffer, 0, 100); // blockiert bis Nachricht empfangen
                String nachricht = new String(buffer, 0, anzahlZeichen);
                String[] werte = nachricht.split("\\s");
                System.out.println("Nachricht vom Client:_" + nachricht);
                out.println("Antwort vom Server");  //R�ckgabe Ergebnis an den Client
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /* finally {
            if (client != null)
                try {
                    client.close();
                } catch (IOException e) {
                }
        } */

    }
}
