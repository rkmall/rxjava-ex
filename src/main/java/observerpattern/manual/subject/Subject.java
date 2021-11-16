package observerpattern.manual.subject;

import observerpattern.manual.observers.Observer;

public interface Subject {

    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers();
}
