package operators.filter_sort_operators;

import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;

public class TakeEx {

    public static void main(String[] args) {

        TakeEx takeEx = new TakeEx();
        //takeEx.executeTake();
        //takeEx.executeTakeLast();
        //takeEx.executeTakeWhile();
        takeEx.executeFirst();
    }

    public void executeTake() {
        Observable.fromArray(new String[] {"C", "C++", "Java", "Kotlin", "Python"})
                .take(3)      // take first 3 items
                .subscribe(s -> System.out.println("onNext: " + s));
    }

    public void executeTakeLast() {
        Observable.fromArray(new String[] {"C", "C++", "Java", "Kotlin", "Python"})
                .takeLast(2)  // take last 2 items
                .subscribe(s -> System.out.println("onNext: " + s));
    }

    public void executeTakeWhile() {
        Observable.fromIterable(DataSource.prepareTasks())
                .takeWhile(task -> task.isComplete())       // predicate
                .subscribe(s -> System.out.println("onNext: " + s));
    }

    public void executeFirst() {
        List<String> list = Arrays.asList("C", "C++", "Java", "Kotlin", "Python");
        Observable<List<String>> source = Observable.create(emitter -> emitter.onNext(list));

        source.map(l -> l.get(0))
                .subscribe(s -> System.out.println(s));
    }
}
