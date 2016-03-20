package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread threadConnectionRunTester = new Thread();
    private long ThreadID;


    public void startConnecionStatusCheck() {
        threadConnectionRunTester = new Thread(this);
        threadConnectionRunTester.setName("connectionRunTester");
        threadConnectionRunTester.start();
}

    public void killConnecionStatusCheck() {
        if (threadConnectionRunTester.isAlive()){
            System.out.println("alive");
        }
            System.out.println("Dead");

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
        while (!shutdown) {
            try {
                System.out.println("Thread id: " + threadConnectionRunTester.getId() + "-  '" + threadConnectionRunTester.getName() + "'.");
                threadConnectionRunTester.sleep(1000);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + ThreadID + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
