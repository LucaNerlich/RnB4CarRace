import java.util.ArrayList;
import java.util.Random;

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
    private static ArrayList<String> carNames = new ArrayList<>();
    private static ArrayList<RaceCar> raceCars = new ArrayList<>();

    private RaceCalculator() {
        //
    }

    //singleton
    public synchronized static RaceCalculator getInstance() {
        if (instance == null) {
            RaceCalculator.instance = new RaceCalculator();
        }
        return RaceCalculator.instance;
    }

    public static RaceCar calculateRace() {
        RaceCar winner = null;
        int amountOfCompetitors = carNames.size();

        //generate a Time for each Car
        for (String carName : carNames) {
            Random random = new Random();
            Long racetime = random.nextLong();

            //alle zeiten muessen > 0 sein.
            if(racetime < 0){
                racetime = (racetime * -1);
            }
            RaceCar raceCar = new RaceCar(carName, racetime);
            raceCars.add(raceCar);
        }

        //calculate Winner from Cars

        if (raceCars.size() > 0) {

            String currentWinnerName = raceCars.get(0).getName();
            long currentWinnerTime = raceCars.get(0).getTimeFinished();

            for (RaceCar raceCar : raceCars) {
                if (raceCar.getTimeFinished() < currentWinnerTime) {
                    currentWinnerTime = raceCar.getTimeFinished();
                    currentWinnerName = raceCar.getName();
                    winner = raceCar;
                }
            }

        }
        return winner;
    }

    public static void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public synchronized static void addCar(String carName) {
        if (carName.length() > 0) {
            carNames.add(carName);
        }
    }

    /**
     * ueberschreibe beide Arrays mit einem leeren, neuen.
     */
    public static void clearAllCars() {
        carNames = new ArrayList<>();
        raceCars = new ArrayList<>();
    }
}
