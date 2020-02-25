package kolekce;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class SeznamNaPoli<E> implements Seznam<E>, Serializable, Cloneable {

    private static int MAX_VALUE;
    private static final int DEFAULT_CAPACITY = 10;

    private E[] arrayProstredku;
    private int currentIndex = 0;
    int indexPrejdiNaDalsi;

    private E aktualni;

    private int indexAktualni = 0;

    public SeznamNaPoli() {
        this.arrayProstredku = (E[]) new Object[DEFAULT_CAPACITY];
        MAX_VALUE = 100;
    }

    public SeznamNaPoli(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + size);
        }
        MAX_VALUE = size;
        this.arrayProstredku = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int getVelikost() {
        return MAX_VALUE;
    }

    @Override
    public int getPocet() {
        return currentIndex;
    }

    @Override
    public boolean jePrazdny() {
        return currentIndex == 0;
    }

    @Override
    public boolean jePlny() {
        return currentIndex >= MAX_VALUE;
    }

    @Override
    public void pridej(E data) throws KolekceException {
        if (jePlny()) {
            throw new KolekceException("The maximum allowed number of elements is filled!");
        }
        if (data == null) {
            throw new NullPointerException("Your element is null.");
        }
        if (currentIndex == arrayProstredku.length) {
            E[] newArray = (E[]) new Object[arrayProstredku.length * 2];
            System.arraycopy(arrayProstredku, 0, newArray, 0, arrayProstredku.length);
            arrayProstredku = newArray;
        }
        arrayProstredku[currentIndex++] = data;
    }

    @Override
    public void pridej(E... data) throws KolekceException {
        for (E e : data) {
            pridej(e);
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (arrayProstredku[0] != null) {
            aktualni = arrayProstredku[0];
            indexAktualni = 0;
            indexPrejdiNaDalsi = 0;
        } else {
            throw new KolekceException("Have an empty list!");
        }
    }

    @Override
    public void prejdiNaDalsi() throws KolekceException {
        if (aktualni == null) {
            nastavPrvni();
        }
        if (arrayProstredku[indexPrejdiNaDalsi + 1] != null) {
            aktualni = arrayProstredku[indexPrejdiNaDalsi + 1];
            indexAktualni = indexPrejdiNaDalsi + 1;
            indexPrejdiNaDalsi++;
        } else {
            throw new KolekceException("Dalsi prvek neexistuje");
        }
    }

    @Override
    public E zpristupni() throws KolekceException {
        if (aktualni != null) {
            return (E) aktualni;
        } else {
            throw new KolekceException("Current element's data is null");
        }
    }

    @Override
    public boolean jeDalsi() throws KolekceException {
        if (arrayProstredku[indexAktualni + 1] != null) {
            return true;
        }
        if (arrayProstredku[0] == null) {
            throw new KolekceException("Have an empty list!");
        }
        if (aktualni == null) {
            nastavPrvni();
        }
        return false;
    }

    @Override
    public void vlozZa(E data) throws KolekceException {
        if (data == null) {
            throw new KolekceException("Received element is null!" + data);
        }
        if (aktualni == null) {
            if (arrayProstredku[0] == null) {
                pridej(data);
                nastavPrvni();
            } else {
                nastavPrvni();
            }
        } else {
            E[] newArray = (E[]) new Object[arrayProstredku.length + 2];
            System.arraycopy(arrayProstredku, 0, newArray, 0, indexAktualni);
            newArray[indexAktualni + 1] = data;
            System.arraycopy(arrayProstredku, indexAktualni + 1, newArray, indexAktualni + 2, this.currentIndex - indexAktualni + 1);
            arrayProstredku = newArray;
        }
    }

    @Override
    public void vlozPred(E data) throws KolekceException {
        if (data == null) {
            throw new KolekceException("Received element is null!" + data);
        }
        if (aktualni == null) {
            if (arrayProstredku[0] == null) {
                pridej(data);
                nastavPrvni();
            } else {
                nastavPrvni();
            }
        } else {
            E[] newArray = (E[]) new Object[arrayProstredku.length + 2];
            System.arraycopy(arrayProstredku, 0, newArray, 0, indexAktualni - 1);
            newArray[indexAktualni] = data;
            System.arraycopy(arrayProstredku, indexAktualni, newArray, indexAktualni + 1, currentIndex - indexAktualni + 1);
            arrayProstredku = newArray;
        }
    }

    @Override
    public E odeber() throws KolekceException {
        if (aktualni == null) {
            nastavPrvni();
        }
        if (arrayProstredku[0] == null) {
            throw new KolekceException("Have an empty list!");
        }
        E removedElement = this.arrayProstredku[indexAktualni];
        System.arraycopy(this.arrayProstredku, indexAktualni + 1, this.arrayProstredku, indexAktualni, this.currentIndex - this.indexAktualni);
        this.arrayProstredku[this.currentIndex] = null;
        this.currentIndex--;

        if (arrayProstredku[0] != null) {
            nastavPrvni();
        } else {
            aktualni = null;
            indexAktualni = 0;
        }
        return removedElement;
    }

    @Override
    public void zrus() {
        if (!jePrazdny()) {
            currentIndex = indexAktualni = 0;
            arrayProstredku = null;
            aktualni = null;
        }
    }

    @Override
    public E[] toArray() throws CloneNotSupportedException {
        return arrayProstredku.clone();
    }

    @Override
    public E[] toArray(E[] array) throws IllegalArgumentException, CloneNotSupportedException {
        Objects.requireNonNull(array);
        int index = 0;
        for (int i = 0; i < currentIndex; i++) {
            if (this.arrayProstredku.getClass() == array.getClass().getComponentType()) {
                index++;
            }
        }
        Object[] obj = new Object[index];
        index = 0;
        for (int i = 0; i < obj.length; i++) {
            if (this.arrayProstredku.getClass() == array.getClass().getComponentType()) {
                obj[i] = array[i];
            }
        }
        return (E[]) obj;
    }

    @Override
    public E[] toArray(Function<Integer, E[]> createFunction) throws CloneNotSupportedException {
        return createFunction.apply(currentIndex);
    }

    @Override
    public Stream<E> stream() {
        return Seznam.super.stream();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < currentIndex;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return arrayProstredku[index++];
            }
        };
    }
}
