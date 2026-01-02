package application;

import application.view.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;


public class SchoolManagerApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneHandler.getInstance().init(stage);
    }
}