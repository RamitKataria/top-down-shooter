package ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmBox {
    Stage stage;
    private boolean answer;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    public ConfirmBox() {
    }

    @FXML
    private void initialize() {
    }

    public boolean display(Stage stage) {
        this.stage = stage;
        stage.showAndWait();
        return answer;
    }

    public void handleButtons(Event event) {
        if (event.getSource().equals(yesButton)) {
            answer = true;
        } else if (event.getSource().equals(noButton)) {
            answer = false;
        }
        stage.close();
    }
}
