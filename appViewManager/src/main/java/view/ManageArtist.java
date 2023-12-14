package view;

import domain.Artist;
import domain.Artwork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import presenter.*;

import java.util.List;

public class ManageArtist extends BorderPane {
    private ObservableList<Artist> observableListArtist;
    private ListView<Artist> listViewArtist;

    public ManageArtist() {

        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {

        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // CREATE A LIST VIEW
        List<Artist> listArtists = MainGetArtists.getAllArtists();
        this.observableListArtist = FXCollections.observableArrayList(listArtists);
        this.listViewArtist = new ListView<>(observableListArtist);

        // BUTTONS

        Button createButton = new Button("Criar");
        createButton.setOnAction(e -> getScene().setRoot(new CreateArtistView()));

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> editSelectedArtist());

        /*editButton.setOnAction(e -> {
            Person person = list.getSelectionModel().getSelectedItem();
            if (person != null) {
                list.getScene().setRoot(new PersonViewer(persons, person));
            }
        });

         */
        //Botão apagar
        Button removeButton = new Button("Apagar");
        /*removeButton.setOnAction(e -> {
            Person person = list.getSelectionModel().getSelectedItem();
            if (person != null) {
                personsList.remove(person);
//                persons.removePerson(person);
                persons.remove(person);
            }

         */
        createButton.getStyleClass().add("button-modern");
        editButton.getStyleClass().add("button-modern");
        removeButton.getStyleClass().add("button-modern");
        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(createButton, editButton, removeButton);
        hBoxMenu.setSpacing(50);
        hBoxMenu.setAlignment(Pos.CENTER);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        this.setCenter(listViewArtist);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());

    }

    private HBox getFooterBox (){

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

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);

        // HBOX BOTTOM
        HBox hBoxBottomLayout = new HBox(labelButtonStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        hBoxBottomLayout.setSpacing(500);

        return hBoxBottomLayout;
    }

    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        String searchOrigText = "Procurar por nome do artista";
        TextField textFieldSearch = new TextField(searchOrigText);
        setOriginalDescription(textFieldSearch,searchOrigText);
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

        // I~A LOGO
        Image logo = new Image("Images/logo/logoIA-02.png");
        ImageView logoView = new ImageView(logo);
        logoView.preserveRatioProperty();
        logoView.setFitWidth(27);
        logoView.setFitHeight(27);
        logoView.setSmooth(true);

        // SEARCH ICON
        Image searchIcon = new Image("Icons/searchIcon-03.png");
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.preserveRatioProperty();
        searchIconView.setFitWidth(20);
        searchIconView.setFitHeight(20);
        searchIconView.setSmooth(true);
        searchIconView.setOnMouseClicked(e -> handleSearchIconSelection(textFieldSearch));

        // ADD PAGE HEADER ELEMENTS --
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new ManageMainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new ManageGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibition()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new ManageArtwork()));
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

    private void handleSearchIconSelection(TextField textFieldSearch ){
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByName(searchText);
            if(artists != null){
                ObservableList<Artist> observableListArtistFiltered = FXCollections.observableArrayList(artists);
                ListView<Artist> listViewArtistFiltered = new ListView<>(observableListArtistFiltered);
                this.setCenter(listViewArtistFiltered);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }

        }
    }

    private void setArtistLabelsOnAction(Artist artist){
        getScene().setRoot(doArtistDetailsLayout(artist));
    }

    public BorderPane doArtistDetailsLayout(Artist artist) {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ARTWORK IMAGE
        String imageArtwork = artist.getReferenceImage();
        ImageView imageViewArtist = new ImageView(new Image(imageArtwork));
        defaultSizeArtistImage(imageViewArtist);

        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------

        setTop(getHeaderBox());

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // LABELS
        Label labelArtistName = new Label(artist.getName());
        Label labelNationality = new Label(artist.getNationality());
        Label labelBirthday = new Label(artist.getBirthdate());
        Label labelDeathday = new Label(artist.getDeathdate());
        Label labelHeaderAboutArtist = new Label("Sobre o Artista");
        Label labelBiography = new Label(artist.getBiography());
        labelBiography.setWrapText(true);

        labelArtistName.getStyleClass().add("my-center-label-1");

        ScrollPane scrollPaneBiography = new ScrollPane(labelBiography);
        scrollPaneBiography.getStyleClass().add("scroll-pane");

        VBox vBoxArtistInfo = new VBox(labelArtistName, labelNationality, labelBirthday, labelDeathday);
        VBox vBoxArtistImage = new VBox(imageViewArtist);
        HBox hBoxCenterLayout = new HBox(vBoxArtistImage, vBoxArtistInfo);
        hBoxCenterLayout.setSpacing(50);
        VBox vBoxGlobalCenterLayout = new VBox(hBoxCenterLayout, labelHeaderAboutArtist, scrollPaneBiography);
        vBoxGlobalCenterLayout.setSpacing(20);


        List<Artwork> artistsArtworks = MainGetArtworks.getArtworksByArtistId(artist.getId());
        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildThisArtisArtworkGrid(artistsArtworks));

        VBox vBoxCenterLayout = new VBox(vBoxGlobalCenterLayout, scrollPane);
        vBoxCenterLayout.setSpacing(20);
        setCenter(vBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS -------------------------------------------------

        return this;
    }

    private GridPane buildThisArtisArtworkGrid(List<Artwork> artworkList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        grid.getStyleClass().add("grid-pane");

        for (int i = 0; i < artworkList.size(); i++) {

            int maxTextLength = 23;

            Artwork artwork = artworkList.get(i);
            String imageRef = artwork.getReferenceImage().replace("{imageVersion}","square");
            Image image = new Image(imageRef);
            ImageView imageViewArtwork = new ImageView(image);
            defaultSizeArtistImage(imageViewArtwork);

            imageViewArtwork.setOnMouseClicked(e-> getScene().setRoot(new SceneArtwork().doDetailsLayout(artwork)));
            // Create a new VBox for each iteration
            String artworkName = artwork.getName();
            Hyperlink hyperArtworkName;

            if (artworkName.length() < maxTextLength){
                hyperArtworkName = new Hyperlink(artworkName);
            } else{
                hyperArtworkName = new Hyperlink(artworkName.substring(0,maxTextLength)+"...");
            }

            hyperArtworkName.setOnAction(e-> getScene().setRoot(new SceneArtwork().doDetailsLayout(artwork)));

            String galleryName = MainGetGalleries.getGalleryById(artwork.getIdGallery()).getNameGallery();
            Hyperlink hyperGalleryName;
            if (galleryName.length() < maxTextLength){
                hyperGalleryName = new Hyperlink(galleryName);
            } else{
                hyperGalleryName = new Hyperlink(galleryName.substring(0,maxTextLength)+"...");
            }

            String price = String.valueOf(artwork.getPrice());
            Hyperlink hyperPrice = new Hyperlink(price);
            VBox vBoxLabelArtwork = new VBox(hyperArtworkName, hyperGalleryName, hyperPrice);

            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtwork, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);


        }

        return grid;
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

    private void editSelectedArtist() {
        Artist selectedArtist = listViewArtist.getSelectionModel().getSelectedItem();

        if (selectedArtist != null) {
            // Chame o método show da EditArtistView para editar o artista
            getScene().setRoot(new EditArtistView(selectedArtist));
            // Atualize a lista após a edição
            listViewArtist.refresh();
        }
    }

}
