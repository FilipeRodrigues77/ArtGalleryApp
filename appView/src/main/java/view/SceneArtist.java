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

public class SceneArtist extends BorderPane {

    public SceneArtist() {

        doLayout();

        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(30));

        // Controls

        // Hyperlinks
        Hyperlink hyperlinkArtwork = new Hyperlink("Obras de Arte");
        Hyperlink hyperlinkMain = new Hyperlink("Principal");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");

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
        Button buttonMain = new Button("PRINCIPAL");
        Button buttonGallery = new Button("GALERIAS");
        Button buttonExhibition = new Button("EXPOSIÇÕES");

        // Imagens
        // Caminho para o arquivo de imagem
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        String imageMagnifier  = "Icons/IconeLupa.jpg";
        ImageView imageViewMagnifier = new ImageView(new Image(imageMagnifier));
        defaultSizeIcon(imageViewMagnifier);

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
        HBox hBoxSearch = new HBox(textFieldSearch, imageViewMainExhibition);
        HBox hBoxHyperlink = new HBox(hyperlinkArtwork,hyperlinkMain,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(10);
        VBox vBoxTop = new VBox(hBoxSearch,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // Layout Bottom
        setBottom(labelBottonStatus);

        // Layout Center
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(0,0,10,0));

        // Configuração de columnSpan e rowSpan para cobrir a célula (0,0)
        // Ajustar as propriedades de alinhamento
        grid.add(imageViewMainArtwork,0,0);
        grid.add(imageViewMainArtist,0,1);
        grid.add(imageViewMainExhibition,0,2);
        grid.add(imageViewMainGallery,0,3);

        VBox vBoxArtwork = new VBox(labelCenterArtwork,buttonArtwork);
        VBox vBoxArtist = new VBox(labelCenterArtist,buttonMain);
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
        buttonMain.setOnAction(e -> getScene().setRoot(new MainView()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

        hyperlinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(30); // Ajuste a altura conforme necessário
        imageView.setFitWidth(30);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeMainImage (ImageView imageView){
        imageView.setFitHeight(100); // Ajuste a altura conforme necessário
        imageView.setFitWidth(364);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

}
