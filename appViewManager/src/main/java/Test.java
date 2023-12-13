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
        primaryStage.setTitle("Iuvennis ManagerApp");

        // CREATE SCENE
        Scene detailsArtworkScene = new Scene(new DetailsSceneArtwork(), 800, 600);
        Scene detailsArtistScene = new Scene(new DetailsSceneArtist(), 800, 600);
        Scene detailsGalleryScene = new Scene(new DetailsSceneGallery(), 800, 600);
        Scene detailsExhibitionScene = new Scene(new DetailsSceneExhibition(), 800, 600);

        Scene sceneArtwork = new Scene(new SceneArtwork(), 800, 600);
        Scene sceneArtist = new Scene(new ManageArtist(), 800, 600);
        Scene sceneExhibition = new Scene(new SceneExhibition(), 800, 600);
        Scene sceneGallery = new Scene(new SceneGallery(), 800, 600);

        // SET FIRST SCENE AND SHOW
        primaryStage.setScene(detailsExhibitionScene);
        primaryStage.show();
    }
}