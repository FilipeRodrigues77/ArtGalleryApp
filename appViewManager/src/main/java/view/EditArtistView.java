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

public class EditArtistView extends BorderPane {

    private TextField textFieldArtistName;
    private TextField textFieldBirthday;
    private TextField textFieldDeathday;
    private TextField textFieldBiography;
    private TextField textFieldSlug;
    private ComboBox<String> statusMenu;
    private Spinner<Integer> yearBirthdaySpinner;
    private Spinner<Integer> yearDeathdaySpinner;
    private Label labelSucessMensage;

    public EditArtistView(Artist artist) {
        initialize(artist);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void initialize(Artist artist) {
        // Vamos criar aqui o layout deste painel
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
            labelSucessMensage.setText("Não foi possível atualizar!");
        } else {
            MainManageArtists.updateArtist(artist);
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
