package Race;

import java.util.ArrayList;

/**
 * Created by Luca Nerlich on 26.06.2015.
 */
public class RaceOverview implements Runnable {

    ArrayList<RaceCar> cars;
    ArrayList<Thread> threads;

    public RaceOverview(ArrayList<RaceCar> cars) {
        this.cars = cars;
    }

    public void startRace(){

    }

    @Override
    public void run() {
        startRace();
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }
}
