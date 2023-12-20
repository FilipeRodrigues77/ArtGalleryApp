package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The {@code ShowErrorView} class represents a view to be displayed when an error occurs or the desired content is not found.
 * It extends {@link BorderPane} and provides a user-friendly message along with the option to return to the main view.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class ShowErrorView extends BorderPane {

    /**
     * Constructs a new {@code ShowErrorView} instance.
     * Initialises the layout and displays an error message along with
     * the application logo.
     *
     * @see ShowErrorView#doLayout()
     */
    public ShowErrorView() {
        doLayout();
    }

    /**
     * Sets up the layout for the {@code ShowErrorView}, including an error message, the application logo, and a back arrow.
     * Users can click the back arrow to return to the main view when an error occurs or the desired content is not found.
     */
    private void doLayout() {
        setPadding(new Insets(20));

        Image backArrow = new Image("Icons/backArrow.png");
        ImageView arrowView = new ImageView(backArrow);
        arrowView.setFitHeight(25);
        arrowView.setFitWidth(50);
        this.setTop(arrowView);
        arrowView.setOnMouseClicked(e -> getScene().setRoot(new ManageMainView()));

        VBox logoErrorMessage = new VBox();

        Text message = new Text("Ups, não encontramos o que procurava :(");
        Text message2 = new Text("Neste caso, vais ter mesmo é de apreciar a nossa logo ;)");
        message.setFont(Font.font("Inter", 20));
        message.setFill(Color.GRAY);
        message2.setFont(Font.font("Inter", 20));
        message2.setFill(Color.GRAY);


        Image logo = new Image("Images/logo/logoIA-01.png");
        ImageView logoView = new ImageView(logo);
        // logoView.setOpacity(0.5);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);
        logoErrorMessage.getChildren().addAll(logoView,message, message2);
        logoErrorMessage.setSpacing(10);
        logoErrorMessage.setAlignment(Pos.CENTER);
        this.setCenter(logoErrorMessage);

    }
}
