package application.exportStrategy;

import application.model.StudenteTable;
import application.model.ValutazioneStudente;
import application.persistence.Database;
import application.view.SceneHandler;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

// Classe Context per gestire l'esportazione utilizzando diverse strategie di esportazione (PDF, CSV, ecc.)
// E' implementata come Singleton per garantire un'unica istanza in tutta l'applicazione.
public class ExportContext {

    private static ExportContext instance;
    private final Database database = Database.getInstance();
    private final SceneHandler sceneHandler = SceneHandler.getInstance();

    private ExportStrategy<?> strategy;

    private ExportContext() {
    }

    // Restituisce l'istanza singleton di ExportContext
    public static ExportContext getInstance() {
        if (instance == null) {
            instance = new ExportContext();
        }
        return instance;
    }

    // Imposta la strategia di esportazione
    public void setStrategy(ExportStrategy<?> strategy) {
        this.strategy = strategy;
    }

    // Mostra la finestra di dialogo per scegliere il file di esportazione
    private File getExportFile(String defaultFileName, String extensionDescription, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva File di Esportazione");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionDescription, extension));
        fileChooser.setInitialFileName(defaultFileName);
        return fileChooser.showSaveDialog(null);
    }

    // Esegue l'esportazione dei voti di uno studente
    @SuppressWarnings("unchecked")
    public void exportValutazione(List<ValutazioneStudente> voti) {
        if (strategy instanceof ExportVotiStudente studentStrategy) {
            try {
                String username = sceneHandler.getUsername();
                String nominativo = database.getFullName(username);
                String classe = database.getClasseUser(username);

                boolean isCSV = strategy.getClass().getSimpleName().contains("CSV");
                String fileExtension = isCSV ? "*.csv" : "*.pdf";
                String extensionDescription = isCSV ? "CSV (Comma Separated Values)" : "PDF Document";
                String defaultFileName = nominativo + " - " + classe + "." + (isCSV ? "csv" : "pdf");

                File file = getExportFile(defaultFileName, extensionDescription, fileExtension);
                studentStrategy.export(voti, file);

            } catch (Exception e) {
                System.out.println("Errore nel salvataggio del file: " + e.getMessage());
            }
        } else {
            System.out.println("Strategy non impostata o non corretta per la valutazione studente.");
        }
    }

    // Esegue l'esportazione dell'andamento della classe
    @SuppressWarnings("unchecked")
    public void exportAndamentoClasse(List<StudenteTable> studentiList) {
        if (strategy instanceof ExportVotiClasse classStrategy) {
            try {
                String username = sceneHandler.getUsername();
                String nominativo = database.getFullName(username);
                String classe = database.getClasseUser(username);

                boolean isCSV = strategy.getClass().getSimpleName().contains("CSV");
                String fileExtension = isCSV ? "*.csv" : "*.pdf";
                String extensionDescription = isCSV ? "CSV (Comma Separated Values)" : "PDF Document";
                String defaultFileName = nominativo + " - Andamento classe " + classe + "." + (isCSV ? "csv" : "pdf");

                File file = getExportFile(defaultFileName, extensionDescription, fileExtension);
                classStrategy.export(studentiList, file);

            } catch (Exception e) {
                System.out.println("Errore nel salvataggio del file: " + e.getMessage());
            }
        } else {
            System.out.println("Strategy non impostata o non corretta per l'andamento classe.");
        }
    }
}