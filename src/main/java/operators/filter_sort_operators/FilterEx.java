package operators.filter_sort_operators;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilterEx {

    public static void main(String[] args) {
        FilterEx filterEx = new FilterEx();
        filterEx.executeFilter();
    }

    public void executeFilter() {
        List<String> list = Arrays.asList("C", "C++", "Java", "Python");
        Observable.fromIterable(list)
                .filter(s -> s.startsWith("C"))
                .subscribe(s -> System.out.println("onNext: " + s));
    }
}
