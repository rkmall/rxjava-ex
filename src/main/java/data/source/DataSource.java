package data.source;

import data.model.Address;
import data.model.Task;
import data.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String TAG = "MyMain";

    public static List<Task> prepareTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Wake up at 7", true));
        tasks.add(new Task(2, "Go to shopping", false));
        tasks.add(new Task(3, "Prepare breakfast", true));
        tasks.add(new Task(4, "Study RxJava", true));
        tasks.add(new Task(5, "Go for running", false));
        return tasks;
    }

    public static List<User> prepareMaleUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Jim", "male"));
        users.add(new User("Kale", "male"));
        users.add(new User("Jiwan", "male"));
        users.add(new User("Hari", "male"));
        return users;
    }

    public static List<User> prepareFemaleUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Kate", "male"));
        users.add(new User("Tina", "male"));
        users.add(new User("Veronica", "male"));
        users.add(new User("Priya", "male"));
        return users;
    }

    public static List<Address> prepareAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("1600 Amphitheatre Parkway, Mountain View, CA 94043"));
        addresses.add(new Address("2300 Traverwood Dr. Ann Arbor, MI 48105"));
        addresses.add(new Address("500 W 2nd St Suite 2900 Austin, TX 78701"));
        addresses.add(new Address("355 Main Street Cambridge, MA 02142"));
        return addresses;
    }
}
