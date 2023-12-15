package view;

import locality.NationalityList;
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
import presenter.MainCreateArtists;

import java.util.Objects;

public class CreateArtistView extends BorderPane {

    private TextField textFieldArtistName;
    private TextField textFieldBirthday;
    private TextField textFieldDeathday;
    private TextField textFieldBiography;
    private TextField textFieldSlug;
    private ComboBox<String> statusMenu;
    private Spinner<Integer> yearBirthdaySpinner;
    private Spinner<Integer> yearDeathdaySpinner;
    private Label labelSucessMensage;

    public CreateArtistView() {
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
                NationalityList.getAllNationality()
        );
        this.statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Nacionalidade");
        statusMenu.getStyleClass().add("combo-box");

        // SPINNER
        this.yearBirthdaySpinner = new Spinner<>(0, 2999, 1990);
        yearBirthdaySpinner.setEditable(true);

        this.yearDeathdaySpinner = new Spinner<>(0, 2999,0);
        yearDeathdaySpinner.setEditable(true);

        // LABELS
        Label labelArtistName = new Label("Nome do Artista (max. 100 caracteres):");
        Label labelBirthday = new Label("Ano Nascimento (ex: 1990):");
        Label labelDeathday = new Label("Ano da Morte (4 dígitos):");
        Label labelBiography = new Label("Biografia (max.10000 caracteres):");
        Label labelSlug = new Label("Slug (nome-separado-por-hifén-em-minúsculo):");

        this.textFieldArtistName = new TextField();
        this.textFieldBirthday = new TextField();
        this.textFieldDeathday = new TextField();
        this.textFieldBiography = new TextField();
        this.textFieldSlug = new TextField();

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
        this.setBottom(getFooterBox());

    }

    private VBox getFooterBox (){
        // Buttons
        Button createArtistButton = new Button("Criar Artista");
        Button cancelButton = new Button("Cancelar");

        createArtistButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        createArtistButton.setOnAction(event -> createArtist());

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
        HBox hBoxButtons = new HBox(createArtistButton, cancelButton);
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
        hyperlinkArtist.getStyleClass().add("actual-page-hyperlink");

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

    public void defaultSizeArtistImage(ImageView imageView){
        imageView.setFitHeight(160); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

    private void createArtist() {
        //STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldArtistName.getText();
        String biography = textFieldBiography.getText();
        String birthdate = yearBirthdaySpinner.getValue().toString();
        String deathdate = yearDeathdaySpinner.getValue().toString();
        String slug = textFieldSlug.getText();
        String nationality = statusMenu.getValue();
        String refenceImage = "Images/Artist/ArtistSquare/square0.jpg";

        //CREATE NEW OBJECT
        Artist newArtist = new Artist();
        newArtist.setName(name);
        newArtist.setBiography(biography);
        newArtist.setBirthdate(birthdate);
        newArtist.setDeathdate(deathdate);
        newArtist.setSlug(slug);
        newArtist.setNationality(nationality);
        newArtist.setReferenceImage(refenceImage);

        //ADD TO DATABASE
        if (newArtist.getName() == null || newArtist.getName().trim().isEmpty()
            || newArtist.getName().isBlank() || Objects.equals(statusMenu.getValue(), "Nacionalidade") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSucessMensage.setText("Não foi possível atualizar!");
        } else {
            MainCreateArtists.addArtist(newArtist);
            labelSucessMensage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");

        }


    }

        // Verifica se a string é um número válido entre 0 e 2023
        private boolean isValidNumber(String newValue) {
            if (newValue.isEmpty()) {
                return true; // Aceitar campo vazio
            }
            try {
                int value = Integer.parseInt(newValue);
                return value >= 0 && value <= 2023;
            } catch (NumberFormatException e) {
                return false; // Não é um número válido
            }
        }

}
