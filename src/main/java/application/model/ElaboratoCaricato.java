package application.model;

public record ElaboratoCaricato(CompitoAssegnato compito, String studente, String data, String commento, byte[] file,
                                int id) {
    // Costruttore aggiuntivo per compatibilità con il codice esistente (se usato per l'inserimento)
    // Quando inseriamo un nuovo elaborato, l'ID viene generato dal DB, quindi potremmo passare 0 o -1.
    public ElaboratoCaricato(CompitoAssegnato compito, String studente, String data, String commento, byte[] file) {
        this(compito, studente, data, commento, file, -1);
    }
}
