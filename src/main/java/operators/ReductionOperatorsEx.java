package operators;

import io.reactivex.rxjava3.core.Observable;
import org.apache.tools.ant.taskdefs.Zip;

public class ReductionOperatorsEx {

    public static void main(String[] args) {

        //executeReduce();
        //executeCount();
        //executeAll();
        //executeAny();

        executeContains();
    }

    public static void executeReduce() {
        Observable.just(5,3,6,8,2)
                .reduce((total, next) -> total + next)
                .subscribe(result -> System.out.println(result));
    }

    public static void executeCount() {
        Observable.just(1,2,3,4,5)
                .count()
                .subscribe(count -> System.out.println(count));
    }

    public static void executeAll() {
        Observable.just(1,2,3,4,5)
                .all(i -> i < 10)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeAny() {
        Observable.just(15,12,13,4,15)
                .any(i -> i < 10)
                .subscribe(res -> System.out.println(res));
    }

    public static void executeContains() {
        Observable.range(1, 50)
                .contains(55)
                .subscribe(res -> System.out.println(res));
    }
}
