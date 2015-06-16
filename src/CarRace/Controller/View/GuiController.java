package CarRace.Controller.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML
    private Label displayCommandsLabel;
    @FXML
    private TextFlow getConsoleOutputTextFlow;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSendConsoleInputButton(){

    }

    @FXML
    public void onAddCarButton(){

    }

    private void initializeUiHelper() {
       // UiHelper.setdisplayCommandsLabel(displayCommandsLabel);
    }
}
