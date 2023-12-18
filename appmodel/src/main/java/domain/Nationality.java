package domain;

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
