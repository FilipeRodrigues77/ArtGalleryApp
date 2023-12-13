package view;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import presenter.MainGetArtists;
import presenter.MainGetArtworks;
import presenter.MainGetExhibitions;
import presenter.MainGetGalleries;

import java.util.List;

public class SceneExhibition extends BorderPane {

    public SceneExhibition() {
        doLayout();
        MainView mainView = new MainView();
        String cssTheme = mainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // CREATE SEARCH BY FILTER FIELDS
        TextField textFieldSearchByStartDate = new TextField("Inicio ex. 21-02-2023:");
        textFieldSearchDefault(textFieldSearchByStartDate);

        TextField textFieldSearchByEndDate = new TextField("Data de fecho:");
        textFieldSearchDefault(textFieldSearchByEndDate);

        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "Aberta",
                "Fechada"
        );
        ComboBox<String> statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(100);
        statusMenu.setValue("Status");

        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel, statusMenu, textFieldSearchByStartDate, textFieldSearchByEndDate);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        List<Exhibition> exhibitionList = MainGetExhibitions.getAllExhibitions();
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


    private BorderPane doExhibitionDetailsLayout( Exhibition  exhibition ) {
        setPadding(new Insets(20));
        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // EXHIBITION IMAGE
        String imageExhibition = "Images/Exhibition/Exhibition26.jpg";
        ImageView imageViewExhibition = new ImageView(new Image(imageExhibition));
        defaultSizeExhibitionImage(imageViewExhibition);

        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------

        setTop(getHeaderBox());

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // LABELS
        Label labelExhibitionName = new Label(exhibition.getNameExhibition());
        Label labelGalleryName = new Label(MainGetGalleries.getGalleryById(exhibition.getIdGallery()).getNameGallery());
        Label labelStartDate = new Label(String.valueOf(exhibition.getStartDate()));
        Label labelEndDate = new Label(String.valueOf(exhibition.getEndDate()));
        Label labelAboutExhibition= new Label("Sobre a Exposição:");
        Label labelExhibitionDetails= new Label(exhibition.getExdescription());

        labelExhibitionDetails.setWrapText(true);
        labelAboutExhibition.getStyleClass().add("my-center-label-3");
        labelExhibitionName.getStyleClass().add("my-center-label-1");

        HBox hboxStartAndEndDate = new HBox(labelStartDate, labelEndDate);
        hboxStartAndEndDate.setSpacing(10);
        VBox vBoxGalleryInfo = new VBox(labelExhibitionName, labelGalleryName, hboxStartAndEndDate);
        VBox vBoxExhibitionDetails = new VBox(labelAboutExhibition, labelExhibitionDetails);
        HBox hBoxCenterLayout = new HBox(imageViewExhibition, vBoxExhibitionDetails);
        hBoxCenterLayout.setSpacing(10);
        VBox vBoxCenterLayout = new VBox(hBoxCenterLayout, vBoxGalleryInfo);
        vBoxCenterLayout.setSpacing(10);

        // CREATE A GRIDPANE
        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        // FILL THE GRIDPANE WITH IMAGES AND LABELS
        List<Artwork> artworks = MainGetArtworks.getArtworksByExhibitionId(exhibition.getId());
        // Get the artworklist for this exhibition
        for (int i = 0; i < 7; i++) {
            int imageNum = i+1;
            Image image = new Image("Images/Artwork/AllArtworks/square" + imageNum+ ".jpg");
            ImageView imageViewArtwork = new ImageView(image);
            defaultSizeExhibitionImage(imageViewArtwork);

            // Create a new VBox for each iteration
            Label labelArtworkName = new Label("Artwork " + i);
            Label labelArtistName = new Label("Artist " + i);
            Label labelPrice = new Label("Price " + i);

            VBox vBoxLabelArtwork = new VBox(labelArtworkName, labelArtistName, labelPrice);

            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtwork, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);
        }

        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(grid);

        VBox vBoxGlobalCenterLayout = new VBox(vBoxCenterLayout, scrollPane);
        vBoxGlobalCenterLayout.setSpacing(20);

        setCenter(vBoxGlobalCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        setBottom(getFooterBox());

        // ---------------------------------------------- END  PLUS ----------------------------------------------

        return this;

    }

    private GridPane buildThiExhibitionArtworkGrid(List<Artwork> artworkList){

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

        hyperlinkExhibition.getStyleClass().add("actual-page-hyperlink");

        // Text Fields
        String searchOrigText = "Procurar por artista, galeria, exposição ou obra de arte";
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
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    private void handleSearchIconSelection(TextField textFieldSearch ){
        ScrollPane finalScrollPane = new ScrollPane();
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByName(searchText);
            if(artists != null){
                // finalScrollPane.setContent(filteredGrid(artists));
                this.setCenter(finalScrollPane);
            }
            else{
                getScene().setRoot(new ShowErrorArtist());
            }
            this.setCenter(finalScrollPane);

        }
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

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeExhibitionImage(ImageView imageView){
        imageView.setFitHeight(250); // Ajuste a altura conforme necessário
        imageView.setFitWidth(380);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

    public void textFieldSearchDefault(TextField textField) {
        textField.setPrefSize(150, 15);
        textField.setOnMouseClicked(e -> textField.clear());
    }

    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(300); // Ajuste a altura conforme necessário
        imageView.setFitWidth(360);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

}
