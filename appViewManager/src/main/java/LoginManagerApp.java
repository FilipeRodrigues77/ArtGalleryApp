import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.*;

public class LoginManagerApp extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private boolean loginSuccess = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Iuvennis ManagerApp");
        Stage loginStage = new Stage(StageStyle.UNDECORATED);

        // VERIRY LOGIN BEFORE SHOW MAIN SCENE
        if (performLogin(loginStage)) {
            showMainScene(primaryStage);
        } else {
            primaryStage.close();
        }
    }

    private boolean performLogin(Stage loginStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label lblLogin = new Label("Login:");
        grid.add(lblLogin, 0, 1);

        usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label lblPassword = new Label("Senha:");
        grid.add(lblPassword, 0, 2);

        passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button buttonLogin = new Button("Login");
        buttonLogin.getStyleClass().add("button-modern");
        buttonLogin.setOnAction(e -> {
            // LOGIN AND PASSWORD LOGIC
            if ("IuvennisArt".equals(usernameField.getText()) && "Admin123".equals(passwordField.getText())) {
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
        loginScene.getStylesheets().add(getClass().getResource("appStyleDark.css").toExternalForm());
        loginStage.setScene(loginScene);
        loginStage.showAndWait();
        return loginSuccess;
    }

    private void closeLoginStage(Stage loginStage) {
        loginStage.close();
    }

    private void showMainScene(Stage primaryStage) {
        Scene mainScene = new Scene(new ManageMainView(), 800, 600);
        Scene sceneArtwork = new Scene(new ManageArtworkView(), 800, 600);
        Scene sceneArtist = new Scene(new ManageArtistView(), 800, 600);
        Scene sceneExhibition = new Scene(new ManageExhibitionView(), 800, 600);
        Scene sceneGallery = new Scene(new ManageGalleryView(), 800, 600);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
