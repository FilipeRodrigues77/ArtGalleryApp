package view;

import domain.Artist;
import domain.Artwork;
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
import presenter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditArtworkView extends BorderPane {

    private Spinner<Double> priceSpinner;
    private TextField textFieldDimensionCm;
    private TextField textFieldDimensionIN;
    private TextField textFieldArtworkname;
    private TextField textFieldNewMedium;
    private TextField textFieldNewCategory;
    private Spinner<Integer> spinnerCreationDate;
    private List<Artwork> artworkList;
    private List<Artist> artistsList;
    private List<Gallery> galleriesList;
    private Gallery referenceGallery;
    private int idGallery;
    private int idArtist;
    private Label labelSucessMensage;

    public EditArtworkView(Artwork SelectedArtwork) {
        initialize(SelectedArtwork);
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void initialize(Artwork selectedArtwork) {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // SPINNER
        this.priceSpinner = new Spinner<>(0.00, 10000000.00, selectedArtwork.getPrice());
        priceSpinner.setEditable(true);

        int initialValueDate;
        if (selectedArtwork.getCreationDate() == null){
            initialValueDate = 0;
        } else {
            initialValueDate = Integer.parseInt(selectedArtwork.getCreationDate());
        }
        this.spinnerCreationDate = new Spinner<>(0,2999,initialValueDate);
        spinnerCreationDate.setEditable(true);

        // LISTS
        this.artworkList = MainGetArtworks.getAllArtworks();
        this.artistsList = MainGetArtists.getAllArtists();
        this.galleriesList = MainGetGalleries.getAllGalleries();

        // COMBO BOX
        ComboBox<String> comboBoxMedium = new ComboBox<>(createOptionsMenu(artworkList,"medium"));
        comboBoxMedium.setMaxWidth(300);
        comboBoxMedium.setOnAction(e-> handleMediumSelection(comboBoxMedium, artworkList));
        comboBoxMedium.setValue(selectedArtwork.getMedium());

        ComboBox<String> comboBoxCategory = new ComboBox<>(createOptionsMenu(artworkList,"category"));
        comboBoxCategory.setMaxWidth(300);
        comboBoxCategory.setOnAction(e-> handleCategorySelection(comboBoxCategory, artworkList));
        comboBoxCategory.setValue(selectedArtwork.getCategory());

        ComboBox<String> comboBoxArtist = new ComboBox<>(createArtistMenu(artistsList,"artist"));
        comboBoxArtist.setMaxWidth(200);
        comboBoxArtist.setOnAction(e-> handleArtistSelection(comboBoxArtist, artistsList));
        comboBoxArtist.setVisibleRowCount(9);

        ComboBox<Gallery> comboBoxGalleryRef = new ComboBox<>();
        comboBoxGalleryRef.getItems().addAll(galleriesList);
        comboBoxGalleryRef.setOnAction(e -> {
                    this.referenceGallery = comboBoxGalleryRef.getSelectionModel().getSelectedItem();
                    if (referenceGallery != null) {
                        this.idGallery = referenceGallery.getId();
                    }
                });
        comboBoxGalleryRef.setVisibleRowCount(3);

        // LABELS
        Label labelArtworkName = new Label("Nome da Obra de Arte (max. 100 caracteres):");
        Label labelDimensionIn = new Label("Dimensões em In (ex: 12.1 x 12.1 in):");
        Label labelDimensionCm = new Label("Dimensões em Cm (ex: 30,5 x 30,5 cm):");
        Label labelRefGallery = new Label("Galeria referente");
        Label labelRefArtist = new Label("Artista referente");
        Label labelCategory = new Label("Categoria referente");
        Label labelRefMedium = new Label("Método referente");
        Label labelpriceArtwork = new Label("Preço da obra (ex. 500,00):");
        Label labelCreationDate = new Label("Ano de Criação (ex. 2023):");

        this.textFieldArtworkname = new TextField(selectedArtwork.getName());
        this.textFieldDimensionCm = new TextField(selectedArtwork.getDimensionCm());
        this.textFieldDimensionIN = new TextField(selectedArtwork.getDimensionIN());
        this.textFieldNewMedium = new TextField();
        this.textFieldNewCategory = new TextField();

        textFieldNewMedium.setPrefWidth(520);
        textFieldNewMedium.setDisable(true);
        textFieldNewCategory.setPrefWidth(520);
        textFieldNewCategory.setDisable(true);

        VBox vBoxArtworkName = new VBox(labelArtworkName,textFieldArtworkname);
        VBox vBoxDimensionCm = new VBox(labelDimensionCm,textFieldDimensionCm);
        VBox vBoxDimensionIn = new VBox(labelDimensionIn,textFieldDimensionIN);
        VBox vBoxPriceArtwork = new VBox(labelpriceArtwork, priceSpinner);
        VBox vBoxCreateDate = new VBox(labelCreationDate, spinnerCreationDate);
        VBox vBoxGallery = new VBox(labelRefGallery, comboBoxGalleryRef);
        VBox vBoxRefArtistId = new VBox(labelRefArtist,comboBoxArtist);
        VBox vBoxCategory = new VBox(labelCategory, comboBoxCategory);
        VBox vBoxRefMedium = new VBox(labelRefMedium, comboBoxMedium);

        HBox hBoxNewMedium = new HBox(textFieldNewMedium);
        HBox hBoxNewCategory = new HBox(textFieldNewCategory);
        HBox hBoxPriceAndDate = new HBox(vBoxPriceArtwork,vBoxCreateDate);
        HBox hBoxArtistRef = new HBox(vBoxRefArtistId);
        HBox hBoxDimensions = new HBox(vBoxDimensionCm, vBoxDimensionIn);

        VBox vBoxMediumWithNewMedium = new VBox(vBoxRefMedium, hBoxNewMedium);
        VBox vBoxCategoryWithNewCategory = new VBox(vBoxCategory, hBoxNewCategory);
        VBox vBoxArtworkInfo = new VBox(vBoxArtworkName, hBoxDimensions, hBoxPriceAndDate, vBoxMediumWithNewMedium, vBoxCategoryWithNewCategory);
        VBox vBoxRefArtistGallery = new VBox(vBoxGallery,hBoxArtistRef);

        HBox hBoxCenterLayout = new HBox(vBoxArtworkInfo,vBoxRefArtistGallery);

        vBoxMediumWithNewMedium.setSpacing(10);
        vBoxCategoryWithNewCategory.setSpacing(10);
        hBoxPriceAndDate.setSpacing(50);
        hBoxDimensions.setSpacing(40);
        hBoxCenterLayout.setSpacing(50);
        vBoxArtworkInfo.setSpacing(10);
        vBoxArtworkInfo.setPrefWidth(520);
        vBoxRefArtistGallery.setSpacing(15);

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // Buttons
        Button saveChangesButton = new Button("Salvar alterações");
        Button cancelButton = new Button("Cancelar");

        saveChangesButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        saveChangesButton.setOnAction(event -> {
            saveChangesArtwork(selectedArtwork);
            System.out.println(selectedArtwork);
        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));

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
        hyperLinkArtwork.getStyleClass().add("actual-page-hyperlink");

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

    private ObservableList<String> createOptionsMenu (List<Artwork>artworkList, String field){

        List<String> testing = new ArrayList<>();
        testing.add("Novo");
        String menuField = field.toLowerCase();

        switch (menuField){
            case "category": for(Artwork art : artworkList){
                String option = art.getCategory();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
                break;
            case "medium": for(Artwork art : artworkList){
                String option = art.getMedium();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
                break;
        }
        return FXCollections.observableArrayList(testing);
    }
    private void handleMediumSelection (ComboBox<String> mediumMenu, List<Artwork> artworkMediumList){
        String selectedMedium = mediumMenu.getSelectionModel().getSelectedItem();
        if(selectedMedium != null) {
            if (selectedMedium.equals("Novo")) {
                textFieldNewMedium.setText("");
                textFieldNewMedium.setDisable(false);
            }
            if (!selectedMedium.equals("Novo")) {
                textFieldNewMedium.setText(selectedMedium);
                textFieldNewMedium.setDisable(true);
            }
        }
    }

    private void handleCategorySelection (ComboBox<String> categoryMenu, List<Artwork> artworkCategoryList) {
        String selectedCategory = categoryMenu.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            if (selectedCategory.equals("Novo")){
                textFieldNewCategory.setText("");
                textFieldNewCategory.setDisable(false);
            }
            if (!selectedCategory.equals("Novo")){
                textFieldNewCategory.setText(selectedCategory);
                textFieldNewCategory.setDisable(true);
            }
        }
    }
    private void handleArtistSelection (ComboBox<String> comboBoxArtistRef, List<Artist> artistsList) {
        String selectedArtist = comboBoxArtistRef.getSelectionModel().getSelectedItem();
        if (selectedArtist != null) {
            this.idArtist = extractNumber(selectedArtist);
        }
    }
    private ObservableList<String> createArtistMenu (List<Artist>artistsList, String artistField){

        List<String> testing = new ArrayList<>();
        String menuArtistField = artistField.toLowerCase();

        switch (menuArtistField){
            case "artist": for(Artist artist : artistsList){
                String option = artist.getName();
                int optionId = artist.getId();
                String fullOption = optionId + " - " + option;
                if(!testing.contains(fullOption)){
                    testing.add(fullOption);
                }
            }
                break;
        }
        return FXCollections.observableArrayList(testing);
    }
    public static int extractNumber(String input) {
        Pattern pattern = Pattern.compile("^\\d+");
        // OBJECT INPUT
        Matcher matcher = pattern.matcher(input);
        // VERIFY CORRESPONDENCE
        if (matcher.find()) {
            // GET STRING NUMBER AND CONVERT TO INT
            String numeroString = matcher.group();
            return Integer.parseInt(numeroString);
        } else {
            throw new IllegalArgumentException("Nenhum número encontrado na string");
        }
    }

    private void saveChangesArtwork(Artwork selectedArtwork) {
        //STRINGS ATTRIBUTES FOR OBJECT
        selectedArtwork.setName(textFieldArtworkname.getText());
        selectedArtwork.setPrice(priceSpinner.getValue());
        selectedArtwork.setDimensionCm(textFieldDimensionCm.getText());
        selectedArtwork.setDimensionIN(textFieldDimensionIN.getText());
        selectedArtwork.setMedium(textFieldNewMedium.getText());
        selectedArtwork.setCreationDate(spinnerCreationDate.getValue().toString());
        selectedArtwork.setCategory(textFieldNewCategory.getText());
        selectedArtwork.setIdGallery(idGallery);
        selectedArtwork.setIdArtist(idArtist);

        //ADD TO DATABASE (FAZER OS TESTES PARA TRATAMENTO DA INFORMAÇÃO)
        if (selectedArtwork.getName() == null || selectedArtwork.getName().trim().isEmpty() || selectedArtwork.getName().isBlank()
                || idGallery == 0 || idArtist == 0) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSucessMensage.setText("Não foi possível atualizar!");
        } else {
            System.out.println(selectedArtwork);
            MainManageArtwork.updateArtwork(selectedArtwork);
            labelSucessMensage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }

    }

}
