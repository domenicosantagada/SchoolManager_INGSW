package application.observer;


// Interfaccia Subject: Definisce i metodi per gestire gli observer.
public interface Subject {

    // metodo per registrare un observer
    void attach(Observer observer);

    // metodo per de-registrare un observer
    void detach(Observer observer);

    // metodo per notificare tutti gli observer di un cambiamento
    void notifyObservers(Object event);
}