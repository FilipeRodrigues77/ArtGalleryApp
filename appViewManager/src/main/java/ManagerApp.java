import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

/**
 * The {@code ManagerApp} class serves as the entry point for the Iuvenis Artem ManagerApp application. It launches
 * the application and sets up the primary stage with the main scene.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class ManagerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvenis Artem ManagerApp");
        Scene mainScene = new Scene(new ManageMainView(), 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}