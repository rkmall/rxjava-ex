package transformers;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;

import java.util.ArrayList;

public class ObservableTransformersEx {

    public static void main(String[] args) {
        //withOutTransformer();
        withTransformer();
    }

    public static void withOutTransformer() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .map(s -> s.toString())
                .collect(ArrayList::new, ArrayList::add)
                .subscribe(res -> System.out.println(res));

        Observable.range(1, 10)
                .map(i -> i.toString())
                .collect(ArrayList::new, ArrayList::add)
                .subscribe(res -> System.out.println(res));
    }

    public static <T> ObservableTransformer<T, ArrayList<String>> toArrayListString() {
        return upstream -> {
            return upstream.map(item -> item.toString())
                    .collect(ArrayList<String>::new, ArrayList::add)
                    .toObservable();    // must turn Single to Observable
        };
    }

    public static void withTransformer() {
        Observable.just("Alpha", "Beta", "Gamma", "Delta")
                .compose(toArrayListString())
                .subscribe(res -> System.out.println(res));

        Observable.range(1, 10)
                .compose(toArrayListString())
                .subscribe(res -> System.out.println(res));
    }
}
