package domain;

public class Gallery {

    private int id;
    private String nameGallery;
    private String email;
    private String regionName;
    private String referenceShows;

    public Gallery() {
    }

    public Gallery(int id, String nameGallery, String email, String regionName, String referenceShows) {

        this.id = id;
        this.nameGallery = nameGallery;
        this.email = email;
        this.regionName = regionName;
        this.referenceShows = referenceShows;
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

    @Override
    public String toString() {
        return "Gallery{" +
                "nameGallery='" + nameGallery + '\'' +
                ", email='" + email + '\'' +
                ", regionName='" + regionName + '\'' +
                ", referenceShows='" + referenceShows + '\'' +
                '}';
    }
}
