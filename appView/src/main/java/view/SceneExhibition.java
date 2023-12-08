package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SceneExhibition extends VBox {

    public SceneExhibition() {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label labelScene = new Label("CENARIO DAS EXPOSIÇÕES");
        Button buttonMain = new Button("PRINCIPAL");
        Button buttonArtist = new Button("ARTISTAS");
        Button buttonGallery = new Button("GALERIAS");
        Button buttonArtwork = new Button("OBRAS");

        getChildren().addAll(labelScene, buttonMain, buttonArtist, buttonGallery, buttonArtwork);

        // Configurar a ação do botão para mudar cenario
        buttonMain.setOnAction(e -> getScene().setRoot(new MainView()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        buttonArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
    }
}