package domain;

import java.time.LocalDate;

public class Exhibition {

    private int id;
    private String nameExhibition;
    private LocalDate startDate;
    private LocalDate endDate;
    private String Exdescription;
    private String Exstatus;
    private int idGallery;
    private String referenceImage;

    public Exhibition() {

    }

    public Exhibition(int id, String nameExhibition, LocalDate startDate, LocalDate endDate,
                      String exdescription, String exstatus, int idGallery, String referenceImage) {

        this.id = id;
        this.nameExhibition = nameExhibition;
        this.startDate = startDate;
        this.endDate = endDate;
        Exdescription = exdescription;
        Exstatus = exstatus;
        this.idGallery = idGallery;
        this. referenceImage = referenceImage;
    }

    public int getId() {
        return id;
    }

    public String getNameExhibition() {
        return nameExhibition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getExdescription() {
        return Exdescription;
    }

    public String getExstatus() {
        return Exstatus;
    }

    public int getIdGallery() {
        return idGallery;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameExhibition(String nameExhibition) {
        this.nameExhibition = nameExhibition;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setExdescription(String exdescription) {
        Exdescription = exdescription;
    }

    public void setExstatus(String exstatus) {
        Exstatus = exstatus;
    }

    public void setIdGallery(int idGallery) {
        this.idGallery = idGallery;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }

    @Override
    public String toString() {
        return "Exhibition{\n" +
                "  id=" + id + "\n"+
                ", nameExhibition='" + nameExhibition + '\'' + "\n"+
                ", startDate='" + startDate + '\'' + "\n"+
                ", endDate='" + endDate + '\'' + "\n"+
                ", Exdescription='" + Exdescription + '\'' + "\n"+
                ", Exstatus='" + Exstatus + '\'' + "\n"+
                ", idGallery='" + idGallery + '\'' + "\n"+
                ", referenceImage='" + referenceImage + '\'' + "\n"+
                '}' + "\n\n";
    }
}
