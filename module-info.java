module proiect2poo.tournamenttracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens proiect2poo.tournamenttracker to javafx.fxml;
    exports proiect2poo.tournamenttracker;
    exports proiect2poo.tournamenttracker.ui;
    opens proiect2poo.tournamenttracker.ui to javafx.fxml;
}