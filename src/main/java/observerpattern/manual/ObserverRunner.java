package observerpattern.manual;

import observerpattern.manual.observers.NewsObserver;
import observerpattern.manual.subject.NewsChannel;
import observerpattern.manual.observers.NewsObserver;
import observerpattern.manual.subject.NewsChannel;

public class ObserverRunner {

    public static void main(String[] args) {

        // Observables
        NewsChannel newsChannel = new NewsChannel();

        // Observers
        NewsObserver subscriber1 = new NewsObserver("Subscriber1");
        NewsObserver subscriber2 = new NewsObserver("Subscriber2");
        NewsObserver subscriber3 = new NewsObserver("Subscriber3");

        // Observers know about which channel they have subscribed to
        subscriber1.subscribeSource(newsChannel);
        subscriber2.subscribeSource(newsChannel);
        subscriber3.subscribeSource(newsChannel);

        // Channel sets the news and notifies observers
        newsChannel.newNews("New headlines, EPL kicks off today");

        // Observers display updated news
        subscriber1.display();
        subscriber2.display();
        subscriber3.display();

        subscriber3.unSubscribeSource();

        // Channel sets the news and notifies observers
        newsChannel.newNews("Brentford FC in the EPL");

        // Observers display updated news
        subscriber1.display();
        subscriber2.display();
        subscriber3.display();
    }
}
