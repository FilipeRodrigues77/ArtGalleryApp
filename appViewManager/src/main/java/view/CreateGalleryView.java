package view;

import domain.Artist;
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
import locality.RegionList;
import presenter.MainCreateArtists;
import presenter.MainCreateGallery;

import java.util.Objects;

public class CreateGalleryView extends BorderPane {

    private TextField textFieldGalleryName;
    private TextField textFieldEmail;
    private ComboBox<String> statusMenu;
    private Label labelSucessMensage;

    public CreateGalleryView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // COMBO BOX
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                RegionList.getAllRegion()
        );
        this.statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Região");
        statusMenu.getStyleClass().add("combo-box");

        // LABELS
        Label labelGalleryName = new Label("Nome da Galeria (max. 100 caracteres):");
        Label labelEmail = new Label("Email:");

        this.textFieldGalleryName = new TextField();
        this.textFieldEmail = new TextField();

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
        this.setBottom(getFooterBox());
    }

    private VBox getFooterBox (){
        // Buttons
        Button createGalleryButton = new Button("Criar Galleria");
        Button cancelButton = new Button("Cancelar");

        createGalleryButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        createGalleryButton.setOnAction(event -> createGallery());

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
        HBox hBoxButtons = new HBox(createGalleryButton, cancelButton);
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

        return vBoxBottomLayout;
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
        hyperlinkGallery.getStyleClass().add("actual-page-hyperlink");

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

    private void createGallery() {
        //STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldGalleryName.getText();
        String email = textFieldEmail.getText();
        String region = statusMenu.getValue();
        String refenceImage = "Images/Gallery/DefaultGallery.jpg";

        //CREATE NEW OBJECT
        Gallery newGallery = new Gallery();
        newGallery.setNameGallery(name);
        newGallery.setEmail(email);
        newGallery.setRegionName(region);
        newGallery.setReferenceImage(refenceImage);

        //ADD TO DATABASE
        if (newGallery.getNameGallery() == null || newGallery.getNameGallery().trim().isEmpty()
            || newGallery.getNameGallery().isBlank() || Objects.equals(statusMenu.getValue(), "Região") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSucessMensage.setText("Não foi possível atualizar!");
        } else {
            MainCreateGallery.addGallery(newGallery);
            labelSucessMensage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");

        }

    }

}
