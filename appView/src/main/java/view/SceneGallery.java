package view;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Gallery;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import presenter.MainGetArtists;
import presenter.MainGetArtworks;
import presenter.MainGetExhibitions;
import presenter.MainGetGalleries;

import java.util.List;
import java.util.Objects;

public class SceneGallery extends BorderPane {

    public SceneGallery() {
        doLayout();
        MainView mainView = new MainView();
        String cssTheme = mainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        String regionOrigText = "Região: (inglês):";
        TextField textFieldSearchByRegion = new TextField(regionOrigText);
        setOriginalDescription(textFieldSearchByRegion,regionOrigText);
        textFieldSearchDefault(textFieldSearchByRegion);
        textFieldSearchByRegion.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                handleRegionSelection(textFieldSearchByRegion);
            }
        });



        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel, textFieldSearchByRegion);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        List<Gallery> listGalleries = MainGetGalleries.getAllGalleries();
        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildMainGrid(listGalleries));
        // LAST STEP: CENTER THE SCROLL_PANE
        setCenter(scrollPane);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------

    }

    private GridPane buildMainGrid (List<Gallery> listGalleries){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);
        grid.getStyleClass().add("grid-pane");

        for (int i = 0; i < listGalleries.size(); i++) {

            Gallery gallery = listGalleries.get(i);
            String imageRef = gallery.getReferenceImage();
            Image image;
            ImageView imageViewGallery;
            image = new Image(Objects.requireNonNullElse(imageRef, "Images/Gallery/DefaultGallery.jpg"));

            imageViewGallery = new ImageView(image);
            defaultSizeGalleryImage(imageViewGallery);

            imageViewGallery.setOnMouseClicked(e-> getScene().setRoot(doDetailsLayout(gallery)));
            // Create a new VBox for each iteration

            String nomeGallery = gallery.getNameGallery();
            Label labelGalleryName = new Label(nomeGallery);

            String regionGallery = gallery.getRegionName();
            Label labelGalleryRegion = new Label(regionGallery);

            Hyperlink hyperlinkContactGallery = new Hyperlink("Contatar Galeria ");

            VBox vBoxGalleryDetails = new VBox(labelGalleryName,labelGalleryRegion,hyperlinkContactGallery);
            vBoxGalleryDetails.setAlignment(Pos.CENTER);
            vBoxGalleryDetails.setPrefWidth(355);
            HBox hBoxCenter = new HBox(imageViewGallery, vBoxGalleryDetails);

            labelGalleryName.getStyleClass().add("my-center-label-6");
            labelGalleryRegion.getStyleClass().add("my-center-label-5");
            hyperlinkContactGallery.getStyleClass().add("my-center-label-5");
            vBoxGalleryDetails.getStyleClass().add("cell-gray-background");

            int col = 0;
            int row = i;
            grid.add(hBoxCenter, col, row + 1);
        }

        return grid;
    }

    private BorderPane doDetailsLayout(Gallery gallery) {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // GALLERY IMAGE
        String imageGallery = gallery.getReferenceImage();

        ImageView imageViewGallery;
        imageViewGallery = new ImageView(new Image(Objects.requireNonNullElse(imageGallery, "Images/Gallery/DefaultGallery.jpg")));
        defaultSizeGalleryImage(imageViewGallery);
        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------

        setTop(getHeaderBox());

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // LABELS
        Label labelGalleryName = new Label(gallery.getNameGallery());
        Label labelGalleryRegion = new Label(gallery.getRegionName());
        Label labelGalleryEmail = new Label(gallery.getEmail());
        labelGalleryName.getStyleClass().add("my-center-label-1");

        Label labelArtworks = new Label("Obras de arte ");
        Label labelExhibitions = new Label("Eventos");

        HBox galleryArtworkAndEvents = new HBox(labelArtworks,labelExhibitions);
        galleryArtworkAndEvents.setSpacing(60);
        galleryArtworkAndEvents.setPadding(new Insets(5,0,5,0));

        VBox vBoxGalleryInfo = new VBox(labelGalleryName, labelGalleryRegion, labelGalleryEmail);

        HBox hBoxImageInfo = new HBox(imageViewGallery, vBoxGalleryInfo);
        hBoxImageInfo.setSpacing(30);

        VBox vBoxGalleryImageArtworkEvents = new VBox(hBoxImageInfo,galleryArtworkAndEvents);
        vBoxGalleryImageArtworkEvents.setSpacing(30);
        VBox vBoxCenterLayout = new VBox(vBoxGalleryImageArtworkEvents);
        vBoxCenterLayout.setSpacing(10);


        List<Exhibition> galleryExhibition = MainGetExhibitions.getExhibitionsByIdGallery(gallery.getId());
        List<Artwork> galleryArtworks = MainGetArtworks.getArtworksByGalleryId(gallery.getId());
        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildThisGalleryArtworkGrid(galleryArtworks));
        VBox vBoxGlobalCenterLayout = new VBox(vBoxCenterLayout, scrollPane);
        vBoxGlobalCenterLayout.setSpacing(20);

        // CHANGE
        labelArtworks.setOnMouseClicked(e-> {
            labelExhibitions.getStyleClass().remove("actual-page-label");
            scrollPane.setContent(buildThisGalleryArtworkGrid(galleryArtworks));
            labelArtworks.getStyleClass().add("actual-page-label");
        });
        labelExhibitions.setOnMouseClicked(e-> {
            labelArtworks.getStyleClass().remove("actual-page-label");
            scrollPane.setContent(buildThisGalleryExhibitionGrid(galleryExhibition));
            labelExhibitions.getStyleClass().add("actual-page-label");
        });

        setCenter(vBoxGlobalCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------

        return this;

    }

    private GridPane buildThisGalleryExhibitionGrid (List<Exhibition> exhibitionList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        for (int i = 0; i < exhibitionList.size(); i++) {

            int maxTextLength = 23;

            Exhibition exhibition = exhibitionList.get(i);
            String imageExhibition = exhibition.getReferenceImage();
            ImageView imageViewExhibition ;
            imageViewExhibition = new ImageView(new Image(Objects.requireNonNullElse(imageExhibition, "Images/Exhibition/SmallDefault.jpg")));
            defaultSizeArtworkImage(imageViewExhibition);

            imageViewExhibition.setOnMouseClicked(e-> getScene().setRoot(new SceneExhibition().doExhibitionDetailsLayout(exhibition)));
            // Create a new VBox for each iteration
            String exhibitionName = exhibition.getNameExhibition();
            Hyperlink hyperExhibitionName;

            if (exhibitionName.length() < maxTextLength){
                hyperExhibitionName = new Hyperlink(exhibitionName);
            } else{
                hyperExhibitionName = new Hyperlink(exhibitionName.substring(0,maxTextLength)+"...");
            }
            hyperExhibitionName.setOnAction(e-> getScene().setRoot(new SceneExhibition().doExhibitionDetailsLayout(exhibition)));


            Gallery gallery = MainGetGalleries.getGalleryById(exhibition.getIdGallery());
            String exhibitionGallery = gallery.getNameGallery();
            Hyperlink hyperGalleryName = new Hyperlink(exhibitionGallery);
            VBox vBoxLabelArtwork = new VBox(hyperExhibitionName, hyperGalleryName);
            hyperGalleryName.setOnAction(e-> getScene().setRoot(new SceneGallery().doDetailsLayout(gallery)));


            int col = i % 4;
            int row = i / 4 * 2;
            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewExhibition, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);

        }

        return grid;
    }

    private GridPane buildThisGalleryArtworkGrid (List<Artwork> artworkList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

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

            String currency = "€";
            String price = String.valueOf(currency + artwork.getPrice());
            Hyperlink hyperPrice = new Hyperlink(price);
            hyperPrice.getStyleClass().add("my-desc2-price-hyperlink");

            VBox vBoxLabelArtwork = new VBox(hyperArtworkName, hyperPrice);

            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtwork, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);


        }

        return grid;
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

    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        hyperlinkGallery.getStyleClass().add("actual-page-hyperlink");

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

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    private void handleRegionSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Gallery> galleries = MainGetGalleries.getGalleriesByRegion(searchText);
            if(galleries != null){
                finalScrollPane.setContent(buildMainGrid(galleries));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorGallery());
            }
            this.setCenter(finalScrollPane);

        }
    }

    private void handleSearchIconSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Gallery> galleries = MainGetGalleries.getGalleryByName(searchText);
            if(galleries != null){
                finalScrollPane.setContent(buildMainGrid(galleries));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorGallery());
            }
            this.setCenter(finalScrollPane);

        }
    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeGalleryImage(ImageView imageView){
        imageView.setFitHeight(250); // Ajuste a altura conforme necessário
        imageView.setFitWidth(380);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

    public void textFieldSearchDefault(TextField textField) {
        textField.setPrefSize(150, 15);
        textField.setOnMouseClicked(e -> textField.clear());
    }

    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(160); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

}
