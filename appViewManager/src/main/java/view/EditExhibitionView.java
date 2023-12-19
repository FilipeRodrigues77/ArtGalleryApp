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
import presenter.MainGetGalleries;
import presenter.MainManageExhibition;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EditExhibitionView extends BorderPane {

    private TextField textFieldnameExhibition;
    private DatePicker datePickerstartDate;
    private DatePicker datePickerEndDate;
    private TextField textFieldExdescription;
    private ComboBox<String> statusMenu;
    private int idGallery;
    private Gallery selectedGallery;
    private ComboBox<Gallery> comboBoxGalleryRef;
    private Label labelSucessMensage;

    public EditExhibitionView(Exhibition exhibition) {
        initialize(exhibition);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

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


        this.datePickerstartDate = new DatePicker(exhibition.getStartDate());
        this.datePickerEndDate = new DatePicker(exhibition.getEndDate());
        this.textFieldnameExhibition = new TextField(exhibition.getNameExhibition());
        this.textFieldExdescription = new TextField(exhibition.getExdescription());

        textFieldExdescription.setPrefSize(520,200);

        VBox vBoxGallery = new VBox(labelRefGallery, labelGalleryId);
        VBox vBoxExhibitionName = new VBox(labelExhibitionName,textFieldnameExhibition);
        VBox vBoxStartDate = new VBox(labelStartDate,datePickerstartDate);
        VBox vBoxEndDate = new VBox(labelEndDate,datePickerEndDate);
        VBox vBoxDescription = new VBox(labelDescription,textFieldExdescription);
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
        this.labelSucessMensage = new Label("");
        VBox vBoxButtons = new VBox(hBoxButtons, labelSucessMensage);
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

    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        Label labelManagerInfo = new Label("Sistema de Gestão Iuvennis Art");
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

    private void setOriginalDescription(TextField textField, String originalText){

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (textField.getText().isEmpty()) {
                    textField.setText(originalText);
                }
            }
        });
    }

    // image treatment
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    private void saveChangesExhibition(Exhibition exhibition) {
        //STRINGS ATTRIBUTES FOR OBJECT
        exhibition.setNameExhibition(textFieldnameExhibition.getText());
        exhibition.setExdescription(textFieldExdescription.getText());
        exhibition.setStartDate(datePickerstartDate.getValue());
        exhibition.setEndDate(datePickerEndDate.getValue());
        exhibition.setExstatus(statusMenu.getValue());

        //ADD TO DATABASE (FAZER OS TESTES PARA TRATAMENTO DA INFORMAÇÃO)
        if (exhibition.getNameExhibition() == null || exhibition.getNameExhibition().trim().isEmpty()
            || exhibition.getNameExhibition().isBlank() || Objects.equals(statusMenu.getValue(), "Status") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank() ||
                exhibition.getStartDate() == null || exhibition.getEndDate() == null) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSucessMensage.setText("Não foi possível atualizar!");
        } else {
            MainManageExhibition.updateExhibition(exhibition);
            labelSucessMensage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }


    }

}
