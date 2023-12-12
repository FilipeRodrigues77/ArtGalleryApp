package view;

import domain.Artwork;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class ShowFullImage extends BorderPane {

    double sceneSize ;
    Artwork artwork;

    public ShowFullImage(String imageToShowRef,Artwork artwork, double sceneSize) {
        doLayout(imageToShowRef);
        this.artwork = artwork;
        this.sceneSize = sceneSize;

    }

    private void doLayout(String reference ) {

        setPadding(new Insets(20));

        Image backArrow = new Image("Icons/backArrow.png");
        ImageView arrowView = new ImageView(backArrow);
        arrowView.setFitHeight(25);
        arrowView.setFitWidth(50);
        this.setTop(arrowView);
        arrowView.setOnMouseClicked(e -> getScene().setRoot(new SceneArtwork().doDetailsLayout(artwork)));

        String imageArtwork = reference.replace("{imageVersion}", "large");
        Image imageToDisplay = new Image(imageArtwork);

        // The below code is used to resize the larger image to fit in our application window;
        double resizePercentage ;
        if(imageToDisplay.getHeight() >= sceneSize ){
           resizePercentage = 50;
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
