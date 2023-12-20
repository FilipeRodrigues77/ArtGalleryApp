package domain;

/**
 * The {@code Artwork} class represents an artwork with various attributes such as ID, price, dimensions,
 * name, medium, creation date, category, collecting institution, slug reference to the artist,
 * reference image URL, gallery ID, and artist ID.
 *
 * <p>An artwork object can be instantiated either with an empty constructor or with a constructor
 * that initializes its attributes.</p>
 *
 * <p>This class also provides getter and setter methods for accessing and modifying the attributes of an artwork.</p>
 *
 * <p>The {@code toString()} method is overridden to provide a formatted string representation of the artwork object.</p>
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class Artwork {

    private int id;
    private double price;
    private String dimensionCm;
    private String dimensionIN;
    private String name;
    private String medium;
    private String creationDate;
    private String category;
    private String collectingInstitution;
    private String slugReferenceArtist;
    private String referenceImage;
    private int idGallery;
    private int idArtist;

    /**
     * Constructs an empty {@code Artwork} object.
     * This constructor is useful for creating an artwork object without initializing its attributes.
     */
    public Artwork() {
    }

    /**
     * Constructs an {@code Artwork} object with the specified attributes.
     *
     * @param id The unique identifier of the artwork.
     * @param price The price of the artwork.
     * @param dimensionCm The dimensions of the artwork in centimeters.
     * @param dimensionIN The dimensions of the artwork in inches.
     * @param name The name of the artwork.
     * @param medium The medium used for creating the artwork.
     * @param creationDate The creation date of the artwork.
     * @param category The category of the artwork.
     * @param collectingInstitution The collecting institution associated with the artwork.
     * @param slugReferenceArtist The slug reference to the artist associated with the artwork.
     * @param referenceImage The reference image URL associated with the artwork.
     * @param idGallery The unique identifier of the gallery associated with the artwork.
     * @param idArtist The unique identifier of the artist associated with the artwork.
     */
    public Artwork(int id, double price, String dimensionCm, String dimensionIN,
                   String name, String medium, String creationDate, String category,
                   String collectingInstitution, String slugReferenceArtist,
                   String referenceImage, int idGallery, int idArtist) {

        this.id = id;
        this.price = price;
        this.dimensionCm = dimensionCm;
        this.dimensionIN = dimensionIN;
        this.name = name;
        this.medium = medium;
        this.creationDate = creationDate;
        this.category = category;
        this.collectingInstitution = collectingInstitution;
        this.slugReferenceArtist = slugReferenceArtist;
        this.referenceImage = referenceImage;
        this.idGallery = idGallery;
        this.idArtist = idArtist;
    }

    // Getter and setter methods for each attribute are provided below.
    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getDimensionCm() {
        return dimensionCm;
    }

    public String getDimensionIN() {
        return dimensionIN;
    }

    public String getName() {
        return name;
    }

    public String getMedium() {
        return medium;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCategory() {
        return category;
    }

    public String getCollectingInstitution() {
        return collectingInstitution;
    }

    public String getSlugReferenceArtist() {
        return slugReferenceArtist;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public int getIdGallery() {
        return idGallery;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDimensionCm(String dimensionCm) {
        this.dimensionCm = dimensionCm;
    }

    public void setDimensionIN(String dimensionIN) {
        this.dimensionIN = dimensionIN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCollectingInstitution(String collectingInstitution) {
        this.collectingInstitution = collectingInstitution;
    }

    public void setSlugReferenceArtist(String slugReferenceArtist) {
        this.slugReferenceArtist = slugReferenceArtist;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    public void setIdGallery(int idGallery) {
        this.idGallery = idGallery;
    }

    /**
     * Returns a formatted string representation of the {@code Artwork} object.
     *
     * @return A formatted string containing the attributes of the artwork.
     */
    @Override
    public String toString() {
        return "Artwork{\n" +
                "  id=" + id + "\n" +
                ", price=" + price + "\n" +
                ", dimensionCm='" + dimensionCm + '\'' +"\n" +
                ", dimensionIN='" + dimensionIN + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", medium='" + medium + '\'' + "\n" +
                ", creationDate='" + creationDate + '\'' + "\n" +
                ", category='" + category + '\'' + "\n" +
                ", collectingInstitution='" + collectingInstitution + '\'' + "\n" +
                ", slugReferenceArtist='" + slugReferenceArtist + '\'' + "\n" +
                ", referenceImage='" + referenceImage + '\'' + "\n" +
                ", idGallery='" + idGallery + '\'' + "\n" +
                ", idArtist='" + idArtist + '\'' + "\n" +
                '}' + "\n\n";
    }
}
