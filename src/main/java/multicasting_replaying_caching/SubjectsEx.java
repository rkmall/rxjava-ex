package multicasting_replaying_caching;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.*;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SubjectsEx {

    public static void main(String[] args) {

        //publishSubjectEx();
        //publishSubjectEx1();
        //publishSubjectWrongWay();

        //behaviourSubjectEx();
        //behaviourSubjectEx1();
        //behaviourSubjectEx2();
        behaviourSubjectFirstElement();

        //replaySubjectEx();

        //asyncSubjectEx();

        //uniCastSubjectEx();
    }

    public static void sleep(long millis) {
        try {
            System.out.println("Sleeping: " + Thread.currentThread().getName());
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // PublishSubject --------------------------------------------------------------------------------
    public static void publishSubjectEx() {
        // Subject acting as Observable
        Subject<String> subject = PublishSubject.create();
        subject.map(s -> s.length()).subscribe(res -> System.out.println(res));

        // Subject acting as Observer
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();
    }

    public static void publishSubjectWrongWay() {
        Subject<String> subject = PublishSubject.create();

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();

        subject.map(s -> s.length()).subscribe(res -> System.out.println(res));  // emissions missed
        // Observers must be setup before calling onNext() calls
    }

    public static void publishSubjectEx1() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS)
                                                .map(l -> "Source1: " + (l+1) + " seconds");

        Observable<String> source2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                                                .map(l -> "Source2: " + ((l+1) * 500) + " seconds");

        // In this case it is better to use merge, but for demo, Subject is being used
        // Observable.merge(source1, source2).subscribe(res -> System.out.println(res));

        // Subject acting as Observable
        Subject<String> subject = PublishSubject.create();
        subject.subscribe(res -> System.out.println(res));

        // Subject acting as Observer
        source1.subscribe(subject);
        source2.subscribe(subject);

        sleep(3000);
    }

    // BehaviourSubject ---------------------------------------------------------------------------
    public static void behaviourSubjectEx() {
        Subject<String> subject = BehaviorSubject.create();
        subject.subscribe(res -> System.out.println("Observer1: " + res));      // Observer1

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(res -> System.out.println("Observer2: " + res));      // Observer2
    }

    public static void behaviourSubjectEx1() {
        Subject<String> subject = BehaviorSubject.create();
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(res -> System.out.println("Observer1: " + res));      // Observer will receive only "Gamma"
    }

    public static void behaviourSubjectEx2() {
        Subject<String> subject = BehaviorSubject.create();
        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();

        subject.subscribe(res -> System.out.println("Observer: " + res));      // Observer will receive only onComplete()
    }

    public static void behaviourSubjectFirstElement() {
        Subject<String> subject = BehaviorSubject.create();

        subject.firstElement().subscribe(res -> System.out.println("FirstElement: " + res));

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(res -> System.out.println("Observer1: " + res));

        subject.onNext("Delta");
        subject.onNext("Epsilon");
    }


    // ReplaySubject -----------------------------------------------------------------------------------
    public static void replaySubjectEx() {
        Subject<String> subject = ReplaySubject.create();
        subject.subscribe(res -> System.out.println("Observer1: " + res));

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");

        subject.subscribe(res -> System.out.println("Observer2: " + res));
    }

    // AsyncSubject ---------------------------------------------------------------------------------------
    public static void asyncSubjectEx() {
        Subject<String> subject = AsyncSubject.create();
        subject.subscribe(res -> System.out.println("Observer1: " + res));

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();

        subject.subscribe(res -> System.out.println("Observer2: " + res));
    }


    // UniCastSubject ---------------------------------------------------------------------------------------
    public static void uniCastSubjectEx() {
        Subject<String> subject = UnicastSubject.create();

        Observable.interval(500, TimeUnit.MILLISECONDS)
                        .map(l -> ((l + 1) * 500) + " milliseconds")
                        .subscribe(subject);

        sleep(2000);

        subject.subscribe(res -> System.out.println("Observer1: " + res));
    }

}
