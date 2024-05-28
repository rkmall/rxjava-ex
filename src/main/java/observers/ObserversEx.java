package observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObserversEx {

    public static void main(String[] args) {

        ObserversEx observersEx = new ObserversEx();
        //observersEx.createObserverUsingAnonClass();
        //observersEx.createObserverUsingOnNext();
        //observersEx.createObserverUsingOnNextAndOnError();
        observersEx.createObserverUsingOnNextAndOnErrorOnComplete();
    }


    // Passing Observer using Anonymous class
    public void createObserverUsingAnonClass() {
        Observable<String> source = Observable.just("Apple", "Ball","Cat");
        source.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull String s) { System.out.println("onNext: " + s);}

            @Override
            public void onError(@NonNull Throwable e) { System.out.println("onError: " + e);}

            @Override
            public void onComplete() { System.out.println("onComplete: done!");}
        });
    }

    // Passing implementation of Observer's
    // 1. onNext() as Consumer
    public void createObserverUsingOnNext() {
        Observable<String> source = Observable.just("Apple", "Ball","Cat");
        source.subscribe(s -> System.out.println("onNext: " + s));      // onNext
    }

    // Passing implementation of Observer's
    // 1. onNext() as Consumer
    // 2. onError() as Consumer
    public void createObserverUsingOnNextAndOnError() {
        Observable<String> source = Observable.just("Apple", "Ball","Cat");
        source.subscribe(
                s -> System.out.println("onNext: " + s),        // onNext
                error -> System.out.println(error)              // onError
        );
    }

    // Passing implementation of Observer's
    // 1. onNext() as Consumer
    // 2. onError() as Consumer
    // 3. onComplete() as Action

    CompositeDisposable disposable = new CompositeDisposable();

    public void createObserverUsingOnNextAndOnErrorOnComplete() {
        Observable<String> source = Observable.just("Apple", "Ball","Cat");
        source.subscribe(
                s -> System.out.println("onNext: " + s),        // onNext
                error -> System.out.println(error),             // onError
                () -> System.out.println("onComplete: done!"),  // onComplete
                disposable                                      // disposable container
        );
    }
}
