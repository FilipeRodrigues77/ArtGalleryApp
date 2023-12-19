package view;

import domain.Artist;
import domain.Exhibition;
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
import presenter.MainGetGalleries;
import presenter.MainManageArtists;
import presenter.MainManageExhibition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class CreateExhibitionView extends BorderPane {

    private TextField textFieldnameExhibition;
    private DatePicker datePickerstartDate;
    private DatePicker datePickerEndDate;
    private TextField textFieldExdescription;

    private int idGallery;
    private Gallery selectedGallery;
    private ComboBox<Gallery> comboBoxGalleryRef;
    private Label labelSucessMensage;
    private TextField textFieldRefenceImage;
    private String refenceImage;
    private File selectedFile;
    private ImageView imageView;

    public CreateExhibitionView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
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

        Button defaultImageButton = new Button("Imagem Padrão");
        defaultImageButton.getStyleClass().add("button-modern");
        defaultImageButton.setOnAction(event ->setDefaultImage());

        // COMBO BOX
        List<Gallery> galleries = MainGetGalleries.getAllGalleries();
        this.comboBoxGalleryRef = new ComboBox<>();
        comboBoxGalleryRef.getItems().addAll(galleries);
        comboBoxGalleryRef.setOnAction(e -> {
                    this.selectedGallery = comboBoxGalleryRef.getSelectionModel().getSelectedItem();
                    if (selectedGallery != null) {
                        this.idGallery = selectedGallery.getId();
                    }
                });
        comboBoxGalleryRef.setVisibleRowCount(3);



        // LABELS
        Label labelExhibitionName = new Label("Nome da Exposição (max. 100 caracteres):");
        Label labelStartDate = new Label("Data Inicio (ex: 2016-02-23):");
        Label labelEndDate = new Label("Data Fecho (ex: 2025-02-23):");
        Label labelDescription = new Label("Descrição (max.10000 caracteres):");
        Label labelRefGallery = new Label("Galeria Referente");
        Label labelReferenceImage = new Label("Caminho referencia da imagem");

        this.datePickerstartDate = new DatePicker();
        this.datePickerEndDate = new DatePicker();
        this.textFieldnameExhibition = new TextField();
        this.textFieldExdescription = new TextField();
        this.textFieldRefenceImage = new TextField(refenceImage);

        textFieldRefenceImage.setDisable(true);
        textFieldExdescription.setPrefSize(520,200);

        HBox hBoxButtonImage = new HBox(attachImageButton, viewimageButton, defaultImageButton);
        VBox vBoxRefImageLabelAndText = new VBox(labelReferenceImage,textFieldRefenceImage);
        VBox vBoxReferenceImage = new VBox(vBoxRefImageLabelAndText, hBoxButtonImage);
        VBox vBoxGallery = new VBox(labelRefGallery, comboBoxGalleryRef);
        VBox vBoxExhibitionName = new VBox(labelExhibitionName,textFieldnameExhibition);
        VBox vBoxStartDate = new VBox(labelStartDate,datePickerstartDate);
        VBox vBoxEndDate = new VBox(labelEndDate,datePickerEndDate);
        VBox vBoxDescription = new VBox(labelDescription,textFieldExdescription);

        HBox hBoxDate = new HBox(vBoxStartDate, vBoxEndDate);
        VBox vBoxExhibitionInfo = new VBox(vBoxExhibitionName, hBoxDate, vBoxDescription, vBoxReferenceImage);
        VBox vBoxStatusGallery = new VBox(vBoxGallery);
        HBox hBoxCenterLayout = new HBox(vBoxExhibitionInfo,vBoxStatusGallery);

        hBoxButtonImage.setSpacing(10);
        vBoxReferenceImage.setSpacing(10);
        hBoxDate.setSpacing(50);
        hBoxCenterLayout.setSpacing(50);
        vBoxExhibitionInfo.setSpacing(10);
        vBoxExhibitionInfo.setPrefWidth(520);
        vBoxStatusGallery.setPadding(new Insets(15));

        this.setCenter(hBoxCenterLayout);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());
    }

    private VBox getFooterBox (){
        // Buttons
        Button createExhibitionButton = new Button("Criar Exposição");
        Button cancelButton = new Button("Cancelar");

        createExhibitionButton.getStyleClass().add("button-modern");
        cancelButton.getStyleClass().add("button-modern");

        createExhibitionButton.setOnAction(event -> {

            saveImage();
            createExhibition();

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
        HBox hBoxButtons = new HBox(createExhibitionButton, cancelButton);
        hBoxButtons.setSpacing(30);
        hBoxButtons.setAlignment(Pos.CENTER_RIGHT);
        this.labelSucessMensage = new Label("");
        VBox vBoxButtons = new VBox(hBoxButtons, labelSucessMensage);
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

    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        Label labelManagerInfo = new Label("Sistema de Gestão Iuvennis Art");
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
        hyperlinkExhibition.getStyleClass().add("actual-page-hyperlink");

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

    // image treatment
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

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

                refenceImage = selectedFile.toURI().toString();
                textFieldRefenceImage.setText(refenceImage);
            } else {
                showAlert("Imagem Inválida", "A imagem selecionada não atende aos requisitos.");
            }
        }
    }

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

    private void saveImage() {
        if (selectedFile != null) {
            String artistName = textFieldnameExhibition.getText().trim();

            if (artistName.isEmpty()) {
                showAlert("Nome da exposição Vazio", "Por favor, insira um nome para a exposição antes de salvar a imagem.");
                return;
            }


            // Pasta predefinida (pode ser ajustada conforme necessário)
            File destinationFolder = new File("images/analise/exposicoes");

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
            refenceImage = null;
            textFieldRefenceImage.setText(refenceImage);
            showAlert("Sem Imagem", "Nenhuma imagem selecionada. Será usada a imagem padrão.");
        }
    }

    private void setDefaultImage() {
        imageView.setImage(null);
        selectedFile = null;
        refenceImage = "Images/Exhibition/Default.jpg";
        textFieldRefenceImage.setText(refenceImage);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidImage(File file) {
        long fileSize = file.length();
        if (fileSize < 100 * 1024 || fileSize > 5 * 1024 * 1024) {
            return false;
        }

        Image image = new Image(file.toURI().toString());
        return image.getWidth() >= 400 && image.getHeight() >= 400 && image.getWidth() <= 2000 && image.getHeight() <= 2000;
    }

    private void createExhibition() {

        //STRINGS ATTRIBUTES FOR OBJECT
        String name = textFieldnameExhibition.getText();
        String description = textFieldExdescription.getText();
        LocalDate startDate = datePickerstartDate.getValue();
        LocalDate endDate = datePickerEndDate.getValue();
        refenceImage = null;

        //CREATE NEW OBJECT
        Exhibition newExhibition = new Exhibition();
        newExhibition.setNameExhibition(name);
        newExhibition.setExdescription(description);

        newExhibition.setStartDate(startDate);
        newExhibition.setEndDate(endDate);


        newExhibition.setIdGallery(idGallery);
        newExhibition.setReferenceImage(refenceImage);

        //ADD TO DATABASE (FAZER OS TESTES PARA TRATAMENTO DA INFORMAÇÃO)
        if (newExhibition.getNameExhibition() == null || newExhibition.getNameExhibition().trim().isEmpty()
            || newExhibition.getNameExhibition().isBlank() || idGallery == 0 || startDate == null || endDate == null || isEndDateBeforeStartDate(startDate, endDate)) {
            System.out.println("Campo vazio. Não é possível atualizar.");
            labelSucessMensage.setText("Não foi possível atualizar!");

        } else {
            MainManageExhibition.addExhibition(newExhibition);
            labelSucessMensage.setText("Alterações efetuadas com sucesso!");
            System.out.println("atualizado com sucesso");
        }


    }

    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        return endDate.isBefore(startDate);
    }

}
