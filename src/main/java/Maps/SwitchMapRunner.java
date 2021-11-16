package Maps;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

import java.util.Arrays;
import java.util.List;

public class SwitchMapRunner {

    public static void main(String[] args) {

        getOriginalObservable()
                .switchMap(new Function<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> apply(final Integer integer)  {
                        return getModifiedObservable(integer);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static Observable<Integer> getOriginalObservable() {
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);

        return Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) {
                        for(Integer integer : integers) {

                            if (!emitter.isDisposed()) {
                                emitter.onNext(integer);
                            }
                        }

                        if(!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }

                });
    }

    private static Observable<Integer> getModifiedObservable(final Integer integer) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws InterruptedException {
                emitter.onNext((integer * 2));
                emitter.onComplete();
            }
        });
    }
}
