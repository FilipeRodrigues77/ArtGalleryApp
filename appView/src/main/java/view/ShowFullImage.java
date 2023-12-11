package view;

import domain.Artwork;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class ShowFullImage extends BorderPane {

    public ShowFullImage(String imageToShowRef ) {
        doLayout(imageToShowRef);

    }

    private void doLayout(String reference ) {
        int sceneLength = 600;
        setPadding(new Insets(20));

        Image backArrow = new Image("Icons/backArrow.png");
        ImageView arrowView = new ImageView(backArrow);
        arrowView.setFitHeight(25);
        arrowView.setFitWidth(50);
        this.setTop(arrowView);
        arrowView.setOnMouseClicked(e -> getScene().setRoot(new SceneArtwork()));

        String imageArtwork = reference.replace("{imageVersion}", "large");
        Image imageToDisplay = new Image(imageArtwork);

        double resizePercentage ;
        if(imageToDisplay.getHeight() >= sceneLength ){
           resizePercentage = 50; // Resize to 50% of the original size
        }
        else {
            resizePercentage = 60;
        }

        // Calculate the new dimensions based on the percentage
        double newWidth = imageToDisplay.getWidth() * (resizePercentage / 100);
        double newHeight = imageToDisplay.getHeight() * (resizePercentage / 100);

        ImageView imageView = new ImageView(imageToDisplay);
        imageView.setFitWidth(newWidth);
        imageView.setFitHeight(newHeight);

        this.setCenter(imageView);

    }
}
