package domain;

import com.google.gson.annotations.SerializedName;

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




    public Artwork() {

    }

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

    @Override
    public String toString() {
        return "Artwork{\n" +
                "  id=" + id + "\n" +
                ", price=" + price +
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
