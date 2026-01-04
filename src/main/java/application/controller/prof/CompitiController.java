package application.controller.prof;

import application.model.CompitoAssegnato;
import application.observer.DatabaseObserver;
import application.persistence.Database;
import application.persistence.DatabaseEvent;
import application.persistence.DatabaseEventType;
import application.utility.MessageDebug;
import application.view.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompitiController implements DatabaseObserver {

    @FXML
    private Label classLabel;

    @FXML
    private TextArea compitiAssegnati;

    private String materia;
    private String classe;

    @FXML
    public void backButtonClicked() throws IOException {
        // Disaccoppiamento dell'observer prima di lasciare la pagina per evitare memory leak
        Database.getInstance().detach(this);
        SceneHandler.getInstance().setProfessorHomePage(SceneHandler.getInstance().getUsername());
    }

    @FXML
    public void inviaCompiti(ActionEvent actionEvent) throws IOException {
        if (compitiAssegnati.getText().trim().isEmpty()) {
            SceneHandler.getInstance().showWarning(MessageDebug.CAMPS_NOT_EMPTY);
            compitiAssegnati.setText("");
        } else {
            CompitoAssegnato compito = new CompitoAssegnato(
                    -1,
                    SceneHandler.getInstance().getUsername(),
                    materia,
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    compitiAssegnati.getText().toUpperCase(),
                    classe);

            if (Database.getInstance().insertCompito(compito)) {
                SceneHandler.getInstance().showInformation(MessageDebug.COMPITO_INSERTED);
                compitiAssegnati.setText("");
                backButtonClicked();
            } else {
                SceneHandler.getInstance().showWarning(MessageDebug.COMPITO_NOT_INSERTED);
            }
        }
    }

    @FXML
    public void initialize() {
        // Registrazione del controller come observer nel Database
        Database.getInstance().attach(this);

        classe = Database.getInstance().getClasseUser(SceneHandler.getInstance().getUsername());
        classLabel.setText(classe);
        materia = Database.getInstance().getMateriaProf(SceneHandler.getInstance().getUsername());
    }

    @Override
    public void update(DatabaseEvent event) {
        // Logica per reagire ai cambiamenti dei dati
        // Anche se questa pagina è di solo inserimento, è buona norma gestire gli eventi correlati
        if (event.type() == DatabaseEventType.NUOVO_COMPITO) {
            Platform.runLater(() -> {
                System.out.println("Notifica: Un nuovo compito è stato inserito per la classe " + event.data());
                // Qui si potrebbe aggiungere logica per aggiornare una eventuale lista di compiti già inviati
            });
        }
    }
}