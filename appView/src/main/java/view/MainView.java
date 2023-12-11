package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainView extends BorderPane {

    public MainView() {

        doLayout();

        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        // Controls

        // Hyperlinks
        Hyperlink hyperlinkArtwork = new Hyperlink("Obras de Arte");
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperlinkSlideShow = new Hyperlink("SlideShow");

        // Labels
        Label labelCenterArtwork = new Label("Mundo~Obras de Arte");
        Label labelCenterArtist = new Label("Mundo~Artistas");
        Label labelCenterGallery = new Label("Mundo~Galerias");
        Label labelCenterExhibition = new Label("Mundo~Exposições");
        Label labelBottonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        labelCenterArtwork.getStyleClass().add("my-label");

        // Text Fields
        TextField textFieldSearch = new TextField("Procurar por artista, galeria, exposição ou obra de arte");
        textFieldSearch.setPrefSize(800, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

        // Buttons
        Button buttonArtwork = new Button("OBRAS");
        Button buttonArtist = new Button("ARTISTAS");
        Button buttonGallery = new Button("GALERIAS");
        Button buttonExhibition = new Button("EXPOSIÇÕES");

        // Imagens
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

        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        String imageMainArtwork = "Images/Main/artwork01.jpg";
        ImageView imageViewMainArtwork = new ImageView(new Image(imageMainArtwork));
        defaultSizeMainImage(imageViewMainArtwork);

        String imageMainArtist = "Images/Main/artista2.jpg";
        ImageView imageViewMainArtist = new ImageView(new Image(imageMainArtist));
        defaultSizeMainImage(imageViewMainArtist);

        String imageMainGallery = "Images/Main/museo1.jpg";
        ImageView imageViewMainGallery = new ImageView(new Image(imageMainGallery));
        defaultSizeMainImage(imageViewMainGallery);

        String imageMainExhibition = "Images/Main/exposicao3.jpg";
        ImageView imageViewMainExhibition = new ImageView(new Image(imageMainExhibition));
        defaultSizeMainImage(imageViewMainExhibition);

        // Layout Top
        HBox hBoxSearch = new HBox(logoView, textFieldSearch, searchIconView);
        hBoxSearch.setSpacing(20);
        HBox hBoxHyperlink = new HBox(hyperlinkArtwork,hyperlinkArtist,hyperlinkGallery, hyperlinkExhibition, hyperlinkSlideShow);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);


        VBox vBoxTop = new VBox(hBoxSearch,hBoxHyperlink);

        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // Layout Bottom
        HBox bottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        bottomImages.setSpacing(10);
        HBox bottomLayout = new HBox(labelBottonStatus,bottomImages);
        bottomLayout.setSpacing(500);
        setBottom(bottomLayout);

        // Layout Center
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(0,0,10,0));

        // Configuração de columnSpan e rowSpan para cobrir a célula (0,0)
        // Ajustar as propriedades de alinhamento
        grid.add(imageViewMainArtwork,0,0);
        grid.add(imageViewMainArtist,0,1);
        grid.add(imageViewMainExhibition,0,2);
        grid.add(imageViewMainGallery,0,3);

        VBox vBoxArtwork = new VBox(labelCenterArtwork,buttonArtwork);
        VBox vBoxArtist = new VBox(labelCenterArtist,buttonArtist);
        VBox vBoxGallery = new VBox(labelCenterGallery,buttonGallery);
        VBox vBoxExhibition = new VBox(labelCenterExhibition,buttonExhibition);

        grid.add(vBoxArtwork, 1, 0);
        grid.add(vBoxArtist, 1,1);
        grid.add(vBoxGallery, 1,2);
        grid.add(vBoxExhibition, 1,3);

        vBoxArtwork.setAlignment(Pos.CENTER);
        vBoxArtist.setAlignment(Pos.CENTER);
        vBoxGallery.setAlignment(Pos.CENTER);
        vBoxExhibition.setAlignment(Pos.CENTER);

        labelCenterArtwork.setAlignment(Pos.CENTER);
        labelCenterArtist.setAlignment(Pos.CENTER);
        labelCenterGallery.setAlignment(Pos.CENTER);
        labelCenterExhibition.setAlignment(Pos.CENTER);

        // Fazer com que os botões preencham a grelha uniformemente

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(50);
        cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
        cc.setFillWidth(true); // ask nodes to fill space for column
        grid.getColumnConstraints().addAll(cc, cc);

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS) ; // allow row to grow
        rc.setFillHeight(true); // ask nodes to fill height for row
        grid.getRowConstraints().addAll(rc, rc, rc, rc);

        labelCenterArtwork.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterArtist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterGallery.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterExhibition.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setCenter(grid);

        // Configurar a ação do botão para mudar cenario
        buttonArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

        hyperlinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
        hyperlinkSlideShow.setOnAction(e -> getScene().setRoot(new SlideShowView()));

    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeMainImage (ImageView imageView){
        imageView.setFitHeight(100); // Ajuste a altura conforme necessário
        imageView.setFitWidth(364);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

}
