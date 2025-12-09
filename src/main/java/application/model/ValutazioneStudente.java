package application.model;

// Modello per le valutazioni degli studenti

public record ValutazioneStudente(String studente, String prof, String materia, String data, Integer voto) {

    // Metodo per confrontare le valutazioni in base alla materia
    public int compareTo(ValutazioneStudente valutazioneStudente) {
        return materia.compareTo(valutazioneStudente.materia);
    }
}
