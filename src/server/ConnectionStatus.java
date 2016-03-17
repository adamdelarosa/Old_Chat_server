package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iconnectionRunTester;

    public ConnectionStatus(Boolean stopping){
        shutdown = stopping;
    }



    public void startConnecionStatusCheck() {
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();

    }
    public void killConnecionStatusCheck(){
        shutdown = true;
    }

    @Override
    public void run() {
                while(!shutdown) {
                    try {
                        System.out.println("Thread id: " + iconnectionRunTester.getId());
                        iconnectionRunTester.sleep(1000);
                        System.out.println(shutdown);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
        }
}
