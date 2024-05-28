package concurrency;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelizationEx {

    public static void main(String[] args) {

        //withoutParallelization();

        //withParallelization();

        //parallelizationBetterApproach();
        parallelizationBetterApproachDebug();
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

    // Sequential execution, happens on the main thread
    public static void withoutParallelization() {
        Observable.range(1, 10)
                .map(i -> intenseCalculation(i))
                .subscribe(res -> System.out.println("Received: " + res
                        + " at " + LocalTime.now()));
        sleep(10000);
    }

    // Concurrent execution, from single Observable
    public static void withParallelization() {
        Observable.range(1, 10)
                .flatMap( i -> Observable.just(i)                   // each emission turned to Observable
                        .subscribeOn(Schedulers.computation())      // each Observable gets its own thread
                        .doOnNext(item -> System.out.println("Inside flatMap, doOnNext for: "
                                + item + " on: " + Thread.currentThread().getName()))
                        .map(i2 -> intenseCalculation(i2))
                )
                .subscribe(res -> System.out.println("Received: " + res
                + " at " + LocalTime.now()));

        sleep(10000);
    }

    public static void parallelizationBetterApproach() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        AtomicInteger assigner = new AtomicInteger(0);

        Observable.range(1, 10)
                .groupBy(i -> assigner.incrementAndGet() % coreCount)
                .flatMap(grp -> grp.observeOn(Schedulers.computation())
                        .map(item -> intenseCalculation(item))
                )
                .subscribe(res -> System.out.println("Received: " + res
                        + " at: " + LocalTime.now()
                        + " on: " + Thread.currentThread().getName()));
        sleep(10000);
    }


    public static void parallelizationBetterApproachDebug() {
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("No. of cores: " + coreCount);

        AtomicInteger assigner = new AtomicInteger(0);

        Observable.range(1, 80)
                .groupBy(i -> {
                    int i1 = assigner.incrementAndGet() % coreCount;
                    System.out.println("GroupBy key: " + i1 + " for " + i);
                    return i1;
                })
                .flatMap(grp -> grp.observeOn(Schedulers.computation())
                        .map(item -> intenseCalculation(item))
                )
                .subscribe(res -> System.out.println("Received: " + res
                        + " at: " + LocalTime.now()
                        + " on: " + Thread.currentThread().getName()));
        sleep(10000);
    }
}
