package server;

public class ConnectionStatus implements Runnable {

    public boolean iconnectionRunTesterSwitch;
    private Thread iconnectionRunTester;



    public void startConnecionStatusCheck() {
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();

    }
    public void killConnecionStatusCheck(boolean booleanIconnectionRunTesterSwitch){
        System.out.print(booleanIconnectionRunTesterSwitch);
        iconnectionRunTesterSwitch = booleanIconnectionRunTesterSwitch;
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
