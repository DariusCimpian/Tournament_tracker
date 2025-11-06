package proiect2poo.tournamenttracker.ui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import proiect2poo.tournamenttracker.ui.AddPlayerView;

public class MainMenuView {
    public static Scene getMainMenuScene(Stage stage) {
        Text titlu = new Text("MENIU");
        titlu.setFill(Color.web("#31435f"));
        titlu.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Segoe UI Light", Math.max(stage.getHeight()/19, 26)),
                stage.heightProperty()
        ));

        Button btnAddPlayer = new Button("Adaugă Jucător");
        btnAddPlayer.setOnAction(e -> AddPlayerView.show(stage));
        // Tot aici poți pune alte butoane...

        VBox vbox = new VBox(24, titlu, btnAddPlayer);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 600, 400);
        return scene;
    }
}
