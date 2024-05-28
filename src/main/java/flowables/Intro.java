package flowables;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Intro {

    public static void main(String[] args) {

        //executeWithoutFlowablesButSynchronous();
        //executeWithoutFlowablesButAsynchronous();
        //executeWithFlowables();

        //flowableIntervalCanThrowMissingBackPressureException();

        //flowableUsesSubscriptionInsteadOfObserver();
        flowableUsesSubscriptionInsteadOfObserver1();
    }


    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Single thread processing the Items
    // The processing is sequential since single thread is doing all the work
    // No need for Flowables
    public static void executeWithoutFlowablesButSynchronous() {
        Observable.range(1, 999_999_999)
                .map(Item::new)
                .subscribe(item -> {
                    sleep(50);
                    System.out.println("Received item: " + item.id
                            + " on " + Thread.currentThread().getName());
                } );
    }


    // The processing is asynchronous using observeOn()
    // Emissions are pushed mush faster than the Observer can process them
    public static void executeWithoutFlowablesButAsynchronous() {
        Observable.range(1, 999_999_999)
                .map(Item::new)
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    sleep(10);
                    System.out.println("Received item: " + item.id
                            + " on " + Thread.currentThread().getName());
                });

        sleep(Long.MAX_VALUE);
    }

    public static void executeWithFlowables() {
        Flowable.range(1, 999_999_999)
                .map(Item::new)
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    sleep(10);
                    System.out.println("Received item: " + item.id
                            + " on " + Thread.currentThread().getName());
                });

        sleep(Long.MAX_VALUE);
    }


    // Flowable.interval can throw MissingBackPressureException
    public static void flowableIntervalCanThrowMissingBackPressureException() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(i -> intenseCalculation(i))
                .subscribe(res -> System.out.println(res + " received on "
                        + Thread.currentThread().getName()));

        sleep(Long.MAX_VALUE);
    }

    // Creating Flowables ---------------------------------------------------------------------------------------
    public static void flowableUsesSubscriptionInsteadOfObserver() {
        Flowable.range(1, 100)
                .observeOn(Schedulers.io())
                .map(i -> intenseCalculation(i))
                // The default implementation of Subscriber will request unbounded emission
                // But the operator preceding it will still automatically handle backpressure
                .subscribe(
                        res -> System.out.println("Subscriber received: " + res),
                        throwable -> System.out.println(throwable),
                        () -> System.out.println("Done")
                );

        sleep(10000);
    }

    public static void flowableUsesSubscriptionInsteadOfObserver1() {
        Flowable.range(1, 4)
                .observeOn(Schedulers.io())
                .map(i -> intenseCalculation(i))
                // We can also provide custom implementation of Subscriber
                // But we need to manually call request() on Subscription to request for emission
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);  // must request for emission from upstream
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Subscriber received: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {}

                    @Override
                    public void onComplete() {}
                });

        sleep(10000);
    }

}

class Item {
    int id;
    public Item(int id) {
        this.id = id;
        System.out.println("Constructing item: " + id);
    }
}
