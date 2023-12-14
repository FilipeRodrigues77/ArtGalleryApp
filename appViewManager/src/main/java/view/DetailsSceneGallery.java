package view;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetailsSceneGallery extends BorderPane {

    public DetailsSceneGallery() {
        doLayout();
        getStylesheets().add("appStyleLight.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        hyperlinkGallery.getStyleClass().add("actual-page-hyperlink");

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

        // GALLERY IMAGE
        String imageGallery = "Images/Gallery/Gallery1.jpg";
        ImageView imageViewGallery = new ImageView(new Image(imageGallery));
        defaultSizeGalleryImage(imageViewGallery);

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
        Label labelGalleryName = new Label("Nome da Galeria");
        Label labelGalleryRegion = new Label("Região");
        Label labelGalleryEmail = new Label("Email da Galeria @email.com");

        labelGalleryName.getStyleClass().add("my-center-label-1");

        VBox vBoxGalleryInfo = new VBox(labelGalleryName, labelGalleryRegion, labelGalleryEmail);

        VBox vBoxGalleryImage = new VBox(imageViewGallery);
        VBox vBoxCenterLayout = new VBox(vBoxGalleryImage, vBoxGalleryInfo);
        vBoxCenterLayout.setSpacing(10);

        // CREATE A GRIDPANE
        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        // FILL THE GRIDPANE WITH IMAGES AND LABELS
        for (int i = 0; i < 7; i++) {
            int imageNum = i+1;
            Image image = new Image("Images/Artwork/AllArtworks/square" + imageNum+ ".jpg");
            ImageView imageViewArtwork = new ImageView(image);
            defaultSizeArtworkImage(imageViewArtwork);

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
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtist()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new ManageMainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(130); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

    public void defaultSizeGalleryImage(ImageView imageView){
        imageView.setFitHeight(200); // Ajuste a altura conforme necessário
        imageView.setFitWidth(800);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

}