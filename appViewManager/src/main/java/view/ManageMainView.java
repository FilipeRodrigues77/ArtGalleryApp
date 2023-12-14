package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ManageMainView extends BorderPane {
    private Label labelStatus;
    private TextField textExpression;
    private Properties config;
    private RadioMenuItem lightThemeItem;
    private RadioMenuItem darkThemeItem;
    protected String themeCurrent;

    public ManageMainView() {

        doLayout();
        loadConfig();
        String themeName = this.config.getProperty("theme","Light");
        changeTheme(themeName);
        this.themeCurrent = "appStyle" + themeName + ".css";
    }

    public void loadConfig(){
        this.config = new Properties();
        try {
            FileReader file = new FileReader("appViewManager/src/main/resources/iuvennisApp.config");
            this.config.load(file);
            file.close();
        } catch (IOException e) {
            showError("Configuration file not found.");
        }
    }
    private void saveConfig(){
        try {
            this.config.store(new FileWriter("appViewManager/src/main/resources/iuvennisApp.config"),"");
        } catch (IOException e) {
            showError("Not possible to save configuration");
        }
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        // Controls

        // Hyperlinks
        Hyperlink hyperlinkArtwork = new Hyperlink("Obras de Arte");
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");

        // Labels
        Label labelCenterArtwork = new Label("Gestão de Obras de Arte");
        Label labelCenterArtist = new Label("Gestão de Artistas");
        Label labelCenterGallery = new Label("Gestão de Galerias");
        Label labelCenterExhibition = new Label("Gestão de Exposições");
        Label labelBottonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        styleLabelCenterArtwork(labelCenterArtwork);
        styleLabelCenterArtwork(labelCenterArtist);
        styleLabelCenterArtwork(labelCenterGallery);
        styleLabelCenterArtwork(labelCenterExhibition);

        // Text Fields
        Label labelManagerInfo = new Label("Sistema de Gestão Iuvennis Art");
        labelManagerInfo.setPrefSize(550, 30);
        labelManagerInfo.getStyleClass().add("my-center-label-6 ");

        // Buttons
        Button buttonArtwork = new Button("Aceder Obras de Arte");
        Button buttonArtist = new Button("Aceder Artistas");
        Button buttonGallery = new Button("Aceder Galerias");
        Button buttonExhibition = new Button("Aceder Exposições");

        defaultButtonLayout(buttonArtwork);
        defaultButtonLayout(buttonArtist);
        defaultButtonLayout(buttonGallery);
        defaultButtonLayout(buttonExhibition);

        // I~A LOGO
        Image logo = new Image("Images/logo/logoIA-02.png");
        ImageView logoView = new ImageView(logo);
        logoView.preserveRatioProperty();
        logoView.setFitWidth(27);
        logoView.setFitHeight(27);
        logoView.setSmooth(true);

        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        String imageMainArtwork = "Images/Main/exampleMainArtwork.png";
        ImageView imageViewMainArtwork = new ImageView(new Image(imageMainArtwork));
        defaultSizeMainImage(imageViewMainArtwork);

        String imageMainArtist = "Images/Main/exampleMainArtist.png";
        ImageView imageViewMainArtist = new ImageView(new Image(imageMainArtist));
        defaultSizeMainImage(imageViewMainArtist);

        String imageMainGallery = "Images/Main/exampleMainMuseum.png";
        ImageView imageViewMainGallery = new ImageView(new Image(imageMainGallery));
        defaultSizeMainImage(imageViewMainGallery);

        String imageMainExhibition = "Images/Main/exampleMainExhibition.png";
        ImageView imageViewMainExhibition = new ImageView(new Image(imageMainExhibition));
        defaultSizeMainImage(imageViewMainExhibition);

        // Layout Top
        // Create Menu
        Menu menuTheme = new Menu("Tema App");
        ToggleGroup themeGroup = new ToggleGroup(); //Alternador de grupos
        this.lightThemeItem = new RadioMenuItem("Dia");
        this.darkThemeItem = new RadioMenuItem("Noite");
        menuTheme.getItems().addAll(lightThemeItem,darkThemeItem);
        //Toogle
        lightThemeItem.setToggleGroup(themeGroup);
        darkThemeItem.setToggleGroup(themeGroup);

        lightThemeItem.setSelected(true);
        lightThemeItem.setOnAction(event -> changeTheme("Light"));
        darkThemeItem.setOnAction(event -> changeTheme("Dark"));

        //MENUS
        MenuItem aboutItem = new MenuItem("Sobre nós");
        Menu menuAboutUs = new Menu("Ajuda");
        menuAboutUs.getItems().addAll(aboutItem);
        aboutItem.setOnAction(event -> showAbout());
        MenuItem exitItem = new MenuItem("Sair");
        exitItem.setOnAction(event -> exitApplication());
        Menu menuHeader = new Menu("I~A App");
        menuHeader.getItems().addAll(menuAboutUs,menuTheme,exitItem);
        // Create Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuHeader);
        menuBar.setPrefHeight(30);
        // Set Box
        HBox hBoxSearch = new HBox(logoView, labelManagerInfo, menuBar);
        hBoxSearch.setSpacing(20);
        HBox hBoxHyperlink = new HBox(hyperlinkArtist, hyperlinkArtwork, hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        VBox vBoxTop = new VBox(hBoxSearch,hBoxHyperlink);

        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);

        // Layout Bottom
        HBox hBoxBottomStatus = new HBox(labelBottonStatus);
        HBox.setHgrow(hBoxBottomStatus, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomStatus.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);
        HBox.setHgrow(hBoxBottomImages, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomImages.setAlignment(Pos.CENTER_RIGHT);
        HBox bottomLayout = new HBox(hBoxBottomStatus,hBoxBottomImages);
        setBottom(bottomLayout);

        // Layout Center
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(0,0,10,0));

        grid.add(imageViewMainArtist,1,0);
        grid.add(imageViewMainArtwork,1,1);
        grid.add(imageViewMainExhibition,1,2);
        grid.add(imageViewMainGallery,1,3);

        VBox vBoxArtwork = new VBox(labelCenterArtwork,buttonArtwork);
        vBoxArtwork.setSpacing(10);
        VBox vBoxArtist = new VBox(labelCenterArtist,buttonArtist);
        vBoxArtist.setSpacing(10);
        VBox vBoxGallery = new VBox(labelCenterGallery,buttonGallery);
        vBoxGallery.setSpacing(10);
        VBox vBoxExhibition = new VBox(labelCenterExhibition,buttonExhibition);
        vBoxExhibition.setSpacing(10);

        grid.add(vBoxArtist, 0,0);
        grid.add(vBoxArtwork, 0, 1);
        grid.add(vBoxGallery, 0,2);
        grid.add(vBoxExhibition, 0,3);

        vBoxArtwork.setAlignment(Pos.CENTER);
        vBoxArtist.setAlignment(Pos.CENTER);
        vBoxGallery.setAlignment(Pos.CENTER);
        vBoxExhibition.setAlignment(Pos.CENTER);

        labelCenterArtwork.setAlignment(Pos.CENTER);
        labelCenterArtist.setAlignment(Pos.CENTER);
        labelCenterGallery.setAlignment(Pos.CENTER);
        labelCenterExhibition.setAlignment(Pos.CENTER);

        // Re-size view

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(50);
        cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
        cc.setFillWidth(true); // ask nodes to fill space for column
        grid.getColumnConstraints().addAll(cc, cc);

        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS) ; // allow row to grow
        rc.setFillHeight(true); // ask nodes to fill height for row
        grid.getRowConstraints().addAll(rc, rc, rc, rc);

        labelCenterArtwork.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterArtist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterGallery.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterExhibition.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setCenter(grid);

        // Configure action buttons
        buttonArtwork.setOnAction(e -> getScene().setRoot(new ManageArtwork()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new ManageArtist()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new ManageGallery()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibition()));

        hyperlinkArtwork.setOnAction(e -> getScene().setRoot(new ManageArtwork()));
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtist()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new ManageGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibition()));

    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeMainImage (ImageView imageView){
        imageView.setFitHeight(100);
        imageView.setFitWidth(365);
        // imageView.setPreserveRatio(true);
    }

    public void defaultButtonLayout (Button button){
        button.getStyleClass().add("button-modern");
        button.setPrefWidth(180);
        button.setPrefHeight(10);
    }

    public void styleLabelCenterArtwork (Label label){
        label.getStyleClass().add("my-label");
    }

    private void exitApplication(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair da App");
        alert.setHeaderText("Está prestes a sair da Iuvennis ManagerApp");
        alert.setContentText("Deseja sair da aplicação?");
        //Colocar tela de confirmação de saida centralizada a aplicação
        //buscar Cena e Janela.
        alert.initOwner(this.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.exit(0);
        } else {
            // ... user chose CANCEL or closed the dialog
            // do nothing
        }
    }

    private void showAbout(){
        Stage aboutStage = new Stage();
        aboutStage.setTitle("Sobre nós");
        Scene scene = new Scene(new AboutView(), 500,420);
        aboutStage.setScene(scene);
        aboutStage.setResizable(false);
        aboutStage.initOwner(this.getScene().getWindow());
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    protected String changeTheme(String themeName) {
        //Assume que o ficheiro te a estrutura: "appStyle<themeName>.css"
        String cssFile = "appStyle" + themeName + ".css";
        //Limpar estilo para o default e depois adiciona o estilo selecionado
        getStylesheets().clear();
        getStylesheets().add(cssFile);
        //Salvar configuração
        this.config.setProperty("theme", themeName);
        saveConfig();

        switch (themeName){
            case "Light": lightThemeItem.setSelected(true);
                break;
            case "Dark": darkThemeItem.setSelected(true);
                break;

            default: lightThemeItem.setSelected(true);
        }
        return cssFile;
    }

    public void showError(String message) {
        labelStatus.setText(message);
        textExpression.getStyleClass().add("Error");
    }

}
