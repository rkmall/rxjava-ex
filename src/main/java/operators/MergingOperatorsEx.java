package operators;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MergingOperatorsEx {

    public static void main(String[] args) throws InterruptedException {

        //executeMerge();
        //executeMergeWith();
        //executeMergeIterable();

        /*executeMergeInfinite();
        Thread.sleep(3000);*/

        //executeFlatMap();
        executeMapVsFlatMap();

        //executeFlatMap1();
        //Thread.sleep(12000);

        /*executeFlatMap2();
        Thread.sleep(12000);*/

        //executeFlatMap3();

        //executeFlatMap4();
    }

    // Merge --------------------------------------------------------------------------------
    public static void executeMerge() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<String> source2 = Observable.just("Zeta", "Eta", "Theta");

        Observable.merge(source1, source2)
                .subscribe(item -> System.out.println(item));
    }

    public static void executeMergeWith() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<String> source2 = Observable.just("Zeta", "Eta", "Theta");

        source1.mergeWith(source2)
                .subscribe(item -> System.out.println(item));
    }

    public static void executeMergeIterable() {
        Observable<String> source1 = Observable.just("Alpha", "Beta");
        Observable<String> source2 = Observable.just("Zeta", "Eta");
        Observable<String> source3 = Observable.just("Apple", "Ball");
        Observable<String> source4 = Observable.just("Elephant", "Fly");
        List<Observable<String>> sources = Arrays.asList(source1, source2, source3, source4);

        Observable.merge(sources)
                .subscribe(item -> System.out.println(item));
    }


    public static void executeMergeInfinite() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS)
                                                .map(l -> l + 1)
                                                .map(l -> "Source1: " + l + " seconds");

        Observable<String> source2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                                                .map(l -> (l + 1) * 500)
                                                .map(l -> "Source2: " + l + " milliseconds");
        Observable.merge(source1, source2)
                .subscribe(time -> System.out.println(time));
    }


    // FlatMap ------------------------------------------------------------------------------------
    public static void executeFlatMap() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        source.flatMap(str -> Observable.fromArray(str.split("")))
                .subscribe(c -> System.out.println(c));
    }


    public static void executeMapVsFlatMap() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        //Map
        source.map(item -> item.toUpperCase())
                .subscribe(res -> System.out.println("Map: " + res));           // returns T

        //FlatMap
        source.flatMap(item -> Observable.fromArray(item.split("")))      // returns Observable<T>
                .map(c -> c.toUpperCase())
                .subscribe(c -> System.out.println("Flatmap: " + c));
    }

    public static void executeFlatMap1() {
        Observable<Integer> intervalArgs = Observable.just(2,3,10,7);

        intervalArgs.flatMap(
                        i -> Observable.interval(i, TimeUnit.SECONDS)
                        // map inside flatMap
                        // i = intervalArg, i2 = interval starting from 0
                        .map(i2 -> i + "s interval: " + ((i2 + 1) * i) + " seconds elapsed")
                )
                .subscribe(res -> System.out.println(res));
    }


    public static void executeFlatMap2() {
        Observable<Integer> intervalArgs = Observable.just(2,3,10,7);

        intervalArgs.flatMap(
                        i -> {
                            if(i == 0)      // if-else inside flatMap
                                return Observable.empty();
                            else
                                return Observable.interval(i, TimeUnit.SECONDS)
                                        .map(i2 -> i + "s interval: " + ((i2 + 1) * i) + " seconds elapsed");
                        }
                )
                .subscribe(res -> System.out.println(res));
    }


    public static void executeFlatMap3() {
        Observable<String> source = Observable.just(
                "1234/2345/APPLE",
                "1212/2323/3434/BALL",
                "5656/5656/CAT/7878"
        );

        source.flatMap(s -> Observable.fromArray(s.split("/")))
                .filter(s -> s.matches("[0-9]+"))
                .map(Integer::parseInt)
                .subscribe(res -> System.out.println(res));
    }


    public static void executeFlatMap4() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        source.flatMap(s -> Observable.fromArray(s.split("")),
                        (s,r) -> s + "-" + r        // 2nd argument, BiFunction<T,U,R>
                    )
                    .subscribe(System.out::println);
    }
}