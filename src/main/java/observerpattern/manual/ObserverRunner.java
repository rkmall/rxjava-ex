package observerpattern.manual;

import observerpattern.manual.observers.NewsObserver;
import observerpattern.manual.subject.NewsChannel;

public class ObserverRunner {

    public static void main(String[] args) {

        // Observables
        NewsChannel newsChannel = new NewsChannel();

        // Observers
        NewsObserver observer1 = new NewsObserver("observer1");
        NewsObserver observer2 = new NewsObserver("observer2");
        NewsObserver observer3 = new NewsObserver("observer3");

        newsChannel.subscribeObserver(observer1);
        newsChannel.subscribeObserver(observer2);
        newsChannel.subscribeObserver(observer3);
        newsChannel.observers.forEach(System.out::println);     // testing, display observers


        // Channel sets the news and notifies observers
        newsChannel.newNews("New headlines, EPL kicks off today");


        // Observers display updated news
        observer1.display();
        observer2.display();
        observer3.display();

        newsChannel.removeObserver(observer3);
        newsChannel.observers.forEach(System.out::println);     // testing, display observers


        // Channel sets the news and notifies observers
        newsChannel.newNews("Brentford FC in the EPL");

        // Observers display updated news
        observer1.display();
        observer2.display();
        observer3.display();
    }
}
