package prostredky;

import java.io.Serializable;

public class Traktor extends Vozidlo implements Serializable, Cloneable {

    private double tah;
    private final TypVozidlaEnum typ = TypVozidlaEnum.TRAKTOR;

    public Traktor(double tah, String SPZ, double hmotnost, double maxSpeed) {
        super(SPZ, hmotnost, maxSpeed, TypVozidlaEnum.TRAKTOR);
        this.tah = tah;
    }

    public double getTah() {
        return tah;
    }

    public void setTah(double tah) {
        this.tah = tah;
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
        return super.toString() + tah;
    }

}
