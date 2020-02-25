package prostredky;

import java.io.Serializable;

public class NakladniAutomobil extends Vozidlo implements Serializable, Cloneable {

    private int pocetKol;
    private final TypVozidlaEnum typ = TypVozidlaEnum.NAKLADNI_AVTOMOBIL;

    public NakladniAutomobil(int pocetKol, String SPZ, double hmotnost, double maxSpeed) {
        super(SPZ, hmotnost, maxSpeed, TypVozidlaEnum.NAKLADNI_AVTOMOBIL);
        this.pocetKol = pocetKol;
    }

    public int getPocetKol() {
        return pocetKol;
    }

    public void setPocetKol(int pocetKol) {
        this.pocetKol = pocetKol;
    }

    @Override
    public TypVozidlaEnum getTyp() {
        return typ;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();  
    }

    @Override
    public String toString() {
        return super.toString() + pocetKol;
    }
}
