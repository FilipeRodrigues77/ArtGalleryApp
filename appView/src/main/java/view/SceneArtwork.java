package view;

import domain.Artist;
import domain.Artwork;
import javafx.geometry.Pos;
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



public class SceneArtwork extends BorderPane {

    public SceneArtwork() {
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


        // Text Fields
        TextField textFieldSearch = new TextField("Procurar por artista, galeria, exposição ou obra de arte");
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

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

        // ADD PAGE HEADER ELEMENTS
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox();

        // SET THE OPTIONS FOR EACH MENU
        // NOTE: THIS WILL EVENTUALLY BECOME A METHOD

        /*
        Maybe price needs to be a text field where user can input the max value and the min value
         */
        ObservableList<String> priceOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
        );

        ObservableList<String> dateOptions = FXCollections.observableArrayList(
                "1400-1599",
                "1600-1799",
                "1800-1999",
                "2000-2023"
        );


        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU
        List<Artwork> artworkList = MainGetAllArtworks.getAllArtworks();

        ComboBox<String> mediumMenu = new ComboBox<>(createOptionsMenu(artworkList,"medium"));
        mediumMenu.setMaxWidth(100);
        mediumMenu.setValue("Medium");

        mediumMenu.setOnAction(e->{
            // get the selected item*
            String selected = mediumMenu.getSelectionModel().getSelectedItem();
            if(selected != null){
               List<Artwork> artworks = MainGetArtworkByMedium.getArtworks(selected);
                System.out.println(artworks);
            }
        });


        ComboBox<String> priceMenu = new ComboBox<>(priceOptions);
        priceMenu.setValue("Preço");

        ComboBox<String> dateMenu = new ComboBox<>(dateOptions);
        dateMenu.setValue("Data Criação");

        ComboBox<String>categoryMenu = new ComboBox<>(createOptionsMenu(artworkList,"category"));
        categoryMenu.setMaxWidth(120);
        categoryMenu.setValue("Categoria");


        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // ADD ELEMENTS FOR THE MENU HBOX
        hBoxMenu.getChildren().addAll(filterLabel, mediumMenu, priceMenu, dateMenu,categoryMenu);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink, hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // CREATE A GRIDPANE
        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        // FILL THE GRIDPANE WITH IMAGES AND LABELS
        for (int i = 0; i < 8; i++) {
             // int imageNum = i+1;

             int maxTextLength = 23;
            Artwork artwork = artworkList.get(i);
            String imageRef = artwork.getReferenceImage().replace("{imageVersion}","square");
            Image image = new Image(imageRef);
            ImageView imageViewArtwork = new ImageView(image);
            defaultSizeArtworkImage(imageViewArtwork);



            // Create a new VBox for each iteration

            String artworkName = artwork.getName();
            Hyperlink hyperArtworkName;

            if (artworkName.length() < maxTextLength){
                 hyperArtworkName = new Hyperlink(artworkName);
            } else{
                 hyperArtworkName = new Hyperlink(artworkName.substring(0,maxTextLength)+"...");
            }


            String artistName = MainGetArtistById.getArtistById(artwork.getIdArtist()).getName();
            Hyperlink hyperArtistName;
            if (artistName.length() < maxTextLength){
                 hyperArtistName = new Hyperlink(artistName);
            } else{
                 hyperArtistName = new Hyperlink(artistName.substring(0,maxTextLength)+"...");
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

            hyperArtworkName.setOnAction(e-> setArtworkLabelsOnAction(artwork.getReferenceImage(), artworkName,artistName,galleryName,
                    artwork.getMedium(),artwork.getCategory(), artwork.getDimensionCm(),artwork.getDimensionIN(),price));

            hyperArtistName.setOnAction(e-> setArtworkLabelsOnAction(artwork.getReferenceImage(),artworkName,artistName,galleryName,
                    artwork.getMedium(),artwork.getCategory(), artwork.getDimensionCm(),artwork.getDimensionIN(),price));

            hyperGalleryName.setOnAction(e-> setArtworkLabelsOnAction(artwork.getReferenceImage(),artworkName,artistName,galleryName,
                    artwork.getMedium(),artwork.getCategory(), artwork.getDimensionCm(),artwork.getDimensionIN(),price));

            imageViewArtwork.setOnMouseClicked(e-> setArtworkLabelsOnAction(artwork.getReferenceImage(),artworkName,artistName,galleryName,
                    artwork.getMedium(),artwork.getCategory(), artwork.getDimensionCm(),artwork.getDimensionIN(),price));


            VBox vBoxLabelArtwork = new VBox(hyperArtworkName, hyperArtistName, hyperGalleryName, hyperPrice);

            // CALCULATE THE COORDINATE FOR EACH CELL (4 COLUMNS)
            int col = i % 4;
            int row = i / 4 * 2; // MULTIPLY BY TWO TO JUMP LINE ON EACH ITTERATION

            // ADD IMAGES AND LABELS TO EACH CALCULATED SPOT
            grid.add(imageViewArtwork, col * 2, row);
            grid.add(vBoxLabelArtwork, col * 2, row + 1);
        }

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
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(grid);
        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(bottomLayout);


        // ---------------------------------------------- END  PLUS ----------------------------------------------
        // LAST STEP: CENTER THE SCROLL_PANE
        this.setCenter(scrollPane);

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

    }


