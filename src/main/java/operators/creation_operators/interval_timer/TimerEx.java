package operators.creation_operators.interval_timer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;


public class TimerEx {

    public static void main(String[] args) throws InterruptedException {

        TimerEx timerEx = new TimerEx();
        timerEx.usingRxTimer();

        Thread.sleep(5000);
    }

    public void usingRxTimer() {
        Observable<Long> timeObservable = Observable
                .timer(3000, TimeUnit.MILLISECONDS)    // timer has delay of 3s
                .doOnSubscribe((s) -> System.out.println("Subscription thread: " + Thread.currentThread().getName()));


        timeObservable.subscribe(new Observer<Long>() {
            long time = 0;
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe:");
                time = System.currentTimeMillis();    // initial time on Subscribe
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println(
                        "Runtime onNext thread: " + "Running on: " + Thread.currentThread().getName()
                                + ": "
                                + (System.currentTimeMillis() - time)/1000 + " seconds have elapsed."
                );
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(
                        "Runtime onComplete thread: Runtime completed on: "
                                + Thread.currentThread().getName()
                );
            }
        });
    }
}
