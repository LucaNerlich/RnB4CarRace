package CarRace.Model;

/**
 * Placeholder Klasse fuer das Autorennen. Dient der moeglichen Erweiterung.
 * Startplatz, Geschwindigkeit, Rundenzeit...
 */
public class RaceCar {

    String name;
    String geschwindigkeit;

    public RaceCar() {
        //
    }

    public RaceCar(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
