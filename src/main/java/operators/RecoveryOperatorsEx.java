package operators;

import io.reactivex.rxjava3.core.Observable;

public class RecoveryOperatorsEx {

    public static void main(String[] args) {

        //executeOnErrorReturn();

        //executeOnErrorReturnItem();

        //executeOnErrorResumeNext();

        //executeOnErrorResumeWith();

        executeRetry();

    }

    public static void executeOnErrorReturn() {
        Observable.just(5, 2, 4, 0, 5, 10)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .onErrorReturn(err -> -1)
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }

    public static void executeOnErrorReturnItem() {
        Observable.just(5, 2, 4, 0, 5, 10)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .onErrorReturnItem(-1)
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }

    public static void executeOnErrorResumeNext() {
        Observable.just(5, 2, 4, 0, 5, 10)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .onErrorResumeNext(err -> Observable.just(-1).repeat(3))
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }

    public static void executeOnErrorResumeWith() {
        Observable.just(5, 2, 4, 0, 5, 10)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .onErrorResumeWith(Observable.just(-1).repeat(3))
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }

    public static void executeRetry() {
        Observable.just(5, 2, 4, 0, 5, 10)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .retry(2)
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }
}
