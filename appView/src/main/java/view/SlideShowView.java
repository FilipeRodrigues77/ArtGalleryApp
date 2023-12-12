package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideShowView extends BorderPane {

    private static final String FOLDER_PATH = "appView/src/main/resources/Images/Artwork/AllArtworks";
    private List<Integer> randomOrder;
    private int currentIndex = 0;

    public SlideShowView() {
        // Configuração do layout
        getStylesheets().add("appStyleLight.css");
        getStyleClass().add("SlideShowView");
        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);

        File folder = new File(FOLDER_PATH);

        if (folder.exists() && folder.isDirectory()) {
            File[] imageFiles = folder.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg")
            );

            if (imageFiles != null && imageFiles.length > 0) {
                // Gera uma ordem aleatória para os índices das imagens
                randomOrder = generateRandomOrder(imageFiles.length);

                ImageView imageView = new ImageView();
                imageView.setFitWidth(400); // ajuste a largura conforme necessário
                imageView.setFitHeight(400); // ajuste a altura conforme necessário

                // Adiciona a imagem inicial ao ImageView
                showImage(imageView, currentIndex);

                centerPane.getChildren().add(imageView);

                // Adiciona o botão "Fechar"
                Button buttonMain = new Button("PRINCIPAL");
                buttonMain.setOnAction(e -> getScene().setRoot(new MainView()));
                setBottom(buttonMain);

                // Configuração do Timeline para mudar de imagem a cada 3 segundos
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3), event -> showNextImage(imageView))
                );
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
            } else {
                System.out.println("A pasta não contém arquivos de imagem.");
                getScene().setRoot(new MainView());
            }
        } else {
            System.out.println("O caminho especificado não é uma pasta válida.");
            getScene().setRoot(new MainView());
        }

        setCenter(centerPane);
    }

    private void showImage(ImageView imageView, int index) {
        File folder = new File(FOLDER_PATH);
        File[] imageFiles = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg")
        );

        if (imageFiles != null && index >= 0 && index < imageFiles.length) {
            Image image = new Image(imageFiles[randomOrder.get(index)].toURI().toString());
            imageView.setImage(image);
        }
    }

    private void showNextImage(ImageView imageView) {
        currentIndex = (currentIndex + 1) % randomOrder.size();
        showImage(imageView, currentIndex);
    }

    private List<Integer> generateRandomOrder(int size) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            order.add(i);
        }
        Collections.shuffle(order);
        return order;
    }

    private void closeSlideshow() {
        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }
}