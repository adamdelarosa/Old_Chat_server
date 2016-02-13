package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {


    @FXML private Label onlineOffline;
    @FXML private TextArea serverChatArea;
    @FXML private TextField serverChatField;
    private DataOutputStream sendToClient;
    private DataInputStream getFromClient;
    private ServerSocket serverState;
    private Socket serverConnectionStatus;
    private int port =6789;
    private int numberOfConnetions=100;

    public void connectToClient(){
        try {
            serverState = new ServerSocket(port,numberOfConnetions);
            waitingForConnection();
            setSteams();
        }catch (IOException ioexception){
            serverChatArea.appendText("IOException - Server connection error.");
            ioexception.printStackTrace();
        }
    }
    private void waitingForConnection() throws IOException{
        serverChatArea.appendText("Waiting for connection...");
        serverConnectionStatus = serverState.accept();
        serverChatArea.appendText("Connected.");
        onlineOffline.setText("Online");
    }
    private void setSteams() throws IOException{
        sendToClient = new DataOutputStream(serverConnectionStatus.getOutputStream());
        sendToClient.flush();
        getFromClient = new DataInputStream(serverConnectionStatus.getInputStream());
        serverChatArea.appendText("Steams UP.");
    }

}
