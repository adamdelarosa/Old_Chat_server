package sample;

public class ConnectionStatus implements Runnable {

    private boolean iconnectionRunTesterSwitch;
    private Thread iconnectionRunTester;

    public void test() {
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();
    }

    @Override
    public void run() {
                do {
                    try {
                        System.out.println("Thread id: " + iconnectionRunTester.getId());
                        iconnectionRunTester.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } while (!iconnectionRunTesterSwitch);
        }
}
