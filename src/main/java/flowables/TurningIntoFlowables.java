package flowables;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TurningIntoFlowables {

    public static void main(String[] args) {

        //turnObservableToFlowables();
        turnFlowableToObservables();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void turnObservableToFlowables() {
        Observable<Integer> source = Observable.range(1, 1000);

        source.toFlowable(BackpressureStrategy.BUFFER)      // turn Observable to Flowable
                .observeOn(Schedulers.io())
                .subscribe(res -> System.out.println(res));

        sleep(8000);
    }

    public static void turnFlowableToObservables() {
        Flowable<Integer> flowable = Flowable.range(1, 1000)
                                        .subscribeOn(Schedulers.computation());

        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                // turn Flowable to Observable
                // mapping 1000 emissions for each string
                .flatMap(str -> flowable.map(i -> i + "-" + str).toObservable())
                .subscribe(res -> System.out.println(res));

        sleep(5000);
    }
}
