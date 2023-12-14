import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class TestManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvennis ManagerApp");

        // CREATE SCENE
        Scene sceneCreateArtist = new Scene(new CreateArtistView(), 800, 600);


        // SET FIRST SCENE AND SHOW
        primaryStage.setScene(sceneCreateArtist);
        primaryStage.show();
    }
}