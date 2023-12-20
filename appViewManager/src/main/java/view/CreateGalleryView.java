package view;

import domain.Gallery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import presenter.MainGetRegions;
import presenter.MainManageGallery;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * The {@code CreateGalleryView} class represents the view for creating a new gallery.
 * It extends {@link BorderPane} and provides a user interface for entering gallery information, including a name,
 * email, reference image, and region. Users can attach, view, and remove images, and create a new gallery.
 * This class is part of the graphical user interface for managing galleries in the application.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class CreateGalleryView extends BorderPane {

    private TextField textFieldGalleryName;
    private TextField textFieldEmail;
    private TextField textFieldReferenceImage;
    private ComboBox<String> statusMenu;
    private Label labelSuccessMessage;
    private String referenceImage;
    private File selectedFile;
    private ImageView imageView;

    /**
     * Constructor for the CreateExhibitionView class. Initializes the layout and sets the theme.
     */
    public CreateGalleryView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Sets up the layout of the {@code CreateGalleryView} class, including header, center, and bottom sections.
     * The layout includes elements for entering gallery information.
     */
    private void doLayout() {

        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // ADD IMAGE - FILE CHOOSER
        imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);

        Button attachImageButton = new Button("Carregar Imagem");
        attachImageButton.setOnAction(event -> openFileChooser());
        attachImageButton.getStyleClass().add("button-modern");

        Button viewimageButton = new Button("Visualizar Imagem");
        viewimageButton.setOnAction(event -> viewImage());
        viewimageButton.getStyleClass().add("button-modern");

        Button removeImageButton = new Button("Remover imagem");
        removeImageButton.getStyleClass().add("button-modern");
        removeImageButton.setOnAction(event -> removeImage());

        // COMBO BOX

        List<String> regionList;
        ObservableList<String> regionOptions = FXCollections.observableArrayList(
                regionList = MainGetRegions.getAllRegions()
        );
        this.statusMenu = new ComboBox<>(regionOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Região");
        statusMenu.getStyleClass().add("combo-box");

        // LABELS
        Label labelGalleryName = new Label("Nome da Galeria (max. 100 caracteres):");
        Label labelEmail = new Label("Email:");
        Label labelReferenceImage = new Label("Caminho referencia da imagem");

        this.textFieldGalleryName = new TextField();
        this.textFieldEmail = new TextField();
        this.textFieldReferenceImage = new TextField(referenceImage);
        textFieldReferenceImage.setDisable(true);

        HBox hBoxButtonImage = new HBox(attachImageButton, viewimageButton, removeImageButton);
        VBox vBoxRefImageLabelAndText = new VBox(labelReferenceImage, textFieldReferenceImage);
        VBox vBoxReferenceImage = new VBox(vBoxRefImageLabelAndText, hBoxButtonImage);

        VBox vBoxArtistName = new VBox(labelGalleryName,textFieldGalleryName);
        VBox vBoxEmail = new VBox(labelEmail,textFieldEmail);

        VBox vBoxArtistInfo = new VBox(vBoxArtistName, vBoxEmail, vBoxReferenceImage);

        VBox vBoxStatusRegion = new VBox(statusMenu);
        HBox hBoxCenterLayout = new HBox(vBoxArtistInfo,vBoxStatusRegion);

        hBoxButtonImage.setSpacing(10);
        vBoxReferenceImage.setSpacing(10);
        hBoxCenterLayout.setSpacing(50);
        vBoxArtistInfo.setSpacing(10);
        vBoxArtistInfo.setPrefWidth(520);
        vBoxStatusRegion.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());
    }

    /**
     * Generates the footer box, including buttons for creating an exhibition and canceling the operation.
     *
     * @return The VBox containing the footer elements.
     */
    private VBox getFooterBox (){
        // Buttons
        Button createGalleryButton = new Button("Criar Galleria");
        createGalleryButton.getStyleClass().add("button-modern");

        createGalleryButton.setOnAction(event -> {
            if (selectedFile != null || referenceImage != null) {
                saveImage();
                createGallery();
            } else {
                referenceImage = "Images/Gallery/DefaultGallery.jpg";
            }
        });

        Button cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("button-modern");
        cancelButton.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // LABEL
        Label labelButtonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX
        HBox hBoxButtons = new HBox(createGalleryButton, cancelButton);
        hBoxButtons.setSpacing(30);
        hBoxButtons.setAlignment(Pos.CENTER_RIGHT);
        this.labelSuccessMessage = new Label("");
        VBox vBoxButtons = new VBox(hBoxButtons, labelSuccessMessage);
        vBoxButtons.setSpacing(10);

        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);

        // HBOX BOTTOM
        HBox hBoxBottomLayout = new HBox(labelButtonStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        hBoxBottomLayout.setSpacing(500);
        VBox vBoxBottomLayout = new VBox(vBoxButtons, hBoxBottomLayout);
        vBoxBottomLayout.setSpacing(20);

        return vBoxBottomLayout;
    }

    /**
     * Generates the header box, including hyperlinks for navigation and system information.
     *
     * @return The VBox containing the header elements.
     */
    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        Label labelManagerInfo = new Label("Sistema de Gestão Iuvenis Artem");
        labelManagerInfo.setPrefSize(550, 30);
        labelManagerInfo.getStyleClass().add("my-center-label-6 ");

        // I~A LOGO
        Image logo = new Image("Images/logo/logoIA-02.png");
        ImageView logoView = new ImageView(logo);
        logoView.preserveRatioProperty();
        logoView.setFitWidth(27);
        logoView.setFitHeight(27);
        logoView.setSmooth(true);

        // ADD PAGE HEADER ELEMENTS --
        HBox hBoxHeader = new HBox(logoView,labelManagerInfo, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtistView()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new ManageMainView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibitionView()));
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));
        hyperlinkGallery.getStyleClass().add("actual-page-hyperlink");

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    private void setOriginalDescription(TextField textField, String originalText){

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (textField.getText().isEmpty()) {
                    textField.setText(originalText);
                }
            }
        });
    }

    /**
     * Sets the default size for an {@link ImageView}, adjusting the fit width and fit height.
     *
     * @param imageView The {@code ImageView} to set the default size for.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);

    }

    /**
     * Opens a file chooser dialogue to select an image file for the gallery.
     */
    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("Todos os Arquivos", "*.*")
        );

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            if (isValidImage(selectedFile)) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);

                referenceImage = selectedFile.toURI().toString();
                textFieldReferenceImage.setText(referenceImage);
            } else {
                showAlert("Imagem Inválida", "A imagem selecionada não atende aos requisitos.");
            }
        }
    }

    /**
     * Displays a separate window to view the selected image.
     */
    private void viewImage() {
        if (selectedFile != null) {
            ImageView viewImageView = new ImageView(new Image(selectedFile.toURI().toString()));
            javafx.scene.layout.StackPane secondaryLayout = new javafx.scene.layout.StackPane();
            secondaryLayout.getChildren().add(viewImageView);

            Scene secondScene = new Scene(secondaryLayout, 600, 500);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Visualizar Imagem");
            newWindow.setScene(secondScene);

            // Show the new window without referencing the primary stage
            newWindow.show();
        } else {
            showAlert("Sem Imagem", "Nenhuma imagem selecionada para visualização.");
        }
    }

    /**
     * Saves the selected image to a specified folder and updates the reference image path.
     */
    private void saveImage() {
        if (selectedFile != null) {
            String galleryName = textFieldGalleryName.getText().trim();

            if (galleryName.isEmpty()) {
                showAlert("Nome da Galeria Vazio", "Por favor, insira um nome para a galeria antes de salvar a imagem.");
                return;
            }

            // Verificar se uma região foi selecionada
            if (Objects.equals(statusMenu.getValue(), "Região") || statusMenu.getValue().trim().isEmpty()) {
                showAlert("Região Não Selecionada", "Por favor, selecione uma região antes de salvar a imagem.");
                return;
            }

            // Pasta predefinida (pode ser ajustada conforme necessário)
            File destinationFolder = new File("images/analise/galerias");

            try {
                // Verificar se a pasta de destino existe; se não, crie-a
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                // Nome da imagem usando o nome da galeria e a extensão do arquivo original
                String imageFileName = galleryName + "." + getFileExtension(selectedFile.getName());

                // Caminho completo para o novo arquivo
                File destinationFile = new File(destinationFolder, imageFileName);

                // Copiar o arquivo para a pasta de destino
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                // FOR NOW ALL IMAGES BEFORE ANALISE IS DEFAULT
                //refenceImage = destinationFile.getAbsolutePath();

                showAlert("Imagem Salva", "Imagem salva com sucesso em: " + destinationFile.getAbsolutePath() + "\n" +
                        "A imagem será avaliada e posteriormente adicionada à base de dados");
            } catch (IOException e) {
                showAlert("Erro ao Salvar Imagem", "Ocorreu um erro ao salvar a imagem.");
            }
        } else {
            // Se nenhum arquivo foi selecionado, use a imagem padrão
            referenceImage = null;
            textFieldReferenceImage.setText(referenceImage);
            showAlert("Sem Imagem", "Nenhuma imagem selecionada. Será usada a imagem padrão.");
        }
    }

    /***
     * Sets the default image for the gallery.
     */
    private void removeImage() {
        imageView.setImage(null);
        selectedFile = null;
        referenceImage = null;
        textFieldReferenceImage.setText(referenceImage);
    }

    /**
     * Retrieves the file extension from a given file name.
     *
     * @param fileName The name of the file.
     * @return The file extension.
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * Displays an alert dialogue with the given title and content.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Checks if the selected file is a valid image based on size and dimensions.
     *
     * @param file The selected image file.
     * @return True if the image is valid, false otherwise.
     */
    private boolean isValidImage(File file) {
        long fileSize = file.length();
        if (fileSize < 100 * 1024 || fileSize > 5 * 1024 * 1024) {
            return false;
        }

        Image image = new Image(file.toURI().toString());
        return image.getWidth() >= 400 && image.getHeight() >= 400 && image.getWidth() <= 2000 && image.getHeight() <= 2000;
    }

    /**
     * Creates a new gallery based on user input and adds it to the database.
     */
    private void createGallery() {
        // STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldGalleryName.getText();
        String email = textFieldEmail.getText();
        String region = statusMenu.getValue();
        referenceImage = null;

        // CREATE NEW OBJECT
        Gallery newGallery = new Gallery();
        newGallery.setNameGallery(name);
        newGallery.setEmail(email);
        newGallery.setRegionName(region);
        newGallery.setReferenceImage(referenceImage);

        // ADD TO DATABASE
        if (newGallery.getNameGallery() == null || newGallery.getNameGallery().trim().isEmpty()
                || newGallery.getNameGallery().isBlank() || Objects.equals(statusMenu.getValue(), "Região")
                || statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            MainManageGallery.addGallery(newGallery);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }
    }

}
