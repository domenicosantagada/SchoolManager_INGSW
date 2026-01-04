package application.observer;


import application.persistence.DatabaseEvent;

// Interfaccia DatabaseSubject: Definisce i metodi per gestire gli observer.
public interface DatabaseSubject {

    // metodo per registrare un observer
    void attach(DatabaseObserver observer);

    // metodo per de-registrare un observer
    void detach(DatabaseObserver observer);

    // metodo per notificare tutti gli observer di un cambiamento
    void notifyObservers(DatabaseEvent event);
}