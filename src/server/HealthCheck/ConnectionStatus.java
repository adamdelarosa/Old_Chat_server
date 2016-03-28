package Server.HealthCheck;
import Server.Controller;

import java.util.Date;


public class ConnectionStatus implements Runnable {

    private boolean shutdown = false;
    private Thread iThread, onlineBlink;
    private Controller startButton, stopButton, onlineOfflineText, serverLogArea;

    Date logDate = new Date();

    public ConnectionStatus(Boolean stopping, Controller startbutton, Controller stopbutton, Controller onlineofflinetext, Controller serverlogarea) {
        shutdown = stopping;
        startButton = startbutton;
        stopButton = stopbutton;
        onlineOfflineText = onlineofflinetext;
        serverLogArea = serverlogarea;
    }

    public void startConnecionStatusCheck() {
        stopButton.connectionStatusStop.setDisable(false);
        iThread = new Thread(this);
        iThread.start();
        onlineOfflineText.connectionStatusActive.setText("ONLINE");
        onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
        onlineBlink = new Thread(() -> {
            while (shutdown) {
                try {
                    onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
                    onlineBlink.sleep(1000);
                    onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#0000FF"));
                    onlineBlink.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    System.out.println("InterruptedException - onlineOfflineText switch Error.");
                }
            }
        });
        onlineBlink.start();
        serverLogArea.serverLogArea.appendText("\n" + logDate + ": Status check started");
    }

    public void killConnecionStatusCheck() {
        shutdown = false;
        startButton.connectionStatusStart.setDisable(false);
        stopButton.connectionStatusStop.setDisable(true);
        onlineOfflineText.connectionStatusActive.setText("OFFLINE");
        onlineOfflineText.connectionStatusActive.setTextFill(javafx.scene.paint.Color.web("#ff0000"));
        serverLogArea.serverLogArea.appendText("\n" + logDate + ": Status check stopped");
    }

    @Override
    public void run() {
        onlineOfflineText.connectionStatusActive.setText("ONLINE");
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
