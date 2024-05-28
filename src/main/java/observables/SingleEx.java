package observables;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SingleEx {

    public static void main(String[] args) {

        SingleEx singleEx = new SingleEx();
        //singleEx.createSingle();
        singleEx.createSingleFromObservableUsingFirst();
        singleEx.createSingleFromObservableUsingSingleOrError();
        singleEx.createObservableFromSingle();
    }

    CompositeDisposable disposable = new CompositeDisposable();

    public void createSingle() {
        Single<String> single = Single.just("Hello");
        single.subscribe(
                s -> System.out.println(s),                     // onSuccess
                throwable -> System.out.println(throwable),     // onError
                disposable                                      // disposable container
        );
    }

    public void createSingleFromObservableUsingFirst() {
        Observable<String> source = Observable.just("Jim", "Kale", "Emma");
        Single<String> singleSrc = source.first("Name"); // first() returns Single with first element
        singleSrc.subscribe(
                s -> System.out.println(s),                     // onSuccess
                throwable -> System.out.println(throwable),     // onError
                disposable                                      // disposable container
        );
    }

    public void createSingleFromObservableUsingSingleOrError() {
        Observable<String> source = Observable.just("Jim", "Kale", "Emma");
        Single<String> singleSrc = source.singleOrError();      // returns error if more than 1 element present
        singleSrc.subscribe(
                s -> System.out.println(s),                     // onSuccess
                throwable -> System.out.println(throwable),     // onError
                disposable                                      // disposable container
        );
    }


    public void createObservableFromSingle() {
        Single<String> single = Single.just("Hello");
        Observable<String> observable = single.toObservable();
        observable.subscribe(s -> System.out.println(s));
    }
}
