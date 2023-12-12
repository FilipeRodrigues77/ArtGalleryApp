package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DetailsSceneArtwork extends BorderPane {

    public DetailsSceneArtwork() {
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
        String imageArtwork = "Images/Artwork/AllArtworks/square1.jpg";
        ImageView imageViewArtwork = new ImageView(new Image(imageArtwork));
        defaultSizeArtworkImage(imageViewArtwork);

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
        Label labelArtworkName = new Label("Nome da Obra de Arte");
        Label labelArtistName = new Label("Artista Relacionado");
        Label labelGalleryName = new Label("Galeria");
        Label labelArtworkMedium = new Label("Método");
        Label labelArtworkCategory = new Label("Categoria");
        Label labelArtworkSize = new Label("Tamanho");
        Label labelPrice = new Label("Preço");
        Label labelBiography = new Label("Aqui está a biografia do artista");

        labelArtworkName.getStyleClass().add("my-center-label-1");
        labelArtistName.getStyleClass().add("my-center-label-2");
        labelGalleryName.getStyleClass().add("my-center-label-2");
        labelArtworkMedium.getStyleClass().add("my-center-label-3");
        labelArtworkCategory.getStyleClass().add("my-center-label-3");
        labelArtworkSize.getStyleClass().add("my-center-label-3");
        labelPrice.getStyleClass().add("my-center-label-4");

        VBox vBoxArtworkImage = new VBox(imageViewArtwork);
        VBox vBoxLabel = new VBox(labelArtworkName, labelArtistName, labelGalleryName, labelArtworkMedium, labelArtworkSize,labelArtworkCategory, labelPrice);
        vBoxLabel.setAlignment(Pos.TOP_LEFT);
        vBoxLabel.setSpacing(10);
        HBox hBoxCenterLayout = new HBox(vBoxArtworkImage, vBoxLabel);
        hBoxCenterLayout.setSpacing(50);
        HBox hBoxBiography = new HBox(labelBiography);
        hBoxBiography.setPadding(new Insets(15,0,0,0));
        VBox vBoxCenterLayout = new VBox(hBoxCenterLayout, hBoxBiography);
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

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeArtworkImage(ImageView imageView){
        imageView.setFitHeight(300); // Ajuste a altura conforme necessário
        imageView.setFitWidth(360);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

}
