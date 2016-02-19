package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller implements Runnable {



    @FXML private Label connectToClientText;
    @FXML private Label setSteamsText;
    @FXML private Label getFromClientText;
    @FXML public TextArea serverChatArea;
    @FXML public TextField serverChatField;
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
                        getMessage();
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
        connectToClientText.setText("ONLINE");
        connectToClientText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
    }

    private void waitingForConnection() throws IOException {
        serverChatArea.appendText("Waiting for connection...");
        serverSocketConnectionStatus = serverSocketState.accept();
    }

    private void setSteams() throws IOException {
        sendToClient = new DataOutputStream(serverSocketConnectionStatus.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(serverSocketConnectionStatus.getInputStream());
        Platform.runLater(() -> {
            setSteamsText.setText("ONLINE");
            setSteamsText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
        });
    }

@FXML
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

    private  void connectionStatus(){

    }

    public void sendMessage(){
        String messageOut = serverChatField.getText();
        try {
            sendToClient.writeUTF(messageOut);
            sendToClient.flush();
            serverChatField.setText("");
            Platform.runLater(() -> serverChatArea.appendText("\n" + messageOut));
            }catch (IOException e) {
            serverChatArea.appendText("Message was not sent.");
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
                String msg = getFromClient.readUTF();
                Platform.runLater(() -> serverChatArea.appendText(msg + "\n"));

            } catch (EOFException eofexception){

            } catch (IOException e){e.printStackTrace();
            }
            Platform.runLater(() -> {
                getFromClientText.setText("ONLINE");
                getFromClientText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
            });
        } while (true);
    }
}
