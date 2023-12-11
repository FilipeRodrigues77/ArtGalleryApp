package view;

import domain.Artist;
import domain.Artwork;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import presenter.*;

import java.util.List;

public class SceneArtist extends BorderPane {

    public SceneArtist() {
        doLayout();
        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Labels
        Label labelfilter = new Label("Filtro =");

        // Text Fields
        String searchOrigText = "Procurar por artista, galeria, exposição ou obra de arte";
        TextField textFieldSearch = new TextField(searchOrigText);
        setOriginalDescription(textFieldSearch,searchOrigText);
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());


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


        // Images

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

        // ADD PAGE HEADER ELEMENTS
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel, textFieldSearchByNation, textFieldSearchByBirthdate, textFieldSearchByDeathdate);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink, hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // CREATE A GRIDPANE
        List<Artist> listArtists = MainGetArtists.getAllArtists();
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildMainGrid(listArtists));

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // DEFINE A HBOX THAT WILL CONTRAIN ALL THE BOTTOM ELEMENTS
        HBox bottomLayout = new HBox();

        // IMAGES

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox bottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        bottomImages.setSpacing(10);

        // STATUS LABEL
        Label labelBottonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");
        bottomLayout.getChildren().addAll(labelBottonStatus,bottomImages);
        bottomLayout.setPadding(new Insets(20,0,0,0));
        bottomLayout.setSpacing(500);

        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(bottomLayout);


        // ---------------------------------------------- END  PLUS ----------------------------------------------
        // LAST STEP: CENTER THE SCROLL_PANE
        this.setCenter(scrollPane);

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

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

    private GridPane buildMainGrid(List<Artist> artistList){

        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

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

            String artistOrigen = MainGetArtists.getArtistById(artist.getId()).getName();
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

            imageViewArtist.setOnMouseClicked(e-> doArtistDetailsLayout(artist));

            VBox vBoxLabelArtist = new VBox(hyperArtistName, hyperArtistOrigen, hyperBirthDeathDate);
            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtist, col * 2, row);
            grid.add(vBoxLabelArtist, col * 2, row + 1);

            imageViewArtist.setOnMouseClicked(e->doArtistDetailsLayout(artist));
        }

        return grid;
    }

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

            imageViewArtist.setOnMouseClicked(e-> doArtistDetailsLayout(artist));

            VBox vBoxLabelArtist = new VBox(hyperArtistName, hyperArtistOrigen, hyperBirthDeathDate);
            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtist, col * 2, row);
            grid.add(vBoxLabelArtist, col * 2, row + 1);

            imageViewArtist.setOnMouseClicked(e->doArtistDetailsLayout(artist));
        }

        return grid;
    }

    private void doArtistDetailsLayout(Artist artist) {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // TEXT FIELDS
        TextField textFieldSearch = new TextField("Procurar por artista, galeria, exposição ou obra de arte");
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

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

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // LINKEDIN IMAGE
        String imageArtwork = artist.getReferenceImage();
        ImageView imageViewArtist = new ImageView(new Image(imageArtwork));
        defaultSizeArtistImage(imageViewArtist);

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
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // LABELS
        Label labelArtistName = new Label(artist.getName());
        Label labelNationality = new Label(artist.getNationality());
        Label labelBirthday = new Label(artist.getBirthdate());
        Label labelDeathday = new Label(artist.getDeathdate());
        Label labelHeaderAboutArtist = new Label("Sobre o Artista");

        TextArea bioTextArea = new TextArea();
        bioTextArea.setPadding(new Insets(20, 0, 0, 0));
        bioTextArea.setPrefWidth(300);
        bioTextArea.setPrefHeight(200);
        bioTextArea.setText(artist.getBiography());

        labelArtistName.getStyleClass().add("my-center-label-1");

        VBox vBoxArtistInfo = new VBox(labelArtistName, labelNationality, labelBirthday, labelDeathday);
        VBox vBoxArtistBiography = new VBox(labelHeaderAboutArtist, bioTextArea);

        VBox vBoxArtistImage = new VBox(imageViewArtist);
        HBox hBoxCenterLayout = new HBox(vBoxArtistImage, vBoxArtistInfo, vBoxArtistBiography);
        hBoxCenterLayout.setSpacing(50);


        List<Artwork> artistsArtworks = MainGetArtworks.getArtworksByArtistId(artist.getId());

        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(buildThisArtisArtworkGrid(artistsArtworks));

        VBox vBoxCenterLayout = new VBox(hBoxCenterLayout, scrollPane);
        vBoxCenterLayout.setSpacing(20);

        setCenter(vBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // LABEL
        Label labelBottonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);

        // HBOX BOTTOM
        HBox hBoxBottomLayout = new HBox(labelBottonStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        hBoxBottomLayout.setSpacing(500);
        setBottom(hBoxBottomLayout);

        // ---------------------------------------------- END  PLUS ----------------------------------------------

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

    }

    private GridPane buildThisArtisArtworkGrid(List<Artwork> artworkList){

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
            defaultSizeArtistImage(imageViewArtwork);

            // Create a new VBox for each iteration
            String artworkName = artwork.getName();
            Hyperlink hyperArtworkName;

            if (artworkName.length() < maxTextLength){
                hyperArtworkName = new Hyperlink(artworkName);
            } else{
                hyperArtworkName = new Hyperlink(artworkName.substring(0,maxTextLength)+"...");
            }


            String galleryName = MainGetGalleryById.getGalleryById(artwork.getIdGallery()).getNameGallery();
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
        imageView.setPreserveRatio(true);
    }

    public void textFieldSearchDefault(TextField textField) {
        textField.setPrefSize(150, 15);
        textField.setOnMouseClicked(e -> textField.clear());
    }

}
