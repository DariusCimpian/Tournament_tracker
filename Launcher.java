package proiect2poo.tournamenttracker.ui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import proiect2poo.tournamenttracker.data.DataManager;
import proiect2poo.tournamenttracker.model.entitati.Echipa;
import proiect2poo.tournamenttracker.model.entitati.Meci;
import proiect2poo.tournamenttracker.model.entitati.Turneu;
import proiect2poo.tournamenttracker.model.oameni.Jucator;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private final DataManager dataManager = new DataManager();
    private final Turneu turneu = new Turneu("Turneul campionilor");
    public static MediaPlayer mediaPlayer;
    private Image iconSoundOn = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_on.png").toExternalForm());
    private Image iconSoundOff = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_off.png").toExternalForm());

    @Override
    public void start(Stage stage) {
        stage.setWidth(980);
        stage.setHeight(700);
        stage.setMinWidth(980);
        stage.setMinHeight(700);
        iconSoundOn = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_on.png").toExternalForm());
        iconSoundOff = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_off.png").toExternalForm());
        String musicPath = getClass().getResource("/proiect2poo/tournamenttracker/music.mp3").toExternalForm();
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        File fisier = new File("turneu_date.txt");
        dataManager.load(fisier.getAbsolutePath());
        turneu.getMeciuri().clear();
        turneu.getMeciuri().addAll(dataManager.getMeciuri());
        System.out.println("Path absolut: " + fisier.getAbsolutePath());
        stage.getIcons().add(new Image(getClass().getResource("/proiect2poo/tournamenttracker/app_icon.png").toExternalForm()));
        Scene scene = buildMainMenu(stage);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("TOURNAMENT TRACKER");
        stage.setUserData(scene);

        stage.setOnCloseRequest((WindowEvent we) -> {
            we.consume();
            confirmClose(stage);
        });
        stage.show();
    }

    private Scene buildMainMenu(Stage stage) {
        ImageView imgBackground = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setPreserveRatio(false);
        imgBackground.setOpacity(0.24);
        imgBackground.setEffect(new GaussianBlur(12));
        imgBackground.fitWidthProperty().bind(stage.widthProperty());
        imgBackground.fitHeightProperty().bind(stage.heightProperty());

        Rectangle cardBg = new Rectangle();
        cardBg.setArcHeight(40);
        cardBg.setArcWidth(40);
        cardBg.setFill(Color.rgb(255,255,255, 0.13));
        cardBg.widthProperty().bind(stage.widthProperty().divide(2.15));
        cardBg.heightProperty().bind(stage.heightProperty().divide(1.17));
        cardBg.setStroke(Color.rgb(170,190,215, 0.45));
        cardBg.setStrokeWidth(1.2);

        Text titlu = new Text("MENIU");
        titlu.setFont(Font.font("Segoe UI Light", FontWeight.LIGHT, 52));
        titlu.setFill(Color.web("#31435f"));
        titlu.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Segoe UI Light", FontWeight.LIGHT, Math.max(stage.getHeight()/19, 26)),
                stage.heightProperty()
        ));

        String[][] butoane = {
                {"Adaugă Jucător", "icon_user.png"},
                {"Adaugă Echipă", "icon_team.png"},
                {"Vizualizează meciuri", "icon_match.png"},
                {"Clasament", "icon_rank.png"},
                {"Ieșire", "icon_exit.png"}
        };
        Button[] bs = new Button[butoane.length];
        for(int i=0; i<butoane.length; i++) {
            ImageView iv = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/"+butoane[i][1]).toExternalForm()));
            iv.fitWidthProperty().bind(stage.heightProperty().divide(35.0));
            iv.fitHeightProperty().bind(stage.heightProperty().divide(35.0));
            iv.setPreserveRatio(true);

            Button b = new Button(butoane[i][0], iv);
            b.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.7); " +
                            "-fx-background-radius: 18; -fx-border-radius: 20;" +
                            "-fx-font-family: 'Segoe UI Light'; -fx-text-fill: #233; -fx-font-weight: 400;" +
                            "-fx-padding:12 0 12 0; -fx-border-color: #9abaf2; -fx-border-width: 0.7;"
            );
            b.prefWidthProperty().bind(stage.widthProperty().divide(2.35));
            b.fontProperty().bind(Bindings.createObjectBinding(
                    () -> Font.font("Segoe UI Light", Math.max(stage.getHeight()/44, 13)),
                    stage.heightProperty()
            ));

            final String baseStyle = b.getStyle();
            b.setOnMouseEntered(e -> b.setStyle(baseStyle + "; -fx-effect: dropshadow(gaussian,#b0c4de,14,0.23,0,3);"));
            b.setOnMouseExited(e -> b.setStyle(baseStyle));
            bs[i]=b;

        }

        bs[0].setOnAction(e -> showAddPlayer(stage));
        bs[1].setOnAction(e -> showAddTeam(stage));
        bs[2].setOnAction(e -> showViewMatches(stage));
        bs[3].setOnAction(e -> showRanking(stage));
        bs[4].setOnAction(e -> confirmClose(stage));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(28);
        vbox.getChildren().add(titlu);
        vbox.getChildren().addAll(bs);

        StackPane card = new StackPane(cardBg, vbox);
        card.setAlignment(Pos.CENTER);

        // ---- Buton SUNET fix sus-dreapta FĂRĂ să blocheze restul butoanelor ----
        Image iconSoundOn = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_on.png").toExternalForm());
        Image iconSoundOff = new Image(getClass().getResource("/proiect2poo/tournamenttracker/icon_sound_off.png").toExternalForm());
        ImageView ivSound = new ImageView(iconSoundOn);
        ivSound.setFitWidth(32); ivSound.setFitHeight(32);

        Button btnSound = new Button("", ivSound);
        btnSound.setStyle("-fx-background-color: transparent; -fx-padding:6;");
        btnSound.setFocusTraversable(false);
        btnSound.setOnAction(e -> {
            if (mediaPlayer.isMute()) {
                mediaPlayer.setMute(false);
                ivSound.setImage(iconSoundOn);
            } else {
                mediaPlayer.setMute(true);
                ivSound.setImage(iconSoundOff);
            }
        });
