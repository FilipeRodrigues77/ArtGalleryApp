package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AboutView extends BorderPane {

    public AboutView(){
        doLayout();
        ManageMainView manageMainView = new ManageMainView();
        String cssTheme = manageMainView.themeCurrent;
        getStylesheets().add(cssTheme);
    }

    private void doLayout() {
        setPadding(new Insets(10));


        // ---------------------------------------------- TOP LAYOUT ----------------------------------------------


        Label titleLabel = new Label("Sobre Iuvenni ManagerApp");
        titleLabel.getStyleClass().add("my-center-label-3");

        String aboutText = "Temos como missão criar uma aplicação que conecta nosso público-alvo a artistas, galerias, " +
                "exposições com uma interface em português. " + "\n\n" +
                "Nossa aplicação busca promover a apreciação da arte e o acesso a uma ampla variedade de obras, " +
                "com uma visualização simples e direcionada ao " +
                "nosso nicho de mercado, jovens adultos, de modo a proporcionar uma experiência única ao " +
                "utilizador final.";

        Label aboutLabel = new Label(aboutText);
        VBox vBoxTop = new VBox(titleLabel, aboutLabel);
        vBoxTop.setSpacing(10);
        aboutLabel.setWrapText(true);
        setTop(vBoxTop);

        // ---------------------------------------------- CENTER LAYOUT ----------------------------------------------

        Label labelTeam = new Label("Equipe do Projeto: ");
        labelTeam.getStyleClass().add("my-blue-label");
        Label labelNuely = new Label("- Nuely Fernandes Furtado - (github.com/nueLY)");
        Label labelFilipe = new Label("- Filipe Antonio Rodrigues Severino Alves - (github.com/FilipeRodrigues77)");
        VBox vBoxTeam = new VBox(labelTeam,labelNuely,labelFilipe);
        vBoxTeam.setPadding(new Insets(10));
        vBoxTeam.setSpacing(5);

        Label labelSuportTeam = new Label("Agradecimentos aos formadores UpSkill:");
        labelSuportTeam.getStyleClass().add("my-blue-label");
        String suportNames = "- Aníbal Ponte, Bruno Silva, Cédric Grueau, Isabel Marques, Luís Damas, " +
                "Luís Esteves, Maria Santos, Paula Miranda. A toda equipe UpSkill Java 2023.";
        Label labelSuportNames = new Label(suportNames);
        labelSuportNames.setWrapText(true);
        VBox vBoxSuportTeam = new VBox(labelSuportTeam, labelSuportNames);
        vBoxSuportTeam.setPadding(new Insets(0,10,10,10));
        vBoxSuportTeam.setSpacing(5);

        VBox vBoxCenterLayout = new VBox(vBoxTeam, vBoxSuportTeam);
        vBoxCenterLayout.setSpacing(10);
        setCenter(vBoxCenterLayout);
        // ---------------------------------------------- BOTTOM LAYOUT ----------------------------------------------

        // LABEL
        Label labelHelp = new Label("Dúvidas e sugestões? Por favor contacte-nos:");
        labelHelp.getStyleClass().add("my-blue-label");
        Label labelHelpEmail1 = new Label("nuefurth@gmail.com");
        Label labelHelpEmail2 = new Label("sr.filipe.rodrigues77@gmail.com");
        Label labelBottonStatus = new Label("I~A © 2023  Todos os direitos reservados");
        Label labelAppVersion = new Label("App versão 1.0");
        // DEFINE A HBOX THAT WILL CONTAIN THE IMAGES (ADD SIMULTANEOUSLY)
        HBox hBoxBottomAppVersion = new HBox(labelAppVersion);
        hBoxBottomAppVersion.setSpacing(10);
        HBox.setHgrow(hBoxBottomAppVersion, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomAppVersion.setAlignment(Pos.CENTER_RIGHT);

        // HBOX BOTTOM
        VBox vBoxHelp = new VBox(labelHelp,labelHelpEmail1,labelHelpEmail2);
        vBoxHelp.setAlignment(Pos.CENTER);
        HBox hBoxBottomStatus = new HBox(labelBottonStatus);
        HBox.setHgrow(hBoxBottomStatus, javafx.scene.layout.Priority.ALWAYS);
        hBoxBottomStatus.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxBottomLayout = new HBox(hBoxBottomStatus, hBoxBottomAppVersion);
        hBoxBottomLayout.setPadding(new Insets(10,0,0,0));
        VBox vBoxBottomLayout = new VBox(vBoxHelp, hBoxBottomLayout);
        setBottom(vBoxBottomLayout);
    }

}
