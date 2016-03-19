package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iconnectionRunTester;
    Controller controller = new Controller();

    public ConnectionStatus(Boolean stopping){
        shutdown = stopping;
    }

    public void startConnecionStatusCheck() {
        if(iconnectionRunTester != null){
            return;
        }else {
            iconnectionRunTester = new Thread(this);
            iconnectionRunTester.start();
        }
    }
    public void killConnecionStatusCheck(){
        if(iconnectionRunTester == null){
            return;
        }else {
            shutdown = true;
            long ThreadID = iconnectionRunTester.getId();
            System.out.print("Kill: " + ThreadID);
            iconnectionRunTester.interrupt();
        }
    }

    @Override
    public void run() {
                while(!shutdown) {
                    try {
                        System.out.println("Thread id: " + iconnectionRunTester.getId());
                        iconnectionRunTester.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
        }
}
