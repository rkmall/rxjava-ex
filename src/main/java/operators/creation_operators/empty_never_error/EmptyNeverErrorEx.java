package operators.creation_operators.empty_never_error;

import io.reactivex.rxjava3.core.Observable;

public class EmptyNeverErrorEx {

    public static void main(String[] args) {

        EmptyNeverErrorEx emptyNeverErrorEx = new EmptyNeverErrorEx();
        //emptyNeverErrorEx.executeEmpty();
        //emptyNeverErrorEx.executeNever();
        emptyNeverErrorEx.executeError();
    }


    public void executeEmpty() {
        // Immediately invokes onComplete
        Observable<String> emptyObservable = Observable.empty();
        emptyObservable.subscribe(
                item -> System.out.println(item),
                ex -> System.out.println(ex),
                () -> System.out.println("onComplete done!")
        );
    }

    public void executeNever() {
        // Never sends any item or notification, used for testing
        Observable<String> neverObservable = Observable.never();
        neverObservable.subscribe(
                item -> System.out.println(item),
                ex -> System.out.println(ex),
                () -> System.out.println("onComplete done!")
        );
    }

    public void executeError() {
        // Never sends any item or notification, used for testing
        Observable<String> errorObservable = Observable.error(new Throwable("Forces Error!!!"));
        errorObservable.subscribe(
                item -> System.out.println(item),
                ex -> System.out.println(ex),
                () -> System.out.println("onComplete done!")
        );
    }
}
