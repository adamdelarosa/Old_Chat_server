package sample;

import javafx.application.Platform;

/**
 * Created by ROSA on 3/16/16.
 */
public class ConnectionStatus implements Runnable {

    private Thread iconnectionRunTester;

    public void test() {
        iconnectionRunTester = new Thread(this);
        iconnectionRunTester.run();
    }


    public void run() {
        Platform.runLater(() -> {
            do {
                try {
                    System.out.print("Thread id: ");
                    System.out.print(iconnectionRunTester.getId());
                    iconnectionRunTester.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            } while (true);
        });
    }
}
