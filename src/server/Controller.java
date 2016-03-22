package server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller implements Runnable {

    @FXML private Label connectToClientText;
    @FXML private Label setSteamsText;
    @FXML private Label getFromClientText;
    @FXML public Label connectionStatusActive;
    @FXML public TextArea serverChatArea,serverLogArea;
    @FXML public TextField serverChatField;
    @FXML public Button connectionStatusStart;
    @FXML public Button connectionStatusStop;
    boolean tofConnectionStatus;


    private DataOutputStream sendToClient;
    private DataInputStream getFromClient;
    private ServerSocket serverSocketState;
    public Socket socketState;
    private int port = 6789;
    private int numberOfConnetions = 100;
    private Thread iThread;
    private boolean getFromClientSwitch;

    private ConnectionStatus classconnectionstatus;

    public void connectToClient() {

        Thread runAndConnectToClient = new Thread(() -> {
            try {
                serverSocketState = new ServerSocket(port, numberOfConnetions);
                while (!getFromClientSwitch) {
                    try {
                        waitingForConnection();
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
        socketState = serverSocketState.accept();
        serverChatArea.appendText("\nWaiting for connection...");

    }
    @FXML
    public void connectionStatusStart(){
        classconnectionstatus = new ConnectionStatus(true,this,this,this,this);
        classconnectionstatus.startConnecionStatusCheck();
    }
    @FXML
    public void connectionStatusStop(){
        classconnectionstatus.killConnecionStatusCheck();
    }
    public void testSwitch(){
        tofConnectionStatus = !tofConnectionStatus;
        System.out.println("STATE: " + tofConnectionStatus);
    }


    private void setSteams() throws IOException {
        sendToClient = new DataOutputStream(socketState.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(socketState.getInputStream());
        Platform.runLater(() -> {
            serverChatArea.appendText("\nClient connected.");
            setSteamsText.setText("ONLINE");
            setSteamsText.setTextFill(javafx.scene.paint.Color.web("#0076a3"));
        });
    }

    public void closeConnection() {
            getFromClientSwitch = true; //<--- Kill Controller Thread
            if(!true) {
                try {
                    serverSocketState.close(); // <---need to test first.
                    System.out.println("closeConnection - DONE.");
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }
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
    }
}
