package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

        // Labels
        Label labelfilter = new Label("Filtro =");

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
        ObservableList<String> mediumOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
        );

        ObservableList<String> priceOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
        );

        ObservableList<String> dateOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
        );

        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
        );

        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU
        ComboBox<String> mediumMenu = new ComboBox<>(mediumOptions);
        mediumMenu.setValue("Medium");

        ComboBox<String> priceMenu = new ComboBox<>(mediumOptions);
        priceMenu.setValue("Preço");

        ComboBox<String> dateMenu = new ComboBox<>(mediumOptions);
        dateMenu.setValue("Data Criação");

        ComboBox<String>categoryMenu = new ComboBox<>(mediumOptions);
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
        for (int i = 0; i < 85; i++) {
            int imageNum = i+1;
            Image image = new Image("Images/Square/square" + imageNum+ ".jpg");
            ImageView imageViewArtwork = new ImageView(image);
            defaultSizeArtworkImage(imageViewArtwork);


            // Create a new VBox for each iteration
            Label labelArtworkName = new Label("Artwork " + i);
            Label labelArtistName = new Label("Artist " + i);
            Label labelGalleryName = new Label("Gallery " + i);
            Label labelPrice = new Label("Price " + i);

            VBox vBoxLabelArtwork = new VBox(labelArtworkName, labelArtistName, labelGalleryName, labelPrice);

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

}
