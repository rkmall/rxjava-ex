package operators;

import data.model.Task;
import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionOperatorsEx {

    public static void main(String[] args) {

        //executeToList();
        //executeToList1();
        //executeToSortedList();

        //executeToMap();
        //executeToMap1();
        //executeMultiMap();

        executeCollect();

    }


    public static void executeToList() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .toList()
                .subscribe(res -> System.out.println(res));
    }

    public static void executeToList1() {
        Observable.range(1, 5)
                .map(i -> {
                    Task task = new Task(i, "Task no: " + i, false);
                    return task;
                })
                .toList()
                .subscribe(taskList -> {
                    taskList.forEach(task -> System.out.println(task));
                });
    }

    public static void executeToSortedList() {
        Observable.just(5,1,8,7,6,9,0,4)
                .toSortedList()
                .subscribe(list -> System.out.println(list));
    }

    public static void executeToMap() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .toMap(s -> s.charAt(0))
                .subscribe(res -> System.out.println(res));
    }

    public static void executeToMap1() {
        Observable.fromIterable(DataSource.prepareTasks())
                .toMap(
                        task -> task.getId(),
                        task -> task.getDesc(),
                        ConcurrentHashMap::new
                )
                .subscribe(res -> {
                    res.forEach((k,v) -> System.out.println(k + ": " + v));
                });
    }

    public static void executeMultiMap() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .toMultimap(s -> s.length())
                .subscribe(res -> System.out.println(res));
    }

    public static void executeCollect() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .collect(
                        HashSet::new,   // provide the container (Supplier)
                        HashSet::add    // provide the collector (BiConsumer)
                )
                .subscribe(res -> System.out.println(res));
    }

}