    private void setArtworkLabelsOnAction(String imageRef,String artworkName,String artistName, String galleryName,String medium, String category,
                                          String sizeCM , String sizeIN, String price){

        doDetailsLayout(imageRef,artworkName, artistName, galleryName, medium, category, sizeCM , sizeIN, price);
    }

    private ObservableList<String> createOptionsMenu (List<Artwork>artworkList, String field){

        List<String> testing = new ArrayList<>();

        String menuField = field.toLowerCase();

        switch (menuField){
            case "medium": for(Artwork art : artworkList){
                String option = art.getMedium();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
            break;
            case "category": for(Artwork art : artworkList){
                String option = art.getCategory();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
            break;

            case "price": for(Artwork art : artworkList){
                String option = String.valueOf(art.getPrice());
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }

        }

        return FXCollections.observableArrayList(testing);
    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(160); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }


    private void doDetailsLayout(String imageRef,String artworkName,String artistName, String galleryName,String medium, String category,
                                 String sizeCM , String sizeIN, String price) {

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
        String imageArtwork = imageRef.replace("{imageVersion}", "square");
        ImageView imageViewArtwork = new ImageView(new Image(imageArtwork));
        imageViewArtwork.setFitHeight(300);
        imageViewArtwork.setFitWidth(360);
        // defaultSizeArtworkImage(imageViewArtwork);

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
        Label labelArtworkName = new Label(artworkName);
        Label labelArtistName = new Label(artistName);
        Label labelGalleryName = new Label(galleryName);
        Label labelArtworkMedium = new Label(medium);
        Label labelArtworkCategory = new Label(category);
        Label labelArtworkSizeCM = new Label(sizeCM.substring(1,sizeCM.length()-1));
        Label labelArtworkSizeIN = new Label(sizeIN.substring(1,sizeIN.length()-1));
        Label labelPrice = new Label(price);

        // This should be a button that links to the artists details page:
        // Label labelBiography = new Label("Aqui está a biografia do artista");

        labelArtworkName.getStyleClass().add("my-center-label-1");
        labelArtistName.getStyleClass().add("my-center-label-2");
        labelGalleryName.getStyleClass().add("my-center-label-2");
        labelArtworkMedium.getStyleClass().add("my-center-label-3");
        labelArtworkCategory.getStyleClass().add("my-center-label-3");
        labelArtworkSizeCM.getStyleClass().add("my-center-label-3");
        labelArtworkSizeIN.getStyleClass().add("my-center-label-3");
        labelPrice.getStyleClass().add("my-center-label-4");

        VBox vBoxArtworkImage = new VBox(imageViewArtwork);
        VBox vBoxLabel = new VBox(labelArtworkName, labelArtistName, labelGalleryName, labelArtworkMedium,
                labelArtworkCategory, labelArtworkSizeCM,labelArtworkSizeIN, labelPrice);

        vBoxLabel.setAlignment(Pos.TOP_LEFT);
        vBoxLabel.setSpacing(10);
        HBox hBoxCenterLayout = new HBox(vBoxArtworkImage, vBoxLabel);
        hBoxCenterLayout.setSpacing(50);
        // Box hBoxBiography = new HBox(labelBiography);
        VBox vBoxCenterLayout = new VBox(hBoxCenterLayout);
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

}
