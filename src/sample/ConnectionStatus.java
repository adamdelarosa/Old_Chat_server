package sample;

public class ConnectionStatus implements Runnable {

    public boolean iconnectionRunTesterSwitch;
    private Thread iconnectionRunTester;

    public void test() {
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();
    }
    public boolean geticonnectionRunTesterSwitch(boolean iconnectionRunTesterSwitch){
        return iconnectionRunTesterSwitch;
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
