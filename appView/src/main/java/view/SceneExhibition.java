package view;

import domain.Artwork;
import domain.Exhibition;
import domain.Gallery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import presenter.MainGetArtworks;
import presenter.MainGetExhibitions;
import presenter.MainGetGalleries;

import java.util.List;
import java.util.Objects;

/**
 * The SceneExhibition class represents the graphical user interface for displaying and interacting
 * with a list of art exhibitions.
 * It extends the BorderPane class and incorporates features such as search filters,
 * exhibition details, and navigation links.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class SceneExhibition extends BorderPane {

    /**
     * Constructs a new SceneExhibition object, initialising the layout and adding event listeners
     * for various user interactions.
     * It also sets the current theme based on the MainView's theme.
     */
    public SceneExhibition() {
        doLayout();
        MainView mainView = new MainView();
        String cssTheme = mainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Configures the layout of the SceneExhibition, including header, center, and bottom sections.
     * Initializes search filters, status menus, and exhibition details for display.
     */
    private void doLayout() {

        setPadding(new Insets(20));
        List<Exhibition> exhibitionList = MainGetExhibitions.getAllExhibitions();
        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // CREATE SEARCH BY FILTER FIELDS
        String startDateOriginalText = "Inicio ex. aaaa-mm-dd";
        TextField textFieldSearchByStartDate = new TextField(startDateOriginalText);
        setOriginalDescription(textFieldSearchByStartDate,startDateOriginalText);
        textFieldSearchDefault(textFieldSearchByStartDate);
        textFieldSearchByStartDate.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleStartDateSelection(textFieldSearchByStartDate);
            }
        });

        String endDateOriginalText = "Fecho ex. aaaa-mm-dd";
        TextField textFieldSearchByEndDate = new TextField(endDateOriginalText);
        setOriginalDescription(textFieldSearchByEndDate,endDateOriginalText);
        textFieldSearchDefault(textFieldSearchByEndDate);
        textFieldSearchByEndDate.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleEndDateSelection(textFieldSearchByEndDate);
            }
        });

        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros     = ");

        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "Todas",
                "Aberta",
                "Fechada"
        );

        ComboBox<String> statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(100);
        statusMenu.setValue("Status");
        statusMenu.setOnAction(e-> handleStatusSelection(statusMenu, exhibitionList));

        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel, statusMenu, textFieldSearchByStartDate, textFieldSearchByEndDate);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildMainGrid(exhibitionList));
        scrollPane.getStyleClass().add("scroll-pane");
        // LAST STEP: CENTER THE SCROLL_PANE
        setCenter(scrollPane);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS --------------------------------------------------

    }

    /**
     * Builds the main grid for displaying exhibitions.
     *
     * @param exhibitionList The list of all exhibitions to be displayed.
     * @return The GridPane representing the main grid layout.
     */
    private GridPane buildMainGrid(List<Exhibition> exhibitionList){

        // CREATE A GRIDPANE
        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        grid.getStyleClass().add("grid-pane");

        // FILL THE GRIDPANE WITH IMAGES AND LABELS
        for (int i = 0; i < exhibitionList.size(); i++) {

            Exhibition exhibition = exhibitionList.get(i);

            // Create a new VBox for each iteration
            Label labelExhibitionName = new Label(exhibition.getNameExhibition());
            Label labelGalleryName = new Label(MainGetGalleries.getGalleryById(exhibition.getIdGallery()).getNameGallery());

            String strStatus = exhibition.getExstatus();
            Label labelStatus;
            if (strStatus.equalsIgnoreCase("open")){
                labelStatus = new Label(" aberta");
            }
            else {
                labelStatus = new Label(" fechada");
            }

            Label labelStartDate = new Label(String.valueOf(exhibition.getStartDate()));
            Label labelEndDate = new Label(String.valueOf(exhibition.getEndDate()));

            Label statusDescription = new Label("Estado : ");
            Label startDateDescription = new Label("Data abertura : ");
            Label endDateDescription = new Label("Data fecho : ");

            Button buttonExhibitionDetails = new Button("Aceder Exposição");
            buttonExhibitionDetails.getStyleClass().add("button-modern");
            buttonExhibitionDetails.setPrefWidth(180);
            buttonExhibitionDetails.setPrefHeight(10);

            buttonExhibitionDetails.setOnAction(e-> getScene().setRoot(doExhibitionDetailsLayout(exhibition)));

            HBox hBoxExhibitionName = new HBox(labelExhibitionName);
            hBoxExhibitionName.setPadding(new Insets(10));

            HBox hBoxGalleryName = new HBox(labelGalleryName);
            hBoxGalleryName.setPadding(new Insets(10));

            HBox hBoxExhibitionStatus = new HBox(statusDescription,labelStatus);
            hBoxExhibitionStatus.setPadding(new Insets(5,0,5,10));

            HBox hBoxExhibitionStartDate = new HBox(startDateDescription,labelStartDate);
            hBoxExhibitionStartDate.setPadding(new Insets(5,0,5,10));

            HBox hBoxExhibitionEndDate = new HBox(endDateDescription,labelEndDate);
            hBoxExhibitionEndDate.setPadding(new Insets(5,0,5,10));

            HBox hBoxExhibitionDetails = new HBox(buttonExhibitionDetails);
            hBoxExhibitionDetails.setAlignment(Pos.CENTER);

            VBox vBoxExhibitionDetails = new VBox(hBoxExhibitionName,hBoxGalleryName,hBoxExhibitionStatus,
                    hBoxExhibitionStartDate, hBoxExhibitionEndDate, hBoxExhibitionDetails);
            vBoxExhibitionDetails.setPrefWidth(730);

            labelExhibitionName.getStyleClass().add("my-label-gray-medium-size");
            vBoxExhibitionDetails.getStyleClass().add("cell-clear-background");

            int col = 0;
            int row = i;
            grid.add(vBoxExhibitionDetails, col, row + 1);
        }

        return grid;
    }

    /**
     * Creates a detailed layout for displaying exhibition information, including image,
     * labels, and related artworks.
     *
     * @param exhibition The exhibition object for which details are displayed.
     * @return The updated BorderPane with the detailed exhibition layout.
     */
    public BorderPane doExhibitionDetailsLayout( Exhibition  exhibition ) {
        setPadding(new Insets(20));
        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // EXHIBITION IMAGE
        String imageExhibition = exhibition.getReferenceImage();
        ImageView imageViewExhibition ;
        imageViewExhibition = new ImageView(new Image(Objects.requireNonNullElse(imageExhibition, "Images/Exhibition/Default.jpg")));
        defaultSizeExhibitionImage(imageViewExhibition);

        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------

        setTop(getHeaderBox());

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // LABELS
        Label labelExhibitionName = new Label(exhibition.getNameExhibition());

        Gallery gallery = MainGetGalleries.getGalleryById(exhibition.getIdGallery());
        Hyperlink hyperGalleryName = new Hyperlink(gallery.getNameGallery());
        hyperGalleryName.setOnAction(e-> getScene().setRoot(new SceneGallery().doDetailsLayout(gallery)));

        Label labelStartDate = new Label(String.valueOf(exhibition.getStartDate()));
        Label labelEndDate = new Label(String.valueOf(exhibition.getEndDate()));
        Label labelAboutExhibition= new Label("Sobre a Exposição:");
        Label labelExhibitionDetails= new Label(exhibition.getExdescription());
        Label labelExhibitionArtworks = new Label("Obras de arte");

        labelExhibitionDetails.setWrapText(true);
        labelAboutExhibition.getStyleClass().add("my-center-label-3");
        labelExhibitionName.getStyleClass().add("my-center-label-1");

        HBox hboxStartAndEndDate = new HBox(labelStartDate, labelEndDate);
        hboxStartAndEndDate.setSpacing(10);

        VBox vBoxGalleryInfo = new VBox(labelExhibitionName, hyperGalleryName, hboxStartAndEndDate);
        vBoxGalleryInfo.setPadding(new Insets(0,0,10,0));
        VBox vBoxExhibitionDetails = new VBox(vBoxGalleryInfo ,labelAboutExhibition, labelExhibitionDetails);

        HBox hBoxCenterLayout = new HBox(imageViewExhibition, vBoxExhibitionDetails);
        hBoxCenterLayout.setSpacing(10);
        VBox vBoxCenterLayout = new VBox(hBoxCenterLayout,labelExhibitionArtworks);
        vBoxCenterLayout.setSpacing(10);


        List<Artwork> artworks = MainGetArtworks.getArtworksByExhibitionId(exhibition.getId());
        ScrollPane scrollPane = new ScrollPane();
        // NOTE: WE CAN ONLY SEE IMAGES OF EXHIBITIONS THAT ARE OPEN
        // WHEN WE HAVE NO
        scrollPane.setContent(buildThisExhibitionArtworkGrid(artworks));

        VBox vBoxGlobalCenterLayout = new VBox(vBoxCenterLayout, scrollPane);
        vBoxGlobalCenterLayout.setSpacing(20);
        setCenter(vBoxGlobalCenterLayout);


        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------

        return this;

    }

    /**
     * Constructs and returns a GridPane containing details of the provided artworks.
     * Each artwork is represented by an image and accompanying labels.
     *
     * @param artworkList The list of artworks to be displayed.
     * @return The GridPane containing artwork details.
     */
    private GridPane buildThisExhibitionArtworkGrid(List<Artwork> artworkList){

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
            defaultSizeArtworkImage(imageViewArtwork);

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
            hyperGalleryName.getStyleClass().add("my-desc2-hyperlink");
            hyperGalleryName.setOnAction(e-> getScene().setRoot(new SceneGallery()
                    .doDetailsLayout(MainGetGalleries.getGalleryById(artwork.getIdGallery()))));


            String currency = "€";
            String price = currency + artwork.getPrice();
            Hyperlink hyperPrice = new Hyperlink(price);
            hyperPrice.getStyleClass().add("my-desc2-price-hyperlink");

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
     * Constructs and returns an HBox containing footer information, including social media icons.
     *
     * @return The HBox containing footer information.
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
     * Constructs and returns a VBox containing header elements, including navigation links and search bar.
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

        hyperlinkExhibition.getStyleClass().add("actual-page-hyperlink");

        // Text Fields
        String searchOrigText = "Procurar por exposição...";
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

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    /**
     * Handles the selection of the status filter menu.
     * It filters and displays exhibitions based on the selected status.
     *
     * @param statusMenu     The ComboBox containing status filter options.
     * @param exhibitionList  The list of all exhibitions.
     */
    private void handleStatusSelection(ComboBox<String> statusMenu, List<Exhibition> exhibitionList){

        ScrollPane finalScrollPane = new ScrollPane();
        // get the selected item*
        String selected = statusMenu.getSelectionModel().getSelectedItem();
        List<Exhibition> filteredExhibitions;

        if(selected != null){

            if (selected.equalsIgnoreCase("Todas")){
                finalScrollPane.setContent(buildMainGrid(exhibitionList));
            }
            else if (selected.equalsIgnoreCase("Aberta")){
                filteredExhibitions = MainGetExhibitions.getExhibitionsByStatus("open");
                finalScrollPane.setContent(buildMainGrid(filteredExhibitions));
            }
            else{
                filteredExhibitions = MainGetExhibitions.getExhibitionsByStatus("closed");
                finalScrollPane.setContent(buildMainGrid(filteredExhibitions));
            }
            this.setCenter(finalScrollPane);
        }

    }

    /**
     * Handles the user's selection of the start date filter, updating the displayed exhibitions accordingly.
     *
     * @param textFieldSearch The TextField representing the start date filter.
     */
    private void handleStartDateSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Exhibition> exhibitions = MainGetExhibitions.getExhibitionsByStartDate(searchText);
            if(exhibitions != null){
                finalScrollPane.setContent(buildMainGrid(exhibitions));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorExhibition());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Handles the user's selection of the end date filter, updating the displayed exhibitions accordingly.
     *
     * @param textFieldSearch The TextField representing the end date filter.
     */
    private void handleEndDateSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Exhibition> exhibitions = MainGetExhibitions.getExhibitionsByEndDate(searchText);
            if(exhibitions != null){
                finalScrollPane.setContent(buildMainGrid(exhibitions));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorExhibition());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Handles the user's selection of the search icon, updating the displayed
     * exhibitions based on the search text.
     *
     * @param textFieldSearch The TextField representing the search bar.
     */
    private void handleSearchIconSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Exhibition> exhibitions = MainGetExhibitions.getExhibitionsByName(searchText);
            if(exhibitions != null){
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorExhibition());
            }
            this.setCenter(finalScrollPane);

        }
    }

    /**
     * Sets the original description for a TextField, displaying the provided text when the field is not focused.
     *
     * @param textField      The TextField for which the original description is set.
     * @param originalText   The original text to be displayed when the TextField is not focused.
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
     * Sets the default size for social media icons.
     *
     * @param imageView The ImageView for the social media icon.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    /**
     * Sets the default size for Exhibition images.
     *
     * @param imageView The ImageView for the artwork image.
     */
    public void defaultSizeExhibitionImage(ImageView imageView){
        imageView.setFitHeight(250);
        imageView.setFitWidth(380);
        imageView.setPreserveRatio(true);
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

    /**
     * Sets the default size for artwork images.
     *
     * @param imageView The ImageView for the artwork image.
     */
    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(160);
        imageView.setFitWidth(160);
    }

}
