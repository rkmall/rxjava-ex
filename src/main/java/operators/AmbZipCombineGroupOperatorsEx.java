package operators;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.GroupedObservable;
import observables.MaybeEx;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmbZipCombineGroupOperatorsEx {

    public static void main(String[] args) throws InterruptedException {

        //executeAmb();
        //Thread.sleep(3000);

        //executeZip();
        //executeZip1();
        //executeZip2();
        //executeZip3();
        /*executeZip4();
        Thread.sleep(6000);*/

        executeZip5();

        /*executeCombineLatest();
        Thread.sleep(3000);*/
        /*executeWithLatestFrom();
        Thread.sleep(3000);*/

        //executeGroupBy();
        //executeGroupBy1();
        //executeGroupBy2();
    }

    // Amb
    public static void executeAmb() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .map(l -> l + 1)
                .map(l -> "Source1: " + l + "seconds");

        Observable<String> source2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(l -> (l + 1) * 500)
                .map(l -> "Source2: " + l + "seconds");

        Observable.amb(Arrays.asList(source1,source2))
                .subscribe(res -> System.out.println(res));
    }

    // Zip
    public static void executeZip() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Integer> source2 = Observable.range(1,6);

        Observable.zip(source1, source2, (str, i ) -> str + "-" + i)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeZip1() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Integer> source2 = Observable.range(1,6);

        source1.zipWith(source2, (str, i ) -> str + "-" + i)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeZip2() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Character> source2 = Observable.just('A', 'B', 'G', 'D', 'E', 'D');
        Observable<Integer> source3 = Observable.range(1,6);

        Observable.zip(source1,
                        source2,
                        source3,
                        (str, c, i) -> str + "-" + c + "-" + i)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeZip3() {
        Observable<String> source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Character> source2 = Observable.just('A', 'B', 'G', 'D', 'E', 'D');
        Observable<Integer> source3 = Observable.range(1,6);

        //
        Observable.zip(Arrays.asList(source1,
                        source2,
                        source3),
                        (Function<Object[], Object>) objects -> {
            List<Object> res = new ArrayList<>();

            for(Object obj : objects) {
                res.add(obj);
            }

            res.forEach(System.out::println);
            return res.get(0) + "-" + res.get(1) + "-" + res.get(2);
        })
                .subscribe(res -> System.out.println(res));
    }

    public static void executeZip4() {
        Observable<String> strings = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);

        Observable.zip(strings,
                seconds,
                (str, sec ) -> str
        )
                .subscribe(res -> System.out.println("Received: " + res + " at " + LocalTime.now()));
    }

    public static void executeZip5() {
        List<String> list1 = Arrays.asList("One", "Two", "Three", "Four");
        List<String> list2 = Arrays.asList("Apple", "Ball", "Cat", "Dog");

        Observable<String> source1 = Observable.fromIterable(list1);
        Observable<String> source2 = Observable.fromIterable(list2);

        Observable.zip(
                source1,
                source2,
                (src1, scr2) -> src1 + scr2
        )
                .subscribe(res -> System.out.println(res));
    }

    // CombineLatest
    public static void executeCombineLatest() {
        Observable<Long> source1 = Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS);

        Observable.combineLatest(source1,
                                source2,
                                (time1, time2) -> "Source1: " + time1 + " Source2: " + time2
                )
                .subscribe(res -> System.out.println(res));
    }

    public static void executeWithLatestFrom() {
        Observable<Long> source1 = Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS);

        source1.withLatestFrom(source2, (time1, time2) -> "Source1: " + time1 + " Source2: " + time2 )
                .subscribe(res -> System.out.println(res));
    }

    // Group
    public static void executeGroupBy() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Observable<GroupedObservable<Integer, String>> byLength= source.groupBy(s -> s.length());

        byLength.flatMapSingle(grp -> grp.toList())
                .subscribe(res -> System.out.println(res));
    }


    public static void executeGroupBy1() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        source.groupBy(s -> s.length())
                .flatMapSingle( grp ->
                    grp.reduce(
                            "",
                            (x,y) -> x.equals("") ? y : x + ", " + y
                    ).map(s -> grp.getKey() + ": " + s)
                ).subscribe(res -> System.out.println(res));
    }

    public static void executeGroupBy2() {
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        source.groupBy(s -> s.length())
                .flatMap(grp ->
                        Observable.fromSingle(
                            grp.reduce(
                                    "",
                                    (x,y) -> x.equals("") ? y : x + ", " + y
                            ).map(s -> grp.getKey() + ": " + s)
                        )
                ).subscribe(res -> System.out.println(res));
    }
}
