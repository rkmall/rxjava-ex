package operators.creation_operators.interval_timer;

import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;


public class IntervalEx {

    public static void main(String[] args) throws InterruptedException {
        IntervalEx intervalEx = new IntervalEx();
        intervalEx.intervalUsingRxInterval();        // interval run on computation scheduler by default
        Thread.sleep(8_000);                   // call sleep to let interval to execute
    }
    
    public void intervalUsingRxInterval() {
        Observable<Long> intervalObservable = Observable
                .interval(2, TimeUnit.SECONDS); // delay 2s, elements will be emitted in 2 seconds
               // .takeWhile(num -> num <= 3)
                 //       .doOnSubscribe(d -> System.out.println("Subscription thread: " + Thread.currentThread().getName()));

        intervalObservable.subscribe(
                aLong ->  System.out.println("Runtime onNext thread: " + Thread.currentThread().getName() + ": " + aLong)
                //System.out::println,
                //() -> System.out.println("Runtime onComplete thread: " + Thread.currentThread().getName())
        );
    }
}
