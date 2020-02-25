package gui;

import prostredky.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kolekce.Seznam;

public class NovyWindow {

    public void start(Stage parentStage, Seznam seznam) {
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();

        ComboBox<TypVozidlaEnum> cmb = new ComboBox();
        cmb.getItems().addAll(TypVozidlaEnum.values());
        cmb.setValue(TypVozidlaEnum.NONE);

        AnchorPane.setTopAnchor(cmb, 10.0);
        AnchorPane.setLeftAnchor(cmb, 40.0);
        root.getChildren().add(cmb);

        Button btnUloz = new Button("Novy");
        AnchorPane.setLeftAnchor(btnUloz, 10.0);
        AnchorPane.setTopAnchor(btnUloz, 60.0);
        root.getChildren().add(btnUloz);

        btnUloz.setOnAction((event) -> {
            if (cmb.getValue() == TypVozidlaEnum.OSOBNI_AUTOMOBIL) {
                NovyOsobni no = new NovyOsobni();
                no.start(stage, seznam);
            }
            if (cmb.getValue() == TypVozidlaEnum.NAKLADNI_AVTOMOBIL) {
                NovyNakladni nn = new NovyNakladni();
                nn.start(stage, seznam);
            }
            if (cmb.getValue() == TypVozidlaEnum.DODAVKA) {
                NovyDodavka nn = new NovyDodavka();
                nn.start(stage, seznam);
            }
            if (cmb.getValue() == TypVozidlaEnum.TRAKTOR) {
                NovyTraktor nt = new NovyTraktor();
                nt.start(stage, seznam);
            }
        });

        Scene scene = new Scene(root, 300, 100);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Pridej auto");
        stage.setScene(scene);
        stage.show();
    }

}
