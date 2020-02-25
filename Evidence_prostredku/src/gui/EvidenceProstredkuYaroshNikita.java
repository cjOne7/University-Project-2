package gui;

import java.util.Iterator;
import kolekce.KolekceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import prostredky.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kolekce.Seznam;
import kolekce.SpojovySeznam;
import sprava.EvidenceAut;
import java.util.function.Function;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import sprava.*;

public class EvidenceProstredkuYaroshNikita extends Application {

    private static final String SOUBOR_TXT = "prostredky.txt";
    private static final String SOUBOR_BIN = "zaloha.bin";

    private ListView<Vozidlo> listView;

    private EvidenceAut<Vozidlo> evidenceAut;

    private static ObservableList<Vozidlo> itemsForList;

    static Function<String, Vozidlo> func = (t) -> {
        Vozidlo vozidlo = null;
        if (t.length() != 0) {
            String[] data = t.split(",");
            String spz = data[1].trim();
            double hmotnost = Double.valueOf(data[2].trim());
            double maxSpeed = Double.valueOf(data[3].trim());
            switch (data[0].trim().toLowerCase()) {
                case "oa":
                    int pocetSedadel = Integer.parseInt(data[4].trim());
                    vozidlo = new OsobniAutomobil(pocetSedadel, spz, hmotnost, maxSpeed);
                    return vozidlo;
                case "tr":
                    double tah = Double.parseDouble(data[4].trim());
                    vozidlo = new Traktor(tah, spz, hmotnost, maxSpeed);
                    return vozidlo;
                case "do":
                    double nosnost = Double.parseDouble(data[4].trim());
                    vozidlo = new Dodavka(nosnost, spz, hmotnost, maxSpeed);
                    return vozidlo;
                case "na":
                    int pocetKol = Integer.parseInt(data[4].trim());
                    vozidlo = new NakladniAutomobil(pocetKol, spz, hmotnost, maxSpeed);
                    return vozidlo;
                default:
                    return vozidlo;
            }
        } else {
            return vozidlo;
        }
    };

    static Function<Vozidlo, String> mapper = (vozidlo) -> {
        return vozidlo.toString();
    };

    public static void obnovSeznam(final Seznam s) {
        itemsForList.clear();
        Iterator<Vozidlo> it = s.iterator();
        while (it.hasNext()) {
            itemsForList.add(it.next());
        }
    }

