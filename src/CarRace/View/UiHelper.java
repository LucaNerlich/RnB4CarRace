package CarRace.View;

/**
 * Created by Luca Nerlich on 15.06.2015.
 */
public class UiHelper {

    public void updateUi() {
        if (DerbyManager.getAllSuggestion().isEmpty()) {
            suggestionLabel.setText("");
            newSuggestionButton.setDisable(true);
        } else {
            suggestionLabel.setText("");
            newSuggestionButton.setDisable(false);
        }
        updateTable();
    }
}
