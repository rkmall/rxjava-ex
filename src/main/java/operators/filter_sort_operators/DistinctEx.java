package operators.filter_sort_operators;

import data.model.Task;
import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class DistinctEx {

    public static void main(String[] args) {

        //executeDistinct();
        executeDistinctUntilChanged();

    }

    public static void executeDistinct() {
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.prepareTasks())
                .distinct(new Function<Task, String>() {
                    @Override
                    public String apply(Task task) throws Exception {
                        return task.getDesc();
                    }
                });
        taskObservable.subscribe(task -> System.out.println("onNext: " + task));
    }

    public static void executeDistinctUntilChanged() {
        Observable.just(1,1,1,2,2,3,3,2,1,1)
                .distinctUntilChanged()
                .subscribe(i -> System.out.println(i));
    }

}
