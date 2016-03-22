package server;

public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iThread;
    private Controller startButton,stopButton,onlineOfflineText;

    public ConnectionStatus(Boolean stopping,Controller startbutton,Controller stopbutton,Controller onlineofflinetext) {
        shutdown = stopping;
        startButton = startbutton;
        stopButton = stopbutton;
        onlineOfflineText = onlineofflinetext;

    }

    public void startConnecionStatusCheck() {
        stopButton.connectionStatusStop.setDisable(false);
        iThread = new Thread(this);
        iThread.start();
        onlineOfflineText.connectionStatusActive.setText("ONLINE");
        onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
    }

    public void killConnecionStatusCheck() {
        shutdown = false;
        startButton.connectionStatusStart.setDisable(false);
        stopButton.connectionStatusStop.setDisable(true);
        onlineOfflineText.connectionStatusActive.setText("OFFLINE");
        onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#ff0000"));
    }

    @Override
    public void run() {
        onlineOfflineText.connectionStatusActive.setText("ONLINE");
        while (shutdown) {
            try {
                //Active or not
                startButton.connectionStatusStart.setDisable(true);
                System.out.println("Thread id: ");
                iThread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                System.out.println("InterruptedException: thread " + "(SLEEP) - " + "Connection status");
            }
        }
    }
}
