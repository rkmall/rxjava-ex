package operators.creation_operators.from_just;

import data.model.Task;
import data.source.DataSource;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.Callable;

public class FromEx {

    public static void main(String[] args) {

        FromEx fromEx = new FromEx();
        //fromEx.executeFromArray();
        //fromEx.executeFromIterable();
        //fromEx.executeFromCallable();
        fromEx.executeFromPublisher();
    }

    public void executeFromArray() {
        String[] languages = new String[] {"C", "C++", "Java", "Kotlin", "Python"};
        Observable.fromArray(languages)
                .subscribe(s -> System.out.println("onNext: " + s));
    }


    public void executeFromIterable() {
        Observable.fromIterable(DataSource.prepareTasks())
                .subscribe(task -> System.out.println("onNext: " + task.getDesc()));
    }

    public void executeFromCallable() {
        List<Task> dataSource = DataSource.prepareTasks();
        Observable<List<Task>> callable = Observable
                .fromCallable(new Callable<List<Task>>() {
                    @Override
                    public List<Task> call() throws Exception {
                        return dataSource;
                    }
                });

        callable.subscribe(list -> {
            for(Task task : list) {
                System.out.println("onNext: " + task);
            }
        });

        dataSource.set(0, new Task(1, "Changed!", false));

        callable.subscribe(list -> {
            for(Task task : list) {
                System.out.println("onNext: " + task);
            }
        });
    }

    public void executeFromPublisher() {
        Observable<String> source = Observable.fromPublisher(new Flowable<String>() {
            @Override
            protected void subscribeActual(@NonNull Subscriber<? super String> subscriber) {
                while (true) {
                    subscriber.onNext("I am infinte");
                }
            }
        });
        source.subscribe(s -> System.out.println(s));
    }
}
