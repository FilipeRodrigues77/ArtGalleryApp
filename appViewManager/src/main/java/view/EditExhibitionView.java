package view;

import domain.Exhibition;
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
import presenter.MainManageExhibition;

import java.util.Objects;

/**
 * The {@code EditExhibitionView} class represents the view for editing exhibition information.
 * It extends {@link BorderPane} and provides a user interface for updating details such as exhibition name, start and end dates,
 * description, status, and reference to the associated gallery. Users can make changes and save them to the database.
 * This class is part of the graphical user interface for managing exhibitions in the application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class EditExhibitionView extends BorderPane {

    private TextField textFieldNameExhibition;
    private DatePicker datePickerStartDate;
    private DatePicker datePickerEndDate;
    private TextField textFieldExDescription;
    private ComboBox<String> statusMenu;
    private int idGallery;
    private Gallery selectedGallery;
    private ComboBox<Gallery> comboBoxGalleryRef;
    private Label labelSuccessMessage;

    /**
     * Constructs a new {@code EditExhibitionView} instance for a specific exhibition.
     * Initialises the layout and applies the current theme.
     *
     * @param exhibition The {@link Exhibition} object for which the details will be edited.
     * @see ManageMainView
     */
    public EditExhibitionView(Exhibition exhibition) {
        initialize(exhibition);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Initialises the layout of the {@code EditExhibitionView} class, including header, center, and bottom sections.
     * The layout includes elements for updating exhibition information such as name, start and end dates, description,
     * status, and the associated gallery.
     * Users can make changes and save them to the database.
     *
     * @param exhibition The {@link Exhibition} object for which the details will be edited.
     */
    private void initialize(Exhibition exhibition) {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // COMBO BOX
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "open",
                "close"
        );
        this.statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Status");
        statusMenu.getStyleClass().add("combo-box");
        statusMenu.setValue(exhibition.getExstatus());

        // LABELS
        Label labelExhibitionName = new Label("Nome da Exposição (max. 100 caracteres):");
        Label labelStartDate = new Label("Data Inicio (ex: 2016-02-23):");
        Label labelEndDate = new Label("Data Fecho (ex: 2025-02-23):");
        Label labelDescription = new Label("Descrição (max.10000 caracteres):");
        Label labelRefGallery = new Label("ID da Galeria Referente:");
        Label labelGalleryId = new Label(String.valueOf(exhibition.getIdGallery()));


        this.datePickerStartDate = new DatePicker(exhibition.getStartDate());
        this.datePickerEndDate = new DatePicker(exhibition.getEndDate());
        this.textFieldNameExhibition = new TextField(exhibition.getNameExhibition());
        this.textFieldExDescription = new TextField(exhibition.getExdescription());

        textFieldExDescription.setPrefSize(520,200);

        VBox vBoxGallery = new VBox(labelRefGallery, labelGalleryId);
        VBox vBoxExhibitionName = new VBox(labelExhibitionName, textFieldNameExhibition);
        VBox vBoxStartDate = new VBox(labelStartDate, datePickerStartDate);
        VBox vBoxEndDate = new VBox(labelEndDate,datePickerEndDate);
        VBox vBoxDescription = new VBox(labelDescription, textFieldExDescription);
        VBox vBoxStatus = new VBox(statusMenu);
        HBox hBoxDate = new HBox(vBoxStartDate, vBoxEndDate);
        VBox vBoxExhibitionInfo = new VBox(vBoxExhibitionName, hBoxDate, vBoxStatus, vBoxDescription);
        VBox vBoxStatusGallery = new VBox(vBoxGallery);
        HBox hBoxCenterLayout = new HBox(vBoxExhibitionInfo,vBoxStatusGallery);

        hBoxDate.setSpacing(50);
        hBoxCenterLayout.setSpacing(50);
        vBoxExhibitionInfo.setSpacing(10);
        vBoxExhibitionInfo.setPrefWidth(520);
        vBoxStatusGallery.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        // Buttons
        Button saveChangesButton = new Button("Salvar Alterações");
        Button cancelButton = new Button("Cancelar");

        saveChangesButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        saveChangesButton.setOnAction(event -> {
            saveChangesExhibition(exhibition);
            System.out.println(exhibition);
        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageExhibitionView()));

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
     * such as Artists, Home, Galleries, Exhibitions, and Artworks.
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
        hyperlinkExhibition.getStyleClass().add("actual-page-hyperlink");

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
     * Handles the action to save changes made to the exhibition details and updates the database.
     * Displays success or error messages accordingly.
     *
     * @param exhibition The {@link Exhibition} object representing the exhibition whose details are being edited.
     */
    private void saveChangesExhibition(Exhibition exhibition) {
        //STRINGS ATTRIBUTES FOR OBJECT
        exhibition.setNameExhibition(textFieldNameExhibition.getText());
        exhibition.setExdescription(textFieldExDescription.getText());
        exhibition.setStartDate(datePickerStartDate.getValue());
        exhibition.setEndDate(datePickerEndDate.getValue());
        exhibition.setExstatus(statusMenu.getValue());

        //ADD TO DATABASE (FAZER OS TESTES PARA TRATAMENTO DA INFORMAÇÃO)
        if (exhibition.getNameExhibition() == null || exhibition.getNameExhibition().trim().isEmpty()
            || exhibition.getNameExhibition().isBlank() || Objects.equals(statusMenu.getValue(), "Status") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank() ||
                exhibition.getStartDate() == null || exhibition.getEndDate() == null) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            MainManageExhibition.updateExhibition(exhibition);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }


    }

}
