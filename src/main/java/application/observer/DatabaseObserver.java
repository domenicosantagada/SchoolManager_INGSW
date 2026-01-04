package application.observer;

import application.persistence.DatabaseEvent;

// Interfaccia Observer: Definisce il metodo per aggiornare gli observer.
public interface DatabaseObserver {

    // metodo per aggiornare l'observer con un evento
    void update(DatabaseEvent event);
}