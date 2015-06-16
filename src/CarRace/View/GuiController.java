package CarRace.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML
    private Label displayCommandsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initializeUiHelper() {
       // UiHelper.setdisplayCommandsLabel(displayCommandsLabel);
    }
}
