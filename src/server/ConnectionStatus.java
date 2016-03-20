package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread threadConnectionRunTester;
    private long ThreadID;

    public ConnectionStatus(Boolean stopping){
        shutdown = stopping;
    }

    public void startConnecionStatusCheck() {
        if(threadConnectionRunTester != null){
            return;
        }else {
            threadConnectionRunTester = new Thread();
            threadConnectionRunTester.start();
        }
    }
    public void killConnecionStatusCheck(){
        if (true) {

            System.out.println("DEAD" + threadConnectionRunTester);

            return;
        }
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
                while(!shutdown) {
                    try {
                        System.out.println("Thread id: " + threadConnectionRunTester.getId());
                        threadConnectionRunTester.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        System.out.println("InterruptedException: thread "+ ThreadID +"(SLEEP) - "  + "Connection status");
                    }
                }
        }
}
