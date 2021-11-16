package JustFromRangeRepeat;

import com.sun.org.apache.xpath.internal.objects.XNumber;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Enumeration;
import java.util.List;

public class Runner1 {

    public static void main(String[] args) {

        //----------------------just() and from()--------------------------//
        Observable<String> observableJust = getAnimalObservablesJust();
        Observable<String> observableFrom = getAnimalObservablesFrom();
        Observer<String> observer = getAnimalObserver();

        System.out.println("Observable type: " + observableJust.getClass().getSimpleName());

        // Observer subscribing to Observables
        observableJust.subscribe(observer);



        //--------------just() taking single value and emitting single value----------------------//
        /*Integer[] numbers = new Integer[] {1,2,3,4,5};

        Observable<Integer[]> observable = Observable.just(numbers);

        System.out.println(observable.getClass().getSimpleName());

        Observable.just(numbers).subscribe(new Observer<Integer[]>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(Integer @NonNull [] integers) {
                System.out.println("Size: " + integers.length);
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        });*/


        //------------------------------range()---------------------------------//

        Observable.range(1, 10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        });




                //------------------------------repeat()---------------------------------//

        /*Observable.range(1,5)
                .repeat(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {}

                    @Override
                    public void onComplete() {}
                });*/

    }

    // Using just
    private static Observable<String> getAnimalObservablesJust() {
        return Observable.just("Ant", "Bat", "Cat", "Dog", "Fox");
    }

    // Using from
    private static Observable<String> getAnimalObservablesFrom() {
        return Observable.fromArray("Ant", "Bat", "Cat", "Dog", "Fox");
    }

    // Observer
    private static Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("Name: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("All items are emitted");
            }
        };
    }
}
