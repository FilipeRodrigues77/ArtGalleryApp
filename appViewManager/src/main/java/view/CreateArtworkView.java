package view;

import domain.Artist;
import domain.Artwork;
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
import presenter.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the view for creating new artworks in the art management system.
 * It extends the BorderPane class and includes various UI elements for input and interaction.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 *
 */
public class CreateArtworkView extends BorderPane {

    private Spinner<Double> priceSpinner;
    private TextField textFieldDimensionCm;
    private TextField textFieldDimensionIN;
    private TextField textFieldArtworkName;
    private TextField textFieldNewMedium;
    private TextField textFieldNewCategory;
    private Spinner<Integer> spinnerCreationDate;
    private List<Artwork> artworkList;
    private List<Artist> artistsList;
    private List<Gallery> galleriesList;
    private Gallery selectedGallery;
    private int idGallery;
    private int idArtist;
    private Label labelSuccessMessage;
    private TextField textFieldReferenceImage;
    private String referenceImage;
    private File selectedFile;
    private ImageView imageView;

    /**
     * CreateArtworkView constructor for the CreateArtworkView class.
     * Initialises the layout and sets the theme.
     */
    public CreateArtworkView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Sets up the layout of the CreateArtworkView, including header, center, and bottom elements.
     */
    private void doLayout() {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------
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


        Button defaultImageButton = new Button("Imagem Padrão");
        defaultImageButton.getStyleClass().add("button-modern");
        defaultImageButton.setOnAction(event ->setDefaultImage());

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox());
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------
        // SPINNER
        this.priceSpinner = new Spinner<>(0.00, 10000000.00, 0.00);
        priceSpinner.setEditable(true);

        this.spinnerCreationDate = new Spinner<>(0,2999,0);
        spinnerCreationDate.setEditable(true);

        // LISTS
        this.artworkList = MainGetArtworks.getAllArtworks();
        this.artistsList = MainGetArtists.getAllArtists();
        this.galleriesList = MainGetGalleries.getAllGalleries();

        // COMBO BOX
        ComboBox<String> comboBoxMedium = new ComboBox<>(createOptionsMenu(artworkList,"medium"));
        comboBoxMedium.setMaxWidth(300);
        comboBoxMedium.setOnAction(e-> handleMediumSelection(comboBoxMedium, artworkList));

        ComboBox<String> comboBoxCategory = new ComboBox<>(createOptionsMenu(artworkList,"category"));
        comboBoxCategory.setMaxWidth(300);
        comboBoxCategory.setOnAction(e-> handleCategorySelection(comboBoxCategory, artworkList));

        ComboBox<String> comboBoxArtist = new ComboBox<>(createArtistMenu(artistsList));
        comboBoxArtist.setMaxWidth(200);
        comboBoxArtist.setOnAction(e-> handleArtistSelection(comboBoxArtist, artistsList));
        comboBoxArtist.setVisibleRowCount(9);

        // Line of code to be reviewed
        ComboBox<Gallery> comboBoxGalleryRef = new ComboBox<>();
        comboBoxGalleryRef.getItems().addAll(galleriesList);
        comboBoxGalleryRef.setOnAction(e -> {
            this.selectedGallery = comboBoxGalleryRef.getSelectionModel().getSelectedItem();
            if (selectedGallery != null) {
                this.idGallery = selectedGallery.getId();
            }
        });
        comboBoxGalleryRef.setVisibleRowCount(3);

        // LABELS
        Label labelArtworkName = new Label("Nome da Obra de Arte (max. 100 caracteres):");
        Label labelDimensionIn = new Label("Dimensões em In (ex: 12.1 x 12.1 in):");
        Label labelDimensionCm = new Label("Dimensões em Cm (ex: 30,5 x 30,5 cm):");
        Label labelRefGallery = new Label("Galeria referente");
        Label labelRefArtist = new Label("Artista referente");
        Label labelCategory = new Label("Categoria referente");
        Label labelRefMedium = new Label("Método referente");
        Label labelpriceArtwork = new Label("Preço da obra (ex. 500,00):");
        Label labelCreationDate = new Label("Ano de Criação (ex. 2023):");
        Label labelReferenceImage = new Label("Caminho referencia da imagem");

        this.textFieldArtworkName = new TextField();
        this.textFieldDimensionCm = new TextField();
        this.textFieldDimensionIN = new TextField();
        this.textFieldNewMedium = new TextField();
        this.textFieldNewCategory = new TextField();
        this.textFieldReferenceImage = new TextField(referenceImage);

        textFieldReferenceImage.setDisable(true);
        textFieldNewMedium.setPrefWidth(520);
        textFieldNewMedium.setDisable(true);
        textFieldNewCategory.setPrefWidth(520);
        textFieldNewCategory.setDisable(true);

