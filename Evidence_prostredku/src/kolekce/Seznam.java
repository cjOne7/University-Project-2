package kolekce;

import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Rozhrani predepisuje jednoduche rozhrani pro ruzne implementace seznamu
 * objektu.
 * <p>
 * Rozhrani rozsiruje rozhrani Iterable, ktere ma jednu abstraktni metodu
 * {@code Iterator<T> iterator()} a dalsi dve metody, a to
 * {@code void forEach(Consumer<? super T> action)} a
 * {@code Spliterator<T> spliterator()}, ktere jsou oznaceny jako
 * {@code default}. Tyto defaultni metody zajistuji implicitni chovani vsem
 * implementacnim tridam pro praci s prvky seznamu.
 * <p>
 * <p>
 * Protoze se jedna o univerzalni rozhrani, jsou nektere metody navrzeny jako
 * {@code default}, aby je nektere tridy nemusely implementovat.
 * <p>
 * <p>
 * Metody tohoto rozhrani byly zvoleny tak, aby se procvicila latka z prednasek
 * a cviceni. Dalsim duvodem je to, ze implementace podobnych rozhrani bude
 * vyzadovana v pristim semestru v predmetu datove struktury.
 *
 * @author karel@simerda.cz
 */
public interface Seznam<E> extends Iterable<E> {

    /* =========================================================================
       Zjistovaci metody 
     */
    /**
     * Metoda vrátí maximální velikost seznamu (kapacitu). Pokud to bude mít
     * smysl.
     *
     * @return maximalni pocet mist v seznamu nebo -1, kdyz to nebude mozne
     */
    default public int getVelikost() {
        return -1;
    }

    /**
     * Metoda vrati aktualni pocet vlozenych objektu.
     *
     * @return pocet objektu v seznamu
     */
    int getPocet();

    /**
     * Metoda zjisti, zda seznam obsahuje prvky.
     *
     * @return vraci {@code true}, kdy je seznam neprazdny, jinal {@code false}
     */
    boolean jePrazdny();

    /**
     * Metoda zjisti, zda je seznam plny.
     * <p>
     * <p>
     * Plnost seznamu zavisi na tom, jake jsou pozadavky na implementacni tridy.
     * Toto rozhrani to neresí. Omezeni muze byt dano velikosti maximalnim
     * poctem v parametru konstruktoru, pricemz to nemusi zalezet na tom, zda
     * implementace je na poli nebo zda je realizovana spojovym seznamem.
     *
     * @return vraci {@code true}, kdyz je seznam plny, jinak {@code false}
     */
    default boolean jePlny() {
        return false;
    }

    /* =========================================================================
       Metody pridavani a odebirani datovych prvku ze seznamu.    
    
     */
    /**
     * Vlozi objekt s daty do seznamu na prvni volne misto.
     * <p>
     * <p>
     * U spojoveho seznamu to bude vzdy na konec seznamu. U implementace pomoci
     * pole to bude zalezet na tom, zda jsou prvky po odebrani prvku presunuty
     * nebo ne.
     *
     * @param data vkladany objekt do seznamu
     *
     * @throws KolekceException pokud se vyskytla chyba pri vkladani
     */
    void pridej(E data) throws KolekceException;

    /**
     * Metoda vlozi objekty v parametrech do seznamu
     * <p>
     * <p>
     * Tato metoda slouzi k usnadneni vkladani datovych objektu do seznamu. Tim,
     * ze touto {@code default} metodou budou "vybaveny" vsechny implemetnacni
     * tridy tohoto rozhrani. Samozrejme, ze bude zaviset na tom, zda bude
     * funkcni implemetace metody {@code pridej(E data)}
     *
     * @param data vkladane objekty
     *
     * @throws KolekceException pokud se vyskytla chyba pri vkladani
     */
    default void pridej(E... data) throws KolekceException {
        for (E prvek : data) {
            pridej(prvek);
        }
    }

