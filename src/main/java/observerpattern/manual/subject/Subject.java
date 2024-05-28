package observerpattern.manual.subject;

import observerpattern.manual.observers.Observer;

public interface Subject {

    void subscribeObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}

