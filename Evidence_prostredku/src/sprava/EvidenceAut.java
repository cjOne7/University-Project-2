package sprava;

import gui.EvidenceProstredkuYaroshNikita;
import java.io.BufferedWriter;
import java.io.File;
import kolekce.KolekceException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.*;

public class EvidenceAut<E> implements Ovladani<E>, Serializable, Cloneable {

    private Seznam<E> seznam;
    private Comparator<? super E> comparator;

    public EvidenceAut(SeznamNaPoli<E> naPoli) {
        seznam = naPoli;
    }

    public EvidenceAut(SpojovySeznam<E> spojovySeznam) {
        seznam = spojovySeznam;
    }

    public Seznam<E> getSeznam() {
        return seznam;
    }

    @Override
    public void vytvorSeznam(Supplier<Seznam<E>> supplier) {
        seznam = supplier.get();
    }

    @Override
    public void vytvorSeznam(Function<Integer, Seznam<E>> funtion, int size) throws KolekceException {
        seznam = funtion.apply(size);
    }

    @Override
    public void nastavKomparator(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void nastavErrorLog(Consumer<String> errorLog) {
        Ovladani.super.nastavErrorLog(errorLog);
    }

    @Override
    public void vlozPolozku(E data) throws NullPointerException {
        Objects.requireNonNull(data, "Data: " + data + " is null");
        Objects.requireNonNull(seznam, "Seznam: " + seznam + " is null");
        try {
            seznam.pridej(data);
        } catch (KolekceException ex) {
            KolekceException exx = new KolekceException("Object " + data + " is null");
        }
    }

    @Override
    public E najdiPolozku(E klic) {
        Objects.requireNonNull(klic);
        Objects.requireNonNull(seznam);

        if (seznam.jePrazdny()) {
            return null;
        }
        Iterator<E> it = seznam.iterator();
        while (it.hasNext()) {
            E prvek = it.next();
            if (prvek.equals(klic)) {
                return prvek;
            }
        }
        return null;
    }

    @Override
    public void prejdiNaPrvniPolozku() {
        Objects.requireNonNull(seznam);
        try {
            seznam.nastavPrvni();
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prejdiNaPosledniPolozku() {
        Objects.requireNonNull(seznam);
        try {
            Iterator<E> it = seznam.iterator();
            while (it.hasNext()) {
                seznam.prejdiNaDalsi();
            }
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void prejdiNaDalsiPolozku() {
        Objects.requireNonNull(seznam);
        try {
            seznam.prejdiNaDalsi();
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nastavAktualniPolozku(E klic) {
        Objects.requireNonNull(seznam);
        Objects.requireNonNull(klic);
        try {
            seznam.nastavPrvni();
            Iterator<E> it = seznam.iterator();
            while (it.hasNext()) {
                E prvek = it.next();
                if (prvek.equals(klic)) {
                    break;
                }
                seznam.prejdiNaDalsi();
            }
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public E vyjmiAktualniPolozku() throws OvladaniException {
        Objects.requireNonNull(seznam);
        try {
            E prvek = seznam.zpristupni();
            seznam.odeber();
            return prvek;
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public E dejKopiiAktualnPolozky() {
        Objects.requireNonNull(seznam);
        try {
            return (E) seznam.zpristupni();
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void editujAktualniPolozku(Function<E, E> editor) {
        Objects.requireNonNull(seznam);
        try {
            editor.apply(seznam.zpristupni());
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ulozDoSouboru(String soubor) {
        Objects.requireNonNull(seznam);
        try (ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(new FileOutputStream(soubor, false))) {
            System.out.println("\nStart writing in file " + soubor + "... .");

            objectOutputStream.writeInt(seznam.getPocet());
            objectOutputStream.writeInt(seznam.getVelikost());

            Iterator<E> it = seznam.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }

            System.out.println("Your file is successfully written.\n" + "Written: " + seznam.getPocet() + " objects.");

        } catch (FileNotFoundException ex) {
            KolekceException exx = new KolekceException("File " + soubor + " not found");
        } catch (IOException ex) {
            KolekceException exx = new KolekceException("File " + soubor + " do not exist");
        }
    }

    @Override
    public void nactiZeSouboru(String soubor) {
        Objects.requireNonNull(seznam);
        seznam.zrus();
        try (ObjectInputStream objectInputStream
                = new ObjectInputStream(new FileInputStream(soubor))) {

            System.out.println("\nStart reading file " + soubor + "... .");

            int pocet = objectInputStream.readInt();
            int velikost = objectInputStream.readInt();

            for (int i = 0; i < pocet; i++) {
                E prvek = (E) objectInputStream.readObject();
                seznam.pridej(prvek);
            }

            System.out.println("Your file is successfully read.\n" + "Read: " + seznam.getPocet() + " objekts.");

        } catch (FileNotFoundException ex) {
            KolekceException exx = new KolekceException("File " + soubor + " not found");
        } catch (IOException ex) {
            KolekceException exx = new KolekceException("File " + soubor + " do not exist");
        } catch (ClassNotFoundException ex) {
            KolekceException exx = new KolekceException("Class not found");
        } catch (KolekceException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void vypis(Consumer<E> writer) {
        Objects.requireNonNull(seznam);
        Iterator<E> it = seznam.iterator();
        while (it.hasNext()) {
            E prvek = it.next();
            writer.accept(prvek);
        }
    }

    @Override
    public void nactiTextSoubor(String soubor, Function<String, E> mapper) {
        Objects.requireNonNull(seznam);
        seznam.zrus();
        Scanner input;
        try {
            System.out.println("\nStart reading file " + soubor + "... .");

            input = new Scanner(new File(soubor));
            while (input.hasNextLine()) {
                String str = input.nextLine();
                seznam.pridej(mapper.apply(str));
            }
            input.close();

            System.out.println("Your file is successfully read.\n" + "Read: " + seznam.getPocet() + " objekts.");
        } catch (FileNotFoundException | KolekceException | NullPointerException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ulozTextSoubor(String soubor, Function<E, String> mapper) {
        Objects.requireNonNull(seznam);
        PrintWriter printWriter;
        try {
            System.out.println("\nStart writing in file " + soubor + "... .");
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(soubor))));

            Iterator<E> it = seznam.iterator();
            while (it.hasNext()) {
                E prvek = it.next();
                printWriter.println(mapper.apply(prvek));
            }
            printWriter.close();
            System.out.println("Your file is successfully written.\n" + "Written: " + seznam.getPocet() + " objects.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void generujData(int pocetProstredku) {
        Objects.requireNonNull(seznam);

        seznam.zrus();
        EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);

        RandomObjectsGenerator randomObjectsGenerator = new RandomObjectsGenerator();

        if (pocetProstredku <= seznam.getVelikost()) {
            for (int i = 0; i < pocetProstredku; i++) {
                E v = (E) randomObjectsGenerator.generatorVozidel(seznam);
                try {
                    seznam.pridej(v);
                } catch (KolekceException ex) {
                    Logger.getLogger(EvidenceAut.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            EvidenceProstredkuYaroshNikita.obnovSeznam(seznam);
        } else {
            System.out.println("List is full!"
                    + " Your list contains more items than "
                    + seznam.getVelikost()
                    + ".\nPlease generate a smaller number of elements."
                    + "\nYour entered number of generated elements is "
                    + pocetProstredku);
        }
    }

    @Override
    public int dejAktualniPocetPolozek() {
        Objects.requireNonNull(seznam);
        return seznam.getPocet();
    }

    @Override
    public int dejPocetPolozekMax() {
        Objects.requireNonNull(seznam);
        return seznam.getVelikost();
    }

}
