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
 * The {@code ManageMainView} class represents the main view of the Iuvenis Artem Management System.
 * It serves as the entry point for managing different aspects of the art collection, such as artwork, artists, galleries, and exhibitions.
 * The class extends {@link BorderPane} and provides a user interface with hyperlinks, buttons, and themes to navigate through the application.
 * It also allows users to change the application theme between light and dark modes.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class ManageMainView extends BorderPane {

    private Label labelStatus;
    private TextField textExpression;
    private Properties config;
    private RadioMenuItem lightThemeItem;
    private RadioMenuItem darkThemeItem;
    protected String themeCurrent;

    /**
     * Constructs a new {@code ManageMainView} instance.
     * Initialises the layout, loads the application configuration,
     * sets the current theme, and adds stylesheets. It also sets a style to the view.
     *
     * @see ManageMainView#doLayout()
     * @see ManageMainView#loadConfig()
     * @see ManageMainView#changeTheme(String)
     */
    public ManageMainView() {
        doLayout();
        loadConfig();
        String themeName = this.config.getProperty("theme","Light");
        changeTheme(themeName);
        this.themeCurrent = "appStyle" + themeName + ".css";
        getStylesheets().add(themeCurrent);
        getStyleClass().add("wallpaper");
    }

    /**
     * Loads the application configuration from the configuration file. If the file is not found, an error is shown.
     *
     * @see ManageMainView#showError(String)
     */
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

    /**
     * Saves the current application configuration to the configuration file. If an error occurs, an error message is shown.
     *
     * @see ManageMainView#showError(String)
     */
    private void saveConfig(){
        try {
            this.config.store(new FileWriter("appViewManager/src/main/resources/iuvennisApp.config"),"");
        } catch (IOException e) {
            showError("Not possible to save configuration");
        }
    }

    /**
     * Initialises the layout of the {@code ManageMainView} class, including the top, center, and bottom sections.
     * Sets up hyperlinks, buttons, labels, and themes to navigate through the application and manage the art collection.
     */
    private void doLayout() {

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
        Label labelButtonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

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

        // Layout Top
        // Create Menu
        Menu menuTheme = new Menu("Tema App");
        ToggleGroup themeGroup = new ToggleGroup();
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
        HBox hBoxBottomStatus = new HBox(labelButtonStatus);
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
        rc.setVgrow(Priority.ALWAYS) ; // allow the row to grow
        rc.setFillHeight(true); // ask nodes to fill height for row
        grid.getRowConstraints().addAll(rc, rc, rc, rc);

        labelCenterArtwork.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterArtist.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterGallery.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelCenterExhibition.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setCenter(grid);

        // Configure action buttons
        buttonArtwork.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));
        buttonArtist.setOnAction(e -> getScene().setRoot(new ManageArtistView()));
        buttonGallery.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));
        buttonExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibitionView()));

        hyperlinkArtwork.setOnAction(e -> getScene().setRoot(new ManageArtworkView()));
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new ManageArtistView()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new ManageGalleryView()));
        hyperlinkExhibition.setOnAction(e -> getScene().setRoot(new ManageExhibitionView()));

    }

    /**
     * Sets the default size for an {@link ImageView}, adjusting the fit height and fit width.
     *
     * @param imageView The {@code ImageView} to set the default size for.
     */
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);

    }

    /**
     * Sets the default layout for a {@link Button}, including style class, preferred width, and preferred height.
     *
     * @param button The {@code Button} to set the default layout for.
     */
    public void defaultButtonLayout (Button button){
        button.getStyleClass().add("button-modern");
        button.setPrefWidth(180);
        button.setPrefHeight(10);
    }

    /**
     * Applies a specific style to a {@link Label} to enhance its appearance in the view.
     *
     * @param label The {@code Label} to apply the style to.
     */
    public void styleLabelCenterArtwork (Label label){
        label.getStyleClass().add("my-label");
    }

    /**
     * Exits the application, displaying a confirmation dialogue to ensure the user's intention.
     * If the user confirms, the application is terminated; otherwise, nothing happens.
     */
    private void exitApplication(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair da App");
        alert.setHeaderText("Está prestes a sair da Iuvennis ManagerApp");
        alert.setContentText("Deseja sair da aplicação?");
        // put the confirmation pane in the center of the application

        //Search scene and window
        alert.initOwner(this.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.exit(0);
        }
    }

    /**
     * Shows the "About Us" information in a new stage. The information is displayed using the {@code AboutView} class.
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
     * Changes the current theme of the application based on the given theme name. It loads the corresponding
     * stylesheet and updates the configuration. The selected theme is saved, and radio menu items are adjusted accordingly.
     *
     * @param themeName The name of the theme to switch to (e.g., "Light" or "Dark").
     * @return The file name of the applied stylesheet.
     */
    protected String changeTheme(String themeName) {
        // Assumes that the file has the "appStyle<themeName>.css" structure
        String cssFile = "appStyle" + themeName + ".css";

        // remove the style to the default and then add the chosen style
        getStylesheets().clear();
        getStylesheets().add(cssFile);
        // Save configuration
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
     * Displays an error message in the status label and applies an "Error" style to the text expression field.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        if(labelStatus!=null){
            labelStatus.setText(message);
            textExpression.getStyleClass().add("Error");
        }

    }

}
