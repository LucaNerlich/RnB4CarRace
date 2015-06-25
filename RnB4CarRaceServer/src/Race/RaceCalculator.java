package Race;

import Handler.ClientHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lnerlich on 16.06.2015.
 * Bekommt Informationen uber Teilnehmer und berechnet den Ausgang des Rennens.
 */
public class RaceCalculator {
    // klasse zur Zentralen Rennverwaltung
    // kennt alle teilnehmer etc
    // quueue mit allen client threads
    // berechnet das rennen und schickt nachrichten an alle clients

    private static RaceCalculator instance = null;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList();
   // private static ArrayList<String> carNames = new ArrayList<>();
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

    /**
     * Berechet / findet zufaellig einen Gewinner des Autorennens.
     *
     * @return -> Race.RaceCar Gewinner des Rennens.
     */
    public static RaceCar calculateRace() {
        RaceCar winner = null;

        generateTimes();

        //calculate Winner from Cars
        if (raceCars.size() > 0) {
            winner = raceCars.get(0);
            long currentWinnerTime = raceCars.get(0).getTimeFinished();

            for (RaceCar raceCar : raceCars) {
                if (raceCar.getTimeFinished() < currentWinnerTime) {
                    winner = raceCar;
                }
            }
        }
        return winner;
    }

    private static void generateTimes() {
        //generate a random Time for each Car
        if (raceCars.size() > 0) {
            for (RaceCar raceCar : raceCars) {
                //zahlen atm noch zu groß, todo 'realistischere' Zahlen
                Random random = new Random();
                int racetime = random.nextInt();

                //alle zeiten muessen > 0 sein.
                if (racetime < 0) {
                    racetime = (racetime * -1);
                }
                raceCar.setTimeFinished(racetime);
            }

            //RaceCar raceCar = new RaceCar(carName, racetime);
            //System.out.println(racetime);
            //raceCars.add(raceCar);
        }
    }

    public static void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public synchronized static void addCar(Socket client, String carName) {
        if (carName.length() > 0) {
            RaceCar raceCar = new RaceCar(client, carName);
            //  carNames.add(carName);
        }
    }

    /**
     * ueberschreibe beide Arrays mit einem leeren, neuen.
     */
    public static void clearAllCars() {
        //carNames = new ArrayList<>();
        raceCars = new ArrayList<>();
    }
}
