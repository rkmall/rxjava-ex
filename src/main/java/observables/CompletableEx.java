package observables;

import groovy.json.JsonOutput;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.EnumMap;

public class CompletableEx {

    public static void main(String[] args) {

        CompletableEx completableEx = new CompletableEx();
        //completableEx.createCompletableFromAction();
        completableEx.createCompletableFromRunnable();

        //completableEx.createCompletableFromSingle();
        //completableEx.createCompletableFromMaybe();

        //completableEx.completableToObservable();
    }

    CompositeDisposable disposable = new CompositeDisposable();
    public void createCompletableFromAction() {
        Completable completable = Completable
                .fromAction(() -> System.out.println("Action executed"));

        completable.subscribe(
                () -> System.out.println("onComplete: done!"),          // onComplete()
                ex -> System.out.println(ex),                           // onError()
                disposable                                              // disposable container
        );
    }

    public void createCompletableFromRunnable() {
        Completable completable = Completable
                .fromRunnable(() -> System.out.println("Runnable executed"));

        completable.subscribe(
                () -> System.out.println("onComplete: done!"),          // onComplete
                ex -> System.out.println(ex)                            // onError
        );
    }

    public void createCompletableFromSingle() {
        Observable<String> source = Observable.just("Jim", "Kale", "Emma");
        Completable completable = source.first("Name").ignoreElement();

        completable.subscribe(
                () -> System.out.println("onComplete: done!"),          // onComplete
                ex -> System.out.println(ex)                            // onError
        );
    }

    public void createCompletableFromMaybe() {
        Observable<String> source = Observable.just("Jim", "Kale", "Emma");
        Completable completable = source.firstElement().ignoreElement();

        completable.subscribe(
                () -> System.out.println("onComplete: done!"),          // onComplete
                ex -> System.out.println(ex)                            // onError
        );
    }

    public void completableToObservable() {
        Completable completable = Completable.fromCallable(() -> 10);
        Observable<Integer> observable = completable.toObservable();
        observable.subscribe(item -> System.out.println(item));
    }
}
