package domain;

public class Artist {

    private int id;
    private String name;
    private String nationality;
    private String biography;
    private String birthdate;
    private String deathdate;
    private String slug;

    public Artist() {
    }

    public Artist(int id, String name, String nationality, String biography,
                  String birthday, String deathday, String slug) {

        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.biography = biography;
        this.birthdate = birthday;
        this.deathdate = deathday;
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getDeathdate() {
        return deathdate;
    }

    public String getSlug() { return slug; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDeathdate(String deathdate) {
        this.deathdate = deathdate;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Artist{" + "\n" +
                "  id='" + id + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", nationality='" + nationality + '\'' + "\n" +
                ", biography='" + biography + '\'' + "\n" +
                ", birthdate='" + birthdate + '\'' + "\n" +
                ", deathdate='" + deathdate + '\'' + "\n" +
                ", slug='" + slug + '\'' + "\n" +
                '}'+ "\n\n";
    }
}
