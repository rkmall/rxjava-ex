package observerpattern.manual.subject;

import observerpattern.manual.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class NewsChannel implements Subject{

    private String news;
    public List<Observer> observers = new ArrayList<>();     // Subject maintains the list of Observers

    @Override
    public void subscribeObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        observer.update(null);
    }

    // PUSH update to Observers
    @Override
    public void notifyObserver() {
        for(Observer observer : observers){
            observer.update(this.news);     // here we are pushing the new News to the subscribers
        }
    }

    // Every time there is new News, set the new News and notify the subscribers
    public void newNews(String news){
        this.news = news;           // set the new news
        notifyObserver();           // PUSH the new news
    }
}
