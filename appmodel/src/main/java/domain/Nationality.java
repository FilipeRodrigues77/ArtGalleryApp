package domain;


/**
 * The Nationality class represents a person's nationality and provides methods to get and set
 * the nationality value. Instances of this class can be used to store and retrieve information
 * about an individual's nationality.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class Nationality {

    private String nationality;

    public Nationality() {

    }

    public Nationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Nationality{" +
                "nationality='" + nationality + '\'' +
                '}';
    }
}
