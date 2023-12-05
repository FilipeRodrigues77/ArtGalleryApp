package org.example;



import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class IAInterface extends BorderPane {

    public IAInterface() {

        doLayout();
        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(30));

        // Controlos
        Label labelAboutUs = new Label("Sobre nós");
        Label labelContact = new Label("Contato");
        Label labelHelp = new Label("Ajuda");
        Label labelStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        Label labelArtwork = new Label("Obras de Arte");
        Label labelArtist = new Label("Artistas");
        Label labelGallery = new Label("Galerias");
        Label labelExhibition = new Label("Exposições");

        // Imagens
        // Adiciona células à GridPane
        ImageView imageMainArtwork = new ImageView();
        imageMainArtwork.getStyleClass().add("image-main-artwork");





        // Layout Top
        HBox hBoxTop = new HBox(labelAboutUs,labelContact,labelHelp);
        hBoxTop.setPrefHeight(50);
        hBoxTop.setSpacing(10);
        setMargin(hBoxTop, new Insets(0, 0, 20, 0));
        setTop(hBoxTop);

        // Layout Bottom
        setBottom(labelStatus);

        // Layout Center
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(0,10,10,0));

        grid.add(labelArtwork, 1, 1);
        grid.add(labelArtist, 5, 8);
        grid.add(labelGallery, 0, 7);
        grid.add(labelExhibition, 5, 2);

        grid.add(imageMainArtwork, 2,2);

        // Fazer com que os botões preencham a grelha uniformemente
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(25);
        cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
        cc.setFillWidth(true); // ask nodes to fill space for column
        grid.getColumnConstraints().addAll(cc, cc, cc, cc, cc, cc, cc, cc, cc, cc);

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS) ; // allow row to grow
        rc.setFillHeight(true); // ask nodes to fill height for row
        grid.getRowConstraints().addAll(rc, rc, rc, rc, rc, rc, rc, rc, rc, rc);

        labelArtwork.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelArtist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelGallery.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelExhibition.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setCenter(grid);
    }
}
