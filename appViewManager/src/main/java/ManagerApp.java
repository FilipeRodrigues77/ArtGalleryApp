import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class ManagerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvennis ManagerApp");

        Scene mainScene = new Scene(new ManageMainView(), 800, 600);
        Scene sceneArtwork = new Scene(new ManageArtwork(), 800, 600);
        Scene sceneArtist = new Scene(new ManageArtist(), 800, 600);
        Scene sceneExhibition = new Scene(new ManageExhibition(), 800, 600);
        Scene sceneGallery = new Scene(new ManageGallery(), 800, 600);

        primaryStage.setScene(mainScene);

        primaryStage.show();
    }
}