    /* =========================================================================
      

    /**
     * Metoda nastavi aktualni vnitrni ukazatel na prvni prvek seznamu.
     *
     * @throws KolekceException vyjimka se vystavi, kdyz je seznam prazdny nebo
     * kdyz metoda neni implemenovana ve tride
     */
    default void nastavPrvni() throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda presune ukazatel na aktualni prvek na dalsi v seznamu.
     * <p>
     * Pred volanim teto metody musi byt vzdy nastaven ukazatel na aktualni
     * prvek seznamu.
     *
     * @throws KolekceException vyjimka se vystavi, kdyz je seznam prazdny, neni
     * nastaven aktualni prvek nebo kdyz metoda neni implemenovana ve tride
     */
    default void prejdiNaDalsi() throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda vraci referenci na datovy objekt, na jehoz prvek ukazuje vnitrni
     * aktualni ukazatel.
     *
     * @return datovy prvek seznamu z aktualni pozice seznamu
     *
     * @throws KolekceException vyjimka se vystavi, kdyz je seznam prazdny, neni
     * nastaven aktualni prvek nebo kdyz metoda neni implemenovana ve tride
     */
    default E zpristupni() throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda vraci informaci, zda je k dispozici dalsi aktualni prvek seznamu.
     *
     * @return true, kdyz aktualni prvek je napojen na dalsi prvek v seznamu
     *
     * @throws KolekceException vyjimka se vystavi, kdyz je seznam prazdny, neni
     * nastaven aktualni prvek nebo kdyz metoda neni implemenovana ve tride
     */
    default boolean jeDalsi() throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda vlozi datovy objekt jako novy za aktualni prvek
     *
     * @param data vkladany datovy objekt do seznamu
     *
     * @throws KolekceException vystavi vyjimku, kdyz neni nastaven aktualni
     * prvek nebo kdyz neni metoda implementovana
     */
    default void vlozZa(E data) throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda vlozi datovy objekt jako novy pred aktualni prvek
     *
     *
     * @param data vkladany datovy objekt do seznamu
     *
     * @throws KolekceException vystavi vyjimku, kdyz neni nastaven aktualni
     * prvek nebo kdyz neni metoda implementovana
     */
    default void vlozPred(E data) throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda odebere aktualni prvek ze seznamu.
     * <p>
     * Po odebrani prvku je seznam stale spojity. Kdyz se odebere prvni, tak
     * nasledujici se automaticky stane prvnim. Kdyz se odebere prvek uprostred
     * seznamu, tak dojde ke spojeni predchoziho s nasledujicim prvkem. Ukazatel
     * aktualniho prvku je po odebrani nedefinovan. Musi se znovu nastavit
     * ukazatel na prvni polozku a prejit na nove pozadované misto.
     *
     * @return pokud je prvek nalezen vraci se reference na odebirany datovy
     * prvek, jinak se vraci null
     *
     * @throws KolekceException vyjimka se vystavi, kdyz je seznam prazdny, neni
     * nastaven aktualni prvek nebo kdyz metoda neni implemenovana ve tride.
     */
    default E odeber() throws KolekceException {
        throw new KolekceException("Metoda neni implementovana");
    }

    /**
     * Metoda zrusi obsah seznamu
     */
    void zrus();


    /* =========================================================================
       Metody prevodu obsahu seznamu na pole objektu s typem prvku Object nebo  
       s typem, ktery je dan typovym parametrem tridy Seznam.
    
     */
    /**
     * Metoda vrati pole s kopiemi datovych prvku seznamu o delce presne
     * odpovidajici poctu vlozenych objektu. Typ prvku pole bude Object, protoze
     * typ prvku pole nelze zmenit.
     *
     * @return pole objektu
     */
    E[] toArray() throws CloneNotSupportedException;

    /**
     * Metoda vrati pole s kopiemi datovych prvku seznamu o delce presne
     * odpovidajici poctu vlozenych objektu. Pole bude mit prvky stejneho typu
     * jako pole v parametru.
     *
     * @param array vzorove pole s ocekavanym typem prvku pole
     *
     * @return pole objektu
     *
     * @throws IllegalArgumentException vystavi vyjimku, kdyz pole je mensi nez
     * pocet prvku v seznamu
     */
    E[] toArray(E[] array) throws IllegalArgumentException, CloneNotSupportedException;

    /**
     * Metoda vrati pole s kopiemi datovych prvku seznamu o delce presne
     * odpovidajici poctu vlozenych objektu.Pole bude mit prvky typu E
     *
     * @param createFunction funkce na vytvoreni pole skutecnym typem prvku
     *
     * @return pole s kopiemi datovych prvku seznamu
     * @throws java.lang.CloneNotSupportedException
     */
    E[] toArray(Function<Integer, E[]> createFunction) throws CloneNotSupportedException;

    /* =========================================================================
       Metody prevodu obsahu seznamu na datovy proud objektu
    
     */
    /**
     * Metoda prevede obsah seznamu na datovy proud, ktery preda pri navratu.
     * Tato metoda se nepřetěžuje.
     *
     * @return datovy proud
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
