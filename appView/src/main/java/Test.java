import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvennis Art");

        // CREATE SCENE
        Scene detailsArtworkScene = new Scene(new DetailsSceneArtwork(), 800, 600);
        Scene sceneArtwork = new Scene(new SceneArtwork(), 800, 600);
        Scene sceneArtist = new Scene(new SceneArtist(), 800, 600);
        Scene sceneExhibition = new Scene(new SceneExhibition(), 800, 600);
        Scene sceneGallery = new Scene(new SceneGallery(), 800, 600);

        // SET FIRST SCENE AND SHOW
        primaryStage.setScene(detailsArtworkScene);
        primaryStage.show();
    }
}