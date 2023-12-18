package view;

import domain.Artist;
import domain.Artwork;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import presenter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code SceneArtwork} class represents the graphical user interface for displaying artworks,
 * providing features such as filtering, searching, and detailed artwork information.
 * This class extends the JavaFX {@code BorderPane} for layout management.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class SceneArtwork extends BorderPane {


    /**
     * Constructs a new {@code SceneArtwork} object.
     * This constructor initialises the layout, creates filter menus,
     * and sets the initial theme based on the current theme in the {@code MainView}.
     */
    public SceneArtwork() {
        doLayout();
        MainView mainView = new MainView();
        String cssTheme = mainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Sets up the layout for the SceneArtwork, including filter menus, header, center, and footer sections.
     * Creates filter menus, handles user selections, and displays artworks accordingly.
     * This method utilises JavaFX components such as ComboBox, ScrollPane, VBox, HBox, Label, and more.
     */
    private void doLayout() {

        setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane();

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        VBox headerVbox = getHeaderBox();

        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox();

        // CREATE THE COMBO_BOXES
        ObservableList<String> priceOptions = FXCollections.observableArrayList(
                "Todos",
                "100-99999",
                "100000-399999",
                "400000-500000"
        );

        ObservableList<String> dateOptions = FXCollections.observableArrayList(
                "Todos",
                "1400-1599",
                "1600-1799",
                "1800-1999",
                "2000-2023"
        );


        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU
        List<Artwork> artworkList = MainGetArtworks.getAllArtworks();

        ComboBox<String> mediumMenu = new ComboBox<>(createOptionsMenu(artworkList,"medium"));
        mediumMenu.setMaxWidth(100);
        mediumMenu.setValue("Medium");
        mediumMenu.setOnAction(e-> handleMediumSelection(mediumMenu, artworkList));

        ComboBox<String> priceMenu = new ComboBox<>(priceOptions);
        priceMenu.setValue("Preço(€)");
        priceMenu.setOnAction(e-> handlePriceSelection(priceMenu,artworkList));

        ComboBox<String> dateMenu = new ComboBox<>(dateOptions);
        dateMenu.setValue("Data Criação");
        dateMenu.setOnAction(e-> handleDateSelection(dateMenu, artworkList));

        ComboBox<String>categoryMenu = new ComboBox<>(createOptionsMenu(artworkList,"category"));
        categoryMenu.setMaxWidth(120);
        categoryMenu.setValue("Categoria");
        categoryMenu.setOnAction(e->handleCategorySelection(categoryMenu, artworkList));

        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros     = ");

        // ADD ELEMENTS FOR THE MENU HBOX
        hBoxMenu.getChildren().addAll(filterLabel, mediumMenu, priceMenu, dateMenu,categoryMenu);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(headerVbox, hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // CREATE A GRIDPANE
        GridPane grid = buildMainGrid(artworkList);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(grid);
        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------
        // LAST STEP: CENTER THE SCROLL_PANE
        this.setCenter(scrollPane);


    }

    /**
     * Creates a detailed layout for displaying artwork information, including image,
     * labels, and a button to view artist details.
     *
     * @param artwork The artwork object for which details are displayed.
     * @return The updated BorderPane with the detailed artwork layout.
     */
    public BorderPane doDetailsLayout(Artwork artwork) {

        setPadding(new Insets(20));

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = getHeaderBox();
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // ARTWORK IMAGE
        String imageArtwork = artwork.getReferenceImage();
        Image image;
        ImageView imageViewArtwork;
        if (imageArtwork != null){
            image = new Image(imageArtwork.replace("{imageVersion}","square"));
            imageViewArtwork = new ImageView(image);
            imageViewArtwork.setOnMouseClicked(e -> getScene().setRoot(new ShowFullImage(artwork.getReferenceImage(), artwork,getScene().getHeight())));
        }
        else{
            image = new Image("Images/Artwork/AllArtworks/DefaultArtwork.jpg");
            imageViewArtwork = new ImageView(image);
        }
        defaultSizeArtworkImage(imageViewArtwork);

        imageViewArtwork.setFitHeight(300);
        imageViewArtwork.setFitWidth(360);


        // LABELS
        Label labelArtworkName = new Label(artwork.getName());
        Label labelArtistName = new Label(MainGetArtists.getArtistById(artwork.getIdArtist()).getName());
        Label labelGalleryName = new Label(MainGetGalleries.getGalleryById(artwork.getIdGallery()).getNameGallery());
        Label labelArtworkMedium = new Label(artwork.getName());
        Label labelArtworkCategory = new Label(artwork.getCategory());

        Label labelArtworkSizeCM = new Label(artwork.getDimensionCm().substring(1,artwork.getDimensionCm().length()-1));
        Label labelArtworkSizeIN = new Label(artwork.getDimensionIN().substring(1,artwork.getDimensionIN().length()-1));

        String currency = "€";
        Label labelPrice = new Label(String.valueOf(currency + artwork.getPrice()));

        // See artist button
        // This should be a button that links to the artists details page:
        Button artistDetailsButton = new Button("Ver artista");
        artistDetailsButton.getStyleClass().add("button-modern");
        SceneArtist artistDetailsScene = new SceneArtist();
        Artist artist = MainGetArtists.getArtistById(artwork.getIdArtist());
        artistDetailsButton.setOnAction(e-> getScene().setRoot(artistDetailsScene.doArtistDetailsLayout(artist)));

        // CREATE REQUIRED LABELS
        labelArtworkName.getStyleClass().add("my-center-label-1");
        labelArtistName.getStyleClass().add("my-center-label-2");
        labelGalleryName.getStyleClass().add("my-center-label-2");
        labelArtworkMedium.getStyleClass().add("my-center-label-3");
        labelArtworkCategory.getStyleClass().add("my-center-label-3");
        labelArtworkSizeCM.getStyleClass().add("my-center-label-3");
        labelArtworkSizeIN.getStyleClass().add("my-center-label-3");
        labelPrice.getStyleClass().add("my-center-label-4");

        labelArtworkName.setWrapText(true);
        ScrollPane scrollPaneArtworkName = new ScrollPane(labelArtworkName);
        scrollPaneArtworkName.setContent(labelArtworkName);
        scrollPaneArtworkName.setPrefHeight(50);

        VBox vBoxArtworkImage = new VBox(imageViewArtwork, artistDetailsButton);
        vBoxArtworkImage.setSpacing(40);

        VBox vBoxLabel = new VBox(scrollPaneArtworkName, labelArtistName, labelGalleryName, labelArtworkMedium,
                labelArtworkCategory, labelArtworkSizeCM,labelArtworkSizeIN, labelPrice);

        vBoxLabel.setAlignment(Pos.TOP_LEFT);
        vBoxLabel.setSpacing(10);
        HBox hBoxCenterLayout = new HBox(vBoxArtworkImage, vBoxLabel);
        hBoxCenterLayout.setSpacing(50);
        // Box hBoxBiography = new HBox(labelBiography);
        setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------

        return this;
    }

    /**
     * Builds the footer box, containing social media icons and copyright information.
     *
     * @return The HBox representing the footer box.
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
     * Builds the header box, containing hyperlinks for navigation, a search bar, and logo.
     *
     * @return The VBox representing the header box.
     */
    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        hyperLinkArtwork.getStyleClass().add("actual-page-hyperlink");

        // TEXT FIELDS
        String searchOrigText = "Procurar por obra de arte...";
        TextField textFieldSearch = new TextField(searchOrigText);
        setOriginalDescription(textFieldSearch,searchOrigText);
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());
        textFieldSearch.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleSearchIconSelection(textFieldSearch);
            }
        });

        // IMAGES

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

        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------

        // ADD PAGE HEADER ELEMENTS
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));

        return  vBoxTop;

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
     * Handles the action of searching for artists based on a general search criteria.
     *
     * @param textFieldSearch The TextField containing the search criteria.
     */
    private void handleSearchIconSelection (TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artwork> artworks = MainGetArtworks.getArtworkByName(searchText);
            if(artworks != null){
                finalScrollPane.setContent(filteredGrid(artworks));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtwork());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Handles the selection of the medium filter menu.
     * It filters and displays artworks based on the selected medium.
     *
     * @param mediumMenu   The ComboBox containing medium filter options.
     * @param artworkList  The list of all artworks.
     */
    private void handleMediumSelection (ComboBox<String> mediumMenu, List<Artwork> artworkList){

        ScrollPane finalScrollPane = new ScrollPane();
        // get the selected item*
        String selected = mediumMenu.getSelectionModel().getSelectedItem();
        if(selected != null){
            if (selected.equalsIgnoreCase("Todos")){
                finalScrollPane.setContent(buildMainGrid(artworkList));
                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            }
            else{
                List<Artwork> artworks = MainGetArtworks.getArtworksByMedium(selected);
                // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
                finalScrollPane.setContent(filteredGrid(artworks));
                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            }
        }
    }

    /**
     * Handles the selection of the price filter menu.
     * It filters and displays artworks based on the selected price range.
     *
     * @param priceMenu    The ComboBox containing price filter options.
     * @param artworkList  The list of all artworks.
     */
    private void handlePriceSelection (ComboBox<String> priceMenu, List<Artwork> artworkList){
        ScrollPane finalScrollPane = new ScrollPane();
        // get the selected item*
        String selected = priceMenu.getSelectionModel().getSelectedItem();
        if(selected != null){
            if (selected.equalsIgnoreCase("Todos")){
                finalScrollPane.setContent(buildMainGrid(artworkList));
                this.setCenter(finalScrollPane);
            }
            else{
                String [] values  = selected.split("-");
                double min = Double.parseDouble(values[0]);
                double max = Double.parseDouble(values[1]);
                List<Artwork> artworks = MainGetArtworks.getArtworksByPrice(min,max);
                // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
                if(artworks != null){
                    finalScrollPane.setContent(filteredGrid(artworks));
                    // LAST STEP: CENTER THE SCROLL_PANE
                    this.setCenter(finalScrollPane);
                }
                else{
                    getScene().setRoot(new ShowErrorArtwork());
                }

                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            }
        }

    }

    /**
     * Handles the selection of the category filter menu.
     * It filters and displays artworks based on the selected category.
     *
     * @param categoryMenu The ComboBox containing category filter options.
     * @param artworkList  The list of all artworks.
     */
    private void handleCategorySelection (ComboBox<String> categoryMenu, List<Artwork> artworkList) {
        ScrollPane finalScrollPane = new ScrollPane();
        // get the selected item*
        String selected = categoryMenu.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selected.equalsIgnoreCase("Todos")) {
                finalScrollPane.setContent(buildMainGrid(artworkList));
                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            } else {
                List<Artwork> artworks = MainGetArtworks.getArtworksByCategory(selected);
                // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
                finalScrollPane.setContent(filteredGrid(artworks));
                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            }
        }

    }

    /**
     * Handles the selection of the date filter menu.
     * It filters and displays artworks based on the selected creation date range.
     *
     * @param dateMenu     The ComboBox containing date filter options.
     * @param artworkList  The list of all artworks.
     */
    private void handleDateSelection (ComboBox<String> dateMenu, List<Artwork> artworkList) {
        ScrollPane finalScrollPane = new ScrollPane();
        // get the selected item*
        String selected = dateMenu.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selected.equalsIgnoreCase("Todos")) {
                finalScrollPane.setContent(buildMainGrid(artworkList));
                this.setCenter(finalScrollPane);
            } else {
                String[] values = selected.split("-");
                List<Artwork> artworks = MainGetArtworks.getArtworksByDate(values[0], values[1]);
                // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
                if (artworks != null) {
                    finalScrollPane.setContent(filteredGrid(artworks));
                    // LAST STEP: CENTER THE SCROLL_PANE
                    this.setCenter(finalScrollPane);
                } else {
                    getScene().setRoot(new ShowErrorArtwork());
                }

                // LAST STEP: CENTER THE SCROLL_PANE
                this.setCenter(finalScrollPane);
            }
        }

    }

    /**
     * Sets the scene to display details for a specific artwork when a hyperlink is clicked.
     *
     * @param artwork The artist for which details are to be displayed.
     */
    private void setArtworkLabelsOnAction(Artwork artwork){
        getScene().setRoot(doDetailsLayout(artwork));
    }

    /**
     * Creates an ObservableList of options for a ComboBox based on the specified field
     * (e.g. medium or category).
     *
     * @param artworkList The list of all artworks.
     * @param field       The field for which options are created (e.g. "medium" or "category").
     * @return An ObservableList containing options for the specified field.
     */
    private ObservableList<String> createOptionsMenu (List<Artwork>artworkList, String field){

        List<String> menu = new ArrayList<>();
        menu.add("Todos");
        String menuField = field.toLowerCase();

        switch (menuField){
            case "medium": for(Artwork art : artworkList){
                String option = art.getMedium();
                if(!menu.contains(option)){
                    menu.add(option);
                }
            }
                break;
            case "category": for(Artwork art : artworkList){
                String option = art.getCategory();
                if(!menu.contains(option)){
                    menu.add(option);
                }
            }
                break;

        }

        return FXCollections.observableArrayList(menu);
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
     * Sets the default size for artwork images.
     *
     * @param imageView The ImageView for the artwork image.
     */
    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(160); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

    /**
     * Builds the main grid for displaying artworks, including images and labels.
     *
     * @param artworkList The list of all artworks to be displayed.
     * @return The GridPane representing the main grid layout.
     */
    private GridPane buildMainGrid(List<Artwork> artworkList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        grid.getStyleClass().add("grid-pane");

        for (int i = 0; i < 8; i++) {

            int maxTextLength = 23;
            Artwork artwork = artworkList.get(i);

            String imageRef = artwork.getReferenceImage();
            Image image;
            ImageView imageViewArtwork;
            if (imageRef != null){
                image = new Image(imageRef.replace("{imageVersion}","square"));
            }
            else{
                image = new Image("Images/Artwork/AllArtworks/DefaultArtwork.jpg");
            }
            imageViewArtwork = new ImageView(image);
            defaultSizeArtworkImage(imageViewArtwork);


            String artworkName = artwork.getName();
            Hyperlink hyperArtworkName;

            if (artworkName.length() < maxTextLength){
                hyperArtworkName = new Hyperlink(artworkName);
            } else{
                hyperArtworkName = new Hyperlink(artworkName.substring(0,maxTextLength)+"...");
            }
            hyperArtworkName.getStyleClass().add("my-desc-hyperlink");


            String artistName = MainGetArtists.getArtistById(artwork.getIdArtist()).getName();
            Hyperlink hyperArtistName;
            if (artistName.length() < maxTextLength){
                hyperArtistName = new Hyperlink(artistName);
            } else{
                hyperArtistName = new Hyperlink(artistName.substring(0,maxTextLength)+"...");
            }
            hyperArtistName.getStyleClass().add("my-desc2-hyperlink");


            String galleryName = MainGetGalleries.getGalleryById(artwork.getIdGallery()).getNameGallery();
            Hyperlink hyperGalleryName;
            if (galleryName.length() < maxTextLength){
                hyperGalleryName = new Hyperlink(galleryName);
            } else{
                hyperGalleryName = new Hyperlink(galleryName.substring(0,maxTextLength)+"...");
            }
            hyperGalleryName.getStyleClass().add("my-desc2-hyperlink");

            String currency = "€";
            String price = String.valueOf(currency + artwork.getPrice());
            Hyperlink hyperPrice = new Hyperlink(price);
            hyperPrice.getStyleClass().add("my-desc2-price-hyperlink");


            hyperArtworkName.setOnAction(e-> setArtworkLabelsOnAction(artwork));

            imageViewArtwork.setOnMouseClicked(e-> setArtworkLabelsOnAction(artwork));

            hyperArtistName.setOnAction(e-> getScene().setRoot(new SceneArtist()
                    .doArtistDetailsLayout(MainGetArtists.getArtistById(artwork.getIdArtist()))));

            hyperGalleryName.setOnAction(e-> getScene().setRoot(new SceneGallery()
                    .doDetailsLayout(MainGetGalleries.getGalleryById(artwork.getIdGallery()))));

            VBox vBoxLabelArtwork = new VBox(hyperArtworkName, hyperArtistName, hyperGalleryName, hyperPrice);

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
     * Creates a filtered grid based on the provided list of artworks.
     *
     * @param filteredArtwork The list of artworks to be displayed in the filtered grid.
     * @return The GridPane representing the filtered grid layout.
     */
    private GridPane filteredGrid (List<Artwork> filteredArtwork){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        // leave 8 for now

        for (int i = 0; i < filteredArtwork.size(); i++) {
            // int imageNum = i+1;

            int maxTextLength = 23;
            Artwork artwork = filteredArtwork.get(i);
            String imageRef = artwork.getReferenceImage();
            Image image;
            ImageView imageViewArtwork;
            if (imageRef != null){
                image = new Image(imageRef.replace("{imageVersion}","square"));
            }
            else{
                image = new Image("Images/Artwork/AllArtworks/DefaultArtwork.jpg");
            }
            imageViewArtwork = new ImageView(image);
            defaultSizeArtworkImage(imageViewArtwork);
            // Create a new VBox for each iteration

            String artworkName = artwork.getName();
            Hyperlink hyperArtworkName;

            if (artworkName.length() < maxTextLength){
                hyperArtworkName = new Hyperlink(artworkName);
            } else{
                hyperArtworkName = new Hyperlink(artworkName.substring(0,maxTextLength)+"...");
            }


            String artistName = MainGetArtists.getArtistById(artwork.getIdArtist()).getName();
            Hyperlink hyperArtistName;
            if (artistName.length() < maxTextLength){
                hyperArtistName = new Hyperlink(artistName);
            } else{
                hyperArtistName = new Hyperlink(artistName.substring(0,maxTextLength)+"...");
            }


            String galleryName = MainGetGalleries.getGalleryById(artwork.getIdGallery()).getNameGallery();
            Hyperlink hyperGalleryName;
            if (galleryName.length() < maxTextLength){
                hyperGalleryName = new Hyperlink(galleryName);
            } else{
                hyperGalleryName = new Hyperlink(galleryName.substring(0,maxTextLength)+"...");
            }


            String price = String.valueOf(artwork.getPrice());
            Hyperlink hyperPrice = new Hyperlink(price);

            hyperArtworkName.setOnAction(e-> setArtworkLabelsOnAction(artwork));

            imageViewArtwork.setOnMouseClicked(e-> setArtworkLabelsOnAction(artwork));

            hyperArtistName.setOnAction(e-> getScene().setRoot(new SceneArtist()
                    .doArtistDetailsLayout(MainGetArtists.getArtistById(artwork.getIdArtist()))));

            hyperGalleryName.setOnAction(e-> getScene().setRoot(new SceneGallery()
                    .doDetailsLayout(MainGetGalleries.getGalleryById(artwork.getIdGallery()))));


            VBox vBoxLabelArtwork = new VBox(hyperArtworkName, hyperArtistName, hyperGalleryName, hyperPrice);

            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtwork, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);
        }
        return grid;
    }

}
