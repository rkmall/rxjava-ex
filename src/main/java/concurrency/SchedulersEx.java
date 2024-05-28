package concurrency;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class SchedulersEx {

    public static void main(String[] args) {

        //subscribeOnCreatesNewThreadForEachObserver();
        subscribeOnWithMulticastingCreateSingleThreadForAllObservers();
        //topMostSubscribeOnWins();

        //observeOnSwitchedEmissionOnSpecifiedThread();
        //switchingThreadsAsPerRequirement();
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

    public static void write(String text, String path) {
        File file = new File(path);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(text);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // SubscribeOn ---------------------------------------------------------------------------------
    public static void subscribeOnCreatesNewThreadForEachObserver() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .subscribeOn(Schedulers.computation())
                .map(str -> intenseCalculation(str));

        // Separate thread created for Observer1
        source.subscribe(res -> System.out.println("Observer1 received: " + res + " on: "
                + Thread.currentThread().getName()));

        // Separate thread created for Observer2
        source.subscribe(res -> System.out.println("Observer2 received: " + res + " on: "
                + Thread.currentThread().getName()));

        sleep(8000);
    }

    public static void subscribeOnWithMulticastingCreateSingleThreadForAllObservers() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .subscribeOn(Schedulers.computation())
                .map(str -> intenseCalculation(str))
                .publish()
                .autoConnect(2);

        source.subscribe(res -> System.out.println("Observer1 received: " + res + " on: "
                + Thread.currentThread().getName()));

        source.subscribe(res -> System.out.println("Observer2 received: " + res + " on: "
                + Thread.currentThread().getName()));

        sleep(8000);
    }

    public static void topMostSubscribeOnWins() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .subscribeOn(Schedulers.computation())
                .map(str -> intenseCalculation(str))
                .subscribeOn(Schedulers.io())
                .map(str -> str.length())
                .subscribe(res -> System.out.println("Observer2 received: " + res + " on: "
                        + Thread.currentThread().getName()));
        sleep(8000);
    }

    // ObserveOn --------------------------------------------------------------------------------
    public static void observeOnSwitchedEmissionOnSpecifiedThread() {
        Observable.just("APPLE/1234/BALL", "5678/CAT", "2323/DOG/5656")
                // Happens in IO Scheduler
                .subscribeOn(Schedulers.io())
                .doOnNext(res -> System.out.println("SubscribeOn doOnNext: " + res
                        + " on: " + Thread.currentThread().getName()))
                .flatMap(str -> Observable.fromArray(str.split("/")))

                // Happens in Computation Scheduler
                .observeOn(Schedulers.computation())
                .doOnNext(res -> System.out.println("ObserveOn doOnNext: " + res
                        + " on: " + Thread.currentThread().getName()))
                .filter(str -> str.matches("[0-9]+"))
                .map(str -> Integer.valueOf(str))
                .reduce((total, next) -> total + next)
                .subscribe(res -> System.out.println("Calculated: " + res
                        + " on: " + Thread.currentThread().getName()));

        sleep(2000);
    }

    public static void  switchingThreadsAsPerRequirement() {
        Observable.just("APPLE/1234/BALL", "5678/CAT", "2323/DOG/5656")
                // Happens in IO Scheduler
                .subscribeOn(Schedulers.io())
                .flatMap(str -> Observable.fromArray(str.split("/")))

                // Happens in Computation Scheduler
                .observeOn(Schedulers.computation())
                .filter(str -> str.matches("[0-9]+"))
                .map(str -> Integer.valueOf(str))
                .reduce((total, next) -> total + next)
                .doOnSuccess(res -> System.out.println("Calculated: " + res
                        + " on: " + Thread.currentThread().getName()))

                // Switch back to IO Scheduler
                .observeOn(Schedulers.io())
                .map(res -> res.toString())
                .doOnSuccess(res -> System.out.println("Writing result: " + res
                        + " to a file on" + Thread.currentThread().getName()))
                        .subscribe(output -> write(output, "output.txt"));

        sleep(2000);
    }

}
