package sample;

import javafx.application.Platform;

import java.util.Timer;

/**
 * Created by ROSA on 3/16/16.
 */
public class ConnectionStatus implements Runnable {

    private Thread iconnectionRunTester;

    public void test() {
        System.out.println("here");
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();
    }

@Override
    public void run() {
        Platform.runLater(() -> {
            do {
                try {
                    System.out.println("Thread id: " + iconnectionRunTester.getId());
                    iconnectionRunTester.sleep(1000);

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            } while (true);
        });
    }
}
