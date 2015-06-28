package Handler;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author (Daniel.Sommerlig@haw-hamburg.de) &
 *         (Lucasteffen.Nerlich@haw-hamburg.de)
 */
public class MessageHandler {

    PrintWriter out;

    public void sendMessageToClients(ArrayList<PrintWriter> clients, String message){

        for (PrintWriter client : clients) {
            out = client;
            out.println(message);
            System.out.println(message);
        }
    }

    public void sendMessageToSingleClient(PrintWriter pw, String message){
        out = pw;
        out.println(message);
    }
}
