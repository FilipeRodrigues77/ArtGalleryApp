import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.*;

/**
 * The {@code LoginManagerApp} class serves as the entry point for the Iuvenis ManagerApp application. It handles user
 * authentication through a login stage and, upon successful login, launches the main application scene.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class LoginManagerApp extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private boolean loginSuccess = false;

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialises the primary stage for the application and, based on the result of the login attempt, either shows
     * the main scene or exits the application.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvenis Artem ManagerApp");
        Stage loginStage = new Stage(StageStyle.UNDECORATED);

        // VERIFY LOGIN BEFORE SHOW MAIN SCENE
        if (performLogin(loginStage)) {
            showMainScene(primaryStage);
        } else {
            primaryStage.close();
        }
    }

    /**
     * Performs user authentication by displaying a login stage.
     *
     * @param loginStage The stage used for the login interface.
     * @return {@code true} if the login is successful, {@code false} otherwise.
     */
    private boolean performLogin(Stage loginStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label labelLogin = new Label("Login:");
        grid.add(labelLogin, 0, 1);

        usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label labelPassword = new Label("Senha:");
        grid.add(labelPassword, 0, 2);

        passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button buttonLogin = new Button("Login");
        buttonLogin.getStyleClass().add("button-modern");
        buttonLogin.setOnAction(e -> {
            // LOGIN AND PASSWORD LOGIC
            if ("IuvenisArtem".equals(usernameField.getText()) && "Admin123".equals(passwordField.getText())) {
                loginSuccess = true;
                closeLoginStage(loginStage);
            } else {
                showAlert("Login Failed", "Dados inválidos, tente novamente.");
            }
        });

        Button buttonClose = new Button("Fechar");
        buttonClose.getStyleClass().add("button-modern");
        buttonClose.setOnAction(e -> closeLoginStage(loginStage));

        grid.add(buttonLogin, 1, 4);
        grid.add(buttonClose, 0, 4);

        Scene loginScene = new Scene(grid, 400, 300);
        loginScene.getStylesheets().add(getClass().getResource("appStyleLight.css").toExternalForm());
        loginStage.setScene(loginScene);
        loginStage.showAndWait();
        return loginSuccess;
    }

    /**
     * Closes the provided login stage.
     *
     * @param loginStage The stage to be closed.
     */
    private void closeLoginStage(Stage loginStage) {
        loginStage.close();
    }

    /**
     * Displays the main application scene using the primary stage.
     *
     * @param primaryStage The primary stage for the application.
     */
    private void showMainScene(Stage primaryStage) {
        Scene mainScene = new Scene(new ManageMainView(), 800, 600);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Displays an alert dialogue with the specified title and content.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
