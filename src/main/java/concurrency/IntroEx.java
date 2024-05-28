package concurrency;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class IntroEx {

    public static void main(String[] args) {

        //withoutSchedulers();
        withSchedulers();

        //executeBlockingSubscribe();


    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Sequential execution, first Observable emits and completes, then second starts emitting
    public static void withoutSchedulers() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .map(str -> intenseCalculation(str))
                .subscribe(res -> System.out.println(res));

        Observable.range(1, 5)
                .map(i -> intenseCalculation(i))
                .subscribe(res -> System.out.println(res));
    }

    // Simultaneous execution, both Observables start emitting at the same time simultaneously
    public static void withSchedulers() {
        // Observable 1
        Observable.just("Alpha", "Beta", "Gamma", "Delta")      // diverge to Computation thread1
                .subscribeOn(Schedulers.computation())
                .map(str -> intenseCalculation(str))
                .subscribe(res -> System.out.println("Observer1 received: " + res
                        + " on: " + Thread.currentThread().getName()));

        // Observable 2
        Observable.range(1, 5)
                .subscribeOn(Schedulers.computation())          // diverge to Computation thread2
                .map(i -> intenseCalculation(i))
                .subscribe(res -> System.out.println("Observer2 received: " + res
                        + " on: " + Thread.currentThread().getName()));

        sleep(10000);   // keep alive, let other threads execute
    }


    public static void executeBlockingSubscribe() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .subscribeOn(Schedulers.computation())
                .map(str -> intenseCalculation(str))
                .blockingSubscribe(             // stop main thread from exiting until onComplete() is called
                        res -> System.out.println(res),
                        e -> System.out.println(e),
                        () -> System.out.println("Done")
                );
    }












}
