package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread threadConnectionRunTester;
    private long threadID;

    public ConnectionStatus() {
    }

    public void startConnecionStatusCheck() {
        threadConnectionRunTester = new Thread(this
        );
        threadConnectionRunTester.start();
        threadID = threadConnectionRunTester.getId();
    }

    public void killConnecionStatusCheck() {
        if(threadConnectionRunTester.currentThread().isInterrupted()){
            System.out.println("alive " + this.threadID);
        }else{
            System.out.println("dead " + threadID);
        }
        //System.out.println(threadConnectionRunTester.getId());
        //System.out.println(Thread.currentThread().isAlive());
        //Thread.currentThread().interrupt();




        /*if(threadConnectionRunTester.isAlive()){
           // System.out.println("Connection status: Thread " + ThreadID + " - Running.");
            System.out.println("threadConnectionRunTester - run status check first.");
            return;
        }else {
            ThreadID = threadConnectionRunTester.getId();
            shutdown = true;
            threadConnectionRunTester.interrupt();
            System.out.println("Kill: " + ThreadID + " - Connection status");
        }*/
    }

    @Override
    public void run() {
        while (!shutdown) {
            try {
                System.out.println("Thread id: " + threadID);
                threadConnectionRunTester.sleep(1000);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + threadID + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
