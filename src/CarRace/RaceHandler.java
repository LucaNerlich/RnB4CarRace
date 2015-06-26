package CarRace;

import CarRace.Controller.Connection.Listener;
import CarRace.Model.RaceCar;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lnerlich on 16.06.2015.
 * Verwaltet den Clienten. Schickt Nachrichten zum Server.
 */
public class RaceHandler {

    private Socket socket;
    private Listener listener;
    private Set<RaceCar> raceCars = new HashSet<>();

    public RaceHandler(Socket socket, Listener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    /**
     * Nimmt einen String entgegen und schickt diesen an den Server am Socket.
     *
     * @param nachricht -> String der an den Server uebermittelt wird.
     * @throws IOException
     */
    public void schreibeNachricht(String nachricht) throws IOException {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();
    }

    /**
     * Fuegt RaceCars einer Liste hinzu und uebermittelt diese dem Server.
     *
     * @return boolean -> war das hinzufuegen erfolgreich?
     */
    public boolean registerCars() {
        int counter = 1;
        boolean added = false;
        String message;
        StringBuilder sb = new StringBuilder();
        sb.append("/REGCARS");
        sb.append("-");
        if (raceCars.size() > 0) {
            for (RaceCar raceCar : raceCars) {
                sb.append(raceCar.getName() + counter);
                counter++;
                sb.append("-");
            }

            message = sb.toString();
            System.out.println(message);
            try {
                schreibeNachricht(message);
                added = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Keine Race Cars eingetragen!");
        }
        return added;
    }

    public boolean addRaceCar(RaceCar raceCar) {
        boolean added = false;
        if (raceCar != null && !(raceCars.contains(raceCar))) {
            added = raceCars.add(raceCar);
        }
        return added;
    }
}
