package sprava;

import prostredky.*;
import kolekce.Seznam;

public class RandomObjectsGenerator {

    private int randProstredek;
    private int unikatniVlastnostNaO;
    private double unikatniVlastnostDaT;
    private double hmotnost;
    private double maxSpeed;

    private StringBuilder builder = new StringBuilder();

    private final IGenerator generateSpz = (stringBuilder) -> {
        int count = 0;
        char znak;
        int twoNumbers = (int) (Math.random() * 90 + 10);
        stringBuilder.append(twoNumbers);

        for (int j = 0; j < 4; j++) {
            znak = (char) (Math.random() * 25 + 65);
            stringBuilder.append(znak);
            count++;
        }
        twoNumbers = (int) (Math.random() * 90 + 10);
        stringBuilder.append(twoNumbers);
        return stringBuilder;
    };

    private void generateRandomNumbersWithInt() {
        double hmotnost1 = Math.random() * 500 + 700;
        double maxSpeed1 = Math.random() * 100 + 200;

        this.unikatniVlastnostNaO = (int) (Math.random() * 9 + 2);
        this.hmotnost = Math.round(hmotnost1 * 100.0) / 100.0;
        this.maxSpeed = Math.round(maxSpeed1 * 100.0) / 100.0;
    }

    private void generateRandomNumbersWithDouble() {
        double unikatniVlastnostDaT1 = Math.random() * 500 + 100;
        double hmotnost1 = Math.random() * 500 + 700;
        double maxSpeed1 = Math.random() * 100 + 200;

        this.unikatniVlastnostDaT = Math.round(unikatniVlastnostDaT1 * 1000.0) / 1000.0;
        this.hmotnost = Math.round(hmotnost1 * 100.0) / 100.0;
        this.maxSpeed = Math.round(maxSpeed1 * 100.0) / 100.0;
    }

    public Vozidlo generatorVozidel(final Seznam seznam) {
        this.randProstredek = (int) (Math.random() * 4 + 1);
        switch (randProstredek) {
            case 1:
                generateSpz.generateSpz(builder);
                generateRandomNumbersWithInt();
                NakladniAutomobil na = new NakladniAutomobil(unikatniVlastnostNaO, builder.toString(), hmotnost, maxSpeed);
                builder.setLength(0);
                return na;
            case 2:
                generateSpz.generateSpz(builder);
                generateRandomNumbersWithInt();
                OsobniAutomobil os = new OsobniAutomobil(unikatniVlastnostNaO, builder.toString(), hmotnost, maxSpeed);
                builder.setLength(0);
                return os;
            case 3:
                generateSpz.generateSpz(builder);
                generateRandomNumbersWithDouble();
                Traktor tr = new Traktor(unikatniVlastnostDaT, builder.toString(), hmotnost, maxSpeed);
                builder.setLength(0);
                return tr;
            case 4:
                generateSpz.generateSpz(builder);
                generateRandomNumbersWithDouble();
                Dodavka d = new Dodavka(unikatniVlastnostDaT, builder.toString(), hmotnost, maxSpeed);
                builder.setLength(0);
                return d;
        }
        return null;
    }
}
