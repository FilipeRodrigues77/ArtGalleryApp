package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SceneGallery extends VBox {

    public SceneGallery() {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label labelScene = new Label("CENARIO DAS GALERIAS");
        Button buttonMain = new Button("PRINCIPAL");
        Button buttonArtist = new Button("ARTISTAS");
        Button buttonArtwork = new Button("OBRAS");
        Button buttonExhibition = new Button("EXPOSIÇÕES");

        getChildren().addAll(labelScene, buttonMain, buttonArtist, buttonArtwork, buttonExhibition);

        // Configurar a ação do botão para mudar cenario
        buttonMain.setOnAction(e -> getScene().setRoot(new MainView()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        buttonArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
    }
}