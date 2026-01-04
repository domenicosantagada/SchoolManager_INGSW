package application.controller;

import application.model.Professore;
import application.model.Studente;
import application.model.TipologiaClasse;
import application.model.User;
import application.persistence.Database;
import application.utility.BCryptService;
import application.utility.MessageDebug;
import application.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RegistrationController {

    public static final String PROFTYPE = "professore";
    public static final String STUDENTTYPE = "studente";

    @FXML
    private ChoiceBox<String> materiaChoiceBox;
    @FXML
    private Button registerButton;
    @FXML
    private Label materiaLabel;
    @FXML
    private TextField codiceIscrizione;
    @FXML
    private TextField surnameField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ImageView logoView;

    // Registra un nuovo utente studente o professore
    @FXML
    public void registerClicked(ActionEvent actionEvent) throws IOException {
        String username = this.usernameField.getText();
        String nome = this.nameField.getText();
        String cognome = this.surnameField.getText();
        String dataNascita = "";

        if (datePicker.getValue() != null) {
            dataNascita = this.datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        String codiceIscrizione = this.codiceIscrizione.getText();
        String materia = this.materiaChoiceBox.getValue() == null ? "" : this.materiaChoiceBox.getValue();

        String password = this.passwordField.getText();
        String hashedPassword = BCryptService.hashPassword(password);

        // Validazioni principali
        if (username.isEmpty() || nome.isEmpty() || cognome.isEmpty() || dataNascita.isEmpty()
                || codiceIscrizione.isEmpty() || password.isEmpty()) {
            SceneHandler.getInstance().showWarning(MessageDebug.CAMPS_NOT_EMPTY);
        } else if (usernameUtilizzato(username)) {
            SceneHandler.getInstance().showWarning(MessageDebug.USERNAME_NOT_VALID);
        } else if (datePicker.getValue().isAfter(LocalDate.now())) {
            SceneHandler.getInstance().showWarning(MessageDebug.DATE_NOT_VALID);
        } else if (!password.equals(repeatPasswordField.getText())) {
            SceneHandler.getInstance().showWarning(MessageDebug.PASSWORD_NOT_MATCH);
        } else if (password.length() < 4) {
            SceneHandler.getInstance().showWarning(MessageDebug.PASSWORD_NOT_VALID);
        } else {
            User user = new User(username, nome.toUpperCase(), cognome.toUpperCase(), hashedPassword, dataNascita);
            TipologiaClasse tipologiaUtente = Database.getInstance().getTipologiaUtente(codiceIscrizione);

            if (tipologiaUtente == null) {
                SceneHandler.getInstance().showWarning(MessageDebug.CODE_ERROR);
            } else if (tipologiaUtente.tipologia().equals(STUDENTTYPE)) {
                Studente studente = new Studente(user, tipologiaUtente.classe());
                if (Database.getInstance().insertStudente(studente)) {
                    SceneHandler.getInstance().showInformation(MessageDebug.REGISTRATION_OK);
                    SceneHandler.getInstance().setLoginPage();
                }
            } else if (tipologiaUtente.tipologia().equals(PROFTYPE)) {
                if (materia.isEmpty()) {
                    SceneHandler.getInstance().showWarning(MessageDebug.CAMPS_NOT_EMPTY);
                } else {
                    Professore professore = new Professore(user, tipologiaUtente.classe(), materia);
                    if (Database.getInstance().insertProfessore(professore)) {
                        SceneHandler.getInstance().showInformation(MessageDebug.REGISTRATION_OK);
                        SceneHandler.getInstance().setLoginPage();
                    }
                }
            }
        }
    }

    // Controlla se uno username è già in uso
    private boolean usernameUtilizzato(String username) {
        return Database.getInstance().usernameUtilizzato(username);
    }

    // Controlla validità del codice di iscrizione
    private boolean codiceIscrizioneValido(String codiceIscrizione) {
        return Database.getInstance().codiceIscrizioneValido(codiceIscrizione);
    }

    // Recupera la tipologia dell'utente dal codice
    private String tipologiaUser(String codiceIscrizione) {
        return Database.getInstance().tipologiaUser(codiceIscrizione);
    }

    // Inizializza la schermata di registrazione
    public void initialize() {
        String imagePath = getClass().getResource("/icon/logo.png").toExternalForm();
        logoView.setImage(new Image(imagePath));

        addListeners();

        // Nascondi campi relativi ai professori
        materiaChoiceBox.setVisible(false);
        materiaLabel.setVisible(false);

        setMaterieChoiceBox();
    }

    // Popola la choiceBox delle materie
    private void setMaterieChoiceBox() {
        List<String> materie = Database.getInstance().getAllMaterieIstituto();
        materiaChoiceBox.getItems().addAll(materie);
    }

    // Listener per validazione in tempo reale dei campi
    private void addListeners() {

        // Validazione live username
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank() || usernameUtilizzato(newValue)) {
                usernameField.styleProperty().set(MessageDebug.CHECK_NOT_OK_COLOR);
            } else {
                usernameField.styleProperty().set(MessageDebug.CHECK_OK_COLOR);
            }
        });

        // Validazione live codice iscrizione e visibilità campo materia
        codiceIscrizione.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank() || !codiceIscrizioneValido(newValue)) {
                codiceIscrizione.styleProperty().set(MessageDebug.CHECK_NOT_OK_COLOR);
                materiaChoiceBox.setVisible(false);
                materiaLabel.setVisible(false);
            } else {
                codiceIscrizione.styleProperty().set(MessageDebug.CHECK_OK_COLOR);
                if (tipologiaUser(newValue).equals(MessageDebug.PROF_TYPE)) {
                    materiaChoiceBox.setVisible(true);
                    materiaLabel.setVisible(true);
                } else {
                    materiaChoiceBox.setVisible(false);
                    materiaLabel.setVisible(false);
                }
            }
        });

        // Validazione password
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank() || newValue.length() < 4) {
                passwordField.styleProperty().set(MessageDebug.CHECK_NOT_OK_COLOR);
            } else {
                passwordField.styleProperty().set(MessageDebug.CHECK_OK_COLOR);
            }
        });

        // Validazione conferma password
        repeatPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank() || !newValue.equals(passwordField.getText())) {
                repeatPasswordField.styleProperty().set(MessageDebug.CHECK_NOT_OK_COLOR);
            } else {
                repeatPasswordField.styleProperty().set(MessageDebug.CHECK_OK_COLOR);
            }
        });
    }

    // Torna alla pagina di login
    @FXML
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        SceneHandler.getInstance().setLoginPage();
    }
}