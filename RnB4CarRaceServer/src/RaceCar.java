/**
 * Created by lnerlich on 16.06.2015.
 */
public class RaceCar {

    private String name;
    private long timeFinished;

    public RaceCar() {
        //
    }

    public RaceCar(String name, long timeFinished) {
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

    public void setTimeFinished(long timeFinished) {
        this.timeFinished = timeFinished;
    }
}
