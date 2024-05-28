package operators.creation_operators.create;


import data.model.Task;
import data.source.DataSource;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.List;

public class CreateEx {

    public static void main(String[] args) {

        CreateEx createEx = new CreateEx();
        //createEx.executeCreateSingleTask();
        createEx.executeCreateTasks();
    }

    private List<Task> tasks = DataSource.prepareTasks();

    // Single element observable
    public void executeCreateSingleTask() {
        Observable<Task> singleTaskObservable = Observable
                .create((ObservableOnSubscribe<Task>) emitter -> {
                    if(!emitter.isDisposed()) {
                        emitter.onNext(tasks.get(0)); // single task (last task)
                        emitter.onComplete();         // onComplete() called immediately
                    }                                 // since single task
                });

        singleTaskObservable
                .subscribe(task -> System.out.println("onNext: " + task.getDesc()),
                        ex -> System.out.println(ex),
                        () -> System.out.println("onComplete done!"));
    }


    //  List elements observable
    public void executeCreateTasks() {
        Observable<Task> listTaskObservable = Observable
                .create(emitter -> {
                    for(Task task : tasks) {
                        if(!emitter.isDisposed()) {
                            emitter.onNext(task);
                        }
                    }
                    emitter.onComplete();   // onComplete() called after all list items emitted
                });

        listTaskObservable
                .subscribe(task -> System.out.println("onNext: " + task.getDesc()));
    }
}
