package observerpattern.manual.subject;

import observerpattern.manual.observers.Observer;
import observerpattern.manual.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class NewsChannel implements Subject{

    private String news;
    List<Observer> observers = new ArrayList<>();     // Subject maintains the list of Observers

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // PUSH update to Observers
    @Override
    public void notifySubscribers() {
        for(Observer observer : observers){
            observer.update(this.news);     // here we are pushing the new News to the subscribers
        }
    }

    // Every time there is new News, set the new News and notify the subscribers
    public void newNews(String news){
        this.news = news;           // set the new news
        notifySubscribers();        // PUSH the new news
    }
}
