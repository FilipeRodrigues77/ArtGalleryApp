package view;

import domain.Exhibition;
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
import presenter.MainGetExhibitions;
import presenter.MainManageExhibition;


import java.util.List;

/**
 * The {@code ManageExhibitionView} class represents the view for managing art exhibitions in the Iuvenis Artem Management System.
 * It extends {@link BorderPane} and provides a user interface for listing, creating, editing, and deleting art exhibitions.
 * Users can perform these actions through buttons and search functionalities.
 *
 * @author Nuely Furtado
 * @author Filipe Alves
 * @version v1.0
 */
public class ManageExhibitionView extends BorderPane {

    private ObservableList<Exhibition> observableListExhibition;
    private ListView<Exhibition> listViewExhibition;

    /**
     * Constructs a new {@code ManageExhibitionView} instance.
     * Initialises the layout, including header, center, and bottom sections,
     * to facilitate the management of exhibitions. Displays a list of exhibitions, buttons for creating, editing, and deleting exhibitions,
     * and a search bar for filtering exhibitions by name.
     *
     * @see ManageMainView
     */
    public ManageExhibitionView() {
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    /**
     * Initializes the layout of the {@code ManageExhibitionView} class, including header, center, and bottom sections.
     * Displays a list of exhibitions, buttons for creating, editing, and deleting exhibitions, and a search bar for filtering exhibitions by name.
     */
    private void doLayout() {
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // CREATE A LIST VIEW
        List<Exhibition> listExhibition = MainGetExhibitions.getAllExhibitions();
        this.observableListExhibition = FXCollections.observableArrayList(listExhibition);
        this.listViewExhibition = new ListView<>(observableListExhibition);

        // BUTTONS

        Button createButton = new Button("Criar");
        createButton.setOnAction(e -> getScene().setRoot(new CreateExhibitionView()));

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> editSelectedExhibition());

        Button deleteButton = new Button("Apagar");
        deleteButton.setOnAction((e -> removeSelectedExhibition()));

        createButton.getStyleClass().add("button-modern");
        editButton.getStyleClass().add("button-modern");
        deleteButton.getStyleClass().add("button-modern");
        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(createButton, editButton, deleteButton);
        hBoxMenu.setSpacing(50);
        hBoxMenu.setAlignment(Pos.CENTER);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(getHeaderBox(), hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        this.setCenter(listViewExhibition);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());

    }

    /**
     * Returns a {@link HBox} containing the footer elements.
     *
     * @return The {@code HBox} containing the footer elements.
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
        Label labelButtonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        hBoxBottomImages.setSpacing(10);

        // HBOX BOTTOM
        HBox hBoxBottomLayout = new HBox(labelButtonStatus,hBoxBottomImages);
        hBoxBottomLayout.setPadding(new Insets(20,0,0,0));
        hBoxBottomLayout.setSpacing(500);

        return hBoxBottomLayout;
    }

    /**
     * Returns a {@link VBox} containing the header elements, including hyperlinks to various sections of the application
     * such as Artists, Home, Galleries, Exhibitions, and Artworks.
     * Also includes a search bar and the Iuvenis Artem logo.
     *
     * @return The {@code VBox} containing the header elements.
     */
    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        String searchOrigText = "Procurar por nome da exposição";
        TextField textFieldSearch = new TextField(searchOrigText);
        setOriginalDescription(textFieldSearch,searchOrigText);
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

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
        searchIconView.setOnMouseClicked(e -> handleSearchIconSelection(textFieldSearch));

        // ADD PAGE HEADER ELEMENTS --
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
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

    /**
     * Sets the original description for a {@link TextField} to be used as prompt text when the field loses focus.
     *
     * @param textField     The {@code TextField} for which to set the original description.
     * @param originalText  The original description to be set as prompt text.
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
     * Handles the action when the search icon is selected. Filters exhibitions based on the entered search text
     * and updates the list view accordingly. If no results are found, redirects to the error view.
     *
     * @param textFieldSearch The {@code TextField} for entering the search text.
     */
    private void handleSearchIconSelection(TextField textFieldSearch ) {
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Exhibition> exhibitions = MainGetExhibitions.getExhibitionsByName(searchText);
            if (exhibitions != null) {
                ObservableList<Exhibition> observableListExhibitionFiltered = FXCollections.observableArrayList(exhibitions);
                ListView<Exhibition> listViewExhibitionFiltered = new ListView<>(observableListExhibitionFiltered);
                this.setCenter(listViewExhibitionFiltered);
            } else {
                getScene().setRoot(new ShowErrorView());
            }

        }
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
     * Handles the action when the "Editar" (Edit) button is pressed. Opens the edit view for the selected exhibition.
     */
    private void editSelectedExhibition() {
        Exhibition selectedExhibition = listViewExhibition.getSelectionModel().getSelectedItem();

        if (selectedExhibition != null) {
            getScene().setRoot(new EditExhibitionView(selectedExhibition));
            listViewExhibition.refresh();
        }
    }

    /**
     * Handles the action when the "Apagar" (Delete) button is pressed. Prompts the user for confirmation and
     * deletes the selected exhibition from the database if confirmed.
     */
    private void removeSelectedExhibition() {
        Exhibition selectedExhibition = listViewExhibition.getSelectionModel().getSelectedItem();

        if (selectedExhibition != null) {
            // ALERT DELETE MESSAGE
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Deletar Esposição");
            confirmation.setHeaderText("Iuvenis Artem - Base de Dados (Confirmar Exclusão)");
            confirmation.setContentText("Tem certeza que deseja DELETAR a exposição? Essa alteração não tem retorno.");

            ButtonType buttonYes = new ButtonType("Sim");
            ButtonType buttonNo = new ButtonType("Não");
            confirmation.getButtonTypes().setAll(buttonYes, buttonNo);

            confirmation.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    // EXCLUDE OBJECT
                    MainManageExhibition.removeExhibition(selectedExhibition);
                    listViewExhibition.getItems().remove(selectedExhibition);
                }
            });

            // ATUALIZE LISTVIEW
            listViewExhibition.refresh();
        }

    }

}
