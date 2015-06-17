/**
 * Created by lnerlich on 16.06.2015.
 * Placeholder Klasse fuer das Autorennen. Dient der moeglichen Erweiterung.
 * Startplatz, Geschwindigkeit, Rundenzeit...
 */
public class RaceCar {

    private String name;
    private int timeFinished;

    public RaceCar() {
        //
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
}
