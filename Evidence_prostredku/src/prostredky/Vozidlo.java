package prostredky;

import java.io.Serializable;

public abstract class Vozidlo implements Serializable, Cloneable {

    private TypVozidlaEnum typ = TypVozidlaEnum.NONE;
    private String SPZ;
    private double hmotnost;
    private double maxSpeed;

    public Vozidlo(String SPZ, double hmotnost, double maxSpeed, TypVozidlaEnum typ) {
        this.SPZ = SPZ;
        this.hmotnost = hmotnost;
        this.maxSpeed = maxSpeed;
        this.typ = typ;
    }

    public TypVozidlaEnum getTyp() {
        return typ;
    }

    public String getSPZ() {
        return SPZ;
    }

    public void setSPZ(String SPZ) {
        this.SPZ = SPZ;
    }

    public double getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(double hmotnost) {
        this.hmotnost = hmotnost;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String str = typ.toString();
        String s = str.charAt(0) + "";

        if (str.contains(" ")) {
            char[] charArr = str.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                if (charArr[i] == ' ') {
                    s += charArr[i + 1];
                    break;
                }
            }
        } else {
            s += str.charAt(1);
        }
        return s + ", " + SPZ + ", " + hmotnost + ", " + maxSpeed + ", ";
    }
}
