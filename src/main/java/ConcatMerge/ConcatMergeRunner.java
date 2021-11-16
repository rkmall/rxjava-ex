package ConcatMerge;

import data.User;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class ConcatMergeRunner {

    public static void main(String[] args) {

        //----------------------------concat()-------------------------------//
        /*Observable
                .concat(getMaleObservable(), getFemaleObservable())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(User user) {
                        System.out.println(user.getName() + ", " + user.getGender());
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });*/

        //--------------------------------merge()---------------------------------//
        Observable
                .merge(getMaleObservable(), getFemaleObservable())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(User user) {
                        System.out.println(user.getName() + ", " + user.getGender());
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    private static Observable<User> getFemaleObservable() {

        String[] names = new String[]{"Lucy", "Scarlett", "April"};

        final List<User> users = new ArrayList<>();
        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("female");
            users.add(user);
        }
        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        for (User user : users) {
                            if (!emitter.isDisposed()) {
                                Thread.sleep(1000);     // simulate network latency
                                emitter.onNext(user);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                });
    }

    private static Observable<User> getMaleObservable() {
        String[] names = new String[]{"Mark", "John", "Trump", "Obama"};

        final List<User> users = new ArrayList<>();

        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("male");
            users.add(user);
        }
        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        for (User user : users) {
                            if (!emitter.isDisposed()) {
                                Thread.sleep(500);      // simulate network latency
                                emitter.onNext(user);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                });
    }
}
