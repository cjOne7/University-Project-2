package kolekce;

import java.util.Iterator;
import java.util.function.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import prostredky.*;

public class SeznamNaPoliTest {

    public SeznamNaPoliTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetVelikost() {
        System.out.println("getVelikost");
        SeznamNaPoli instance = new SeznamNaPoli();
        assertEquals(100, instance.getVelikost());
    }

    @Test
    public void testGetVelikost1() throws KolekceException {
        System.out.println("getVelikost");
        SeznamNaPoli instance = new SeznamNaPoli(20);
        assertEquals(20, instance.getVelikost());
    }

    @Test
    public void testGetPocet() throws KolekceException {
        System.out.println("getPocet");
        SeznamNaPoli instance = new SeznamNaPoli();

        instance.pridej(new Object());
        instance.pridej(new Object());
        instance.pridej(new Object());

        assertEquals(3, instance.getPocet());

    }

    @Test
    public void testGetPocet1() throws KolekceException {
        System.out.println("getPocet");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(new Object(), new Object(), new Object());
        assertEquals(3, instance.getPocet());
    }

    @Test
    public void testJePrazdny() {
        System.out.println("jePrazdny");
        SeznamNaPoli instance = new SeznamNaPoli();
        assertEquals(true, instance.jePrazdny());
    }

    @Test
    public void testJePrazdny1() throws KolekceException {
        System.out.println("jePrazdny");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(new Object());
        assertEquals(false, instance.jePrazdny());
    }

    @Test
    public void testJePlny() {
        System.out.println("jePlny");
        SeznamNaPoli instance = new SeznamNaPoli();
        assertEquals(false, instance.jePlny());
    }

    @Test
    public void testPridej_1args_1() throws KolekceException {
        System.out.println("pridej");
        Object data = new Object();
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(data);
    }

