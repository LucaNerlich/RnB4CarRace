package CarRace;

import CarRace.Controller.Connection.Listener;
import CarRace.Controller.View.GuiController;
import CarRace.Model.RaceCar;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by lnerlich on 16.06.2015.
 */
public class RaceHandler {

    private Socket socket;
    private Listener listener;

    private ArrayList<RaceCar> raceCars = new ArrayList<>();

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

    public boolean registerCars(){
        boolean added = false;
        String message;
        StringBuilder sb = new StringBuilder();
        sb.append("/REGCARS");
        sb.append("-");
        if(raceCars.size() > 0) {
            for (RaceCar raceCar : raceCars) {
                sb.append(raceCar.getName());
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
        }else{
            System.err.println("Keine Race Cars eingetragen!");
        }
        return added;
    }

    public void addRaceCar(RaceCar raceCar){
        if(raceCar != null){
        raceCars.add(raceCar);
        }
    }
}
