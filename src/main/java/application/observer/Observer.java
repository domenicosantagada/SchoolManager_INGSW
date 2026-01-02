package application.observer;

// Interfaccia Observer: Definisce il metodo per aggiornare gli observer.
public interface Observer {

    // metodo per aggiornare l'observer con un evento
    void update(Object event);
}