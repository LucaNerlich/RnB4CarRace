package Handler;

import Race.RaceCar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Luca Nerlich on 15.06.2015.
 * Verwaltet die einzelnen Clienten.
 */
public class ClientHandler implements Runnable {
    private final Socket client;
    private final ServerSocket serverSocket;
    private static int clientId;
    private BufferedReader bufferedReader;
    private PrintWriter pw;
    private ArrayList<RaceCar> cars = new ArrayList<>();

    ClientHandler(ServerSocket serverSocket, Socket client, PrintWriter pw) { //Server/Client-Socket
        this.client = client;
        this.serverSocket = serverSocket;
        clientId++;
        this.pw = pw;
    }

    @Override
    public void run() {

        PrintWriter out;
        System.out.println("running service, " + Thread.currentThread());
        try {
            while (true) {
                // read and service request on client
                out = new PrintWriter(client.getOutputStream(), true);

                bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        client.getInputStream()));
                char[] buffer = new char[100];
                int anzahlZeichen = bufferedReader.read(buffer, 0, 100); // blockiert bis Nachricht empfangen
                String nachricht = new String(buffer, 0, anzahlZeichen);

                System.out.println("Client_ID_'" + clientId + "'_" + client.getInetAddress() + ":" + client.getLocalPort() + " _ " + nachricht);
                String answer = Race.RaceProtocol.getInstance().processInput(client, nachricht, pw, cars);

                //send message to client
                out.println("Server " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() + " _ " + answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getClientId() {
        return clientId;
    }

    public void addRaceCar(RaceCar car){
        cars.add(car);
    }
}
