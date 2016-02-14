package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller implements Runnable {


    @FXML
    public Label onlineOffline;
    @FXML
    public TextArea serverChatArea;
    @FXML
    public TextField serverChatField;
    private DataOutputStream sendToClient;
    private DataInputStream getFromClient;
    private ServerSocket serverSocketState;
    private Socket serverSocketConnectionStatus;
    private int port = 6789;
    private int numberOfConnetions = 100;
    private Thread iThread;

    public void connectToClient() {
        Thread runAndConnectToClient = new Thread(() -> {
            try {
                serverSocketState = new ServerSocket(port, numberOfConnetions);

                while (true) {
                    try {
                        waitingForConnection();
                        setSteams();
                    } catch (EOFException eofexception) {
                        serverChatArea.appendText("IOException - Server connection error.");
                    } finally {
                        closeConnetion();
                    }
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        });
        runAndConnectToClient.start();
        getMessage();
    }

    private void checkConnection() {
        Thread runCheckConnection = new Thread((() -> {
            //while up and run
        })
        );
        runCheckConnection.start();
    }


    private void waitingForConnection() throws IOException {
        serverChatArea.appendText("Waiting for connection...");
        serverSocketConnectionStatus = serverSocketState.accept();
        serverChatArea.appendText("Connected.");
        onlineOffline.setText("Online");
    }

    private void setSteams() throws IOException {
        sendToClient = new DataOutputStream(serverSocketConnectionStatus.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(serverSocketConnectionStatus.getInputStream());
        serverChatArea.appendText("Steams UP.");
    }

    private void closeConnetion() {
        serverChatArea.appendText("Closing connection . . .");
        try {
            sendToClient.close();
            getFromClient.close();
            serverSocketState.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private void getMessage() {
        iThread = new Thread(this);
        iThread.run();
    }

    public void run() {
        do {
            try {
                String msg = getFromClient.readUTF(); // <--- freeze GUI
                Platform.runLater(() -> serverChatArea.appendText(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
