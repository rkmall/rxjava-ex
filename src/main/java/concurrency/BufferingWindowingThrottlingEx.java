package concurrency;

import io.reactivex.rxjava3.core.Observable;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BufferingWindowingThrottlingEx {

    public static void main(String[] args) {

        //executeBuffer();
        //executeBufferWithSpecifiedCollectionAsOutput();
        //executeBufferWithSkip();
        //executeTimeBasedBuffering();
        //executeTimeBasedBufferingWithSkip();

        executeThrottle();

        //executeWithoutSwitchMap();
        //executeWithSwitchMap();
    }

    public static int randomSleepTime() {
        return ThreadLocalRandom.current().nextInt(2000);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Buffer ------------------------------------------------------------------------
    public static void executeBuffer() {
        Observable.range(1, 35)
                .buffer(8)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeBufferWithSpecifiedCollectionAsOutput() {
        Observable.range(1, 35)
                .buffer(8, HashSet::new)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeBufferWithSkip() {
        Observable.range(1, 10)
                .buffer(2, 1)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeTimeBasedBuffering() {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> ((i + 1) * 300))
                .buffer(1, TimeUnit.SECONDS)
                .subscribe(res -> System.out.println(res));
        sleep(4000);
    }

    public static void executeTimeBasedBufferingWithSkip() {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> ((i + 1) * 300))
                .buffer(1, TimeUnit.SECONDS, 2)
                .subscribe(res -> System.out.println(res));
        sleep(4000);
    }

    // Throttle ----------------------------------------------------------------------------------
    public static void executeThrottle() {
        Observable<String> source1 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 100)
                .map(i -> "Source1: " + i)
                .take(10);

        Observable<String> source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .map(i -> "Source2: " + i)
                .take(2);

        Observable<String> source3 = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 1000)
                .map(i -> "Source3: " + i)
                .take(2);

        Observable.concat(source1, source2, source3)
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(res -> System.out.println(res));

        sleep(6000);
    }


    // SwitchMap -------------------------------------------------------------------------------------
    public static void executeWithoutSwitchMap() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Observable<String> processSource = source
                                        // delay each string to emulate intense calculation
                                        .concatMap(str -> Observable.just(str)
                                                .delay(randomSleepTime(), TimeUnit.MILLISECONDS)
                                        );

        processSource.subscribe(res -> System.out.println(res));
        sleep(10000);
    }

    public static void executeWithSwitchMap() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Observable<String> processSource = source
                // delay each string to emulate intense calculation
                .concatMap(str -> Observable.just(str)
                        .delay(randomSleepTime(), TimeUnit.MILLISECONDS)
                );

        // Run processSource every 2 seconds and kill each previous instance to start next
        Observable.interval(2, TimeUnit.SECONDS)
                        .switchMap(str -> processSource
                                .doOnDispose(() -> System.out.println("Disposing! starting next: "))
                        ).subscribe(res -> System.out.println(res));

        sleep(10000);
    }

}
