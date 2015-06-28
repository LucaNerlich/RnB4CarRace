package Race;

import Handler.MessageHandler;

/**
 * Created by Luca Nerlich on 26.06.2015.
 */
public class RaceCarThread implements Runnable {

    private RaceCar raceCar;
    private MessageHandler messageHandler = new MessageHandler();

    public RaceCarThread(RaceCar raceCar) {
        this.raceCar = raceCar;
    }

    @Override
    public void run() {
        int finalTime = 0;
        for (int i = 0; i < 3; i++) {
            int time = (int) (Math.random() * 5) + 1;
            messageHandler.sendMessageToSingleClient(raceCar.getPw(), raceCar.getName() + " : Runde " + i + ": " + time + " in seconds.");
            try {
                Thread.sleep(time * 250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finalTime += time;
        }

        messageHandler.sendMessageToSingleClient(raceCar.getPw(), raceCar.getName() + " hat das Ziel erreicht!");
        raceCar.setTimeFinished(finalTime);
    }
}
