module com.example.registroelettronico {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires fontawesomefx;
    requires itextpdf;
    requires jbcrypt;
    requires javafx.graphics;
    requires javafx.base;

    opens application to javafx.fxml;
    exports application;

    exports application.controller;
    opens application.controller to javafx.fxml;
    exports application.controller.prof;
    opens application.controller.prof to javafx.fxml;
    exports application.controller.studente;
    opens application.controller.studente to javafx.fxml;
    exports application.model;
    opens application.model to javafx.fxml;
    exports application.exportStrategy;
    opens application.exportStrategy to javafx.fxml;
    exports application.observer;
    opens application.observer to javafx.fxml;
    exports application.view;
    opens application.view to javafx.fxml;
    exports application.utility;
    opens application.utility to javafx.fxml;
    exports application.persistence;
    opens application.persistence to javafx.fxml;
}