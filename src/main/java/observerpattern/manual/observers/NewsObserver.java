package observerpattern.manual.observers;

import observerpattern.manual.PrintInfo;
import observerpattern.manual.subject.Subject;

public class NewsObserver implements Observer, PrintInfo {

    private String name;
    private Subject subject;
    private String news;

    public NewsObserver(String name) {
        this.name = name;
    }

    // Subscribers know about the Subject
    @Override
    public void subscribeSource(Subject subject) {
        this.subject = subject;
        subject.subscribe(this);
    }

    @Override
    public void unSubscribeSource() {
        if(this.subject != null){
            subject.unsubscribe(this);
            news = "N/A";
        }
    }

    // Observables call this method to PUSH updates to Subscribers
    @Override
    public void update(String news) {
        this.news = news;
    }

    @Override
    public void display() {
        System.out.println(this.name + " news: " + this.news);
    }
}