// Butonul să nu fie în HBox/lățime mare, ci direct poziționat:
        StackPane.setAlignment(btnSound, Pos.TOP_RIGHT);

        StackPane root = new StackPane(imgBackground, card, btnSound);
        root.setPadding(new Insets(10,14,0,0)); // padding sus/dreapta

        return new Scene(root, 980, 700);
    }


    private void showAddPlayer(Stage stage) {
        TextField tfEchipa = new TextField();
        tfEchipa.setPromptText("Echipa (opțional)");
        ImageView imgBackground = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setPreserveRatio(false);
        imgBackground.setOpacity(0.24);
        imgBackground.setEffect(new GaussianBlur(12));
        imgBackground.fitWidthProperty().bind(stage.widthProperty());
        imgBackground.fitHeightProperty().bind(stage.heightProperty());

        Rectangle cardBg = new Rectangle();
        cardBg.setArcHeight(36);
        cardBg.setArcWidth(36);
        cardBg.setFill(Color.rgb(255,255,255, 0.13));
        cardBg.widthProperty().bind(stage.widthProperty().divide(2.3));
        cardBg.heightProperty().bind(stage.heightProperty().divide(1.2));
        cardBg.setStroke(Color.rgb(170,190,215, 0.38));
        cardBg.setStrokeWidth(1.1);

        Text titlu = new Text("Adaugă Jucător Nou");
        titlu.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 27));
        titlu.setFill(Color.web("#2a3085"));

        TextField tfId = new TextField(); tfId.setPromptText("ID (număr)");
        TextField tfNume = new TextField(); tfNume.setPromptText("Nume");
        TextField tfPrenume = new TextField(); tfPrenume.setPromptText("Prenume");
        TextField tfRating = new TextField(); tfRating.setPromptText("Rating (număr)");

        Button btnSalveaza = new Button("Salvează");
        Button btnInapoi = new Button("Înapoi la meniu");
        btnSalveaza.prefWidthProperty().bind(stage.widthProperty().divide(4));
        btnInapoi.prefWidthProperty().bind(stage.widthProperty().divide(4));
        btnSalveaza.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Segoe UI", FontWeight.BOLD, Math.max(stage.getHeight()/42, 13)),
                stage.heightProperty()
        ));
        btnInapoi.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Segoe UI", FontWeight.BOLD, Math.max(stage.getHeight()/44, 11)),
                stage.heightProperty()
        ));

        VBox listaJucatoriBox = new VBox(4);
        listaJucatoriBox.setStyle("-fx-background-color: rgba(255,255,255,0.67); -fx-border-radius:6; -fx-padding:8;");
        listaJucatoriBox.setPrefWidth(540);  // sau chiar mai mult, 600, dacă ai liste și mai lungi
        listaJucatoriBox.setMaxWidth(Double.MAX_VALUE); // pentru scalare adaptivă
        listaJucatoriBox.prefWidthProperty().bind(stage.widthProperty().divide(1.6));
        Label lblLista = new Label("Jucători existenți:");
        lblLista.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        listaJucatoriBox.getChildren().add(lblLista);

        refreshListaJucatori(listaJucatoriBox);
        Button btnReset = new Button("RESET");
        btnReset.setStyle("-fx-background-color:#e44; -fx-text-fill:white; -fx-font-weight:bold; -fx-font-size:15;");

        btnReset.setOnAction(ev -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Sigur ștergi TOȚI jucătorii și TOATE meciurile?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    dataManager.getJucatori().clear();
                    dataManager.getMeciuri().clear();
                    // Șterge și participanții/meciurile din turneu pentru siguranță:
                    turneu.getParticipanti().clear();
                    turneu.getMeciuri().clear();
                    // Salvează:
                    File fisier = new File("turneu_date.txt");
                    dataManager.save(fisier.getAbsolutePath());
                    refreshListaJucatori(listaJucatoriBox);
                }
            });
        });
        btnSalveaza.setOnAction(ev -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                // ADD duplicate check here!
                for (Jucator jj : dataManager.getJucatori()) {
                    if (jj.getId() == id) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "Există deja un jucător cu acest ID!", ButtonType.OK);
                        alerta.showAndWait();
                        return;
                    }
                }
                String nume = tfNume.getText().trim();
                String prenume = tfPrenume.getText().trim();
                int rating = Integer.parseInt(tfRating.getText().trim());
                String numeEchipa = tfEchipa.getText().trim();

                Jucator j = new Jucator(id, nume, prenume, rating);
                if (!numeEchipa.isEmpty()) {
                    Echipa echipa = null;
                    for (Echipa e : dataManager.getEchipe()) {
                        if (e.getNume().equalsIgnoreCase(numeEchipa)) {
                            echipa = e;
                            break;
                        }
                    }
                    if (echipa == null) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "Echipa \"" + numeEchipa + "\" nu există! Creează întâi echipa la Adaugă Echipă.", ButtonType.OK);
                        alerta.showAndWait();
                        return; // Nu adaugă jucătorul!
                    }
                    echipa.addJucator(j); // Dacă există, îl adaugă normal
                }

                dataManager.adaugaJucator(j);
                turneu.adaugaParticipant(j);

                refreshListaJucatori(listaJucatoriBox);
                tfId.clear(); tfNume.clear(); tfPrenume.clear(); tfRating.clear(); tfEchipa.clear();
            } catch(Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Completează corect toate câmpurile!", ButtonType.OK);
                alert.showAndWait();
            }
        });


        btnInapoi.setOnAction(e -> stage.setScene((Scene) stage.getUserData()));

        VBox formBox = new VBox(20, titlu, tfId, tfNume, tfPrenume, tfRating, tfEchipa, btnSalveaza, btnInapoi, btnReset);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setStyle("-fx-padding:30;");

        StackPane card = new StackPane(cardBg, formBox);
        card.setAlignment(Pos.CENTER_LEFT);

        HBox page = new HBox(20, card, listaJucatoriBox);
        page.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(imgBackground, page);
        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshListaJucatori(VBox listaJucatoriBox) {
        listaJucatoriBox.getChildren().remove(1, listaJucatoriBox.getChildren().size());
        for (Jucator j : dataManager.getJucatori()) {
            String echipaAfisare = "(Individual)";
            for (Echipa e : dataManager.getEchipe()) {
                if (e.getMembri().contains(j)) {
                    echipaAfisare = e.getNume();
                    break;
                }
            }
            Label l = new Label("ID: " + j.getId() +
                    " | " + j.getNume() + " " + j.getPrenume() +
                    " | Rating: " + j.getRating() +
                    " | Echipa: " + echipaAfisare +
                    " | Victorii: " + j.getVictorii());
            l.setStyle("-fx-font-size:14; -fx-padding:3; -fx-text-fill:#197; -fx-cursor: hand;");
            l.setOnMouseClicked(ev -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Ștergi jucătorul " + j.getNume() + " " + j.getPrenume() + "?", ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(type -> {
                    if (type == ButtonType.YES) {
                        dataManager.stergeJucator(j);
                        refreshListaJucatori(listaJucatoriBox);
                    }
                });
            });
            listaJucatoriBox.getChildren().add(l);
        }
    }
    private void showAddTeam(Stage stage) {
        ImageView imgBackground = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setPreserveRatio(false);
        imgBackground.setOpacity(0.24);
        imgBackground.setEffect(new GaussianBlur(12));
        imgBackground.fitWidthProperty().bind(stage.widthProperty());
        imgBackground.fitHeightProperty().bind(stage.heightProperty());

        Rectangle cardBg = new Rectangle();
        cardBg.setArcHeight(36);
        cardBg.setArcWidth(36);
        cardBg.setFill(Color.rgb(255,255,255, 0.13));
        cardBg.widthProperty().bind(stage.widthProperty().divide(2.3));
        cardBg.heightProperty().bind(stage.heightProperty().divide(1.2));
        cardBg.setStroke(Color.rgb(170,190,215, 0.38));
        cardBg.setStrokeWidth(1.1);

        Text titlu = new Text("Adaugă echipă nouă");
        titlu.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 27));
        titlu.setFill(Color.web("#286c10"));

        TextField tfNumeEchipa = new TextField();
        tfNumeEchipa.setPromptText("Nume echipă");

        Button btnSalveaza = new Button("Salvează");
        Button btnInapoi = new Button("Înapoi la meniu");
        btnSalveaza.prefWidthProperty().bind(stage.widthProperty().divide(4));
        btnInapoi.prefWidthProperty().bind(stage.widthProperty().divide(4));

        VBox listaEchipeBox = new VBox(10);
        listaEchipeBox.setStyle("-fx-background-color: rgba(255,255,255,0.67); -fx-border-radius:6; -fx-padding:12;");
        listaEchipeBox.setPrefWidth(540);
        listaEchipeBox.setMaxWidth(Double.MAX_VALUE);
        listaEchipeBox.prefWidthProperty().bind(stage.widthProperty().divide(1.6));

        Label lblLista = new Label("Echipe existente:");
        lblLista.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        listaEchipeBox.getChildren().add(lblLista);

        refreshListaEchipe(listaEchipeBox);

        btnSalveaza.setOnAction(ev -> {
            String nume = tfNumeEchipa.getText().trim();
            if(nume.isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Completează numele echipei!", ButtonType.OK);
                alerta.showAndWait();
            } else if(dataManager.getEchipe().stream().anyMatch(e -> e.getNume().equalsIgnoreCase(nume))) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Există deja o echipă cu acest nume!", ButtonType.OK);
                alerta.showAndWait();
            } else {
                Echipa e = new Echipa(nume);
                dataManager.adaugaEchipa(e);
                refreshListaEchipe(listaEchipeBox);
                tfNumeEchipa.clear();
            }
        });

        btnInapoi.setOnAction(e -> stage.setScene((Scene) stage.getUserData()));

        VBox formBox = new VBox(20, titlu, tfNumeEchipa, btnSalveaza, btnInapoi);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setStyle("-fx-padding:30;");

        StackPane card = new StackPane(cardBg, formBox);
        card.setAlignment(Pos.CENTER_LEFT);

        HBox page = new HBox(20, card, listaEchipeBox);
        page.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(imgBackground, page);
        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.show();
    }
    private void refreshListaEchipe(VBox listaEchipeBox) {
        listaEchipeBox.getChildren().remove(1, listaEchipeBox.getChildren().size());
        for (Echipa e : dataManager.getEchipe()) {
            Label echipaLabel = new Label("🏓 " + e.getNume());
            echipaLabel.setStyle("-fx-font-weight:bold; -fx-font-size:17; -fx-text-fill:#246;");
            VBox membriBox = new VBox(3);
            membriBox.setStyle("-fx-padding: 0 0 0 26;"); // indent
            if (e.getMembri().isEmpty()) {
                membriBox.getChildren().add(new Label("— niciun membru"));
            } else {
                for (Jucator j : e.getMembri()) {
                    membriBox.getChildren().add(new Label(j.getNume() + " " + j.getPrenume() + " (ID: " + j.getId() + ")"));
                }
            }
            listaEchipeBox.getChildren().addAll(echipaLabel, membriBox);
        }
    }
    private void showViewMatches(Stage stage) {
        ImageView imgBackground = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setPreserveRatio(false);
        imgBackground.setOpacity(0.24);
        imgBackground.setEffect(new GaussianBlur(12));
        imgBackground.fitWidthProperty().bind(stage.widthProperty());
        imgBackground.fitHeightProperty().bind(stage.heightProperty());

        Rectangle cardBg = new Rectangle();
        cardBg.setArcHeight(36);
        cardBg.setArcWidth(36);
        cardBg.setFill(Color.rgb(255,255,255, 0.13));
        cardBg.widthProperty().bind(stage.widthProperty().divide(2.3));
        cardBg.heightProperty().bind(stage.heightProperty().divide(1.2));
        cardBg.setStroke(Color.rgb(170,190,215, 0.38));
        cardBg.setStrokeWidth(1.1);

        Text titlu = new Text("Vizualizează Meciuri");
        titlu.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 27));
        titlu.setFill(Color.web("#36797b"));

        Button btnGenereaza = new Button("Generează Meciuri");
        btnGenereaza.prefWidthProperty().bind(stage.widthProperty().divide(3));

        Button btnInapoi = new Button("Înapoi la meniu");
        btnInapoi.prefWidthProperty().bind(stage.widthProperty().divide(3));

        VBox leftBox = new VBox(35, titlu, btnGenereaza, btnInapoi);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setStyle("-fx-padding: 40;");

        VBox meciuriListBox = new VBox(14);
        meciuriListBox.setStyle("-fx-background-color: rgba(255,255,255,0.78); -fx-border-radius:8; -fx-padding:18; -fx-background-radius:8;");
        meciuriListBox.setPrefWidth(650);
        meciuriListBox.setMaxWidth(Double.MAX_VALUE);

        refreshListaMeciuri(meciuriListBox);

        btnGenereaza.setOnAction(ev -> {
            // Golește lista veche și apelează generarea și refresh
            turneu.sincronizeazaParticipantiCuJucatori(dataManager.getJucatori());
            turneu.genereazaMeciuriJucatorVsJucator();
            refreshListaMeciuri(meciuriListBox);
        });
        btnInapoi.setOnAction(e -> stage.setScene((Scene) stage.getUserData()));

        HBox page = new HBox(24, leftBox, meciuriListBox);
        page.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(imgBackground, page);
        Scene scene = new Scene(root, 1240, 800);
        stage.setScene(scene);
        stage.show();
        dataManager.getMeciuri().clear();
        dataManager.getMeciuri().addAll(turneu.getMeciuri());
        File fisier = new File("turneu_date.txt");
        dataManager.save(fisier.getAbsolutePath());

    }
    private void refreshListaMeciuri(VBox meciuriListBox) {
        meciuriListBox.getChildren().clear();
        for (Meci m : turneu.getMeciuri()) {
            HBox linie = new HBox(12);
            linie.setAlignment(Pos.CENTER_LEFT);

            // Participant A
            String numeA = m.getParticipantA().getNume();
            if (m.getParticipantA() instanceof Jucator)
                numeA += " " + ((Jucator) m.getParticipantA()).getPrenume();
            Label lJ1 = new Label(numeA);

            // Citește scorul deja salvat (dacă există)
            String scorA = "";
            String scorB = "";
            if (!m.getSeturiA().isEmpty() && !m.getSeturiB().isEmpty()) {
                scorA = String.valueOf(m.getSeturiA().get(m.getSeturiA().size()-1));
                scorB = String.valueOf(m.getSeturiB().get(m.getSeturiB().size()-1));
            }

            TextField tfScor1 = new TextField(scorA);
            tfScor1.setPromptText("Scor 1");
            tfScor1.setPrefWidth(54);

            Label vs = new Label("vs");

            String numeB = m.getParticipantB().getNume();
            if (m.getParticipantB() instanceof Jucator)
                numeB += " " + ((Jucator) m.getParticipantB()).getPrenume();
            Label lJ2 = new Label(numeB);

            TextField tfScor2 = new TextField(scorB);
            tfScor2.setPromptText("Scor 2");
            tfScor2.setPrefWidth(54);

            Button btnSalveaza = new Button("Salvează scor");
            btnSalveaza.setOnAction(ev -> {
                try {
                    int sc1 = Integer.parseInt(tfScor1.getText().trim());
                    int sc2 = Integer.parseInt(tfScor2.getText().trim());
                    m.getSeturiA().clear();
                    m.getSeturiB().clear();
                    m.adaugaSet(sc1, sc2);
                    dataManager.getMeciuri().clear();
                    dataManager.getMeciuri().addAll(turneu.getMeciuri());
                    File fisier = new File("turneu_date.txt");
                    dataManager.save(fisier.getAbsolutePath());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Scor salvat!", ButtonType.OK);
                    alert.showAndWait();
                    refreshListaMeciuri(meciuriListBox);
                } catch(Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Introduceți scor valid!", ButtonType.OK);
                    alert.showAndWait();
                }
            });

            linie.getChildren().addAll(lJ1, tfScor1, vs, lJ2, tfScor2, btnSalveaza);
            meciuriListBox.getChildren().add(linie);
        }
    }

    private void showRanking(Stage stage) {
        ImageView imgBackground = new ImageView(new Image(getClass().getResource("/proiect2poo/tournamenttracker/background_pingpong.jpg").toExternalForm()));
        imgBackground.setPreserveRatio(false);
        imgBackground.setOpacity(0.24);
        imgBackground.setEffect(new GaussianBlur(14));
        imgBackground.fitWidthProperty().bind(stage.widthProperty());
        imgBackground.fitHeightProperty().bind(stage.heightProperty());

        Text titlu = new Text("Clasament Jucători");
        titlu.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 38));
        titlu.setFill(Color.web("#307cc1"));

        VBox listaClasament = new VBox(10);
        listaClasament.setStyle("-fx-background-color: rgba(255,255,255,0.90); -fx-border-radius:17; -fx-padding:32 44 25 44; -fx-background-radius:17; -fx-effect: dropshadow(gaussian,#adc8fa,34,0.38,0,9);");
        listaClasament.setPrefWidth(600);

        refreshClasament(listaClasament);

        Button btnInapoi = new Button("Înapoi la meniu");
        btnInapoi.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        btnInapoi.setPrefWidth(260);
        btnInapoi.setOnAction(e -> stage.setScene((Scene) stage.getUserData()));

        VBox mainBox = new VBox(38, titlu, listaClasament, btnInapoi);
        mainBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(imgBackground, mainBox);
        Scene scene = new Scene(root, 1100, 730);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshClasament(VBox listaClasament) {
        listaClasament.getChildren().clear();

        // Culege toți jucătorii
        List<Jucator> jucatori = new ArrayList<>(dataManager.getJucatori());

        // Reset scoruri (dacă nu ai deja un câmp, folosește un HashMap ID->Scor temporar)
        Map<Jucator, Integer> scoruri = new HashMap<>();
        for (Jucator j : jucatori) scoruri.put(j, 0);

        // Parcurge fiecare meci finalizat
        for (Meci m : turneu.getMeciuri()) {
            if (!m.getSeturiA().isEmpty() && !m.getSeturiB().isEmpty()) {
                int scA = m.getSeturiA().get(m.getSeturiA().size()-1);
                int scB = m.getSeturiB().get(m.getSeturiB().size()-1);

                Jucator jA = m.getParticipantA() instanceof Jucator ? (Jucator) m.getParticipantA() : null;
                Jucator jB = m.getParticipantB() instanceof Jucator ? (Jucator) m.getParticipantB() : null;
                if (jA != null && jB != null) {
                    if (scA > scB) scoruri.put(jA, scoruri.get(jA)+2);
                    if (scB > scA) scoruri.put(jB, scoruri.get(jB)+2);
                }
            }
        }

        // Sortează descrescător după scor
        List<Map.Entry<Jucator, Integer>> byScore = new ArrayList<>(scoruri.entrySet());
        byScore.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        int poz = 1;
        for (Map.Entry<Jucator, Integer> entry : byScore) {
            Jucator j = entry.getKey();
            int sc = entry.getValue();
            Label l = new Label(poz++ + ". " + j.getNume() + " " + j.getPrenume() + "   -   " + sc + " puncte");
            l.setStyle("-fx-font-size:22; -fx-padding:5 17 5 8; -fx-font-weight: bold; -fx-text-fill:#3366bb;");
            listaClasament.getChildren().add(l);
        }

        if(byScore.isEmpty()){
            Label l = new Label("Nu există încă jucători sau meciuri cu scor!");
            l.setStyle("-fx-font-size:18; -fx-text-fill:#b44; -fx-padding:12;");
            listaClasament.getChildren().add(l);
        }
    }

    private void confirmClose(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
            File fisier = new File("turneu_date.txt");
            dataManager.getMeciuri().clear();
            dataManager.getMeciuri().addAll(turneu.getMeciuri());
            dataManager.save(fisier.getAbsolutePath());
            we.consume();
            confirmClose(stage);
        });

        Alert alerta = new Alert(Alert.AlertType.WARNING, "Sigur dorești să părăsești aplicația?", ButtonType.YES,ButtonType.NO);
        alerta.setTitle("Exit alert");
        alerta.setHeaderText("Confirmare");
        alerta.initOwner(stage);
        alerta.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                stage.close();
            }
        });
    }
}
