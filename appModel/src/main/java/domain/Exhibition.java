package domain;

import java.time.LocalDate;

/**
 * The {@code Exhibition} class represents an exhibition with various attributes such as ID, name,
 * start date, end date, description, status, gallery ID, and reference image URL.
 *
 * <p>An exhibition object can be instantiated either with an empty constructor or with a constructor
 * that initializes its attributes.</p>
 *
 * <p>This class also provides getter and setter methods for accessing and modifying the attributes of an exhibition.</p>
 *
 * <p>The {@code toString()} method is overridden to provide a formatted string representation of the exhibition object.</p>
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
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

    /**
     * Constructs an {@code Exhibition} object with the specified attributes.
     *
     * @param id The unique identifier of the exhibition.
     * @param nameExhibition The name of the exhibition.
     * @param startDate The start date of the exhibition.
     * @param endDate The end date of the exhibition.
     * @param exdescription The description of the exhibition.
     * @param exstatus The status of the exhibition.
     * @param idGallery The unique identifier of the gallery associated with the exhibition.
     * @param referenceImage The reference image URL associated with the exhibition.
     */
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

    // Getter and setter methods for each attribute are provided below.
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


    /**
     * Returns a formatted string representation of the {@code Exhibition} object.
     *
     * @return A formatted string containing the attributes of the exhibition.
     */
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
