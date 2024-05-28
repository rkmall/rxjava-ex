package observables;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ColdVsHotEx {

    public static void main(String[] args) throws InterruptedException {

        ColdVsHotEx coldVsHotEx = new ColdVsHotEx();
        coldVsHotEx.coldObservables();
        //coldVsHotEx.hotObservables();
        //coldVsHotEx.hotObservableRefCountTest();
    }

    public void coldObservables() throws InterruptedException {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);
        source.subscribeOn(Schedulers.io())
                .subscribe(item -> System.out.println("Observer 1: " + item));

        Thread.sleep(3000);
        source.subscribeOn(Schedulers.io())
                .subscribe(item -> System.out.println("Observer 2: " + item));
        Thread.sleep(5000);
    }

    // ConnectObservable is a single Observable source for different Observers
    // It is used to convert cold Observable to hot Observable, publish() does that
    // Calling subscribe() will not trigger emission but connect() will
    public void hotObservables() throws InterruptedException {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);
        ConnectableObservable<Long> hotSource = source.publish();
        hotSource.subscribe(item -> System.out.println("Observer 1: " + item));
        hotSource.connect();            // connect begins the emission

        Thread.sleep(3000);
        hotSource.subscribe(item -> System.out.println("Observer 2: " + item));
        hotSource.connect();
        Thread.sleep(5000);        // connect begins the emission
    }


    // ConnectableObserver.publish().reCount() returns that will be connected at subscription of an Observer
    // and will be terminated if there are no more Observers
    public void hotObservableRefCountTest() throws InterruptedException {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> hotSource = source.publish().refCount();

        Disposable subscription1 = hotSource
                .doOnSubscribe(str -> System.out.println("Observer 1 subscribed"))
                .doFinally(() -> System.out.println("Observer 1 unsubscribed"))
                .subscribe(item -> System.out.println("Observer 1: " + item));
        Thread.sleep(3000);

        Disposable subscription2 = hotSource
                .doOnSubscribe(str -> System.out.println("Observer 2 subscribed"))
                .doFinally(() -> System.out.println("Observer 2 unsubscribed"))
                .subscribe(item -> System.out.println("Observer 2: " + item));
        Thread.sleep(3000);

        subscription1.dispose();
        subscription2.dispose();        // at this point there are no Observers
                                        // so Observable will be disposed

        Disposable subscription3 = hotSource    // new Observer, the emission starts fresh
                .doOnSubscribe(str -> System.out.println("Observer 3 subscribed"))
                .doFinally(() -> System.out.println("Observer 3 unsubscribed"))
                .subscribe(item -> System.out.println("Observer 3: " + item));
        Thread.sleep(3000);
    }
}
