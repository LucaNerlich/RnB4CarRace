import java.util.ArrayList;

/**
 * Created by lnerlich on 16.06.2015.
 */
public class RaceCalculator {
    // klasse zur Zentralen Rennverwaltung
    // kennt alle teilnehmer etc
    // quueue mit allen client threads
    // berechnet das rennen und schickt nachrichten an alle clients

    private static RaceCalculator instance = null;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList();
    private static ArrayList<String> cars = new ArrayList<>();

    private RaceCalculator(){
        //
    }

    //singleton
    public synchronized static RaceCalculator getInstance() {
        if (instance == null) {
            RaceCalculator.instance = new RaceCalculator();
        }
        return RaceCalculator.instance;
    }

    public static String calculateRace(){
        String winner = "null";

        

        return winner;
    }

    public static void addClientHandler(ClientHandler clientHandler){
     clientHandlers.add(clientHandler);
    }

    public synchronized static void addCar(String carName){
        if(carName.length() > 0){
            cars.add(carName);
        }
    }

    public static void clearAllCars(){
        cars = new ArrayList<>();
    }
}
