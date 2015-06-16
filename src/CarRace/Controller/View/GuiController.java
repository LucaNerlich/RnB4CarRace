package CarRace.Controller.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            connectedLabel.setVisible(false);
    }

    @FXML
    public void onSendConsoleInputButton() {

    }

    @FXML
    public void onLoginButton() {
        getDisplayConsoleTextArea.appendText("\n Ich bin ein RaceClient");

        //todo if connection successful
        connectedLabel.setVisible(true);
    }

    @FXML
    public void onUseDefaultButton() {
        getIpTextField.setText("127.0.0.1");
        getPortTextField.setText("3141");

        getDisplayConsoleTextArea.appendText("\n 127.0.0.1");
        getDisplayConsoleTextArea.appendText("\n 3141");
    }

    @FXML
    public void onAddCarButton() {

    }

    private void initializeUiHelper() {
        // UiHelper.setdisplayCommandsLabel(displayCommandsLabel);
    }
}
