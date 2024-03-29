package view;

import domain.Gallery;
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
import presenter.MainGetRegions;
import presenter.MainManageGallery;

import java.util.List;
import java.util.Objects;

/**
 * The {@code EditGalleryView} class represents the view for editing gallery information.
 * It extends {@link BorderPane} and provides a user interface for updating details such as gallery name, email, and region.
 * Users can make changes and save them to the database. This class is part of the graphical user interface for managing galleries
 * in the application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class EditGalleryView extends BorderPane {

    private TextField textFieldGalleryName;
    private TextField textFieldEmail;
    private ComboBox<String> statusMenu;
    private Label labelSuccessMessage;

    /**
     * Constructs a new {@code EditGalleryView} instance for a specific gallery.
     * Initialises the layout and applies the current theme.
     *
     * @param gallery The {@link Gallery} object for which the details will be edited.
     * @see ManageMainView
     */
    public EditGalleryView(Gallery gallery) {
        initialize(gallery);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Initializes the layout of the {@code EditGalleryView} class, including header, center, and bottom sections.
     * The layout includes elements for updating gallery information such as name, email, and region.
     * Users can make changes and save them to the database.
     *
     * @param gallery The {@link Gallery} object for which the details will be edited.
     */
    private void initialize(Gallery gallery) {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // COMBO BOX
        List<String> regionList;
        ObservableList<String> regionOptions = FXCollections.observableArrayList(
                regionList = MainGetRegions.getAllRegions()
        );
        this.statusMenu = new ComboBox<>(regionOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Região");
        statusMenu.getStyleClass().add("combo-box");
        statusMenu.setValue(gallery.getRegionName());

        // LABELS
        Label labelGalleryName = new Label("Nome da Galeria (max. 100 caracteres):");
        Label labelEmail = new Label("Email:");

        this.textFieldGalleryName = new TextField(gallery.getNameGallery());
        this.textFieldEmail = new TextField(gallery.getEmail());

        VBox vBoxArtistName = new VBox(labelGalleryName,textFieldGalleryName);
        VBox vBoxEmail = new VBox(labelEmail,textFieldEmail);
        VBox vBoxArtistInfo = new VBox(vBoxArtistName, vBoxEmail);
        VBox vBoxStatusRegion = new VBox(statusMenu);
        HBox hBoxCenterLayout = new HBox(vBoxArtistInfo,vBoxStatusRegion);

        hBoxCenterLayout.setSpacing(50);
        vBoxArtistInfo.setSpacing(10);
        vBoxArtistInfo.setPrefWidth(520);
        vBoxStatusRegion.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        // Buttons
        Button saveChangesButton = new Button("Salvar Alterações");
        Button cancelButton = new Button("Cancelar");

        saveChangesButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        saveChangesButton.setOnAction(event -> {
            saveChangesGallery(gallery);
            System.out.println(gallery);
        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));

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
     * such as Artists, Home, Galleries, Exhibitions, and Artworks and others.
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
        hyperlinkGallery.getStyleClass().add("actual-page-hyperlink");

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
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    /**
     * Handles the action to save changes made to the gallery details and updates the database.
     * Displays success or error messages accordingly.
     *
     * @param gallery The {@link Gallery} object representing the gallery whose details are being edited.
     */
    private void saveChangesGallery(Gallery gallery) {
        //STRINGS ATTRIBUTES FOR OBJECT

        gallery.setNameGallery(textFieldGalleryName.getText());
        gallery.setEmail(textFieldEmail.getText());
        gallery.setRegionName(statusMenu.getValue());

        //ADD TO DATABASE
        if (gallery.getNameGallery() == null || gallery.getNameGallery().trim().isEmpty()
                || gallery.getNameGallery().isBlank() || Objects.equals(statusMenu.getValue(), "Região") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            MainManageGallery.updateGallery(gallery);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }

    }

}
