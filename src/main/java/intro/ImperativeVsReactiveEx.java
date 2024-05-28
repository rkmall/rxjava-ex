package intro;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.BiFunction;

import java.util.function.BinaryOperator;

import static io.reactivex.rxjava3.core.Observable.just;

public class ImperativeVsReactiveEx {

    public static void main(String[] args) {

        //imperativeAddition(2, 3);

        int[] ints = {2,3};
        //reactiveAddition(ints);

        reactiveAdditionTestWithCreate(ints);
    }

    public static void imperativeAddition(int a, int b) {
        int sum = a + b;
        System.out.println(sum);        // 5

        a = 22;
        System.out.println(sum);        // 5
    }


    public static void reactiveAddition(int[] ints) {
        BiFunction<Integer, Integer, Integer> reduceFun = (id, sum) -> id + sum;

        Observable<Integer> source = Observable.defer(() -> Observable.just(ints[0], ints[1]));
        source.reduce(reduceFun).subscribe(System.out::println);        // 5

        ints[0] = 22;
        source.reduce(reduceFun).subscribe(System.out::println);        // 25

        ints[1] = 8;
        source.reduce(reduceFun).subscribe(System.out::println);        // 30
    }

    public static void reactiveAdditionTestWithCreate(int[] ints) {
        BiFunction<Integer, Integer, Integer> reduceFun = (id, sum) -> id + sum;

        Observable<Integer> source = Observable.create( emitter -> {
            for (Integer integer : ints)
                emitter.onNext(integer);
            emitter.onComplete();
        });

        source.reduce(reduceFun).subscribe(System.out::println);        // 5

        ints[0] = 22;
        source.reduce(reduceFun).subscribe(System.out::println);        // 25

        ints[1] = 8;
        source.reduce(reduceFun).subscribe(System.out::println);        // 30
    }
}
