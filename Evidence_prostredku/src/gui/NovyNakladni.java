package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kolekce.KolekceException;
import kolekce.Seznam;
import prostredky.NakladniAutomobil;

public class NovyNakladni {

    private final AnchorPane root = new AnchorPane();

    private void createLabel(final String text, final double topPoint, final double leftPoint) {
        Label label = new Label(text);
        AnchorPane.setTopAnchor(label, topPoint);
        AnchorPane.setLeftAnchor(label, leftPoint);
        root.getChildren().add(label);
    }

    private TextField createTextField(final double topPoint, final double leftPoint) {
        TextField textField = new TextField();
        AnchorPane.setTopAnchor(textField, topPoint);
        AnchorPane.setLeftAnchor(textField, leftPoint);
        root.getChildren().add(textField);
        return textField;
    }

    public void start(final Stage parentStage, final Seznam seznam) {
        Stage sg = new Stage();
        
        createLabel("SPZ:", 55.0, 10.0);
        createLabel("Hmotnost(kg):", 95.0, 10.0);
        createLabel("Pocet kol:", 135.0, 10.0);
        createLabel("Max speed(km):", 175.0, 10.0);

        TextField tfSPZ = createTextField(50.0, 150.0);
        TextField tfHmotnost = createTextField(90.0, 150.0);
        TextField tfPocetKol = createTextField(130.0, 150.0);
        TextField tfMaxSpeed = createTextField(170.0, 150.0);

        Button bUloz = new Button("Uloz");
        AnchorPane.setLeftAnchor(bUloz, 10.0);
        AnchorPane.setTopAnchor(bUloz, 200.0);
        root.getChildren().add(bUloz);

        bUloz.setOnAction((event) -> {
            try {
                seznam.pridej(new NakladniAutomobil(
                        Integer.parseInt(tfPocetKol.getText()),
                        tfSPZ.getText(),
                        Double.parseDouble(tfHmotnost.getText()),
                        Double.parseDouble(tfMaxSpeed.getText()))
                );
                EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
                sg.close();
            } catch (KolekceException ex) {
                KolekceException exx = new KolekceException("Chyba seznamu");
            } catch (NumberFormatException ex) {
                KolekceException exx = new KolekceException("Chyba parametru");
            }
        });

        Scene scene = new Scene(root, 400, 270);
        sg.initModality(Modality.WINDOW_MODAL);
        sg.initOwner(parentStage);
        sg.setTitle("Pridej auto");
        sg.setScene(scene);
        sg.show();
    }
}
