package operators.filter_sort_operators;

import data.source.DataSource;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SkipEx {

    public static void main(String[] args) {

        SkipEx skipEx = new SkipEx();
        skipEx.executeSkip();
        skipEx.executeSkipLast();
    }

    public void executeSkip() {
        Observable.fromArray(new String[] {"C", "C++", "Java", "Kotlin", "Python"})
                .skip(3)            // skip first 3 items i.e. {C, C++, Java}
                .subscribe(s -> System.out.println(s));
    }

    public void executeSkipLast() {
        Observable.fromArray(new String[] {"C", "C++", "Java", "Kotlin", "Python"})
                .skipLast(2)                                // skip last 2 items i.e. {Kotlin, Python}
                .subscribe(s -> System.out.println(s));
    }
}
