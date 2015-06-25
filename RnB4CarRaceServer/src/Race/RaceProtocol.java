package Race;

import java.net.Socket;

/**
 * Created by lnerlich on 16.06.2015.
 * Verwaltet und ordnet die Inputs des Clienten zu.
 */
public class RaceProtocol {
    private static RaceProtocol instance = null;
    public static String carNames;

    private RaceProtocol() {
        //
    }

    //singleton
    public synchronized static RaceProtocol getInstance() {
        if (instance == null) {
            RaceProtocol.instance = new RaceProtocol();
        }
        return RaceProtocol.instance;
    }

    //messages werden immer mit bei nem '-' getrennt und verarbeitet.
    //deswegen muessen alle eingehenden strings zwingend bei mehr als einem Wort mit '-' getrennt werden.

    /**
     * Verarbeitet den Inputstring und gibt entsprechende Rueckgaben.
     *
     * @param theInput -> String mit '-' als Trennzeichen
     * @return
     */
    public String processInput(Socket client, String theInput) {
        String theOutput = null;

        //TODO ueberarbeiten, per Handler loesen

        StringBuilder sb = new StringBuilder();
        // theOutput = stringsplit by -
        String[] inputParts = theInput.split("-");

        if (theInput.length() > 0) {
            if (inputParts[0].equals("/HELP")) {
                theOutput = "Commands: '/HELP', '/INFO', '/EXIT'";
            } else if (inputParts[0].equals("/INFO")) {
                theOutput = "Server: INFO ";
            } else if (inputParts[0].equals("/START")) {
                theOutput = "Race will start in 3 Seconds";
            } else if (inputParts[0].equals("/REGCARS")) {
                for (int i = 1; i < inputParts.length; i++) {
                    sb.append(inputParts[i]);

                    //add Car to Race
                    RaceCalculator.addCar(client, inputParts[i]);
                    sb.append(", ");
                }
                carNames = sb.toString();
                theOutput = "Registered Cars are: " + carNames;
            } else if (inputParts[0].equals("/EXIT")) {
                theOutput = "/EXIT";
            } else {
                theOutput = theInput;
            }
        }
        return theOutput;
    }
}
