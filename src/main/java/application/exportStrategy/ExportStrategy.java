package application.exportStrategy;

import java.io.File;

// Interfaccia generica per le strategie di esportazione dei dati
public interface ExportStrategy<T> {
    void export(T data, File outputFile) throws Exception;
}