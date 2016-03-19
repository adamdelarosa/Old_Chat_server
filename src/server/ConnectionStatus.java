package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iconnectionRunTester;
    private long ThreadID;

    public ConnectionStatus(Boolean stopping){
        shutdown = stopping;
    }

    public void startConnecionStatusCheck() {
        if(iconnectionRunTester != null){
            return;
        }else {
            this.iconnectionRunTester = new Thread(this);
            this.iconnectionRunTester.start();
        }
    }
    public void killConnecionStatusCheck(){
        if(iconnectionRunTester == null){
            return;
        }else {
            ThreadID = iconnectionRunTester.getId();
            shutdown = true;
            iconnectionRunTester.interrupt();
            System.out.println("Kill: " + ThreadID + " - Connection status");
        }
    }

    @Override
    public void run() {
                while(!shutdown) {
                    try {
                        System.out.println("Thread id: " + iconnectionRunTester.getId());
                        iconnectionRunTester.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        System.out.println("InterruptedException: thread "+ ThreadID +"(SLEEP) - "  + "Connection status");
                    }
                }
        }
}
