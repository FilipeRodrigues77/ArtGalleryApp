import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

/**
 * The {@code Art} class serves as the main entry point for the Iuvenis Artem application.
 * It extends {@code Application} and defines the main method ({@code main}) and the
 * start method for initialising and launching the application window.
 * <p>
 * The main method calls the launch method to start the JavaFX application. The start
 * method sets up the primary stage, titles it, creates the main scene with the
 * {@code MainView} as its root, and displays the primary stage.
 * <p>
 * The application provides an interface for users to interact with the Iuvenis Artem
 * platform.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class Art extends Application {

    /**
     * The main entry point for the Iuvenis Artem application.
     * Calls the launch method to start the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and launches the primary stage of the Iuvenis Art application. Sets
     * up the title, creates the main scene with the {@code MainView} as its root, and
     * displays the primary stage.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Iuvenis Art");
        Scene mainScene = new Scene(new MainView(), 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}