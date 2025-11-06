package proiect2poo.tournamenttracker.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;

public class MainMenuController {
    @FXML private ImageView imgBackground;
    @FXML private Button btnAdaugaJucator;
    @FXML private Button btnAdaugaEchipa;
    @FXML private Button btnGenereazaMeciuri;
    @FXML private Button btnClasament;
    @FXML private Button btnIesire;

    @FXML
    public void initialize() {
        imgBackground.setImage(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setEffect(new GaussianBlur(9));
        btnIesire.setOnAction(e -> Platform.exit());
    }
}

