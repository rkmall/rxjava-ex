package operators;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransformationOperatorsEx {

    public static void main(String[] args) {

        //executeMap();
        //executeMap1();

        //executeStartWith();
        //executeDefaultIfEmpty();
        //executeSwitchIfEmpty();
        //executedSorted();

        executeDelay();

        //executeScan();
    }

    public static Single<Response> getResponse() {
        MessageDefinition messageDefinition1 = new MessageDefinition(
                "mob", "path/mob/endpoint", "mob1", 3);
        MessageDefinition messageDefinition2 = new MessageDefinition(
                "desk", "path/desk/endpoint", "desk1", 5);

        Response response = new Response();
        response.messages = new ArrayList<>();
        response.messages.add(messageDefinition1);
        response.messages.add(messageDefinition2);
        return Single.just(response);
    }

    public static void executeMap() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        Observable.just("1/3/2016", "1/5/2016", "1/8/2016")
                .map(s -> LocalDate.parse(s, formatter))    // turn String into LocalData object
                .subscribe(i -> System.out.println(i));
    }


    public static void executeMap1() {
        getResponse()
                .map(response -> {
                    List<Message> messages = new ArrayList<>();
                    for(MessageDefinition definition : response.messages) {
                        Message message = new Message();
                        message.messageKey = definition.messageKey;
                        message.id = definition.id;
                        message.pathId = definition.pathId;
                        message.token = definition.token;
                        messages.add(message);
                    }
                    return messages;
                })
                .subscribe(list -> {
                   for(Message message : list) {
                       System.out.println(message.messageKey + "," + message.pathId + "," + message.id + ", " + message.token);
                   }
                });
    }


    public static void executeStartWith() {
        Observable<String> menu = Observable.just("Coffee", "Tea", "Espresso", "Latte");

        menu.startWith(Single.just("---Menu Items---"))
                .subscribe(s -> System.out.println(s));
    }

    public static void executeDefaultIfEmpty() {
        Observable<String> menu = Observable.just("Coffee", "Tea", "Espresso", "Latte");

        menu.filter(item -> item.startsWith("M"))
                .defaultIfEmpty("NONE")
                .subscribe(s -> System.out.println(s));
    }

    public static void executeSwitchIfEmpty() {
        Observable<String> menu = Observable.just("Coffee", "Tea", "Espresso", "Latte");

        menu.filter(item -> item.startsWith("M"))
                .switchIfEmpty(Observable.just("Water", "Juice", "Soda"))
                .subscribe(s -> System.out.println(s));
    }


    public static void executedSorted() {
        Observable.just(5,2,4,1,6,7)
                .sorted()
                .subscribe(i -> System.out.println(i));
    }


    public static void executeDelay() {
        Observable.just("Coffee", "Tea", "Espresso", "Latte")
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribe(s -> System.out.println(s));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void executeScan() {
        Observable.just(1,2,3,4)
                .scan((acc, next) -> acc + next)
                .subscribe(res -> System.out.println(res));
    }
}

class Response {
    List<MessageDefinition> messages;
}

class MessageDefinition {
    String messageKey;
    String pathId;
    String token;
    int id;

    public MessageDefinition(String messageKey, String pathId, String token, int id) {
        this.messageKey = messageKey;
        this.pathId = pathId;
        this.token = token;
        this.id = id;
    }
}

class Message {
    String messageKey;
    String pathId;
    String token;
    int id;
}


