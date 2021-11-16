package Maps;

import data.User;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlatMapConcatMapRunner {

    public static void main(String[] args) {

        //----------------------------flatMap---------------------------------//
        /*getSourceUser()
                .flatMap(new Function<User, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(User user) throws Throwable {

                        return getSourceAddress(user);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        System.out.println("onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getAddress());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done");
                    }
                });*/


        //----------------------------concatMap---------------------------------//
        getSourceUser()
                .concatMap(new Function<User, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(User user) throws Throwable {
                        return getSourceAddress(user);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        System.out.println("onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getAddress());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done");
                    }
                });



    }


    /**
     * Assume this is a network call
     * @param user
     * @return User with address added
     */
    public static Observable<User> getSourceAddress(final User user) {
        String[] addresses = new String[] {
                "260A, Harrow, Middlesex, HA2",
                "12 Wembley, Middlesex, WA4",
                "7 Blueberry, Leeds, LE1",
                "8 Capital, Bristol, BW15"
        };

        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {

                if(!emitter.isDisposed()) {
                    user.setAddress(addresses[new Random().nextInt(4)]);    // randomly set address

                    int sleepTime = new Random().nextInt(1000) + 150; // simulate network latency of around 1s
                    Thread.sleep(sleepTime);
                    emitter.onNext(user);
                    emitter.onComplete();
                }
            }
        });
     }


    /**
     * Assume this is a network call to fetch users
     * @return Users with name and gender but missing address
     */
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
