package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ROSA on 3/16/16.
 */
public class ConnectionStatus implements Runnable {

    private boolean iconnectionRunTesterSwitch;
    private Thread iconnectionRunTester;

    public void test() {
        System.out.println("here");
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.start();
    }

    @Override
    public void run() {
                do {
                    try {
                        System.out.println("Thread id: " + iconnectionRunTester.getId());
                        iconnectionRunTester.sleep(1000);


                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } while (!iconnectionRunTesterSwitch);
        }
}
