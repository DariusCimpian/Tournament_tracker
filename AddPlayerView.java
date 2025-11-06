package proiect2poo.tournamenttracker.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPlayerView {
    public static void show(Stage mainStage) {
        Label title = new Label("Adaugă Jucător");
        TextField tfNume = new TextField();
        tfNume.setPromptText("Nume jucător");
        TextField tfPrenume = new TextField();
        tfPrenume.setPromptText("Prenume jucător");

        Button btnSalveaza = new Button("Salvează");
        Button btnInapoi = new Button("Înapoi");

        btnSalveaza.setOnAction(e -> {
            // Aici poți face validare și adăugare efectivă
            System.out.println("Adăugat: " + tfNume.getText() + " " + tfPrenume.getText());
            mainStage.setScene(MainMenuView.getMainMenuScene(mainStage));
        });

        btnInapoi.setOnAction(e -> mainStage.setScene(MainMenuView.getMainMenuScene(mainStage)));

        VBox layout = new VBox(22, title, tfNume, tfPrenume, btnSalveaza, btnInapoi);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding:40;");

        Scene scene = new Scene(layout, 380, 320);
        mainStage.setScene(scene);
    }
}
