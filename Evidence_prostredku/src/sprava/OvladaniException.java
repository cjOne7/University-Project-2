package sprava;

import javafx.scene.control.Alert;

public class OvladaniException extends Exception {

    public OvladaniException(String message) {
        super(message);
    }
    
     public OvladaniException(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
    
}
