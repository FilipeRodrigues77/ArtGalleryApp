package domain;

/**
 * The {@code Gallery} class represents an art gallery with various attributes such as ID, name,
 * email, region name, reference to shows, and reference image URL.
 *
 * <p>A gallery object can be instantiated either with an empty constructor or with a constructor
 * that initializes its attributes.</p>
 *
 * <p>This class also provides getter and setter methods for accessing and modifying the attributes of a gallery.</p>
 *
 * <p>The {@code toString()} method is overridden to provide a formatted string representation of the gallery object.</p>
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class Gallery {

    private int id;
    private String nameGallery;
    private String email;
    private String regionName;
    private String referenceShows;
    private String referenceImage;

    /**
     * Constructs an empty {@code Gallery} object.
     * This constructor is useful for creating a gallery object without initializing its attributes.
     */
    public Gallery() {
    }

    /**
     * Constructs a {@code Gallery} object with the specified attributes.
     *
     * @param id The unique identifier of the gallery.
     * @param nameGallery The name of the gallery.
     * @param email The email address of the gallery.
     * @param regionName The region name where the gallery is located.
     * @param referenceShows The reference to shows associated with the gallery.
     * @param referenceImage The reference image URL associated with the gallery.
     */
    public Gallery(int id, String nameGallery, String email, String regionName, String referenceShows, String referenceImage) {

        this.id = id;
        this.nameGallery = nameGallery;
        this.email = email;
        this.regionName = regionName;
        this.referenceShows = referenceShows;
        this.referenceImage = referenceImage;
    }

    // Getter and setter methods for each attribute are provided below.
    public int getId() {
        return id;
    }

    public String getNameGallery() {
        return nameGallery;
    }

    public String getEmail() {
        return email;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getReferenceShows() {
        return referenceShows;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameGallery(String nameGallery) {
        this.nameGallery = nameGallery;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setReferenceShows(String referenceShows) {
        this.referenceShows = referenceShows;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    /**
     * Returns a formatted string representation of the {@code Gallery} object.
     *
     * @return A formatted string containing the attributes of the gallery.
     */
    @Override
    public String toString() {
        return "Gallery{" + "\n" +
                "  id='"+ id + '\'' + "\n" +
                ", nameGallery='" + nameGallery + '\'' + "\n" +
                ", email='" + email + '\'' + "\n" +
                ", regionName='" + regionName + '\'' + "\n" +
                ", referenceImage='" + referenceImage + '\'' + "\n" +
                '}'+"\n\n";
    }
}
