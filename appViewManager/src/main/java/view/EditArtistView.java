package view;

import domain.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presenter.MainGetNationalities;
import presenter.MainManageArtists;

import java.util.List;
import java.util.Objects;

/**
 * The {@code EditArtistView} class represents the view for editing artist information.
 * It extends {@link BorderPane} and provides a user interface for updating details such as artist name, biography, birthdate,
 * death date, nationality, and slug.
 * Users can make changes and save them to the database.
 * This class is part of the graphical user interface for managing artists in the application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class EditArtistView extends BorderPane {

    private TextField textFieldArtistName;
    private TextField textFieldBirthday;
    private TextField textFieldDeathday;
    private TextField textFieldBiography;
    private TextField textFieldSlug;
    private ComboBox<String> statusMenu;
    private Spinner<Integer> yearBirthdaySpinner;
    private Spinner<Integer> yearDeathdaySpinner;
    private Label labelSuccessMessage;

    /**
     * Constructs a new {@code EditArtistView} instance for a specific artist. Initializes the layout and applies the current theme.
     *
     * @param artist The {@link Artist} object for which the details will be edited.
     * @see ManageMainView
     */
    public EditArtistView(Artist artist) {
        initialize(artist);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Initializes the layout of the {@code EditArtistView} class, including header, center, and bottom sections.
     * The layout includes elements for updating artist information such as name, biography, birthdate, death date,
     * nationality, and slug. Users can make changes and save them to the database.
     *
     * @param artist The {@link Artist} object for which the details will be edited.
     */
    private void initialize(Artist artist) {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // COMBO BOX
        List<String> nationalityList;
        ObservableList<String> nationalityOptions = FXCollections.observableArrayList(
                nationalityList = MainGetNationalities.getAllNationalities()

        );
        this.statusMenu = new ComboBox<>(nationalityOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Nacionalidade");
        statusMenu.getStyleClass().add("combo-box");
        statusMenu.setValue(artist.getNationality());

        // SPINNER
        int birthday;
        if(artist.getBirthdate() == null || artist.getBirthdate().isBlank()){
            birthday = 0;
        } else {
            birthday = Integer.parseInt(artist.getBirthdate());
        }
        int deathday;
        if(artist.getDeathdate() == null || artist.getDeathdate().isBlank()){
            deathday = 0;
        } else {
            deathday = Integer.parseInt(artist.getDeathdate());
        }

        this.yearBirthdaySpinner = new Spinner<>(0, 2999,  birthday);
        yearBirthdaySpinner.setEditable(true);

        this.yearDeathdaySpinner = new Spinner<>(0, 2999, deathday);
        yearDeathdaySpinner.setEditable(true);

        // LABELS
        Label labelArtistName = new Label("Nome do Artista (max. 100 caracteres):");
        Label labelBirthday = new Label("Ano Nascimento (ex: 1990):");
        Label labelDeathday = new Label("Ano da Morte (4 dígitos):");
        Label labelBiography = new Label("Biografia (max.10000 caracteres):");
        Label labelSlug = new Label("Slug (nome-separado-por-hifén-em-minúsculo):");

        this.textFieldArtistName = new TextField(artist.getName());
        this.textFieldBirthday = new TextField(artist.getBirthdate());
        this.textFieldDeathday = new TextField(artist.getDeathdate());
        this.textFieldBiography = new TextField(artist.getBiography());
        this.textFieldSlug = new TextField(artist.getSlug());

        textFieldBiography.setPrefSize(520,200);

        VBox vBoxArtistName = new VBox(labelArtistName,textFieldArtistName);
        VBox vBoxBirthday = new VBox(labelBirthday,yearBirthdaySpinner);
        VBox vBoxDeathday = new VBox(labelDeathday,yearDeathdaySpinner);
        VBox vBoxBiography = new VBox(labelBiography,textFieldBiography);
        VBox vBoxSlug = new VBox(labelSlug,textFieldSlug);
        HBox hBoxBirthdayDeathday = new HBox(vBoxBirthday, vBoxDeathday);
        VBox vBoxArtistInfo = new VBox(vBoxArtistName, hBoxBirthdayDeathday, vBoxSlug, vBoxBiography);
        VBox vBoxStatusNation = new VBox(statusMenu);
        HBox hBoxCenterLayout = new HBox(vBoxArtistInfo,vBoxStatusNation);

        hBoxBirthdayDeathday.setSpacing(50);
        hBoxCenterLayout.setSpacing(50);
        vBoxArtistInfo.setSpacing(10);
        vBoxArtistInfo.setPrefWidth(520);
        vBoxStatusNation.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        // Buttons
        Button saveChangesButton = new Button("Salvar Alterações");
        Button cancelButton = new Button("Cancelar");

        saveChangesButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        saveChangesButton.setOnAction(event -> {
            saveChangesArtist(artist);
            System.out.println(artist);
        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageArtistView()));

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // LABEL
        Label labelButtonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX
        HBox hBoxButtons = new HBox(saveChangesButton, cancelButton);
        hBoxButtons.setSpacing(30);
        hBoxButtons.setAlignment(Pos.CENTER_RIGHT);
        this.labelSuccessMessage = new Label("");
        VBox vBoxButtons = new VBox(hBoxButtons, labelSuccessMessage);
        vBoxButtons.setSpacing(10);

        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);

        // HBOX BOTTOM
        HBox hBoxBottomLayout = new HBox(labelButtonStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        hBoxBottomLayout.setSpacing(500);
        VBox vBoxBottomLayout = new VBox(vBoxButtons, hBoxBottomLayout);
        vBoxBottomLayout.setSpacing(20);

        this.setBottom(vBoxBottomLayout);

    }

    /**
     * Returns a {@link VBox} containing the header elements, including hyperlinks to various sections of the application
     * such as Artists, Home, Galleries, Exhibitions, and Artworks. Also includes the logo and the system label.
     *
     * @return The {@code VBox} containing the header elements.
     */
    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        Label labelManagerInfo = new Label("Sistema de Gestão Iuvenis Artem");
        labelManagerInfo.setPrefSize(550, 30);
        labelManagerInfo.getStyleClass().add("my-center-label-6 ");

        // I~A LOGO
        Image logo = new Image("Images/logo/logoIA-02.png");
        ImageView logoView = new ImageView(logo);
        logoView.preserveRatioProperty();
        logoView.setFitWidth(27);
        logoView.setFitHeight(27);
        logoView.setSmooth(true);

        // ADD PAGE HEADER ELEMENTS --
        HBox hBoxHeader = new HBox(logoView,labelManagerInfo, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtistView()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new ManageMainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibitionView()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));
        hyperlinkArtist.getStyleClass().add("actual-page-hyperlink");

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    /**
     * Sets the default size for an {@link ImageView}, adjusting the fit width and fit height.
     *
     * @param imageView The {@code ImageView} to set the default size for.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
    }

    /**
     * Saves changes made to the artist details and updates the database.
     * Displays success or error messages accordingly.
     *
     * @param artist The {@link Artist} object representing the artist whose details are being edited.
     */
    private void saveChangesArtist(Artist artist) {
        //STRINGS ATTRIBUTES FOR OBJECT
        artist.setName(textFieldArtistName.getText());
        artist.setBiography(textFieldBiography.getText());
        artist.setBirthdate(yearBirthdaySpinner.getValue().toString());
        artist.setDeathdate(yearDeathdaySpinner.getValue().toString());
        artist.setSlug(textFieldSlug.getText());
        artist.setNationality(statusMenu.getValue());


        //ADD TO DATABASE
        if (artist.getName() == null || artist.getName().trim().isEmpty()
                || artist.getName().isBlank() || Objects.equals(statusMenu.getValue(), "Nacionalidade") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            MainManageArtists.updateArtist(artist);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");

        }

    }

    /**
     * Checks if a given string represents a valid number within a specific range (0 to 2023).
     *
     * @param newValue The string to be checked for validity as a number.
     * @return {@code true} if the string is a valid number within the specified range, {@code false} otherwise.
     */
    private boolean isValidNumber(String newValue) {
            if (newValue.isEmpty()) {
                return true;
            }
            try {
                int value = Integer.parseInt(newValue);
                return value >= 0 && value <= 2023;
            } catch (NumberFormatException e) {
                return false;
            }
        }

}
