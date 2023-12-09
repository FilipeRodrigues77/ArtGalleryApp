package view;

import domain.Artwork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import presenter.MainGetAllArtworks;

import java.util.List;

public class SceneExhibition extends BorderPane {

    public SceneExhibition() {
        doLayout();
        getStylesheets().add("appStyle.css");
        //getStylesheets().add("appStyleDark.css");
    }

    private void doLayout() {
        // Vamos criar aqui o layout deste painel
        setPadding(new Insets(20));

        //--------------------------------------------- HEADER ELEMENTS ---------------------------------------------

        // HYPERLINKS
        Hyperlink hyperlinkArtist = new Hyperlink("Artistas");
        Hyperlink hyperlinkMain = new Hyperlink("     home ^");
        Hyperlink hyperlinkGallery = new Hyperlink("Galerias");
        Hyperlink hyperlinkExhibition = new Hyperlink("Exposições");
        Hyperlink hyperLinkArtwork = new Hyperlink("Obras de Arte");

        // Labels
        Label labelfilter = new Label("Filtro =");

        // Text Fields
        TextField textFieldSearch = new TextField("Procurar por artista, galeria, exposição ou obra de arte");
        textFieldSearch.setPrefSize(550, 30);
        textFieldSearch.setOnMouseClicked(e -> textFieldSearch.clear());

        TextField textFieldSearchByStartDate = new TextField("Inicio ex. 21-02-2023:");
        textFieldSearchDefault(textFieldSearchByStartDate);

        TextField textFieldSearchByEndDate = new TextField("Data de fecho:");
        textFieldSearchDefault(textFieldSearchByEndDate);

        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "Open",
                "Closed"
        );

        // Images

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

        // ADD PAGE HEADER ELEMENTS
        HBox hBoxHeader = new HBox(logoView,textFieldSearch, searchIconView, hyperlinkMain);
        hBoxHeader.setSpacing(20);

        // ADD THE HYPERLINKS
        HBox hBoxHyperlink = new HBox(hyperlinkArtist,hyperLinkArtwork,hyperlinkGallery, hyperlinkExhibition);
        hBoxHyperlink.setPadding(new Insets(10,0,0,0));
        hBoxHyperlink.setPrefHeight(50);
        hBoxHyperlink.setSpacing(20);

        // CREATE LABEL FOR FILTER AREA
        Label filterLabel = new Label("Filtros = ");

        // CREATE A MOMBOX AND ADD THE OBSERVABLELIST TO EACH MENU

        ComboBox<String> statusMenu = new ComboBox<>(statusOptions);
        statusMenu.setMaxWidth(100);
        statusMenu.setValue("Status");

        // ADD ELEMENTS FOR THE MENU HBOX
        // SET HBOX FOR THE FILTER MENUS
        HBox hBoxMenu = new HBox(filterLabel, statusMenu, textFieldSearchByStartDate, textFieldSearchByEndDate);
        hBoxMenu.setSpacing(30);

