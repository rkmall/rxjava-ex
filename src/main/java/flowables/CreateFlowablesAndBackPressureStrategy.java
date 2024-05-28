package flowables;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateFlowablesAndBackPressureStrategy {

    public static void main(String[] args) {

        //backPressureStrategyBuffer();
        //backPressureStrategyDrop();
        //backPressureStrategyLatest();
        //backPressureStrategyError();

        //flowableGenerate();
        flowableGenerateWithState();

        //executeOnBackPressureBuffer();
        //executeBackPressureOverflowStrategy();
        //executeOnBackPressureLatest();
        //executeOnBackPressureDrop();

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Flowable.create() ----------------------------------------------------------------------------
    // Buffers the emissions when backpressure occurs
    // but can throw OutOfMemoryException
    // but at least prevents MissingBackpressureException
    public static void backPressureStrategyBuffer() {
        Flowable<Integer> source = Flowable.create(emitter -> {
            for(int i = 0; i < 1000; i++) {
                if(emitter.isCancelled()) {
                   return;
                }
                emitter.onNext(i);
            }
            //emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        source.observeOn(Schedulers.io())
                .subscribe(res -> System.out.println(res));

        sleep(1000);
    }

    // Drops when the backpressure occurs
    public static void backPressureStrategyDrop() {
        Flowable<Integer> source = Flowable.create(emitter -> {
            for(int i = 0; i < 1000; i++) {
                if(emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.DROP);

        source.observeOn(Schedulers.io())
                .subscribe(res -> System.out.println(res));

        sleep(1000);
    }

    // Provides the emissions it can but when backpressure occurs provides the last emission
    public static void backPressureStrategyLatest() {
        Flowable<Integer> source = Flowable.create(emitter -> {
            for(int i = 0; i < 1000; i++) {
                if(emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.LATEST);

        source.observeOn(Schedulers.io())
                .subscribe(res -> System.out.println(res));

        sleep(1000);
    }

    // Throws MissingBackpressureException exception
    public static void backPressureStrategyError() {
        Flowable<Integer> source = Flowable.create(emitter -> {
            for(int i = 0; i < 1000; i++) {
                if(emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.ERROR);

        source.observeOn(Schedulers.io())
                .subscribe(res -> System.out.println(res));

        sleep(1000);
    }

    // Flowable.generate() -------------------------------------------------------------------------
    public static Flowable<Integer> randomGenerate(int min, int max) {
        return Flowable.generate(emitter ->
                emitter.onNext(ThreadLocalRandom.current().nextInt(min,max)));
    }

    public static Flowable<Integer> rangeReverse(int upperBound, int lowerBound) {
        return Flowable.generate(() ->
                new AtomicInteger(upperBound + 1),
                (state, emitter) -> {
                    int current = state.decrementAndGet();
                    emitter.onNext(current);
                    if(current == lowerBound) {
                        emitter.onComplete();
                    }
                }
        );
    }

    public static void flowableGenerate() {
        randomGenerate(1, 1000)
                .subscribeOn(Schedulers.computation())
                .doOnNext(i -> System.out.println("Emitting: " + i))
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(50);
                    System.out.println("Received: " + res);
                });
        sleep(8000);
    }

    public static void flowableGenerateWithState() {
        rangeReverse(100, -100)
                .subscribeOn(Schedulers.computation())
                .doOnNext(i -> System.out.println("Emitting: " + i))
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(50);
                    System.out.println("Received: " + res);
                });
        sleep(8000);
    }


    // OnBackpressure Operators ---------------------------------------------------------------------
    public static void executeOnBackPressureBuffer() {
        Flowable.interval(1, TimeUnit.MILLISECONDS) // interval does not have backpressure strategy
                .onBackpressureBuffer()         // apply BackpressureStrategy.BUFFER at this point
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(5);
                    System.out.println(res);
                });

        sleep(5000);
    }

    public static void executeBackPressureOverflowStrategy() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)     // interval does not have backpressure strategy
                .onBackpressureBuffer(                         // apply BackpressureStrategy.BUFFER at this point
                        10,
                            () -> System.out.println("overflow"),
                            BackpressureOverflowStrategy.DROP_LATEST
                        )
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(5);
                    System.out.println(res);
                });

        sleep(5000);
    }

    public static void executeOnBackPressureLatest() {
        Flowable.interval(1, TimeUnit.MILLISECONDS) // interval does not have backpressure strategy
                .onBackpressureLatest()         // apply BackpressureStrategy.BUFFER at this point
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(5);
                    System.out.println(res);
                });

        sleep(5000);
    }

    public static void executeOnBackPressureDrop() {
        Flowable.interval(1, TimeUnit.MILLISECONDS) // interval does not have backpressure strategy
                .onBackpressureDrop(i -> System.out.println("Dropping " + i)) // apply BackpressureStrategy.BUFFER at this point
                .observeOn(Schedulers.io())
                .subscribe(res -> {
                    sleep(5);
                    System.out.println(res);
                });

        sleep(5000);
    }


}
