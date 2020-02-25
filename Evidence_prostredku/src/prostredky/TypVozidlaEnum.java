package prostredky;

import java.io.Serializable;

public enum TypVozidlaEnum implements Serializable, Cloneable{
    OSOBNI_AUTOMOBIL("osobní automobil"),
    TRAKTOR("traktor"),
    DODAVKA("dodávka"),
    NAKLADNI_AVTOMOBIL("nákladní automobil"),
    NONE("none");

    private final String name;

    private TypVozidlaEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
