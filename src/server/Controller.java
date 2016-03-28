package Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Server.HealthCheck.ConnectionStatus;

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
    private Label textLabelGetFromClient;
    @FXML
    public Label connectionStatusActive;
    @FXML
    public TextArea serverChatArea, serverLogArea;
    @FXML
    public TextField serverChatField;
    @FXML
    public Button connectionStatusStart;
    @FXML
    public Button connectionStatusStop;
    boolean tofConnectionStatus;

    private DataOutputStream sendToClient;
    private DataInputStream getFromClient;
    private ServerSocket serverSocketState;
    public Socket socketState;
    private int port = 6789;
    private int numberOfConnetions = 100;
    private Thread iThread;
    private boolean getFromClientSwitch = false;
    private String msg;
    private ConnectionStatus classconnectionstatus;

    public void connectToClient() {
        getFromClientSwitch = true;
        serverLogArea.appendText("*** Server Started ***");
        Thread runAndConnectToClient = new Thread(() -> {
            try {

                while (true) {
                    try {
                        serverSocketState();
                        waitingForConnection();
                        setSteams();
                        getMessage();
                    } catch (EOFException eofexception) {
                        closeConnection();
                    }
                }
            } catch (BindException bindexception) {
                bindexception.printStackTrace();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        });
        runAndConnectToClient.start();
        connectToClientText.setText("ONLINE");
        connectToClientText.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
    }

    public void serverSocketState(){
        if(serverSocketState == null) {
            try {
                serverSocketState = new ServerSocket(port, numberOfConnetions);
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverLogArea.appendText("\n" + String.valueOf(serverSocketState));
        }else{
            serverLogArea.appendText("\n" + String.valueOf(serverSocketState));
        }
    }

    public void closeConnection() {
        getFromClientSwitch = false; //<--- Kill Controller Thread
    }

    private void waitingForConnection() throws IOException {
        socketState = serverSocketState.accept();
        serverLogArea.appendText("\nWaiting for connection...");

    }

    @FXML
    public void connectionStatusStart() {
        classconnectionstatus = new ConnectionStatus(true, this, this, this, this);
        classconnectionstatus.startConnecionStatusCheck();
    }

    @FXML
    public void connectionStatusStop() {
        classconnectionstatus.killConnecionStatusCheck();
    }

    public void testSwitch() {
        tofConnectionStatus = !tofConnectionStatus;
        System.out.println("STATE: " + tofConnectionStatus);
    }

    private void setSteams() throws IOException {
        sendToClient = new DataOutputStream(socketState.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(socketState.getInputStream());
        Platform.runLater(() -> {
            serverLogArea.appendText("\nClient connected.");
            setSteamsText.setText("ONLINE");
            setSteamsText.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
        });
    }

    public void sendMessage() {
        String messageOut = serverChatField.getText();
        try {
            sendToClient.writeUTF(messageOut);
            sendToClient.flush();
            serverChatField.setText("");
            Platform.runLater(() -> serverChatArea.appendText("\n" + messageOut));
        } catch (IOException e) {
            serverLogArea.appendText("\nMessage was not sent.");
            e.printStackTrace();
        }
    }
    private void getMessage() {
        getFromClientSwitch = true;
        iThread = new Thread(this);
        iThread.run();
    }

    public void run() {
        serverLogArea.appendText("\nMessages - ONLINE.");
        while (getFromClientSwitch) {
            try {
                msg = getFromClient.readUTF();
                serverChatArea.appendText("\n" + msg);

                Platform.runLater(()->{
                textLabelGetFromClient.setText("ONLINE");
                textLabelGetFromClient.setTextFill(javafx.scene.paint.Color.web("#00FF00"));
                });

            } catch (EOFException eofexception) {
                eofexception.printStackTrace();
                getFromClientSwitch = false;
                serverLogArea.appendText("\nEOFException: getFromClient - STOPPED.");
            } catch (IOException eofexceptionGetMessage) {
                getFromClientSwitch = false;
                serverLogArea.appendText("\nIOException: getFromClient - STOPPED.");
                eofexceptionGetMessage.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            textLabelGetFromClient.setText("OFFLINE");
            textLabelGetFromClient.setTextFill(javafx.scene.paint.Color.web("#ff0000"));
        });
        serverLogArea.appendText("\nMessages - OFFLINE.");
    }
}
