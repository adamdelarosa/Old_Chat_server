package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller implements Runnable {

    @FXML
    private Label connectToClientText;
    @FXML
    private Label setSteamsText;
    @FXML
    private Label getFromClientText;
    @FXML
    private Label testConnection;
    @FXML
    public TextArea serverChatArea;
    @FXML
    public TextField serverChatField;
    private DataOutputStream sendToClient;
    private DataInputStream getFromClient;
    private ServerSocket serverSocketState;
    private Socket socketConnectionStatus;
    private int port = 6789;
    private int numberOfConnetions = 100;
    private Thread iThread;
    private Thread runConnectionStatus;
    private boolean getFromClientSwitch;

    public void connectToClient() {

        Thread runAndConnectToClient = new Thread(() -> {
            try {
                serverSocketState = new ServerSocket(port, numberOfConnetions);
                while (!getFromClientSwitch) {
                    try {
                        waitingForConnection();
                        System.out.print("connectTo");
                        setSteams();
                        getMessage();
                    } catch (EOFException eofexception) {
                        serverChatArea.appendText("\nIOException - Server connection error.");
                    } finally {
                        closeConnection();
                    }
                }
            } catch (BindException bindexception) {
                serverChatArea.appendText("\n Already connected.");
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        });
        runAndConnectToClient.start();
        connectToClientText.setText("ONLINE");
        connectToClientText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
    }

    private void waitingForConnection() throws IOException {
        socketConnectionStatus = serverSocketState.accept();
        serverChatArea.appendText("\nWaiting for connection...");
    }

    private void setSteams() throws IOException {
        sendToClient = new DataOutputStream(socketConnectionStatus.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(socketConnectionStatus.getInputStream());
        Platform.runLater(() -> {
            serverChatArea.appendText("\nClient connected.");
            setSteamsText.setText("ONLINE");
            setSteamsText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
        });

    }

    @FXML
    public void closeConnection() {
        //need to return tof to work.
            Platform.runLater(() -> {
                serverChatArea.appendText("\nClosing connection . . .");
                try {
                    getFromClientSwitch = true;
                    sendToClient.close();
                    setSteamsText.setText("OFFLINE");
                    getFromClient.close();
                    getFromClientText.setText("OFFLINE");
                    serverSocketState.close();
                    connectToClientText.setText("OFFLINE");
                    System.out.println("closeConnection - DONE.");
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            });

            System.out.println("NO CONNECTION.");

    }

    public void connectionStatus() {

        runConnectionStatus = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    if (socketConnectionStatus.isConnected()) {
                        try {
                            runConnectionStatus.sleep(1000);
                            System.out.println("STATUS: Connected.");
                        } catch (InterruptedException e) {
                        }
                    } else {
                    }
                });
            }
        });
        runConnectionStatus.start();
    }


    public void sendMessage() {
        String messageOut = serverChatField.getText();
        try {
            sendToClient.writeUTF(messageOut);
            sendToClient.flush();
            serverChatField.setText("");
            Platform.runLater(() -> serverChatArea.appendText("\n" + messageOut));
        } catch (IOException e) {
            serverChatArea.appendText("\nMessage was not sent.");
            e.printStackTrace();
        }
    }

    private void getMessage() {
        iThread = new Thread(this);
        iThread.run();
    }

    public void run() {

        do {
            try {
                System.out.print("He");
                String msg = getFromClient.readUTF();
                Platform.runLater(() -> serverChatArea.appendText(msg + "\n"));

            } catch (EOFException eofexception) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                getFromClientText.setText("ONLINE");
                getFromClientText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
            });
        } while (!getFromClientSwitch);
        System.out.println("Here.");

        while (true) {
            if (socketConnectionStatus.isConnected()) {
                System.out.println("STATUS: Connected.");
            } else {
            }
        }

    }

}
