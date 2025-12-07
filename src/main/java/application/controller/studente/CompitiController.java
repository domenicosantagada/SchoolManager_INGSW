package application.controller.studente;

import application.Database;
import application.SceneHandler;
import application.model.CompitoAssegnato;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CompitiController {

    private String studente;
    private String classe;
    private List<CompitoAssegnato> compiti = null;

    @FXML
    private VBox votiContainer;
    @FXML
    private Label classeLabel;

    // Metodo per tornare alla home dello studente
    @FXML
    public void backButtonClicked() throws IOException {
        SceneHandler.getInstance().setStudentHomePage(SceneHandler.getInstance().getUsername());
    }

    @FXML
    public void initialize() {
        studente = SceneHandler.getInstance().getUsername();
        classe = Database.getInstance().getClasseUser(studente);
        compiti = Database.getInstance().getCompitiClasse(classe);
        classeLabel.setText(classe.toUpperCase());

        visualizzaCompiti();
    }

    // Metodo per visualizzare i compiti assegnati
    private void visualizzaCompiti() {
        // Controllo se ci sono compiti assegnati
        if (!compiti.isEmpty() && compiti != null) {

            // Pulisco il container dei compiti
            votiContainer.getChildren().clear();

            // Genero le etichette per ogni compito
            for (CompitoAssegnato comp : compiti)
                generaLabel(comp);
        }
    }

    private void generaLabel(CompitoAssegnato comp) {
        BorderPane newBorderPane = new BorderPane();
        Label materia = new Label();
        materia.setText(comp.materia().toUpperCase());
        Label message = new Label();
        message.setText(comp.descrizione());
        Label date = new Label();
        date.setText(comp.data());

        newBorderPane.setTop(materia);
        newBorderPane.setCenter(message);
        newBorderPane.setBottom(date);
        newBorderPane.setAlignment(newBorderPane.getTop(), Pos.CENTER);
        newBorderPane.setAlignment(newBorderPane.getBottom(), Pos.CENTER);
        newBorderPane.setAlignment(newBorderPane.getCenter(), Pos.CENTER);

        // Aggiungo stile al BorderPane
        newBorderPane.getStyleClass().add("compitiPane");
        // Aggiungo stile al label nel top
        materia.getStyleClass().add("materiaLabel");
        // Aggiungo stile al label nel center
        message.getStyleClass().add("messageLabel");
        // Aggiungo stile al label nel bottom
        date.getStyleClass().add("dateLabel");

        // Aggiungo il BorderPane al container dei voti
        votiContainer.getChildren().add(newBorderPane);
    }
}