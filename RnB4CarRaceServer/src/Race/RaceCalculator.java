package Race;

import Handler.ClientHandler;
import Handler.MessageHandler;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
        ArrayList<Thread> carThreads = new ArrayList<>();

        for (RaceCar car : raceCars) {
            RaceCarThread raceCarThread = new RaceCarThread(car);
            Thread thread = new Thread(raceCarThread);
            thread.start();
            carThreads.add(thread);
        }

        //wartet bis alle Autos ihre Runden gefahren sind.
        try {
            for (Thread thread : carThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return calculateWinner();
    }

    private static RaceCar calculateWinner() {
        RaceCar winner = null;

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

    public static void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public synchronized static void addCar(Socket client, String carName, PrintWriter pw, ArrayList<RaceCar> cars) {
        if (carName.length() > 0) {
            RaceCar raceCar = new RaceCar(client, carName, pw);
            raceCars.add(raceCar);
            cars.add(raceCar);
        }
    }
}
