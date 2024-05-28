package operators.filter_sort_operators;

import io.reactivex.rxjava3.core.Observable;

public class ElementAtEx {

    public static void main(String[] args) {

        executeElementAt();
    }

    public static void executeElementAt() {
        Observable.just("Alpha", "Beta", "Zeta", "Eta", "Gamma")
                .elementAt(2)
                .subscribe((i -> System.out.println(i)));
    }
}
