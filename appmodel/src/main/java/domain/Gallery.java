package domain;

public class Gallery {

    private int id;
    private String nameGallery;
    private String email;
    private String regionName;
    private String referenceShows;
    private String referenceImage;

    public Gallery() {
    }

    public Gallery(int id, String nameGallery, String email, String regionName, String referenceShows, String referenceImage) {

        this.id = id;
        this.nameGallery = nameGallery;
        this.email = email;
        this.regionName = regionName;
        this.referenceShows = referenceShows;
        this.referenceImage = referenceImage;
    }

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
