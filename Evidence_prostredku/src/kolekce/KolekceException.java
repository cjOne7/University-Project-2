package kolekce;

import javafx.scene.control.Alert;

public class KolekceException extends Exception {

    public KolekceException(String message) {
        super(message);
    }

    public KolekceException(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
