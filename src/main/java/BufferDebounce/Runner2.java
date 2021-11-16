package BufferDebounce;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.List;

public class Runner2 {

    public static void main(String[] args) {


        //-----------------------------buffer()-------------------------------------//
        Observable<Integer> integerObservable = Observable.fromArray(1,2,3,4,5,6,7,8,9);

        integerObservable
                .buffer(3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        System.out.println("onNext");
                        for(Integer val : integers) {
                            System.out.println(val);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {}

                    @Override
                    public void onComplete() {}
                });

        //------------------------------debounce()----------------------------------------//


    }
}
