package operators.creation_operators.range_repeat;


import data.model.Task;
import data.source.DataSource;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;


public class RepeatEx {

    public static void main(String[] args) {
        RepeatEx repeatEx = new RepeatEx();
        repeatEx.executeRepeat();
    }

    public void executeRepeat() {
        List<Task> taskList = DataSource.prepareTasks();

        Observable.fromIterable(taskList)
                .repeat(2)
                .subscribe(task -> System.out.println("onNext: " + task));
    }
}
