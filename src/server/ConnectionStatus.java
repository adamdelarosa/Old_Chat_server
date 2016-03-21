package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iThread;
    private Controller startButton,stopButton;

    public ConnectionStatus(Boolean stopping,Controller startbutton,Controller stopbutton) {
        shutdown = stopping;
        startButton = startbutton;
        stopButton = stopbutton;

    }

    public void startConnecionStatusCheck() {
        stopButton.connectionStatusStop.setDisable(false);
        iThread = new Thread(this);
        iThread.start();
    }

    public void killConnecionStatusCheck() {
        shutdown = false;
        startButton.connectionStatusStart.setDisable(false);
        stopButton.connectionStatusStop.setDisable(true);
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
