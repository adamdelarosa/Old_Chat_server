package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iThread;

    public ConnectionStatus(Boolean stopping) {
        shutdown = stopping;

    }

    public void startConnecionStatusCheck() {
        iThread = new Thread(this);
        iThread.start();
    }

    public void killConnecionStatusCheck() {

        shutdown = false;
        //threadConnectionRunTester.interrupt();
        //System.out.println("Kill: " + threadID + " - Connection status");

/*
        if(threadConnectionRunTester.isAlive()){
           // System.out.println("Connection status: Thread " + ThreadID + " - Running.");
            System.out.println("threadConnectionRunTester - run status check first.");
            return;
        }else {
            threadID = threadConnectionRunTester.getId();
            shutdown = true;
            threadConnectionRunTester.interrupt();
            System.out.println("Kill: " + threadID + " - Connection status");
        }
*/
    }

    @Override
    public void run() {
        while (shutdown) {
            try {
                System.out.println("Thread id: ");
                iThread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