    private void filtruj(final TypVozidlaEnum typ, final Seznam seznam) throws KolekceException, CloneNotSupportedException {
        if (typ == TypVozidlaEnum.NONE) {
            obnovSeznam(seznam);
        } else {
            if (!seznam.jePrazdny()) {
                obnovSeznam(seznam);
                ObservableList<Vozidlo> vozidloList = FXCollections.observableArrayList();
                itemsForList.forEach(voz -> vozidloList.add(voz));
                itemsForList.clear();
                vozidloList.stream().filter(vozidlo -> vozidlo.getTyp() == typ).forEach(voz -> itemsForList.add(voz));
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        ComboBox<TypVozidlaEnum> cmbFiltr = new ComboBox<>();

        cmbFiltr.getItems().addAll(TypVozidlaEnum.values());
        cmbFiltr.setTranslateX(238);
        cmbFiltr.setTranslateY(462);
        cmbFiltr.setValue(TypVozidlaEnum.NONE);

        cmbFiltr.valueProperty().addListener((observable) -> {
            try {
                filtruj(cmbFiltr.getValue(), evidenceAut.getSeznam());
            } catch (KolekceException | CloneNotSupportedException ex) {
                Logger.getLogger(EvidenceProstredkuYaroshNikita.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Button test = createButton("Test", 20, 423);
        Button generuj = createButton("Generuj", 20, 462);
        Button novy = createButton("Novy", 136, 423);
        Button zrus = createButton("Zrus", 331, 423);
        Button zmen = createButton("Zmen", 238, 423);
        Button zrusFiltaci = createButton("Zrus filtaci", 136, 462);
        Button uloz = createButton("Uloz", 488, 45);
        Button obnov = createButton("Obnov", 574, 45);
        Button ulozTXT = createButton("UlozTXT", 488, 84);
        Button obnovTXT = createButton("ObnovTXT", 574, 84);

        evidenceAut = new EvidenceAut<>(new SpojovySeznam<>());

        listView = new ListView<>();
        listView.setPrefSize(477, 414);
        itemsForList = FXCollections.observableArrayList();

        listView.setItems(itemsForList);

        root.getChildren().addAll(cmbFiltr, test, generuj, novy, zrus,
                zmen, zrusFiltaci, uloz, obnov, ulozTXT, obnovTXT, listView);
////////////////////////////////////////////////////////////////////////////////

        test.setOnAction((event) -> {
            evidenceAut.getSeznam().zrus();
            evidenceAut.nactiTextSoubor("test.txt", func);
            obnovSeznam(evidenceAut.getSeznam());
        });

        generuj.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Generate items");
            dialog.setHeaderText(null);
            dialog.setContentText("How many do you want to generate? ");

            Optional<String> op = dialog.showAndWait();

            RandomObjectsGenerator randomObjectsGenerator = new RandomObjectsGenerator();

            int pocetProstredku;
            if (op.isPresent()) {
                try {
                    pocetProstredku = Integer.parseInt(op.get());
                    if (pocetProstredku <= evidenceAut.getSeznam().getVelikost()) {
                        evidenceAut.getSeznam().zrus();
                        obnovSeznam(evidenceAut.getSeznam());
                        evidenceAut.generujData(pocetProstredku);
                    } else {
                        callAlertWindow(Alert.AlertType.WARNING, "List is full!", "Your list contains more items than "
                                + evidenceAut.getSeznam().getVelikost()
                                + ".\nPlease generate a smaller number of elements."
                                + "\nYour entered number of generated elements is "
                                + op.get());
                    }
                } catch (NumberFormatException e) {
                    callAlertWindow(Alert.AlertType.ERROR,
                            "Wrong number format",
                            "Incorrectly entered numerical values, please be careful and try to enter again."
                    );
                }
            }
        });

        novy.setOnAction((event) -> {
            NovyWindow n = new NovyWindow();
            n.start(stage, evidenceAut.getSeznam());
        });

        zrus.setOnAction((event) -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                itemsForList.remove(listView.getSelectionModel().getSelectedItem());
                try {
                    Vozidlo v = listView.getSelectionModel().getSelectedItem();
                    evidenceAut.nastavAktualniPolozku(v);
                    evidenceAut.vyjmiAktualniPolozku();
                } catch (OvladaniException ex) {
                    Logger.getLogger(EvidenceProstredkuYaroshNikita.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                callAlertWindow(Alert.AlertType.INFORMATION, "Deleting auto", "You didn't choose a vehicle.");
            }
            listView.refresh();
        });

        zmen.setOnAction((event) -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                ZmenWindow zw = new ZmenWindow();
                zw.start(stage, (Vozidlo) listView.getSelectionModel().getSelectedItem(), evidenceAut.getSeznam());
            }
        });

        zrusFiltaci.setOnAction((event) -> {
            cmbFiltr.setValue(TypVozidlaEnum.NONE);
        });

        uloz.setOnAction((event) -> {
            evidenceAut.ulozDoSouboru(SOUBOR_BIN);
        });

        obnov.setOnAction((event) -> {
            evidenceAut.nactiZeSouboru(SOUBOR_BIN);
        });

        ulozTXT.setOnAction((event) -> {
            evidenceAut.ulozTextSoubor(SOUBOR_TXT, mapper);
        });

        obnovTXT.setOnAction((event) -> {
            itemsForList.clear();
            evidenceAut.getSeznam().zrus();

            evidenceAut.nactiTextSoubor(SOUBOR_TXT, func);
            obnovSeznam(evidenceAut.getSeznam());
        });

        Scene scene = new Scene(root, 706, 534);
        stage.setTitle("Evidence automobilu");
        stage.setMinWidth(600);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    public static void callAlertWindow(final Alert.AlertType type, final String title, final String contextText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    private Button createButton(final String name, final int x, final int y) {
        Button btn = new Button(name);
        btn.setTranslateX(x);
        btn.setTranslateY(y);
        btn.setPrefSize(75, 26);
        return btn;
    }

    public static void main(String[] args) throws CloneNotSupportedException, OvladaniException {
        launch(args);
    }
}