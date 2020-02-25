package kolekce;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class SpojovySeznam<E> implements Seznam<E>, Serializable, Cloneable {

    private int maxValue;

    private Node prvni;
    private Node posledni;

    private Node aktualni;

    private int pocet;

    public SpojovySeznam() {
        this.maxValue = 100;
    }

    public SpojovySeznam(int maxValue) throws KolekceException {
        if (maxValue < 0) {
            throw new KolekceException("Illegal Capacity: " + maxValue);
        }
        this.maxValue = maxValue;
    }

    @Override
    public int getVelikost() {
        return maxValue;
    }

    @Override
    public int getPocet() {
        return pocet;
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    @Override
    public boolean jePlny() {
        return pocet >= maxValue;
    }

    @Override
    public void pridej(E data) throws KolekceException {
        if (jePlny()) {
            throw new KolekceException("The maximum allowed number of elements is filled!");
        }
        if (data == null) {
            throw new NullPointerException("Your element is null. " + data);
        }
        if (prvni == null) {
            prvni = new Node(data);
            posledni = prvni;
            pocet++;
            nastavPrvni();
        } else {
            Node node = new Node(data);
            posledni.setNext(node);
            posledni = node;
            pocet++;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (prvni != null) {
            aktualni = prvni;
        } else {
            throw new KolekceException("Have an empty list!");
        }
    }

    @Override
    public void prejdiNaDalsi() throws KolekceException {
        if (aktualni == null) {
            nastavPrvni();
        }
        if (aktualni.next != null) {
            aktualni = aktualni.next;
        } else {
            throw new KolekceException("Dalsi prvek neexistuje");
        }
    }

    @Override
    public E zpristupni() throws KolekceException {
        if (aktualni.value != null) {
            return (E) aktualni.value;
        } else {
            throw new KolekceException("Current element's data is null");
        }
    }

    @Override
    public boolean jeDalsi() throws KolekceException {
        if (aktualni == null) {
            nastavPrvni();
        }
        if (prvni == null) {
            throw new KolekceException("Have an empty list!");
        }

        return aktualni.next != null;

    }

    @Override
    public void vlozZa(E data) throws KolekceException {
        if (data == null) {
            throw new KolekceException("Received element is null!" + data);
        }
        if (aktualni == null) {
            if (prvni == null) {
                pridej(data);
                nastavPrvni();
            } else {
                nastavPrvni();
            }
        } else {
            Node node = new Node(data);
            if (aktualni.next != null) {
                node.setNext(aktualni.getNext());
                aktualni.next = node.next;
            } else {
                aktualni.next = node.next;
            }
        }
    }

    @Override
    public void vlozPred(E data) throws KolekceException {
        if (data == null) {
            throw new KolekceException("Received element is null!" + data);
        }
        if (aktualni == null) {
            if (prvni == null) {
                pridej(data);
            } else {
                nastavPrvni();
            }
        } else {
            Node node = new Node(data);
            Node node1 = prvni;
            while (node1.next != aktualni) {
                node1 = node1.getNext();
            }
            node1.setNext(node);
            node.setNext(aktualni);
        }
    }

    @Override
    public E odeber() throws KolekceException {
        if (aktualni == null) {
            nastavPrvni();
        }
        if (prvni == null) {
            throw new KolekceException("Have an empty list!");
        }
        Node node = null;
        if (prvni == aktualni) {
            node = prvni;
            prvni = prvni.next;
            pocet--;
            return node.value;
        } else {
            node = prvni;
            while (node.next != aktualni) {
                node = node.next;
            }
            Node deletedNode = node.next;
            node.next = aktualni.next;
            pocet--;
            return deletedNode.value;
        }
    }

    @Override
    public void zrus() {
        if (!jePrazdny()) {
            pocet = 0;
            prvni = posledni = aktualni = null;
        }
    }

    @Override
    public E[] toArray() throws CloneNotSupportedException {
        Node node = prvni;
        Object[] obj = new Object[pocet];
        int index = 0;
        while (node != null) {
            obj[index++] = node.getValue();
            node = node.next;
        }
        return (E[]) obj;
    }

    @Override
    public E[] toArray(E[] array) throws IllegalArgumentException, CloneNotSupportedException {
        Objects.requireNonNull(array);

        int index = 0;
        Node node = prvni;
        while (node != null) {
            if (node.value.getClass() == array.getClass().getComponentType()) {
                index++;
            }
            node = node.next;
        }
        Object[] obj = new Object[index];
        index = 0;
        node = prvni;
        while (node != null) {
            if (node.value.getClass() == array.getClass().getComponentType()) {
                obj[index++] = node;
            }
            node = node.next;
        }
        return (E[]) obj;
    }

    @Override
    public E[] toArray(Function<Integer, E[]> createFunction) throws CloneNotSupportedException {
        return createFunction.apply(pocet);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node current = prvni;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E prvek = null;
                if (hasNext()) {
                    prvek = (E) current.value;
                    current = current.next;
                } else {
                    throw new NoSuchElementException();
                }
                return prvek;
            }
        };
    }

    public class Node implements Serializable {

        private Node next;
        private E value;

        public Node(E value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}