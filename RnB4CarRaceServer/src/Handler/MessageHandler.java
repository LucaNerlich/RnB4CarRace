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

        for (int i = 0; i < clients.size(); i++) {
            out = clients.get(i);
            out.println(message);
            System.out.println(message);
        }
    }
}