        HBox hBoxButtonImage = new HBox(attachImageButton, viewimageButton, defaultImageButton);
        VBox vBoxRefImageLabelAndText = new VBox(labelReferenceImage, textFieldReferenceImage);
        VBox vBoxReferenceImage = new VBox(vBoxRefImageLabelAndText, hBoxButtonImage);
        VBox vBoxArtworkName = new VBox(labelArtworkName, textFieldArtworkName);
        VBox vBoxDimensionCm = new VBox(labelDimensionCm,textFieldDimensionCm);
        VBox vBoxDimensionIn = new VBox(labelDimensionIn,textFieldDimensionIN);
        VBox vBoxPriceArtwork = new VBox(labelpriceArtwork, priceSpinner);
        VBox vBoxCreateDate = new VBox(labelCreationDate, spinnerCreationDate);
        VBox vBoxGallery = new VBox(labelRefGallery, comboBoxGalleryRef);
        VBox vBoxRefArtistId = new VBox(labelRefArtist,comboBoxArtist);
        VBox vBoxCategory = new VBox(labelCategory, comboBoxCategory);
        VBox vBoxRefMedium = new VBox(labelRefMedium, comboBoxMedium);

        HBox hBoxNewMedium = new HBox(textFieldNewMedium);
        HBox hBoxNewCategory = new HBox(textFieldNewCategory);
        HBox hBoxPriceAndDate = new HBox(vBoxPriceArtwork,vBoxCreateDate);
        HBox hBoxArtistRef = new HBox(vBoxRefArtistId);
        HBox hBoxDimensions = new HBox(vBoxDimensionCm, vBoxDimensionIn);

        VBox vBoxMediumWithNewMedium = new VBox(vBoxRefMedium, hBoxNewMedium);
        VBox vBoxCategoryWithNewCategory = new VBox(vBoxCategory, hBoxNewCategory);
        VBox vBoxArtworkInfo = new VBox(vBoxArtworkName, hBoxDimensions, hBoxPriceAndDate, vBoxMediumWithNewMedium, vBoxCategoryWithNewCategory);
        VBox vBoxRefArtistGallery = new VBox(vBoxGallery,hBoxArtistRef, vBoxReferenceImage);

        HBox hBoxCenterLayout = new HBox(vBoxArtworkInfo,vBoxRefArtistGallery);

