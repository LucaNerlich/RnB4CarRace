package CarRace.Controller.View;

import CarRace.Controller.Connection.ConnectionHandler;
import CarRace.Controller.Connection.Listener;
import CarRace.Model.RaceCar;
import CarRace.RaceHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML
    private AnchorPane getAnchorPane;
    @FXML
    private TextArea getDisplayConsoleTextArea;
    @FXML
    private TextField getConsoleInputTextField;
    @FXML
    private Button sendConsoleInputButton;
    @FXML
    private Label connectedLabel;
    @FXML
    private Button addCarButton;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField getNameTextField;
    @FXML
    private Label geschwindigkeitLabel;
    @FXML
    private TextField getGetschwindigkeitTextField;
    @FXML
    private Label ipLabel;
    @FXML
    private TextField getIpTextField;
    @FXML
    private Label portLabel;
    @FXML
    private TextField getPortTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button useDefaultButton;

    RaceHandler raceHandler;
    Socket socket;
    Listener listener;
    Thread listenerThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectedLabel.setVisible(false);
    }

    @FXML
    public void onSendConsoleInputButton() {
        if (raceHandler != null) {
            String consoleInput = getConsoleInputTextField.getText();
            getConsoleInputTextField.clear();
            if (consoleInput.length() > 0) {

                try {
                    if (consoleInput.equals("/REGCARS")) {
                        //sende autos, wenn keine existieren -> fehlermeldung
                        if(!raceHandler.registerCars()){
                            getDisplayConsoleTextArea.appendText("\n Error - No Race Cars added!");
                        }
                    } else {
                        raceHandler.schreibeNachricht(consoleInput);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onLoginButton() {

        String ip = getIpTextField.getText();
        String port = getPortTextField.getText();

        if (ip.length() > 0 && port.length() > 0) {
            ConnectionHandler.setupConnection(ip, port);
            try {
                socket = ConnectionHandler.getConnection();
                listener = new Listener(socket, this);
                listenerThread = new Thread(listener);
                listenerThread.start();
                raceHandler = new RaceHandler(socket, listener);

                getDisplayConsoleTextArea.appendText("\n Connection Successful on: " + ip + ":" + port);
                connectedLabel.setVisible(true);

                //prevent multiple socket connections from one client - ghetto way
                getLoginButton().setDisable(true);
                getLoginButton().setVisible(false);
            } catch (IOException e) {
                getDisplayConsoleTextArea.appendText("\n Error while connection to socket!");
            }
        }
    }

    @FXML
    public void onUseDefaultButton() {
        getIpTextField.setText("127.0.0.1");
        getPortTextField.setText("3141");
    }

    @FXML
    public void onAddCarButton() {
        String name = getGetNameTextField().getText();
        if (name.length() > 0) {
            raceHandler.addRaceCar(new RaceCar(name));
            getNameTextField.clear();
            getDisplayConsoleTextArea.appendText("\n Success - Car: " + name + "  added.");
        } else {
            getDisplayConsoleTextArea.appendText("\n Error - No car name specified.");
        }
    }

    private void initializeUiHelper() {
        // UiHelper.setdisplayCommandsLabel(displayCommandsLabel);
    }

    public AnchorPane getGetAnchorPane() {
        return getAnchorPane;
    }

    public TextArea getGetDisplayConsoleTextArea() {
        return getDisplayConsoleTextArea;
    }

    public TextField getGetConsoleInputTextField() {
        return getConsoleInputTextField;
    }

    public Button getSendConsoleInputButton() {
        return sendConsoleInputButton;
    }

    public Label getConnectedLabel() {
        return connectedLabel;
    }

    public Button getAddCarButton() {
        return addCarButton;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public TextField getGetNameTextField() {
        return getNameTextField;
    }

    public Label getGeschwindigkeitLabel() {
        return geschwindigkeitLabel;
    }

    public TextField getGetGetschwindigkeitTextField() {
        return getGetschwindigkeitTextField;
    }

    public Label getIpLabel() {
        return ipLabel;
    }

    public TextField getGetIpTextField() {
        return getIpTextField;
    }

    public Label getPortLabel() {
        return portLabel;
    }

    public TextField getGetPortTextField() {
        return getPortTextField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getUseDefaultButton() {
        return useDefaultButton;
    }

    public RaceHandler getRaceHandler() {
        return raceHandler;
    }

    public Socket getSocket() {
        return socket;
    }

    public Listener getListener() {
        return listener;
    }

    public Thread getListenerThread() {
        return listenerThread;
    }
}
