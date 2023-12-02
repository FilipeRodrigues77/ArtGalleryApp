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
    private String referencePartner;
    private String referenceArtist;
    private String referenceImage;
    private int idGallery;


    public Artwork() {

    }

    public Artwork(int id, double price, String dimensionCm, String dimensionIN, String name, String medium,
                   String creationDate, String category, String collectingInstitution, String slugReferenceArtist,
                   String referencePartner, String referenceArtist, String referenceImage, int idGallery) {

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
        this.referencePartner = referencePartner;
        this.referenceArtist = referenceArtist;
        this.referenceImage = referenceImage;
        this.idGallery = idGallery;
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

    public String getReferencePartner() {
        return referencePartner;
    }

    public String getReferenceArtist() {
        return referenceArtist;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public int getIdGallery() {
        return idGallery;
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

    public void setReferencePartner(String referencePartner) {
        this.referencePartner = referencePartner;
    }

    public void setReferenceArtist(String referenceArtist) {
        this.referenceArtist = referenceArtist;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    public void setIdGallery(int idGallery) {
        this.idGallery = idGallery;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id=" + id +
                ", price=" + price +
                ", dimensionCm='" + dimensionCm + '\'' +
                ", dimensionIN='" + dimensionIN + '\'' +
                ", name='" + name + '\'' +
                ", medium='" + medium + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", category='" + category + '\'' +
                ", collectingInstitution='" + collectingInstitution + '\'' +
                ", slugReferenceArtist='" + slugReferenceArtist + '\'' +
                ", referencePartner='" + referencePartner + '\'' +
                ", referenceArtist='" + referenceArtist + '\'' +
                ", referenceImage='" + referenceImage + '\'' +
                ", idGallery='" + idGallery + '\'' +
                '}';
    }
}