        hBoxButtonImage.setSpacing(10);
        vBoxReferenceImage.setSpacing(10);
        vBoxMediumWithNewMedium.setSpacing(10);
        vBoxCategoryWithNewCategory.setSpacing(10);
        hBoxPriceAndDate.setSpacing(50);
        hBoxDimensions.setSpacing(40);
        hBoxCenterLayout.setSpacing(50);
        vBoxArtworkInfo.setSpacing(10);
        vBoxArtworkInfo.setPrefWidth(520);
        vBoxRefArtistGallery.setSpacing(15);

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());
    }

    /**
     * Generates the footer box, including buttons for creating artwork and canceling the operation.
     *
     * @return The VBox containing the footer elements.
     */
    private VBox getFooterBox (){
        // Buttons
        Button createArtworkButton = new Button("Criar Obra de Arte");
        Button cancelButton = new Button("Cancelar");

        createArtworkButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        createArtworkButton.setOnAction(event -> {
            saveImage();
            createArtwork();
        });

        cancelButton.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));

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
        HBox hBoxButtons = new HBox(createArtworkButton, cancelButton);
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
        hyperLinkArtwork.getStyleClass().add("actual-page-hyperlink");

        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));

        return  vBoxTop;

    }

    /**
     * Sets the original description for a TextField when it loses focus, resetting it to the original text if empty.
     *
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
     * Saves the selected image to a specified folder and updates the reference image path.
     */
    private void saveImage() {
        if (selectedFile != null) {
            String artworkName = textFieldArtworkName.getText().trim();

            if (artworkName.isEmpty()) {
                showAlert("Nome da obra de arte Vazio", "Por favor, insira um nome para a obra antes de salvar a imagem.");
                return;
            }

            // Pasta predefinida (pode ser ajustada conforme necessário)
            File destinationFolder = new File("images/analise/obras");

            try {
                // Verificar se a pasta de destino existe; se não, crie-a
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                // Nome da imagem usando o nome da galeria e a extensão do arquivo original
                String imageFileName = artworkName + "." + getFileExtension(selectedFile.getName());

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
     * Sets the default image for the artwork.
     */
    private void setDefaultImage() {
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
     * Creates a new artwork based on user input and adds it to the database.
     */
    private void createArtwork() {
        //STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldArtworkName.getText();
        double price = priceSpinner.getValue();
        String dimensionCm = textFieldDimensionCm.getText();
        String dimensionIN = textFieldDimensionIN.getText();
        String creationDate = spinnerCreationDate.getValue().toString();
        //String referenceImage = "Images/Artwork/AllArtworks/square0.jpg";

        //CREATE NEW OBJECT
        Artwork newArtwork = new Artwork();
        newArtwork.setName(name);
        newArtwork.setPrice(price);
        String dimensionStrCm = "\"" + dimensionCm + "\"";
        newArtwork.setDimensionCm(dimensionStrCm);
        String dimensionStrIn = "\"" + dimensionIN + "\"";
        newArtwork.setDimensionIN(dimensionStrIn);
        newArtwork.setMedium(textFieldNewMedium.getText());
        newArtwork.setCreationDate(creationDate);
        newArtwork.setCategory(textFieldNewCategory.getText());
        //newArtwork.setReferenceImage(referenceImage);
        newArtwork.setIdGallery(idGallery);
        newArtwork.setIdArtist(idArtist);

        //ADD TO DATABASE (FAZER OS TESTES PARA TRATAMENTO DA INFORMAÇÃO)
        if (newArtwork.getName() == null || newArtwork.getName().trim().isEmpty() || newArtwork.getName().isBlank()
                || idGallery == 0 || idArtist == 0) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSuccessMessage.setText("Não foi possível atualizar!");
        } else {
            System.out.println(newArtwork);
            MainManageArtwork.addArtwork(newArtwork);
            labelSuccessMessage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }


    }

    /**
     * Creates the option menu for ComboBox based on a given field from the artwork list.
     *
     * @param artworkList The list of artworks.
     * @param field       The field to create options for (e.g., "medium" or "category").
     * @return ObservableList containing options for the ComboBox.
     */
    private ObservableList<String> createOptionsMenu (List<Artwork>artworkList, String field){

        List<String> testing = new ArrayList<>();
        testing.add("Novo");
        String menuField = field.toLowerCase();

        switch (menuField){
            case "category": for(Artwork art : artworkList){
                String option = art.getCategory();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
                break;
            case "medium": for(Artwork art : artworkList){
                String option = art.getMedium();
                if(!testing.contains(option)){
                    testing.add(option);
                }
            }
                break;
        }
        return FXCollections.observableArrayList(testing);
    }

    /**
     * Handles the selection of a medium in the ComboBox, enabling/disabling the corresponding TextField.
     *
     * @param mediumMenu      The ComboBox for selecting the medium.
     * @param artworkMediumList The list of artworks with mediums.
     */
    private void handleMediumSelection (ComboBox<String> mediumMenu, List<Artwork> artworkMediumList){
        String selectedMedium = mediumMenu.getSelectionModel().getSelectedItem();
        if(selectedMedium != null) {
            if (selectedMedium.equals("Novo")) {
                textFieldNewMedium.setText("");
                textFieldNewMedium.setDisable(false);
            }
            if (!selectedMedium.equals("Novo")) {
                textFieldNewMedium.setText(selectedMedium);
                textFieldNewMedium.setDisable(true);
            }
        }
    }

    /**
     * Handles the selection of a category in the ComboBox, enabling/disabling the corresponding TextField.
     *
     * @param categoryMenu         The ComboBox for selecting the category.
     * @param artworkCategoryList The list of artworks with categories.
     */
    private void handleCategorySelection (ComboBox<String> categoryMenu, List<Artwork> artworkCategoryList) {
        String selectedCategory = categoryMenu.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            if (selectedCategory.equals("Novo")){
                textFieldNewCategory.setText("");
                textFieldNewCategory.setDisable(false);
            }
            if (!selectedCategory.equals("Novo")){
                textFieldNewCategory.setText(selectedCategory);
                textFieldNewCategory.setDisable(true);
            }
        }
    }

    /**
     * Handles the selection of an artist in the ComboBox, extracting and setting the artist ID.
     *
     * @param comboBoxArtistRef The ComboBox for selecting the artist.
     * @param artistsList        The list of artists.
     */
    private void handleArtistSelection (ComboBox<String> comboBoxArtistRef, List<Artist> artistsList) {
        String selectedArtist = comboBoxArtistRef.getSelectionModel().getSelectedItem();
        if (selectedArtist != null) {
            this.idArtist = extractNumber(selectedArtist);
        }
    }

    /**
     * Creates an artist menu for ComboBox based on the list of artists.
     * Note: This method needs to be reviewed
     *
     * @param artistsList The list of artists.
     * @return ObservableList containing options for the ComboBox.
     */
    private ObservableList<String> createArtistMenu (List<Artist>artistsList){

        List<String> artists = new ArrayList<>();
        String menuArtistField = "artist".toLowerCase();


        for (Artist artist : artistsList) {
            String option = artist.getName();
            int optionId = artist.getId();
            String fullOption = optionId + " - " + option;
            if (!artists.contains(fullOption)) {
                artists.add(fullOption);
            }
        }

        return FXCollections.observableArrayList(artists);
    }

    /**
     * Extracts the numerical part from a string and returns it as an integer.
     *
     * @param input The input string containing a numerical part.
     * @return The extracted integer.
     * @throws IllegalArgumentException If no number is found in the string.
     */
    public static int extractNumber(String input) {
        Pattern pattern = Pattern.compile("^\\d+");
        // OBJECT INPUT
        Matcher matcher = pattern.matcher(input);
        // VERIFY CORRESPONDENCE
        if (matcher.find()) {
            // GET STRING NUMBER AND CONVERT TO INT
            String numeroString = matcher.group();
            return Integer.parseInt(numeroString);
        } else {
            throw new IllegalArgumentException("Nenhum número encontrado na string");
        }
    }

}
