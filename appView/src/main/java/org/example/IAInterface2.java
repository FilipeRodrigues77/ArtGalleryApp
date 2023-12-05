package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class IAInterface2 extends BorderPane {

    public IAInterface2() {
        doLayout();
        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        setPadding(new Insets(10));

        // Top Bar
        HBox hBoxTop = new HBox(createNavigationLabel("Home"), createNavigationLabel("Artworks"), createNavigationLabel("Artists"), createNavigationLabel("Exhibitions"), createNavigationLabel("Galleries"));
        hBoxTop.setSpacing(10);
        hBoxTop.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 10;");
        setTop(hBoxTop);

        // Center Content
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        Label labelArtwork = createCategoryLabel("Artworks");
        Label labelArtist = createCategoryLabel("Artists");
        Label labelExhibition = createCategoryLabel("Exhibitions");
        Label labelGallery = createCategoryLabel("Galleries");

        grid.add(labelArtwork, 0, 0);
        grid.add(labelArtist, 1, 0);
        grid.add(labelExhibition, 2, 0);
        grid.add(labelGallery, 3, 0);

        ImageView imageMainArtwork = new ImageView();
        imageMainArtwork.getStyleClass().add("image-main-artwork");
        grid.add(imageMainArtwork, 0, 1, 4, 1);

        setCenter(grid);
    }

    private Label createNavigationLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("navigation-label");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        return label;
    }

    private Label createCategoryLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("category-label");
        return label;
    }
}

