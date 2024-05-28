package operators.creation_operators.from_just;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

import java.util.Arrays;

public class JustEx {

    public static void main(String[] args) {
        JustEx justEx = new JustEx();
        justEx.executeJustInt();
    }

    public void executeJustInt() {
        Observable.just(1,2,3,4,5,6,7,8,9,10)       // just is limited to max 10 items
                .subscribe(integer -> System.out.println("onNext: " + integer));
    }

    public void executeJustArrays() {
        Integer[] arr1 = {1,2,3,4,5};
        Integer[] arr2 = {10,20,30,40,50};
        Integer[] arr3 = {100,200,300,400,500};
        Observable.just(arr1, arr2, arr3)
                .subscribe(new DisposableObserver<Integer[]>() {
                    @Override
                    public void onNext(Integer[] integers) {
                        System.out.println("onNext: " + Arrays.toString(integers));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }
                });
    }
}
