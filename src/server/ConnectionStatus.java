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
        startButton.connectionStatusStart.setDisable(false);
    }

    @Override
    public void run() {
        while (shutdown) {
            try {
                startButton.connectionStatusStart.setDisable(true);
                System.out.println("Thread id: ");
                iThread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
