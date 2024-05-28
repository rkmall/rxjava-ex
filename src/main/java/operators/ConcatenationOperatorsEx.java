package operators;

import io.reactivex.rxjava3.core.Observable;

import javax.print.attribute.standard.PresentationDirection;
import java.util.concurrent.TimeUnit;

public class ConcatenationOperatorsEx {

    public static void main(String[] args) throws InterruptedException {

        //executeConcat();

        executeConcatWithTake();
        Thread.sleep(5000);

        executeConcatMap();
    }

    public static void executeConcat() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<String> source2 = Observable.just("Zeta", "Eta", "Theta");

        Observable.concat(source1, source2)
                .subscribe(item -> System.out.println(item));
    }

    public static void executeConcatWithTake() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .map(l -> l + 1)
                .map(l -> "Source1: " + l + "seconds");

        Observable<String> source2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(l -> (l + 1) * 500)
                .map(l -> "Source2: " + l + "seconds");

        Observable.concat(source1, source2)
                .subscribe(res -> System.out.println(res));
    }


    public static void executeConcatMap() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        source.concatMap(str -> Observable.fromArray(str.split("")))
                .subscribe(c -> System.out.println(c));
    }
}