    @Test(expected = NullPointerException.class)
    public void testPridej_1args_2() throws KolekceException {
        System.out.println("pridej");
        Object data = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testPridej_1args_3() throws KolekceException {
        System.out.println("pridej");
        Object data = new Object();
        SeznamNaPoli instance = new SeznamNaPoli(1);
        instance.pridej(data, data);
        fail();
    }

    @Test
    public void testPridej_1args_4() throws KolekceException {
        System.out.println("pridej");
        Object[] data = new Object[0];
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(data);
    }

    @Test(expected = NullPointerException.class)
    public void testPridej_1args_5() throws KolekceException {
        System.out.println("pridej");
        Object[] data = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testNastavPrvni() throws KolekceException {
        System.out.println("nastavPrvni");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.nastavPrvni();
        fail();
    }

    @Test
    public void testNastavPrvni1() throws KolekceException {
        System.out.println("nastavPrvni");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(new Object());
        instance.nastavPrvni();
    }

    @Test(expected = KolekceException.class)
    public void testPrejdiNaDalsi() throws KolekceException {
        System.out.println("prejdiNaDalsi");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.prejdiNaDalsi();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testZpristupni() throws KolekceException {
        System.out.println("zpristupni");
        SeznamNaPoli instance = new SeznamNaPoli();
        Object expResult = null;
        Object result = instance.zpristupni();
        assertEquals(expResult, result);
        fail();
    }

    @Test
    public void testZpristupni1() throws KolekceException {
        System.out.println("zpristupni");
        SeznamNaPoli<Vozidlo> instance = new SeznamNaPoli();
        Vozidlo vozidlo = new OsobniAutomobil(0, "abcdef", 0, 0);
        instance.pridej(vozidlo);
        instance.nastavPrvni();
        assertEquals(vozidlo.getTyp(), instance.zpristupni().getTyp());
    }

    @Test
    public void testZpristupni2() throws KolekceException {
        System.out.println("zpristupni");
        SeznamNaPoli<Vozidlo> instance = new SeznamNaPoli();
        Vozidlo vozidlo = new NakladniAutomobil(10, "abcdef", 10, 10);
        instance.pridej(new OsobniAutomobil(0, "abcdef", 0, 0),
                new Dodavka(20, "abcdef", 20, 20),
                new Traktor(30, "abcdef", 30, 30),
                new NakladniAutomobil(40, "abcdef", 40, 40));
        instance.nastavPrvni();
        while (instance.jeDalsi()){
            instance.prejdiNaDalsi();
        }
        assertEquals(vozidlo.getTyp(), instance.zpristupni().getTyp());
    }

    @Test(expected = KolekceException.class)
    public void testJeDalsi() throws KolekceException {
        System.out.println("jeDalsi");
        SeznamNaPoli instance = new SeznamNaPoli();
        assertEquals(false, instance.jeDalsi());
        fail();
    }

    @Test
    public void testJeDalsi1() throws KolekceException {
        System.out.println("jeDalsi");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(new Object(), new Object());
        instance.nastavPrvni();
        assertEquals(true, instance.jeDalsi());
    }

    @Test(expected = KolekceException.class)
    public void testVlozZa() throws KolekceException {
        System.out.println("vlozZa");
        Object data = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.vlozZa(data);
        fail();
    }

    @Test
    public void testVlozZa1() throws KolekceException {
        System.out.println("vlozZa");
        Object data = new Object();
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.vlozZa(data);
    }

    @Test(expected = KolekceException.class)
    public void testVlozPred() throws KolekceException {
        System.out.println("vlozPred");
        Object data = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.vlozPred(data);
        fail();
    }

    @Test
    public void testVlozPred1() throws KolekceException {
        System.out.println("vlozPred");
        Object data = new Object();
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.vlozPred(data);
    }

    @Test(expected = KolekceException.class)
    public void testOdeber() throws KolekceException {
        System.out.println("odeber");
        SeznamNaPoli instance = new SeznamNaPoli();
        assertEquals(null, instance.odeber());
        fail();
    }

    @Test
    public void testOdeber1() throws KolekceException {
        System.out.println("odeber");
        SeznamNaPoli instance = new SeznamNaPoli();
        Object object = new Object();
        instance.pridej(object);
        instance.nastavPrvni();
        Object result = instance.zpristupni();
        instance.odeber();
        Object expResult = object;

        assertEquals(expResult, result);
    }

    @Test
    public void testZrus() throws KolekceException {
        System.out.println("zrus");
        SeznamNaPoli instance = new SeznamNaPoli();
        instance.pridej(new Object(), new Object());
        assertEquals(2, instance.getPocet());
        instance.zrus();

        assertEquals(0, instance.getPocet());
        assertTrue(instance.jePrazdny());
        assertFalse(instance.jePlny());
    }

    @Test
    public void testToArray_0args() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        SeznamNaPoli instance = new SeznamNaPoli();
        Object[] result = instance.toArray();
        assertNotNull(result);
    }

    @Test
    public void testToArray_1args() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        SeznamNaPoli<Vozidlo> instance = new SeznamNaPoli();
        Vozidlo vozidlo = new Dodavka(10, "dodavka", 20, 30);
        Vozidlo vozidlo1 = new OsobniAutomobil(40, "osobni", 50, 60);
        instance.pridej(vozidlo, vozidlo1);

        Vozidlo[] vozidla = new Vozidlo[instance.getPocet()];
        vozidla[0] = vozidlo;
        vozidla[1] = vozidlo1;

        Object[] expResult = instance.toArray();
        assertEquals(expResult[0], vozidla[0]);
    }

    @Test
    public void testToArray_GenericType() throws Exception {
        System.out.println("toArray");
        SeznamNaPoli instance = new SeznamNaPoli();
        Vozidlo[] v = new Vozidlo[0];
        Object[] result = instance.toArray(v);
        assertNotNull(result);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testToArray_GenericType1() throws Exception {
        System.out.println("toArray");
        SeznamNaPoli<Vozidlo> instance = new SeznamNaPoli();
        Dodavka[] d = new Dodavka[0];
        Vozidlo vozidlo = new Dodavka(10, "dodavka", 20, 30);
        Vozidlo vozidlo1 = new OsobniAutomobil(40, "osobni", 50, 60);
        instance.pridej(vozidlo, vozidlo1);

        Object[] voz = instance.toArray(d);
        Dodavka dod = (Dodavka) voz[0];
        assertEquals(dod, vozidlo);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testToArray_Function() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        Function createFunction = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        Object[] expResult = null;
        Object[] result = instance.toArray(createFunction);
        assertArrayEquals(expResult, result);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testToArray_Function1() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        Function createFunction = null;
        SeznamNaPoli instance = new SeznamNaPoli();
        Object[] expResult = new Object[instance.getPocet()];
        Object[] result = instance.toArray(createFunction);
        assertArrayEquals(expResult, result);
        fail();
    }

    @Test
    public void testToArray_Function2() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        Function<Integer, Vozidlo[]> createFunction = (size) -> new Vozidlo[size];
        SeznamNaPoli instance = new SeznamNaPoli();
        Object[] expResult = new Object[instance.getPocet()];
        Object[] result = instance.toArray(createFunction);

        assertArrayEquals(expResult, result);
    }

    @Test
    public void testIterator() {
        System.out.println("iterator");
        SeznamNaPoli instance = new SeznamNaPoli();
        Iterator result = instance.iterator();
        assertNotNull(result);
    }

}
