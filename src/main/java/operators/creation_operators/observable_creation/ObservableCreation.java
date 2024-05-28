package operators.creation_operators.observable_creation;

import data.model.User;
import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ObservableCreation {

    public static void main(String[] args) throws InterruptedException {

        ObservableCreation test = new ObservableCreation();

        //test.createUsingCreate();

        //test.createUsingJust();

        //test.createUsingFromArrayAndIterable();
        //test.createUsingFromCallable();
        //test.createUsingFromFuture();
        //test.createUsingFromRunnable();
        //test.createUsingFromAction();

        //test.createUsingDefer();
        test.differenceBetweenDeferAndJust1();
        //test.differenceBetweenDeferAndJust2();

        //test.createUsingError();
    }

    public void createUsingCreate() {
        List<User> users = DataSource.prepareMaleUsers();

        Observable<String> observable = Observable.create(emitter -> {
            if(!emitter.isDisposed()) {
                for(User user : users) {
                    emitter.onNext(user.getName());
                }
            }
            emitter.onComplete();
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe:");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("done");
            }
        });
    }

    public void createUsingJust() {
        Observable<Object> observable = Observable.just("1", "A", "3.2", "def");
        observable.subscribe(
                        (o) -> System.out.println(o.toString()),
                        (error) -> System.out.println(error.getMessage()),
                        () -> System.out.println("Completed")
                );
    }

    public void createUsingFromArrayAndIterable() {
        List<String> strings = new ArrayList<>(Arrays.asList("Jim", "Tom", "Kale"));
        Observable<Object> observable = Observable.fromIterable(strings);
        observable.subscribe(
                (o) -> System.out.println(o.toString()),
                (error) -> System.out.println(error.getMessage()),
                () -> System.out.println("Completed")
        );
    }


    public void createUsingFromCallable() {
        Callable<String> callable = () -> "Hello there";
        Observable<String> observable = Observable.fromCallable(callable);

        observable.subscribe(
                (o) -> System.out.println(o.toString()),
                (error) -> System.out.println(error.getMessage()),
                () -> System.out.println("Completed")
        );
    }

    public void createUsingFromFuture() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Future<String> future = executor.schedule(() -> "Hello world!", 1, TimeUnit.SECONDS);
        Observable<String> observable = Observable.fromFuture(future);

        observable.subscribe(
               (o) -> System.out.println(o.toString()),
               (error) -> System.out.println(error.getMessage()),
               () -> System.out.println("Completed")
        );
        executor.shutdown();
    }

    public void createUsingFromRunnable() {
        Runnable runnable = () -> System.out.println("Hello there");
        Observable<String> source = Observable.fromRunnable(runnable);
        source.subscribe();
    }

    public void createUsingFromAction() {
        Action action = () -> System.out.println("Hello there");
        Observable<String> source = Observable.fromAction(action);
        source.subscribe();
    }

    public void createUsingDefer() {
        List<String> list = new ArrayList<>();
        list.add("C");
        list.add("C++");
        list.add("Java");
        list.add("Python");

        Observable<String> source = Observable.defer(() -> Observable.fromIterable(list));
        source.subscribe(item -> System.out.println(item));

        list.add("Go");
        source.subscribe(item -> System.out.println(item));
    }


    public void differenceBetweenDeferAndJust1() {
        AtomicInteger index = new AtomicInteger(0);

        Observable<String> source = Observable.just("a", "b", "c", "d", "e")
                .map(s -> index.incrementAndGet() + ":" + s);

        // Observer 1
        source.subscribe(
                System.out::println,
                e -> e.getMessage(),
                () -> System.out.println("onComplete: done!")
        );

        // Observer 2
        source.subscribe(
                System.out::println,
                e -> e.getMessage(),
                () -> System.out.println("onComplete: done!")
        );
    }

    public void differenceBetweenDeferAndJust2() {
        Observable<String> source = Observable.defer(
                () -> {
                    AtomicInteger index = new AtomicInteger(0);
                    return Observable.just("a", "b", "c", "d", "e")
                    .map(s -> index.incrementAndGet() + ":" + s);
                });

        // Observer 1
        source.subscribe(
                System.out::println,
                e -> e.getMessage(),
                () -> System.out.println("onComplete: done!")
        );

        // Observer 2
        source.subscribe(
                System.out::println,
                e -> e.getMessage(),
                () -> System.out.println("onComplete: done!")
        );
    }

    public void createUsingError() {
        Observable<Exception> source = Observable
                .error(new RuntimeException("Runtime exception thrown"));

        source.subscribe(
                item -> System.out.println(item),
                ex -> System.out.println(ex)
        );
    }
}
