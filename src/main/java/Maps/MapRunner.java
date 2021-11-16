package Maps;

import data.User;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class MapRunner {

    public static void main(String[] args) {

        //---------------------------------map()-------------------------------//
        Observable<User> sourceUser = getSourceUser();

        sourceUser
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Throwable {
                        user.setEmail(String.format("%s@in.com", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .subscribe(new Observer<User>() {       // create Observer onsite
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        System.out.println("onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getEmail());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("All items emitted");
                    }
                });

    }


    // Method returns Observable<User>
    public static Observable<User> getSourceUser() {

        String[] names = {"Jim", "Beck", "Chris", "Dinesh", "Elon"};

        List<User> users = new ArrayList<>();       // observable source

        for(String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("Male");
            users.add(user);
        }

        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {

                for(User user : users) {
                    if(!emitter.isDisposed()) {
                        emitter.onNext(user);
                    }
                }

                if(!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }
}
