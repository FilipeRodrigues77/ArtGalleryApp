package domain;

/**
 * The {@code Artist} class represents an artist with various attributes such as ID, name, nationality,
 * biography, birthdate, deathdate, slug, and a reference image.
 *
 * <p>An artist object can be instantiated either with an empty constructor or with a constructor
 * that initializes its attributes.</p>
 *
 * <p>This class also provides getter and setter methods for accessing and modifying the attributes of an artist.</p>
 *
 * <p>The {@code toString()} method is overridden to provide a formatted string representation of the artist object.</p>
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class Artist {

    private int id;
    private String name;
    private String nationality;
    private String biography;
    private String birthdate;
    private String deathdate;
    private String slug;
    private String referenceImage;

    /**
     * Constructs an empty {@code Artist} object.
     * This constructor is useful for creating an artist object without initializing its attributes.
     */
    public Artist() {
    }

    /**
     * Constructs an {@code Artist} object with the specified attributes.
     *
     * @param id The unique identifier of the artist.
     * @param name The name of the artist.
     * @param nationality The nationality of the artist.
     * @param biography The biography of the artist.
     * @param birthday The birthdate of the artist.
     * @param deathday The deathdate of the artist.
     * @param slug The slug associated with the artist.
     * @param referenceImage The reference image URL associated with the artist.
     */
    public Artist(int id, String name, String nationality, String biography,
                  String birthday, String deathday, String slug, String referenceImage) {

        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.biography = biography;
        this.birthdate = birthday;
        this.deathdate = deathday;
        this.slug = slug;
        this.referenceImage= referenceImage;
    }

    // Getter and setter methods for each attribute are provided below.
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

    public String getReferenceImage() {
        return referenceImage;
    }

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

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    /**
     * Returns a formatted string representation of the {@code Artist} object.
     *
     * @return A formatted string containing the attributes of the artist.
     */
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
                ", referenceImage='" + referenceImage + '\'' + "\n" +
                '}'+ "\n\n";
    }
}