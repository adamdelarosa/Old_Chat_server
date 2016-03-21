package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iThread;
    private Controller startButton;

    public ConnectionStatus(Boolean stopping,Controller startbutton) {
        shutdown = stopping;
        startButton = startbutton;

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
                startButton.connectionStatusStart.setDisable(true);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
