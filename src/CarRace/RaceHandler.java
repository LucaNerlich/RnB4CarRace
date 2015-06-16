package CarRace;

import CarRace.Controller.Connection.Listener;
import CarRace.Controller.View.GuiController;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by lnerlich on 16.06.2015.
 */
public class RaceHandler implements Observer {

    private Socket socket;
    private Listener listener;

    public RaceHandler(Socket socket, Listener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    public void schreibeNachricht(String nachricht) throws IOException {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();
    }

    @Override
    public void update(Observable o, Object messageString) {
        if (o instanceof GuiController) {
            try {
                schreibeNachricht((String) messageString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
