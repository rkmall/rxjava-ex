package operators;

import io.reactivex.rxjava3.core.Observable;

import java.sql.PreparedStatement;

public class ActionOperatorsEx {

    public static void main(String[] args) {

        //executeDoOnNext();
        //executeDoAfterNext();
        //executeDoOnComplete();
        //executeDoOnError();
        //executeDoOnSubscribeAndDoOnDispose();
        executeDoOnSuccess();
    }

    public static void executeDoOnNext() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .doOnNext(s -> System.out.println("Processing: " + s))
                .map(s -> s.length())
                .subscribe(len -> System.out.println("Received: " + len));
    }

    public static void executeDoAfterNext() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .doAfterNext(s -> System.out.println("Processing: " + s))
                .map(s -> s.length())
                .subscribe(len -> System.out.println("Received: " + len));
    }

    public static void executeDoOnComplete() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .doOnComplete(() -> System.out.println("Source is done emission"))
                .map(s -> s.length())
                .subscribe(len -> System.out.println("Received: " + len));
    }

    public static void executeDoOnError() {
        Observable.just(5, 2, 4, 0)
                .map(num -> 10/num)
                .doOnError(err -> System.out.println("Division failed"))
                .subscribe(res -> System.out.println("Received: " + res),
                        err -> System.out.println("Error: " + err));
    }


    public static void executeDoOnSubscribeAndDoOnDispose() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .doOnSubscribe(disposable -> System.out.println("Subscribing: " + disposable))
                .doOnDispose(() -> System.out.println("Disposing..."))
                .doFinally(() -> System.out.println("Finally after OnComplete"))
                .subscribe(res -> System.out.println(res));
    }

    public static void executeDoOnSuccess() {
        Observable.just(5,2,3)
                .reduce((acc, next) -> acc + next)
                .doOnSuccess(i -> System.out.println("Emitting: " + i))
                .subscribe(res -> System.out.println("Received: " + res));
    }
}
