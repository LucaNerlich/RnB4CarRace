package CarRace.Controller.Connection;

import CarRace.Controller.View.GuiController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by lnerlich on 16.06.2015.
 * Wartet auf Serverrueckmeldungen und gibt diese aus.
 */
public class Listener implements Runnable {
    private Socket socket;
    private GuiController guiController;

    public Listener(Socket socket, GuiController guiController) {
        this.socket = socket;
        this.guiController = guiController;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));


            while(true){
                //get message from server and print to Text Area
                guiController.getGetDisplayConsoleTextArea().appendText("\n" + in.readLine());
                guiController.getRegcarsButton().setDisable(false);
                guiController.getAddCarButton().setDisable(false);
            }

        } catch (IOException e) {
            guiController.getGetDisplayConsoleTextArea().appendText("\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
