package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread threadConnectionRunTester  = new Thread();
    private long ThreadID;


    public void startConnecionStatusCheck() {
        System.out.println(threadConnectionRunTester.isAlive());
        this.threadConnectionRunTester.setName("connectionRunTester");
        this.threadConnectionRunTester.start();
        System.out.println(this.threadConnectionRunTester.isAlive());
    }
    public void killConnecionStatusCheck(){
        System.out.println(threadConnectionRunTester.isAlive());
        if (!threadConnectionRunTester.isAlive()) {
            System.out.println(threadConnectionRunTester.isAlive());
            System.out.println("Can't run killConnecionStatusCheck - No thread running.");
            System.out.println("Thread status: " + "'" + threadConnectionRunTester + "'.");
            return;
        }else{
            System.out.println("yooyo");
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
                        System.out.println("Thread id: " + threadConnectionRunTester.getId() + "-  '" + threadConnectionRunTester.getName() + "'.");
                        threadConnectionRunTester.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        System.out.println("InterruptedException: thread "+ ThreadID +"(SLEEP) - "  + "Connection status");
                    }
                }
        }
}
