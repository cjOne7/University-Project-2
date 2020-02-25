package prostredky;

import java.io.Serializable;

public class OsobniAutomobil extends Vozidlo implements Serializable, Cloneable {

    private int pocetSedadel;
    private final TypVozidlaEnum typ = TypVozidlaEnum.OSOBNI_AUTOMOBIL;

    public OsobniAutomobil(int pocetSedadel, String SPZ, double hmotnost, double maxSpeed) {
        super(SPZ, hmotnost, maxSpeed, TypVozidlaEnum.OSOBNI_AUTOMOBIL);
        this.pocetSedadel = pocetSedadel;
    }

    public int getPocetSedadel() {
        return pocetSedadel;
    }

    public void setPocetSedadel(int pocetSedadel) {
        this.pocetSedadel = pocetSedadel;
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
        return super.toString() + pocetSedadel;
    }

}
