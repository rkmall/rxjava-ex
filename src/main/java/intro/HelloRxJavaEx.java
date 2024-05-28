package intro;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class HelloRxJavaEx {

    public static void main(String[] args) {

        // ObservableOnSubscribe is an interface that defines action to be taken
        // when an Observer subscribes to this Observable
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello");
                emitter.onNext("RxJava");
                emitter.onComplete();
            }
        });

        // After subscribing to Observable, Observer passes mainly 3 events which are:
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe: " + d.isDisposed());
            }

            // Source Observable passes each item one at a time to Observer
            @Override
            public void onNext(@NonNull String s) {
                System.out.println("Observer 1: " + s);
            }

            // Propagates the error to Observer just as item
            // If any error is encountered, onComplete is never invoked
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: " + e);
            }

            // No more onNext call will occur, the emission of items is complete
            @Override
            public void onComplete() {
                System.out.println("onComplete: done!");
            }
        });

        observable.subscribe(item -> System.out.println("Observer 2: " + item));   // onNext() call, Consumer function
        observable.subscribe(item -> System.out.println("Observer 3: " + item));   // onNext() call, Consumer function

    }
}
