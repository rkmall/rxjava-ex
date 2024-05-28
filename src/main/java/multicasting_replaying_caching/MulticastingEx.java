package multicasting_replaying_caching;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MulticastingEx {

    public static void main(String[] args) {

        //coldObservablesEx();
        //multicastingWithHotObservablesEx();

        //randomTest1();
        //randomTest2();
        //randomTest3();

        //executeAutoConnect();

        //executeRefCount();

        //executeReplay();
        executeReplayWithBufferSize();

        //executeCache();
    }

    public static void sleep(long millis) {
        System.out.println("Sleeping: " + Thread.currentThread().getName());
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int randomIntGen() {
        return ThreadLocalRandom.current().nextInt(1000);
    }

    // Multicasting ---------------------------------------------------------------------------
    public static void coldObservablesEx() {
        Observable<Integer> ints = Observable.range(1,3);
        ints.subscribe(res -> System.out.println("Observer1: " + res)); // observer 1
        ints.subscribe(res -> System.out.println("Observer2: " + res)); // observer 2
    }

    public static void multicastingWithHotObservablesEx() {
        ConnectableObservable<Integer> ints = Observable.range(1,3).publish();
        ints.subscribe(res -> System.out.println("Observer1: " + res)); // observer 1
        ints.subscribe(res -> System.out.println("Observer2: " + res)); // observer 2
        ints.connect();
    }

    public static void randomTest1() {
        Observable<Integer> ints = Observable.range(1,3)
                .map(i -> randomIntGen());

        ints.subscribe(res -> System.out.println("Observer1: " + res)); // observer 1
        ints.subscribe(res -> System.out.println("Observer2: " + res)); // observer 2
    }

    public static void randomTest2() {
        ConnectableObservable<Integer> ints = Observable.range(1,3).publish();  // publish called first
        Observable<Integer> randomInts =  ints.map(i -> randomIntGen());                   // map called

        randomInts.subscribe(res -> System.out.println("Observer1: " + res)); // observer 1
        randomInts.subscribe(res -> System.out.println("Observer2: " + res)); // observer 2
        ints.connect();
    }

    public static void randomTest3() {
        ConnectableObservable<Integer> ints = Observable.range(1,3)
                .map(i -> randomIntGen())
                .publish();                 // publish called after map

        ints.subscribe(res -> System.out.println("Observer1: " + res)); // observer 1
        ints.subscribe(res -> System.out.println("Observer2: " + res)); // observer 2
        ints.connect();
    }


    // AutoConnect ---------------------------------------------------------------------------------
    public static void executeAutoConnect() {
        Observable<Integer> ints = Observable.range(1,3)
                .map(i -> randomIntGen())
                .publish()
                .autoConnect(2);  // autoConnect as soon as there are 2 Observers

        ints.subscribe(res -> System.out.println("Observer1: " + res));

        ints.reduce(0, (total, next) -> total + next)
                .subscribe(res -> System.out.println("Observer2: " + res));
    }


    // RefCount --------------------------------------------------------------------------------
    public static void executeRefCount() {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .publish()
                .refCount();

        // Observer 1
        ints.take(5)
                .doOnComplete(() -> System.out.println("Observer1 done"))
                .subscribe(res ->
                        System.out.println("Observer1 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);

        // Observer 2
        ints.take(2)
                .doOnComplete(() -> System.out.println("Observer2 done"))
                .subscribe(res ->
                        System.out.println("Observer2 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);

        // Observer 3
        ints.doOnComplete(() -> System.out.println("Observer3 done"))
                .subscribe(res ->
                        System.out.println("Observer3 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);
    }


    // Replay -------------------------------------------------------------------------
    public static void executeReplay() {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .replay()
                .autoConnect();

        // Observer 1
        ints.doOnComplete(() -> System.out.println("Observer1 done"))
                .subscribe(res ->
                        System.out.println("Observer1 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);

        // Observer 2
        ints.doOnComplete(() -> System.out.println("Observer2 done"))
                .subscribe(res ->
                        System.out.println("Observer2 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);
    }

    public static void executeReplayWithBufferSize() {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .replay(1)  // bufferSize 1, only the last 1 element will be replayed
                .autoConnect();

        // Observer 1
        ints.doOnComplete(() -> System.out.println("Observer1 done"))
                .subscribe(res ->
                        System.out.println("Observer1 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);

        // Observer 2
        ints.doOnComplete(() -> System.out.println("Observer2 done"))
                .subscribe(res ->
                        System.out.println("Observer2 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);
    }

    public static void executeReplayWithRefCount() {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .replay(1)  // bufferSize 1, only the last 1 element will be replayed
                .refCount();

        // Observer 1
        ints.doOnComplete(() -> System.out.println("Observer1 done"))
                .subscribe(res ->
                        System.out.println("Observer1 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);

        // Observer 2
        ints.doOnComplete(() -> System.out.println("Observer2 done"))
                .subscribe(res ->
                        System.out.println("Observer2 on "
                                + Thread.currentThread().getName()
                                + ", value: " + res));
        sleep(3000);
    }



    // Caching -------------------------------------------------------------------------
    public static void executeCache() {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .cache();

        ints.subscribe(res -> System.out.println("Observer1: " + res));
        sleep(3000);

        ints.subscribe(res -> System.out.println("Observer2: " + res));
        sleep(3000);

        ints.subscribe(res -> System.out.println("Observer3: " + res));
    }
}
