package prostredky;

import java.io.Serializable;

public class Dodavka extends Vozidlo implements Serializable, Cloneable {

    private double nosnost;

    private final TypVozidlaEnum typ = TypVozidlaEnum.DODAVKA;

    public Dodavka(double nosnost, String SPZ, double hmotnost, double maxSpeed) {
        super(SPZ, hmotnost, maxSpeed, TypVozidlaEnum.DODAVKA);
        this.nosnost = nosnost;
    }

    public double getNosnost() {
        return nosnost;
    }

    public void setNosnost(double nosnost) {
        this.nosnost = nosnost;
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
        return super.toString() + nosnost;
    }

}