        // ADD ALL THE ELEMENTS FOR THE TOP SIDE INSIDE A HEADER VBOX
        VBox vBoxTop = new VBox(hBoxHeader,hBoxHyperlink, hBoxMenu);
        setMargin(vBoxTop, new Insets(0, 0, 20, 0));
        setTop(vBoxTop);


        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        // CREATE A GRIDPANE
        GridPane grid = new GridPane();
        grid.setHgap(15.4);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));
        grid.setGridLinesVisible(false);

        // FILL THE GRIDPANE WITH IMAGES AND LABELS
        for (int i = 0; i < 35; i++) {
            int imageNum = i + 1;

            // Create a new VBox for each iteration
            Label labelExhibitionName = new Label("Nome da Exposição " + i);
            Label labelGalleryName = new Label("Galeria " + i);
            Label labelStatus = new Label("Aberto/Fechado " + i);
            Label labelStartDate = new Label("data inicio " + i);
            Label labelEndDate = new Label("data fecho " + i);
            Button buttonExhibitionDetails = new Button("Aceder Exposição");


            HBox hBoxExhibitionName = new HBox(labelExhibitionName);
            hBoxExhibitionName.setPadding(new Insets(10));
            HBox hBoxGalleryName = new HBox(labelGalleryName);
            hBoxGalleryName.setPadding(new Insets(10));
            HBox hBoxExhibitionStatus = new HBox(labelStatus, labelStartDate,labelEndDate);
            hBoxExhibitionStatus.setPadding(new Insets(10));
            HBox hBoxExhibitionDetails = new HBox(buttonExhibitionDetails);
            hBoxExhibitionDetails.setAlignment(Pos.CENTER);
            VBox vBoxExhibitionDetails = new VBox(hBoxExhibitionName,hBoxGalleryName,hBoxExhibitionStatus,hBoxExhibitionDetails);
            vBoxExhibitionDetails.setPrefWidth(730);

            labelExhibitionName.getStyleClass().add("my-label-gray-medium-size");
            vBoxExhibitionDetails.getStyleClass().add("cell-clear-background");

            int col = 0;
            int row = i;
            grid.add(vBoxExhibitionDetails, col, row + 1);
        }

        // CREATE SCROLL_PANE TO ALLOW US TO SCROLL THROUGH THE GRID_PANE
        ScrollPane scrollPane = new ScrollPane();
        // ADD GRID_PANE INSIDE THE SCROLL_PANE OBJ
        scrollPane.setContent(grid);
        scrollPane.getStyleClass().add("scroll-pane");
        // LAST STEP: CENTER THE SCROLL_PANE
        setCenter(scrollPane);

        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // DEFINE A HBOX THAT WILL CONTRAIN ALL THE BOTTOM ELEMENTS
        HBox bottomLayout = new HBox();

        // IMAGES

        // GIT IMAGE
        String imageGitHubPath = "Icons/Github.png";
        ImageView imageViewGitHub = new ImageView(new Image(imageGitHubPath));
        defaultSizeIcon(imageViewGitHub);

        // LINKEDIN IMAGE
        String imageLinkedin = "Icons/Linkedin.png";
        ImageView imageViewLinkedin = new ImageView(new Image(imageLinkedin));
        defaultSizeIcon(imageViewLinkedin);

        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox bottomImages = new HBox(imageViewLinkedin,imageViewGitHub);
        bottomImages.setSpacing(10);

        // STATUS LABEL
        Label labelBottonStatus = new Label("I~A © 2023 I~A  Todos os direitos reservados");
        bottomLayout.getChildren().addAll(labelBottonStatus,bottomImages);
        bottomLayout.setPadding(new Insets(20,0,0,0));
        bottomLayout.setSpacing(500);

        // ADD THE BOTTOM ELEMENTS INSIDE THE DESIGNATED HBOX
        this.setBottom(bottomLayout);


        // ---------------------------------------------- END  PLUS ----------------------------------------------


        // CONFIGURE ACTION TO CHANGE SCENARIO
        hyperLinkArtwork.setOnAction(e -> getScene().setRoot(new SceneArtwork()));
        hyperlinkMain.setOnAction(e -> getScene().setRoot(new MainView()));
        hyperlinkArtist.setOnAction(e -> getScene().setRoot(new SceneArtist()));
        hyperlinkGallery.setOnAction(e -> getScene().setRoot(new SceneGallery()));

    }

    public void defaultSizeIcon (ImageView imageView){
        imageView.setFitHeight(18); // Ajuste a altura conforme necessário
        imageView.setFitWidth(18);  // Ajuste a largura conforme necessário
        // imageView.setPreserveRatio(true);
    }

    public void defaultSizeExhibitionImage(ImageView imageView){
        imageView.setFitHeight(250); // Ajuste a altura conforme necessário
        imageView.setFitWidth(380);  // Ajuste a largura conforme necessário
        imageView.setPreserveRatio(true);
    }

    public void textFieldSearchDefault(TextField textField) {
        textField.setPrefSize(150, 15);
        textField.setOnMouseClicked(e -> textField.clear());
    }

}
