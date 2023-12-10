package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class DetailsSceneExhibition extends BorderPane {

    public DetailsSceneExhibition() {
        doLayout();
        getStylesheets().add("appStyle.css");
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

        hyperlinkExhibition.getStyleClass().add("actual-page-hyperlink");

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

        // EXHIBITION IMAGE
        String imageExhibition = "Images/Exhibition/Exhibition26.jpg";
        ImageView imageViewExhibition = new ImageView(new Image(imageExhibition));
        defaultSizeGalleryImage(imageViewExhibition);

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
        Label labelExhibitionName = new Label("Nome da Exposição");
        Label labelGalleryName = new Label("Galeria");
        Label labelStartDate= new Label("Data de Inicio");
        Label labelEndDate= new Label("Data de Fecho");
        Label labelAboutExhibition= new Label("Sobre a Exposição:");
        Label labelExhibitionDetails= new Label("Aqui estará detalhes da exposição, será uma string longa, que deve conter informações relevantes sobre a exposição em questão...");

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
        HBox.setHgrow(hBoxBottomImages, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomImages.setAlignment(Pos.CENTER_RIGHT);

        // HBOX BOTTOM
        HBox hBoxBottomStatus = new HBox(labelBottonStatus);
        HBox.setHgrow(hBoxBottomStatus, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomStatus.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxBottomLayout = new HBox(hBoxBottomStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        setBottom(hBoxBottomLayout);

        // ---------------------------------------------- END  PLUS ----------------------------------------------

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
        imageView.setFitHeight(130); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

    public void defaultSizeGalleryImage(ImageView imageView){
        imageView.setFitHeight(225); // Ajuste a altura conforme necessário
        imageView.setFitWidth(400);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

}
