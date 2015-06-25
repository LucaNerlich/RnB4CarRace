package Race;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lnerlich on 16.06.2015.
 * Placeholder Klasse fuer das Autorennen. Dient der moeglichen Erweiterung.
 * Startplatz, Geschwindigkeit, Rundenzeit...
 */
public class RaceCar {

    private String name;
    private int timeFinished;
    private Socket socket;
    private PrintWriter pw;

    public RaceCar() {
        //
    }

    public RaceCar(Socket client, String name){
        this.socket = client;
        this.name = name;
    }

    public RaceCar(String name, int timeFinished) {
        this.name = name;
        this.timeFinished = timeFinished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(int timeFinished) {
        this.timeFinished = timeFinished;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }
}
