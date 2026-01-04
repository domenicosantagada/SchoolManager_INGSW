package application.controller.studente;

import application.persistence.Database;
import application.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class HomeStudentController {

    @FXML
    private ImageView logoView;       // Logo dell'istituto
    @FXML
    private Label studentInfo;        // Nome completo dello studente
    @FXML
    private Label classeStudente;     // Classe dello studente

    // Mostra la pagina delle performance dello studente
    @FXML
    public void performanceClicked(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().setAndamentoPage();
    }

    // Mostra la pagina dei compiti assegnati
    @FXML
    public void assignmentClicked(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().setCompitiPage();
    }

    // Mostra la pagina delle note disciplinari
    @FXML
    public void notesButtonClicked(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().setNotePage();
    }

    // Mostra la pagina delle assenze dello studente
    @FXML
    public void assenzeClicked(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().setAssenzeStudentePage();
    }

    // Effettua il logout e ritorna alla pagina di login
    @FXML
    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().setLoginPage();
    }

    // Inizializza la home dello studente: carica logo, nome e classe
    public void initialize() {
        // Carica il logo dell'istituto
        String imagePath = getClass().getResource("/icon/logo1.png").toExternalForm();
        logoView.setImage(new Image(imagePath));
        logoView.setSmooth(true);

        // Imposta il nome completo dello studente
        String studentInfoText = Database.getInstance().getFullName(SceneHandler.getInstance().getUsername());
        studentInfo.setText(studentInfoText.toUpperCase());

        // Imposta la classe dello studente
        String classe = Database.getInstance().getClasseUser(SceneHandler.getInstance().getUsername());
        classeStudente.setText(classe.toUpperCase());
    }
}