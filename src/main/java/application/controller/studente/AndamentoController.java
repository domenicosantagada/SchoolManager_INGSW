package application.controller.studente;

import application.exportStrategy.CSVStudenteStrategy;
import application.exportStrategy.ExportContext;
import application.exportStrategy.PDFStudenteStrategy;
import application.model.ValutazioneStudente;
import application.observer.DatabaseObserver;
import application.persistence.Database;
import application.persistence.DatabaseEvent;
import application.persistence.DatabaseEventType;
import application.view.SceneHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AndamentoController implements DatabaseObserver {

    private List<String> materie = new ArrayList<>();   // Materie dell'istituto
    private String studente;                            // Username dello studente loggato
    private List<ValutazioneStudente> voti = new ArrayList<>(); // Voti dello studente
    private final ExportContext exportContext = ExportContext.getInstance(); // Contesto strategia esportazione

    @FXML
    private VBox listaVotiVBox;
    @FXML
    private BarChart<String, Number> andamentoChart;
    @FXML
    private CategoryAxis materieX;
    @FXML
    private Label votiInAttesaLabel, insufficienzeLabel, sufficienzeLabel, mediaVoti, nominativoStudente, classeStudente;

    // Torna alla home dello studente e rimuove l'observer
    @FXML
    private void backButtonClicked() throws IOException {
        Database.getInstance().detach(this);
        SceneHandler.getInstance().setStudentHomePage(studente);
    }

    // Inizializza il controller e la UI
    @FXML
    public void initialize() {
        studente = SceneHandler.getInstance().getUsername();
        Database.getInstance().attach(this);

        materie = Database.getInstance().getAllMaterieIstituto();
        materieX.setCategories(FXCollections.observableArrayList(materie));

        voti = Database.getInstance().getVotiStudente(studente);

        nominativoStudente.setText(Database.getInstance().getFullName(studente).toUpperCase());
        classeStudente.setText(Database.getInstance().getClasseUser(studente).toUpperCase());

        updateChart(voti);
        updateListVoti(voti);
        updateRiepilogo(voti);
    }

    // Aggiorna riepilogo voti: insufficienze, sufficienze, voti in attesa e media
    private void updateRiepilogo(List<ValutazioneStudente> voti) {
        int insufficienze = 0, sufficienze = 0, votiInAttesa = 0, sommaVoti = 0, votiTotali = 0;

        for (ValutazioneStudente voto : voti) {
            if (voto.voto() == 0) votiInAttesa++;
            else {
                votiTotali++;
                sommaVoti += voto.voto();
                if (voto.voto() < 6) insufficienze++;
                else sufficienze++;
            }
        }

        votiInAttesaLabel.setText(String.valueOf(votiInAttesa));
        insufficienzeLabel.setText(String.valueOf(insufficienze));
        sufficienzeLabel.setText(String.valueOf(sufficienze));

        double media = (votiTotali > 0) ? (double) sommaVoti / votiTotali : 0.0;
        mediaVoti.setText(String.format("%.2f", media));
    }

    // Aggiorna la lista dei voti visualizzati come card
    private void updateListVoti(List<ValutazioneStudente> voti) {
        listaVotiVBox.getChildren().clear();
        for (ValutazioneStudente voto : voti) generaLabel(voto);
    }

    // Genera la card di un singolo voto
    private void generaLabel(ValutazioneStudente voto) {
        BorderPane card = new BorderPane();

        Label materiaVoto = new Label(voto.materia().toUpperCase() + ": " + voto.voto());
        materiaVoto.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 15));

        Label date = new Label(voto.data());
        date.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        card.setStyle("-fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:10px;");

        if (voto.voto() > 1 && voto.voto() < 6) card.setStyle(card.getStyle() + "-fx-background-color: #f55c47;");
        else if (voto.voto() >= 6) card.setStyle(card.getStyle() + "-fx-background-color: #9fe6a0;");
        else card.setStyle(card.getStyle() + "-fx-background-color: #e5e4e2;");

        card.setTop(materiaVoto);
        card.setCenter(date);
        card.setAlignment(materiaVoto, Pos.CENTER);
        card.setAlignment(date, Pos.CENTER);

        listaVotiVBox.getChildren().add(card);
    }

    // Aggiorna il grafico dell'andamento dei voti
    private void updateChart(List<ValutazioneStudente> voti) {
        andamentoChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (ValutazioneStudente voto : voti) {
            if (voto.voto() != 0) {
                XYChart.Data<String, Number> data = new XYChart.Data<>(voto.materia(), voto.voto());
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle(voto.voto() >= 6 ? "-fx-background-color: #9fe6a0" : "-fx-background-color: #f55c47;");
                    }
                });
                series.getData().add(data);
            }
        }
        andamentoChart.getData().add(series);
        andamentoChart.setLegendVisible(false);
    }

    // Aggiorna UI quando riceve eventi dal database
    @Override
    public void update(DatabaseEvent event) {
        if ((event.type() == DatabaseEventType.VOTO_AGGIORNATO || event.type() == DatabaseEventType.NOTA_INSERITA)
                && event.data() != null && event.data().equals(studente)) {

            javafx.application.Platform.runLater(() -> {
                voti = Database.getInstance().getVotiStudente(studente);
                updateChart(voti);
                updateListVoti(voti);
                updateRiepilogo(voti);
                System.out.println("Andamento ricaricato per lo studente: " + studente);
            });
        }
    }

    // Esporta valutazioni in PDF
    @FXML
    private void exportPDF(MouseEvent event) {
        exportContext.setStrategy(new PDFStudenteStrategy());
        exportContext.exportValutazione(voti);
    }

    // Esporta valutazioni in CSV
    @FXML
    public void exportCSV(MouseEvent mouseEvent) {
        exportContext.setStrategy(new CSVStudenteStrategy());
        exportContext.exportValutazione(voti);
    }
}
