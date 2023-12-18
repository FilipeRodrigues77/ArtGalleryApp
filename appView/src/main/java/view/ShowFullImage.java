package view;

import domain.Artwork;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * The {@code ShowFullImage} class represents a scene that displays a full-size image
 * of an artwork. It extends {@code BorderPane} and provides a layout with a back arrow
 * and the full-size image of the artwork.
 *
 * @author Nuely Furtado and Filipe Alves
 * @version v1.0
 */
public class ShowFullImage extends BorderPane {

    double sceneSize ;
    Artwork artwork;

    /**
     * Constructs a new {@code ShowFullImage} object with the specified image reference,
     * artwork, and scene size. Initializes the layout and displays the full-size image.
     *
     * @param imageToShowRef The image reference to be displayed.
     * @param artwork        The artwork associated with the full-size image.
     * @param sceneSize      The size of the scene where the full-size image is displayed.
     */
    public ShowFullImage(String imageToShowRef,Artwork artwork, double sceneSize) {
        doLayout(imageToShowRef);
        this.artwork = artwork;
        this.sceneSize = sceneSize;

    }

    /**
     * Sets up the layout for the scene, including a back arrow and the full-size image of
     * the artwork.
     *
     * @param reference The image reference to be displayed.
     */
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
