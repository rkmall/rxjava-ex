package observerpattern.pushvspull;

public class PushCallBackEx {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Running on main thread");

        Runnable r = () -> new PushCallBackEx().runningAsync(new PushCallBack() {
            @Override
            public void pushData(String data) {
                System.out.println("Callback data: " + data);
            }

            @Override
            public void pushComplete() {
                System.out.println("Callback done");
            }

            @Override
            public void pushError(Exception ex) {
                System.out.println("Callback, exception caught: " + ex);
            }
        });

        new Thread(r).start();
        Thread.sleep(2000);
        System.out.println("Main done!");
    }

    public void runningAsync(PushCallBack callBack) {
        System.out.println("Running on background thread");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callBack.pushData("Data 1");
        callBack.pushData("Data 2");
        callBack.pushData("Data 3");
        callBack.pushError(new RuntimeException("Runtime exception!"));
        callBack.pushComplete();
    }
}

interface PushCallBack {
    void pushData(String data);
    void pushComplete();
    void pushError(Exception ex);
}