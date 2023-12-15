package view;

import domain.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import presenter.*;

import java.util.List;

public class ManageArtistView extends BorderPane {
    private ObservableList<Artist> observableListArtist;
    private ListView<Artist> listViewArtist;

    public ManageArtistView() {

        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {

        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // CREATE A LIST VIEW
        List<Artist> listArtists = MainGetArtists.getAllArtists();
        this.observableListArtist = FXCollections.observableArrayList(listArtists);
        this.listViewArtist = new ListView<>(observableListArtist);

        // BUTTONS

        Button createButton = new Button("Criar");
        createButton.setOnAction(e -> getScene().setRoot(new CreateArtistView()));

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> editSelectedArtist());

        Button deleteButton = new Button("Apagar");
        deleteButton.setOnAction((e -> removeSelectedArtist()));

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

        this.setCenter(listViewArtist);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(getFooterBox());

    }

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

    private VBox getHeaderBox (){

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Text Fields
        String searchOrigText = "Procurar por nome do artista";
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
        hyperlinkArtist.getStyleClass().add("actual-page-hyperlink");

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

    private void handleSearchIconSelection(TextField textFieldSearch ){
        String searchText = textFieldSearch.getText().trim();
        if (!searchText.isEmpty()) {

            List<Artist> artists = MainGetArtists.getArtistByName(searchText);
            if(artists != null){
                ObservableList<Artist> observableListArtistFiltered = FXCollections.observableArrayList(artists);
                ListView<Artist> listViewArtistFiltered = new ListView<>(observableListArtistFiltered);
                this.setCenter(listViewArtistFiltered);
            }
            else{
                getScene().setRoot(new ShowErrorView());
            }

        }
    }
    // image treatment
    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeArtistImage(ImageView imageView){
        imageView.setFitHeight(160);
        imageView.setFitWidth(160);
        //imageView.setPreserveRatio(true);
    }

    private void editSelectedArtist() {
        Artist selectedArtist = listViewArtist.getSelectionModel().getSelectedItem();

        if (selectedArtist != null) {
            getScene().setRoot(new EditArtistView(selectedArtist));
            listViewArtist.refresh();
        }
    }

    private void removeSelectedArtist() {
        Artist selectedArtist = listViewArtist.getSelectionModel().getSelectedItem();

        if (selectedArtist != null) {
            // ALERT DELETE MESSAGE
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Deletar Artista");
            confirmation.setHeaderText("Iuvennis Art - Base de Dados (Confirmar Exclusão)");
            confirmation.setContentText("Tem certeza que deseja DELETAR o Artista? Essa alteração não tem retorno.");

            ButtonType buttonYes = new ButtonType("Sim");
            ButtonType buttonNo = new ButtonType("Não");
            confirmation.getButtonTypes().setAll(buttonYes, buttonNo);

            confirmation.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    // EXCLUDE OBJECT
                    MainRemoveArtists.removeArtist(selectedArtist);
                    listViewArtist.getItems().remove(selectedArtist);
                }
            });

            // ATUALIZE LISTVIEW
            listViewArtist.refresh();
        }

    }

}
