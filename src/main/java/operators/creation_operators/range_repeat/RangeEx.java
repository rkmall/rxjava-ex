package operators.creation_operators.range_repeat;

import data.model.Task;
import io.reactivex.rxjava3.core.Observable;

public class RangeEx {

    public static void main(String[] args) {
        RangeEx rangeEx = new RangeEx();
        rangeEx.executeRange();
    }

    public void executeRange() {
        Observable.range(0, 5)
                .map(i -> new Task(i, "New task", false))
                .subscribe(task ->  System.out.println("onNext: " + task.getId() + "." + task.getDesc()));
    }
}
