package view;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import domain.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presenter.MainGetNationalities;
import presenter.MainManageArtists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * The CreateArtistView class represents the graphical user interface for creating and managing artist information.
 * It extends the BorderPane class and includes various UI elements for inputting and displaying details about an artist.
 * This class provides functionality to create, view, and manage artist information, including their image, biography,
 * and other attributes.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class CreateArtistView extends BorderPane {

    private TextField textFieldArtistName;
    private TextField textFieldBirthday;
    private TextField textFieldDeathday;
    private TextField textFieldBiography;
    private TextField textFieldSlug;
    private ComboBox<String> statusMenu;
    private Spinner<Integer> yearBirthdaySpinner;
    private Spinner<Integer> yearDeathdaySpinner;
    private Label labelSuccessMessage;
    private TextField textFieldReferenceImage;
    private String referenceImage;
    private File selectedFile;
    private ImageView imageView;


    /**
     * CreateArtistView constructs a new CreateArtistView instance.
     * Initializes the layout and sets the stylesheet based on the current theme.
     */
    public CreateArtistView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * This method configures the layout of the CreateArtistView, including header, center, and bottom sections.
     * Sets up various UI elements such as text fields, buttons, and spinners for creating artist information.
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
        List<String> nationalityList;
        ObservableList<String> nationalityOptions = FXCollections.observableArrayList(
                nationalityList = MainGetNationalities.getAllNationalities()

        );
        this.statusMenu = new ComboBox<>(nationalityOptions);
        statusMenu.setMaxWidth(120);
        statusMenu.setValue("Nacionalidade");
        statusMenu.getStyleClass().add("combo-box");

        // SPINNER
        this.yearBirthdaySpinner = new Spinner<>(0, 2999, 1990);
        yearBirthdaySpinner.setEditable(true);

        this.yearDeathdaySpinner = new Spinner<>(0, 2999,0);
        yearDeathdaySpinner.setEditable(true);

        // LABELS
        Label labelArtistName = new Label("Nome do Artista (max. 100 caracteres):");
        Label labelBirthday = new Label("Ano Nascimento (ex: 1990):");
        Label labelDeathday = new Label("Ano da Morte (4 dígitos):");
        Label labelBiography = new Label("Biografia (max.10000 caracteres):");
        Label labelSlug = new Label("Slug (nome-separado-por-hifén-em-minúsculo):");
        Label labelReferenceImage = new Label("Caminho referencia da imagem");

        this.textFieldArtistName = new TextField();
        this.textFieldBirthday = new TextField();
        this.textFieldDeathday = new TextField();
        this.textFieldBiography = new TextField();
        this.textFieldSlug = new TextField();
        this.textFieldReferenceImage = new TextField(referenceImage);
        textFieldReferenceImage.setDisable(true);

        textFieldBiography.setPrefSize(520,200);

        HBox hBoxButtonImage = new HBox(attachImageButton, viewimageButton, removeImageButton);
        VBox vBoxRefImageLabelAndText = new VBox(labelReferenceImage, textFieldReferenceImage);
        VBox vBoxReferenceImage = new VBox(vBoxRefImageLabelAndText, hBoxButtonImage);
        VBox vBoxArtistName = new VBox(labelArtistName,textFieldArtistName);
        VBox vBoxBirthday = new VBox(labelBirthday,yearBirthdaySpinner);
        VBox vBoxDeathday = new VBox(labelDeathday,yearDeathdaySpinner);
        VBox vBoxBiography = new VBox(labelBiography,textFieldBiography);
        VBox vBoxSlug = new VBox(labelSlug,textFieldSlug);
        HBox hBoxBirthdayDeathday = new HBox(vBoxBirthday, vBoxDeathday);
        VBox vBoxArtistInfo = new VBox(vBoxArtistName, hBoxBirthdayDeathday, vBoxSlug, vBoxBiography, vBoxReferenceImage);
        VBox vBoxStatusNation = new VBox(statusMenu);
        HBox hBoxCenterLayout = new HBox(vBoxArtistInfo,vBoxStatusNation);

        hBoxButtonImage.setSpacing(10);
        vBoxReferenceImage.setSpacing(10);
        vBoxStatusNation.setSpacing(50);
        hBoxBirthdayDeathday.setSpacing(50);
        hBoxCenterLayout.setSpacing(50);
        vBoxArtistInfo.setSpacing(10);
        vBoxArtistInfo.setPrefWidth(520);
        vBoxStatusNation.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());

    }

    /**
     * Retrieves the layout for the bottom section, including buttons for creating and canceling artist creation,
     * social media icons, and a status label.
     *
     * @return The VBox containing the bottom section layout.
     */
    private VBox getFooterBox (){
        // Buttons
        Button createArtistButton = new Button("Criar Artista");
        Button cancelButton = new Button("Cancelar");

        createArtistButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        createArtistButton.setOnAction(event -> {
                saveImage();
                createArtist();

        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageArtistView()));

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
        HBox hBoxButtons = new HBox(createArtistButton, cancelButton);
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
     * Retrieves the layout for the header section, including hyperlinks for navigating to different sections
     * and the logo.
     *
     * @return The VBox containing the header section layout.
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
        hyperlinkArtist.getStyleClass().add("actual-page-hyperlink");

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    /**
     * Sets the original description for a TextField when it loses focus, resetting it to the original text if empty.
     * Method not yet in use.*
     * @param textField     The TextField to monitor for changes.
     * @param originalText  The original text to set when the TextField loses focus.
     */
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
     * Sets the default size for an ImageView representing an icon.
     *
     * @param imageView  The ImageView to set the default size for.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    /**
     * Sets the default size for an ImageView representing an artist's image.
     * Method not yet in use.*
     * @param imageView  The ImageView to set the default size for.
     */
    public void defaultSizeArtistImage(ImageView imageView){
        imageView.setFitHeight(160); // Ajuste a altura conforme necessário
        imageView.setFitWidth(160);  // Ajuste a largura conforme necessário
        //imageView.setPreserveRatio(true);
    }

    /**
     * Opens a file chooser dialogue for selecting an image file and updates the UI accordingly.
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
     * Displays a separate window for viewing the selected artist image.
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
     * Saves the selected artist image to a specified destination folder and updates
     * the UI with a success message.
     */
    private void saveImage() {
        if (selectedFile != null) {
            String artistName = textFieldArtistName.getText().trim();

            if (artistName.isEmpty()) {
                showAlert("Nome do artista Vazio", "Por favor, insira um nome para o artista antes de salvar a imagem.");
                return;
            }

            // Verificar se uma região foi selecionada
            if (Objects.equals(statusMenu.getValue(), "Nacionalidade") || statusMenu.getValue().trim().isEmpty()) {
                showAlert("Nacionalidade Não Selecionada", "Por favor, selecione uma nacionalidade antes de salvar a imagem.");
                return;
            }

            // Pasta predefinida (pode ser ajustada conforme necessário)
            File destinationFolder = new File("images/analise/artistas");

            try {
                // Verificar se a pasta de destino existe; se não, crie-a
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                // Nome da imagem usando o nome da galeria e a extensão do arquivo original
                String imageFileName = artistName + "." + getFileExtension(selectedFile.getName());

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

    /**
     * Removes the currently selected artist image from the UI.
     */
    private void removeImage() {
        imageView.setImage(null);
        selectedFile = null;
        referenceImage = null;
        textFieldReferenceImage.setText(referenceImage);
    }

    /**
     * Retrieves the file extension of a given file name.
     *
     * @param fileName  The name of the file.
     * @return The file extension as a String.
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * Displays an alert dialog with the specified title and content.
     *
     * @param title    The title of the alert dialog.
     * @param content  The content or message of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Checks if the selected image file is valid based on size and dimensions.
     *
     * @param file  The image file to validate.
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
     * Creates a new Artist object with the provided information and adds it to the database.
     */
    private void createArtist() {
        //STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldArtistName.getText();
        String biography = textFieldBiography.getText();
        String birthdate = yearBirthdaySpinner.getValue().toString();
        String deathdate = yearDeathdaySpinner.getValue().toString();
        String slug = textFieldSlug.getText();
        String nationality = statusMenu.getValue();
        String refenceImage = null;

        //CREATE NEW OBJECT
        Artist newArtist = new Artist();
        newArtist.setName(name);
        newArtist.setBiography(biography);
        newArtist.setBirthdate(birthdate);
        newArtist.setDeathdate(deathdate);
        newArtist.setSlug(slug);
        newArtist.setNationality(nationality);
        newArtist.setReferenceImage(refenceImage);

        //ADD TO DATABASE
        if (newArtist.getName() == null || newArtist.getName().trim().isEmpty()
            || newArtist.getName().isBlank() || Objects.equals(statusMenu.getValue(), "Nacionalidade") ||
                statusMenu.getValue().trim().isEmpty() || statusMenu.getValue().isBlank()) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            MainManageArtists.addArtist(newArtist);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }
    }

    /**
     * Checks if the provided string represents a valid integer within a specific range.
     *
     * @param newValue  The string to be checked for validity.
     * @return True if the string is a valid integer within the specified range, false otherwise.
     */
    private boolean isValidNumber(String newValue) {
        if (newValue.isEmpty()) {
                return true; // Aceitar campo vazio
        }
        try {
                int value = Integer.parseInt(newValue);
                return value >= 0 && value <= 2023;
        } catch (NumberFormatException e) {
                return false; // Não é um número válido
        }
    }

}
