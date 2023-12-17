package view;

import domain.Artist;
import domain.Artwork;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import presenter.*;

import java.util.List;

/**
 * The {@code SceneArtist} class extends {@code BorderPane} and represents the graphical user interface
 * for displaying and interacting with information related to artists in an art application.
 * It includes features such as filtering artists by nationality, birthdate, and deathdate, as well as
 * providing details about a specific artist and their associated artworks.
 * This class extends the JavaFX {@code BorderPane} for layout management.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class SceneArtist extends BorderPane {

    /**
     * Constructs a new {@code SceneArtist} object.
     * It initialises the layout and sets up the current THeme.
     */
    public SceneArtist() {
        doLayout();
        MainView mainView = new MainView();
        String cssTheme = mainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Initialises the layout of the scene, including header, center, and bottom sections.
     * Handles various user interactions such as searching and filtering artists.
     */
    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        String nacionalOrigText = "Nacionalidade (inglês)";
        TextField textFieldSearchByNation = new TextField(nacionalOrigText);
        setOriginalDescription(textFieldSearchByNation,nacionalOrigText);
        textFieldSearchDefault(textFieldSearchByNation);
        textFieldSearchByNation.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleNationalSelection(textFieldSearchByNation);
            }
        });

        String birthdateOrigText = "Ano nascimento, ex: 1886";
        TextField textFieldSearchByBirthdate = new TextField(birthdateOrigText);
        setOriginalDescription(textFieldSearchByBirthdate,birthdateOrigText);
        textFieldSearchDefault(textFieldSearchByBirthdate);
        textFieldSearchByBirthdate.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleBirthdateSelection(textFieldSearchByBirthdate);
            }
        });

        String deathdateOrigText = "Ano de morte, ex: 1956";
        TextField textFieldSearchByDeathdate = new TextField(deathdateOrigText);
        textFieldSearchDefault(textFieldSearchByDeathdate);
        setOriginalDescription(textFieldSearchByDeathdate, deathdateOrigText);
        textFieldSearchByDeathdate.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleDeathdateSelection(textFieldSearchByDeathdate);
            }
        });


        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel,  textFieldSearchByNation, textFieldSearchByBirthdate, textFieldSearchByDeathdate);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // CREATE A GRIDPANE
        List<Artist> listArtists = MainGetArtists.getAllArtists();
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildMainGrid(listArtists));

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());


        // ---------------------------------------------- END  PLUS ----------------------------------------------
        // LAST STEP: CENTER THE SCROLL_PANE
        this.setCenter(scrollPane);


    }

    /**
     * Retrieves the footer HBox containing social media icons and copyright information.
     *
     * @return The HBox containing social media icons and copyright information.
     */
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
        Label labelBottomStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);
        HBox.setHgrow(hBoxBottomImages, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomImages.setAlignment(Pos.CENTER_RIGHT);

        // HBOX BOTTOM
        HBox hBoxBottomStatus = new HBox(labelBottomStatus);
        HBox.setHgrow(hBoxBottomStatus, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomStatus.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxBottomLayout = new HBox(hBoxBottomStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        setBottom(hBoxBottomLayout);

        return hBoxBottomLayout;
    }

    /**
     * Retrieves the header VBox containing hyperlinks and search functionality.
     *
     * @return The VBox containing header elements.
     */
    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        String searchOrigText = "Procurar por artista, galeria, exposição ou obra de arte";
        TextField textFieldSearch = new TextField(searchOrigText);
        setOriginalDescription(textFieldSearch,searchOrigText);
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());
        textFieldSearch.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleSearchIconSelection(textFieldSearch);
            }
        });

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
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkArtist.getStyleClass().add("actual-page-hyperlink");

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    /**
     * Builds a GridPane to display artist information, including images and details.
     *
     * @param artistList The list of artists to be displayed in the grid.
     * @return The constructed GridPane containing artist information.
     */
    private GridPane buildMainGrid(List<Artist> artistList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        grid.getStyleClass().add("grid-pane");

        for (int i = 0; i < 8; i++) {
            // int imageNum = i+1;

            int maxTextLength = 23;
            Artist artist = artistList.get(i);
            String imageRef = artist.getReferenceImage();
            Image image = new Image(imageRef);
            ImageView imageViewArtist = new ImageView(image);
            defaultSizeArtistImage(imageViewArtist);

            String artistName = artist.getName();
            Hyperlink hyperArtistName;
            if (artistName.length() < maxTextLength){
                hyperArtistName = new Hyperlink(artistName);
            } else{
                hyperArtistName = new Hyperlink(artistName.substring(0,maxTextLength)+"...");
            }
            hyperArtistName.getStyleClass().add("my-desc-hyperlink");

            String artistOrigen = MainGetArtists.getArtistById(artist.getId()).getNationality();
            Hyperlink hyperArtistOrigen;
            if (artistOrigen.length() < maxTextLength){
                hyperArtistOrigen = new Hyperlink(artistOrigen);
            } else{
                hyperArtistOrigen = new Hyperlink(artistOrigen.substring(0,maxTextLength)+"...");
            }
            hyperArtistOrigen.getStyleClass().add("my-desc2-hyperlink");

            String birthDate = artist.getBirthdate();
            String deathDate = artist.getDeathdate();
            Hyperlink hyperBirthDeathDate;
            if(deathDate != null ){
                hyperBirthDeathDate = new Hyperlink(birthDate + " | " + deathDate);
            }
            else {
                hyperBirthDeathDate = new Hyperlink(birthDate );
            }
            hyperBirthDeathDate.getStyleClass().add("my-desc2-hyperlink");

            VBox vBoxLabelArtist = new VBox(hyperArtistName, hyperArtistOrigen, hyperBirthDeathDate);
            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtist, col * 2, row);
            grid.add(vBoxLabelArtist, col * 2, row + 1);

            imageViewArtist.setOnMouseClicked(e->doArtistDetailsLayout(artist));
            hyperArtistName.setOnAction(e-> setArtistLabelsOnAction(artist));
        }

        return grid;
    }

    /**
     * Builds a GridPane to display filtered artist information based on search criteria.
     *
     * @param artistList The list of filtered artists to be displayed in the grid.
     * @return The constructed GridPane containing filtered artist information.
     */
    private GridPane filteredGrid(List<Artist> artistList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        for (int i = 0; i < artistList.size(); i++) {
            // int imageNum = i+1;

            int maxTextLength = 23;
            Artist artist = artistList.get(i);
            String imageRef = artist.getReferenceImage();
            Image image = new Image(imageRef);
            ImageView imageViewArtist = new ImageView(image);
            defaultSizeArtistImage(imageViewArtist);

            String artistName = artist.getName();
            Hyperlink hyperArtistName;

            if (artistName.length() < maxTextLength){
                hyperArtistName = new Hyperlink(artistName);
            } else{
                hyperArtistName = new Hyperlink(artistName.substring(0,maxTextLength)+"...");
            }

            String artistOrigen = artist.getNationality();
            Hyperlink hyperArtistOrigen;
            if (artistOrigen.length() < maxTextLength){
                hyperArtistOrigen = new Hyperlink(artistOrigen);
            } else{
                hyperArtistOrigen = new Hyperlink(artistOrigen.substring(0,maxTextLength)+"...");
            }

            String birthDate = artist.getBirthdate();
            String deathDate = artist.getDeathdate();
            Hyperlink hyperBirthDeathDate;
            if(deathDate != null ){
                hyperBirthDeathDate = new Hyperlink(birthDate + " | " + deathDate);
            }
            else {
                hyperBirthDeathDate = new Hyperlink(birthDate );
            }

            VBox vBoxLabelArtist = new VBox(hyperArtistName, hyperArtistOrigen, hyperBirthDeathDate);
            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtist, col * 2, row);
            grid.add(vBoxLabelArtist, col * 2, row + 1);

            imageViewArtist.setOnMouseClicked(e->doArtistDetailsLayout(artist));
            hyperArtistName.setOnAction(e-> setArtistLabelsOnAction(artist));
        }

        return grid;
    }

    /**
     * Displays the details of a specific artist, including their biography and associated artworks.
     *
     * @param artist The artist for which details are to be displayed.
     * @return The BorderPane containing the artist details layout.
     */
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

        String birthDeathdate = artist.getBirthdate() + "  | "+ artist.getDeathdate();
        Label labelBirthDeathdate = new Label(birthDeathdate);

        Label labelHeaderAboutArtist = new Label("Sobre o Artista");
        Label labelBiography = new Label(artist.getBiography());
        labelBiography.setWrapText(true);

        labelArtistName.getStyleClass().add("my-center-label-1");

        ScrollPane scrollPaneBiography = new ScrollPane(labelBiography);
        scrollPaneBiography.getStyleClass().add("scroll-pane");

        VBox vBoxArtistInfo = new VBox(labelArtistName, labelNationality,labelBirthDeathdate);
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

    /**
     * Builds a GridPane to display the artworks associated with a specific artist.
     *
     * @param artworkList The list of artworks to be displayed in the grid.
     * @return The constructed GridPane containing artwork information.
     */
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

    /**
     * Sets the scene to display details for a specific artist when a hyperlink is clicked.
     *
     * @param artist The artist for which details are to be displayed.
     */
    private void setArtistLabelsOnAction(Artist artist){
        getScene().setRoot(doArtistDetailsLayout(artist));
    }

    /**
     * Sets the original description in a TextField when it loses focus if the field is empty.
     *
     * @param textField      The TextField for which to set the original description.
     * @param originalText   The original text to be displayed when the TextField is empty.
     */
    private void setOriginalDescription(TextField textField, String originalText){

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (textField.getText().isEmpty()) {
                    textField.setText(originalText);
                }
            }
        });
    }

    /**
     * Handles the action of filtering artists based on nationality.
     *
     * @param textFieldSearch The TextField containing the search criteria.
     */
    private void handleNationalSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByNationality(searchText);
            if(artists != null){
                finalScrollPane.setContent(filteredGrid(artists));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Handles the action of filtering artists based on birthdate.
     *
     * @param textFieldSearch The TextField containing the search criteria.
     */
    private void handleBirthdateSelection(TextField textFieldSearch ){

        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByBirthdate(searchText);
            if(artists != null){
                finalScrollPane.setContent(filteredGrid(artists));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }
            this.setCenter(finalScrollPane);

        }

    }

    /**
     * Handles the action of filtering artists based on deathdate.
     *
     * @param textFieldSearch The TextField containing the search criteria.
     */
    private void handleDeathdateSelection(TextField textFieldSearch ){

        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByDeathdate(searchText);
            if(artists != null){
                finalScrollPane.setContent(filteredGrid(artists));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }
            this.setCenter(finalScrollPane);

        }

    }

    /**
     * Handles the action of searching for artists based on a general search criteria.
     *
     * @param textFieldSearch The TextField containing the search criteria.
     */
    private void handleSearchIconSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByName(searchText);
            if(artists != null){
                finalScrollPane.setContent(filteredGrid(artists));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Sets the default size for social media icons.
     *
     * @param imageView The ImageView for the social media icon.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);

    }

    /**
     * Sets the default size for artist images.
     *
     * @param imageView The ImageView for the artist image.
     */
    public void defaultSizeArtistImage(ImageView imageView){
        imageView.setFitHeight(160);
        imageView.setFitWidth(160);

    }


    /**
     * Sets default size and behavior for search-related TextField.
     *
     * @param textField The TextField for which to set default size and behavior.
     */
    public void textFieldSearchDefault(TextField textField) {
        textField.setPrefSize(150, 15);
        textField.setOnMouseClicked(e -> textField.clear());
    }

}
