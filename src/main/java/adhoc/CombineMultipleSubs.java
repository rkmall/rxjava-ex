package adhoc;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Condition: Run two network requests in parallel and then run some
 * code at the end of each network request. Then, at the end of processing
 * of each network run additional.
 * <br></br>
 * Modelled like this:
 * - GET -> /users (run unique code to this request independently once the request is done).
 * - GET -> /groups (run unique code to this request independently once the request is done).
 * - Both requests are done, now run some unique code independent of the request processing.
 */
public class CombineMultipleSubs {

    public static void main(String[] args) {

        // This emulates the first network call
        Observable<List<String>> o1 = Observable.just(Arrays.asList("user1", "user2"));

        // When data arrive, you may transform it
        Observable<List<String>> m1 = o1.map(new Function<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> users) throws Throwable {
                return users;
            }
        });

        // This emulates the first network call
        Observable<List<String>> o2 = Observable.just(Arrays.asList("group1", "group2"));

        // When data arrive, you may transform it
        Observable<List<String>> m2 = o2.map(new Function<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> groups) throws Throwable {
                return groups;
            }
        });


        Observable<Map<String, List<String>>> result =
               Observable.zip(m1, m2, new BiFunction<List<String>, List<String>, Map<String, List<String>>>() {
                   @Override
                   public Map<String, List<String>> apply(List<String> users, List<String> groups) throws Throwable {
                       Map<String, List<String>> result = new HashMap<>();
                       for (String user: users) {
                           result.put(user, groups);
                       }
                       return result;
                   }
               });

        result.subscribe(new Consumer<Map<String, List<String>>>() {
            @Override
            public void accept(Map<String, List<String>> stringListMap) throws Throwable {
                for (String user: stringListMap.keySet()) {
                    System.out.println("User: " + user + ", groups: " + stringListMap.get(user));
                }
            }
        });
    }
}
