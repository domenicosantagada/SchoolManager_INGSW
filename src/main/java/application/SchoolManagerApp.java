package application;

import application.view.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;

// Classe principale JavaFX che avvia l'applicazione SchoolManager
public class SchoolManagerApp extends Application {

    public static void main(String[] args) {
        launch(args); // Avvia l'app JavaFX
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneHandler.getInstance().init(stage); // Inizializza lo stage con la login page
    }
}