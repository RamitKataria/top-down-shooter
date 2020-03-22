package ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// represents the controller for a confirm box
public class ConfirmBox {
    Stage stage;
    private boolean answer;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    // no-args constructor required by FXML
    public ConfirmBox() {
    }

    // this method is required by FXML
    @FXML
    private void initialize() {
    }

    // MODIFIES: this
    // EFFECTS: show the confirm box and return the answer
    public boolean display(Stage stage) {
        this.stage = stage;
        stage.showAndWait();
        return answer;
    }

    // MODIFIES: this
    // EFFECTS: set appropriate answer based on which button is pressed
    public void handleButtons(Event event) {
        if (event.getSource().equals(yesButton)) {
            answer = true;
        } else if (event.getSource().equals(noButton)) {
            answer = false;
        }
        stage.close();
    }
}
