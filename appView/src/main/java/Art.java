import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class Art extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvennis Art");

        // Criar as três cenas
        Scene mainScene = new Scene(new MainView(), 800, 600);
        Scene sceneArtwork = new Scene(new SceneArtwork(), 800, 600);
        Scene sceneArtist = new Scene(new SceneArtist(), 800, 600);
        Scene sceneExhibition = new Scene(new SceneExhibition(), 800, 600);
        Scene sceneGallery = new Scene(new SceneGallery(), 800, 600);

        // Configurar a cena inicial
        primaryStage.setScene(mainScene);

        // Exibir a janela principal
        primaryStage.show();
    }
}