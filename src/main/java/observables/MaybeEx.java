package observables;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.Arrays;
import java.util.List;

public class MaybeEx {

    public static void main(String[] args) {
        MaybeEx maybeEx = new MaybeEx();
        //maybeEx.createMaybe();
        //maybeEx.createEmptyMaybe();
        maybeEx.createMaybeFromObservable();

        //maybeEx.createMaybeFromSource();

        //maybeEx.createObservableFromMaybe();
    }


    // onComplete() is not called as the source is not empty
    public void createMaybe() {
        Maybe<String> source = Maybe.just("Hello");
        source.subscribe(
                s -> System.out.println(s),                     // onSuccess
                error -> System.out.println(error),             // onError
                () -> System.out.println("onComplete: done")    // onComplete
        );
    }


    CompositeDisposable disposable = new CompositeDisposable();
    // onComplete() is called as there is nothing to emit
    public void createEmptyMaybe() {
        Maybe<String> emptySource = Maybe.empty();
        emptySource.subscribe(
                s -> System.out.println(),                      // onSuccess
                error -> System.out.println(error),             // onError
                () -> System.out.println("onComplete: done!"),  // onComplete
                disposable                                      // disposable container to dispose
        );
    }


    public void createMaybeFromObservable() {
        List<String> myList = Arrays.asList("apple", "ball", "cat");
        Observable<String> source = Observable.fromIterable(myList);
        source.firstElement()                   // firstElement returns Maybe with the first element
                .subscribe(
                        s -> System.out.println(s),
                        ex -> System.out.println(ex),
                        () -> System.out.println("onComplete: done!")
        );
    }

    public void createMaybeFromSource() {
        List<String> myList = Arrays.asList("apple", "ball", "cat");
        Single<List<String>> single = Single.fromSupplier(() -> myList);

        Maybe<List<String>> maybe = Maybe.fromSingle(single);       // fromSingle() returns the Maybe with the single element
        maybe.subscribe( list -> list.forEach(s -> System.out.println(s)) );
    }

    public void createObservableFromMaybe() {
        Maybe<String> maybe = Maybe.just("Hello");
        Observable<String> observable = maybe.toObservable();

        observable.subscribe(s -> System.out.println(s));
    }
}
