package operators.creation_operators.defer;

import io.reactivex.rxjava3.core.Observable;

public class DeferEx {

    private static int start = 1;
    private static int count = 5;

    public static void main(String[] args) throws InterruptedException {

        DeferEx defer = new DeferEx();

        //defer.withoutDefer();
        defer.withDefer();

        //defer.createUsingDefer();
        //defer.createWithJust();
        //defer.create();
    }


    public void withoutDefer() {
        int start = 1;
        int[] count = {5};
        Observable<Integer> source = Observable.range(start, count[0]);
        source.subscribe(i -> System.out.println(i));   // Observer1

        count[0] = 10;                                  // modify count
        source.subscribe(i -> System.out.println(i));   // Observer2
    }

    public void withDefer() {
        int start = 1;
        int[] count = {5};
        Observable<Integer> source = Observable.defer(() -> Observable.range(start, count[0]));
        source.subscribe(i -> System.out.println(i));   // Observer1

        count[0] = 2;                                  // modify count
        source.subscribe(i -> System.out.println(i));   // Observer2
    }


    // Processing inside defer is done at the time of subscription
    // When the Observable holds some state and the state changes during the subscription
    // the changes is reflected
    public void createUsingDefer() throws InterruptedException {
        Observable<Long> observable = Observable.defer(() -> Observable.just(System.currentTimeMillis()));
        observable.subscribe(time -> System.out.println(time.toString()));
        Thread.sleep(2000);
        observable.subscribe(time -> System.out.println(time.toString()));
    }

    // The processing is already done at the time of creation
    // When the Observable holds some state and the state changes during the subscription
    // The change is not reflected
    public void createWithJust() throws InterruptedException {
        Observable<Long> observable = Observable.just(System.currentTimeMillis());
        observable.subscribe(time -> System.out.println(time.toString()));
        Thread.sleep(2000);
        observable.subscribe(time -> System.out.println(time.toString()));
    }


    // Create is similar to defer
    public void create() throws InterruptedException {
        Observable<Long> observable = Observable.create(emitter -> {
            emitter.onNext(System.currentTimeMillis());
            emitter.onComplete();
        });
        observable.subscribe(time -> System.out.println(time.toString()));
        Thread.sleep(1000);
        observable.subscribe(time -> System.out.println(time.toString()));
    }
}
