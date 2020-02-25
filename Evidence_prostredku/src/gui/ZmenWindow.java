package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kolekce.Seznam;
import prostredky.*;

public class ZmenWindow {

    private TextField tfSPZ;
    private TextField tfHmotnost;
    private TextField tfVlastniCharakteristika;
    private TextField tfMaxSpeed;

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

    private void setTextField(final Vozidlo vozidlo) {
        tfSPZ.setText(vozidlo.getSPZ());
        tfHmotnost.setText(Double.toString(vozidlo.getHmotnost()));
        tfMaxSpeed.setText(Double.toString(vozidlo.getMaxSpeed()));
    }

    private void setVozidlo(final Vozidlo vozidlo) {
        vozidlo.setSPZ(tfSPZ.getText());
        vozidlo.setHmotnost(Double.parseDouble(tfHmotnost.getText()));
        vozidlo.setMaxSpeed(Double.parseDouble(tfMaxSpeed.getText()));
    }

    public void start(final Stage parentStage, final Vozidlo vozidlo, final Seznam seznam) {
        Stage stage = new Stage();

        createLabel("Typ: " + vozidlo.getTyp().toString(), 15.0, 10.0);
        createLabel("SPZ:", 55.0, 10.0);
        createLabel("Hmotnost(kg):", 95.0, 10.0);
        createLabel("", 135.0, 10.0);
        createLabel("Max speed(km):", 175.0, 10.0);

        tfSPZ = createTextField(50.0, 100.0);
        tfHmotnost = createTextField(90.0, 100.0);
        tfVlastniCharakteristika = createTextField(130.0, 100.0);
        tfMaxSpeed = createTextField(170.0, 100.0);

        Button bUloz = new Button("Uloz");
        AnchorPane.setLeftAnchor(bUloz, 10.0);
        AnchorPane.setTopAnchor(bUloz, 200.0);
        root.getChildren().add(bUloz);

        if (vozidlo.getTyp() == TypVozidlaEnum.OSOBNI_AUTOMOBIL) {
            createLabel("Pocet mist:", 135.0, 10.0);
            OsobniAutomobil osobniAvtomobil = (OsobniAutomobil) vozidlo;

            setTextField(vozidlo);

            tfVlastniCharakteristika.setText(Integer.toString(osobniAvtomobil.getPocetSedadel()));

            bUloz.setOnAction((event) -> {
                setVozidlo(vozidlo);
                osobniAvtomobil.setPocetSedadel(Integer.parseInt(tfVlastniCharakteristika.getText()));

                EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
                stage.close();
            });
        }

        if (vozidlo.getTyp() == TypVozidlaEnum.NAKLADNI_AVTOMOBIL) {
            createLabel("Pocet kol:", 135.0, 10.0);
            NakladniAutomobil nakladniAutomobil = (NakladniAutomobil) vozidlo;

            setTextField(vozidlo);

            tfVlastniCharakteristika.setText(Integer.toString(nakladniAutomobil.getPocetKol()));

            bUloz.setOnAction((event) -> {
                setVozidlo(vozidlo);

                nakladniAutomobil.setPocetKol(Integer.parseInt(tfVlastniCharakteristika.getText()));

                EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
                stage.close();
            });
        }
        if (vozidlo.getTyp() == TypVozidlaEnum.DODAVKA) {
            createLabel("Nosnost(kg):", 135.0, 10.0);
            Dodavka dodavka = (Dodavka) vozidlo;

            setTextField(vozidlo);

            tfVlastniCharakteristika.setText(Double.toString(dodavka.getNosnost()));

            bUloz.setOnAction((event) -> {
                setVozidlo(vozidlo);

                dodavka.setNosnost(Double.parseDouble(tfVlastniCharakteristika.getText()));

                EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
                stage.close();
            });
        }
        if (vozidlo.getTyp() == TypVozidlaEnum.TRAKTOR) {
            createLabel("Tag:", 135.0, 10.0);
            Traktor traktor = (Traktor) vozidlo;

            setTextField(vozidlo);

            tfVlastniCharakteristika.setText(Double.toString(traktor.getTah()));

            bUloz.setOnAction((event) -> {
                setVozidlo(vozidlo);

                traktor.setTah(Double.parseDouble(tfVlastniCharakteristika.getText()));

                EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
                stage.close();
            });
        }

        Scene scene = new Scene(root, 300, 250);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Editace auta");
        stage.setScene(scene);
        stage.show();
    }
}
