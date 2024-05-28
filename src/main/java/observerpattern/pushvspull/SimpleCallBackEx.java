package observerpattern.pushvspull;

public class SimpleCallBackEx {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Running on main thread");

        Runnable r = () -> new SimpleCallBackEx().runningAsync(
                () -> System.out.println("Callback called")
        );

        Thread t = new Thread(r);
        t.start();
        Thread.sleep(2000);
        System.out.println("Main done!");
    }

    public void runningAsync(SimpleCallBack callback) {
        System.out.println("Running on background thread");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.call();
    }
}

interface SimpleCallBack {
    void call();
}
