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


/**
 * The {@code MainView} class represents the main view of the Iuvenis Artem application.
 * It includes the layout for the main menu, search functionality, and featured images.
 * Users can navigate to different sections of the application using hyperlinks and buttons.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class MainView extends BorderPane {

    private Label labelStatus;
    private TextField textExpression;
    private Properties config;
    private RadioMenuItem lightThemeItem;
    private RadioMenuItem darkThemeItem;
    protected String themeCurrent;

    /**
     * Constructs an instance of the {@code MainView} class.
     * Initializes the layout, loads configuration settings, and sets up the initial theme.
     */
    public MainView() {

        doLayout();
        loadConfig();
        String themeName = this.config.getProperty("theme","Light");
        changeTheme(themeName);
        this.themeCurrent = "appStyle" + themeName + ".css";
    }

    /**
     * Loads configuration settings from the configuration file.
     */
    public void loadConfig(){
        this.config = new Properties();
        try {
            FileReader file = new FileReader("appView/src/main/resources/iuvennisApp.config");
            this.config.load(file);
            file.close();
        } catch (IOException e) {
            showError("Configuration file not found.");
        }
    }

    /**
     * Saves the current configuration settings to the configuration file.
     */
    private void saveConfig(){
        try {
            this.config.store(new FileWriter("appView/src/main/resources/iuvennisApp.config"),"");
        } catch (IOException e) {
            showError("Not possible to save configuration");
        }
    }

    /**
     * Configures the layout and visual elements for the main view.
     */
    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20,0,20,0));

        // GET THE HEADER BOX
        setTop(getHeaderBox());

        // Labels
        Label labelCenterArtwork = new Label("I~A Mundo~Obras de Arte");
        Label labelCenterArtist = new Label("I~A Mundo~Artistas");
        Label labelCenterGallery = new Label("I~A Mundo~Galerias");
        Label labelCenterExhibition = new Label("I~A Mundo~Exposições");


        styleLabelCenterArtwork(labelCenterArtwork);
        styleLabelCenterArtwork(labelCenterArtist);
        styleLabelCenterArtwork(labelCenterGallery);
        styleLabelCenterArtwork(labelCenterExhibition);

        // Buttons
        Button buttonArtwork = new Button("Ver Obras de Arte");
        Button buttonArtist = new Button("Ver Artistas");
        Button buttonGallery = new Button("Ver Galerias");
        Button buttonExhibition = new Button("Ver Exposições");

        defaultButtonLayout(buttonArtwork);
        defaultButtonLayout(buttonArtist);
        defaultButtonLayout(buttonGallery);
        defaultButtonLayout(buttonExhibition);

        // ---------------------------------------------------------------
        // CENTER LAYOUT IMAGES
        String imageMainArtwork = "Images/Main/artworkMain.png";
        ImageView imageViewMainArtwork = new ImageView(new Image(imageMainArtwork));
        defaultSizeMainImage(imageViewMainArtwork);

        String imageMainArtist = "Images/Main/artistMain.jpeg";
        ImageView imageViewMainArtist = new ImageView(new Image(imageMainArtist));
        defaultSizeMainImage(imageViewMainArtist);

        String imageMainGallery = "Images/Main/exhibitionMain.png";
        ImageView imageViewMainGallery = new ImageView(new Image(imageMainGallery));
        defaultSizeMainImage(imageViewMainGallery);

        String imageMainExhibition = "Images/Main/galleryMain.jpg";
        ImageView imageViewMainExhibition = new ImageView(new Image(imageMainExhibition));
        defaultSizeMainImage(imageViewMainExhibition);


        // CENTER LAYOUT GRID
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(0,0,10,0));

        // Configuração de columnSpan e rowSpan para cobrir a célula (0,0)
        // Ajustar as propriedades de alinhamento
        grid.add(imageViewMainArtwork,0,0);
        grid.add(imageViewMainArtist,0,1);
        grid.add(imageViewMainExhibition,0,2);
        grid.add(imageViewMainGallery,0,3);

        VBox vBoxArtwork = new VBox(labelCenterArtwork,buttonArtwork);
        vBoxArtwork.setSpacing(10);
        vBoxArtwork.getStyleClass().add("cell-gray-background");

        VBox vBoxArtist = new VBox(labelCenterArtist,buttonArtist);
        vBoxArtist.setSpacing(10);
        vBoxArtist.getStyleClass().add("cell-gray-background");

        VBox vBoxGallery = new VBox(labelCenterGallery,buttonGallery);
        vBoxGallery.setSpacing(10);
        vBoxGallery.getStyleClass().add("cell-gray-background");

        VBox vBoxExhibition = new VBox(labelCenterExhibition,buttonExhibition);
        vBoxExhibition.setSpacing(10);
        vBoxExhibition.getStyleClass().add("cell-gray-background");

        grid.add(vBoxArtwork, 1, 0);
        grid.add(vBoxArtist, 1,1);
        grid.add(vBoxGallery, 1,2);
        grid.add(vBoxExhibition, 1,3);

        vBoxArtwork.setAlignment(Pos.CENTER);
        vBoxArtist.setAlignment(Pos.CENTER);
        vBoxGallery.setAlignment(Pos.CENTER);
        vBoxExhibition.setAlignment(Pos.CENTER);

        labelCenterArtwork.setAlignment(Pos.CENTER);
        labelCenterArtist.setAlignment(Pos.CENTER);
        labelCenterGallery.setAlignment(Pos.CENTER);
        labelCenterExhibition.setAlignment(Pos.CENTER);

        // Fazer com que os botões preencham a grelha uniformemente

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

        // -------------------------------------------------------------------------
        // Configurar a ação do botão para mudar cenario
        buttonArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));

        // -------------------------------------------------------------------------

        setBottom(getFooterBox());
    }

    /**
     * Creates and returns the header box containing hyperlinks, search functionality,
     * and the application logo.
     *
     * @return The VBox representing the header box.
     */
    private VBox getHeaderBox (){

        // Hyperlinks
        Hyperlink hyperlinkArtwork = new Hyperlink("Obras de Arte");
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperlinkSlideShow = new Hyperlink("SlideShow");

        hyperlinkArtwork.getStyleClass().add("hyperlink");

        // Text Fields
        String textFieldOrinalText = "Funcionalidade por aplicar";
        TextField textFieldSearch = new TextField(textFieldOrinalText);
        textFieldSearch.setPrefSize(550, 30);
        setOriginalDescription(textFieldSearch,textFieldOrinalText);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

        // Imagens
        // I~A LOGO
        Image logo = new Image("Images/logo/logoIA-02.png");
        ImageView logoView = new ImageView(logo);
        logoView.preserveRatioProperty();
        logoView.setFitWidth(27);
        logoView.setFitHeight(27);
        logoView.setSmooth(true);

        // SEARCH ICON
        Image searchIcon = new Image("Icons/searchIcon-03.png");
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.preserveRatioProperty();
        searchIconView.setFitWidth(20);
        searchIconView.setFitHeight(20);
        searchIconView.setSmooth(true);


        // Layout Top
        // Create Menu
        //Adiciona Theme no menu
        Menu menuTheme = new Menu("Tema App");
        ToggleGroup themeGroup = new ToggleGroup(); //Alternador de grupos
        this.lightThemeItem = new RadioMenuItem("Dia");
        this.darkThemeItem = new RadioMenuItem("Noite");
        menuTheme.getItems().addAll(lightThemeItem,darkThemeItem);

        //TOGGLE TO ALTERNATE BETWEEN THE OPTIONS
        lightThemeItem.setToggleGroup(themeGroup);
        darkThemeItem.setToggleGroup(themeGroup);
        //THEME MENU EVENTS
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
        // CREATE MENU BAR
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuHeader);
        menuBar.setPrefHeight(30);

        // Set Box
        HBox hBoxSearch = new HBox(logoView, textFieldSearch, searchIconView, menuBar);
        hBoxSearch.setSpacing(20);

        HBox hBoxHyperlink = new HBox(hyperlinkArtist, hyperlinkArtwork, hyperlinkGallery, hyperlinkExhibition, hyperlinkSlideShow);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        VBox vBoxTop = new VBox(hBoxSearch,hBoxHyperlink);
        setMargin(vBoxTop, new Insets(0, 20, 20, 20));

        hyperlinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new SceneExhibition()));
        hyperlinkSlideShow.setOnAction(e -> getScene().setRoot(new SlideShowView()));

        return  vBoxTop;

    }

    /**
     * Creates and returns the footer box containing social media icons and status information.
     *
     * @return The HBox representing the footer box.
     */
    private HBox getFooterBox (){

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // LABEL
        Label labelBottomStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);
        HBox.setHgrow(hBoxBottomImages, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomImages.setAlignment(Pos.CENTER_RIGHT);

        // HBOX BOTTOM
        HBox hBoxBottomStatus = new HBox(labelBottomStatus);
        HBox.setHgrow(hBoxBottomStatus, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomStatus.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxBottomLayout = new HBox(hBoxBottomStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(10,20,30,20));

        return hBoxBottomLayout;
    }

    /**
     * Sets the original description for a TextField and restores it when the focus is lost.
     *
     * @param textField     The TextField to set the original description for.
     * @param originalText  The original description text.
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
     * Sets the default size for an icon ImageView.
     *
     * @param imageView The ImageView to set the default size for.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    /**
     * Sets the default size for a main image ImageView.
     *
     * @param imageView The ImageView to set the default size for.
     */
    public void defaultSizeMainImage (ImageView imageView){
        imageView.setFitHeight(100); // Ajuste a altura conforme necessário
        imageView.setFitWidth(390);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    /**
     * Sets the default layout for a button, applying a modern style.
     *
     * @param button The Button to set the default layout for.
     */
    public void defaultButtonLayout (Button button){
        button.getStyleClass().add("button-modern");
        button.setPrefWidth(180);
        button.setPrefHeight(10);
    }

    /**
     * Styles a label with a specific style class.
     *
     * @param label The Label to style.
     */
    public void styleLabelCenterArtwork (Label label){
        label.getStyleClass().add("my-label");
    }

    /**
     * Exits the application, showing a confirmation dialog.
     */
    private void exitApplication(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair da App");
        alert.setHeaderText("Está prestes a sair da Iuvenis Artem");
        alert.setContentText("Deseja sair da aplicação?");

        alert.initOwner(this.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.exit(0);
        }
    }

    /**
     * Displays the "About" window, providing information about the application.
     */
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

    /**
     * Changes the application theme and saves the configuration settings.
     *
     * @param themeName The name of the theme to apply.
     * @return The CSS file path of the applied theme.
     */
    protected String changeTheme(String themeName) {

        String cssFile = "appStyle" + themeName + ".css";

        getStylesheets().clear();
        getStylesheets().add(cssFile);

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

    /**
     * Displays an error message in the status label.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        if(labelStatus != null){
            labelStatus.setText(message);
            textExpression.getStyleClass().add("Error");
        }

    }

}
