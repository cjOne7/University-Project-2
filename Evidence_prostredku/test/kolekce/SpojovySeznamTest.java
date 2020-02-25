package kolekce;

import java.util.Iterator;
import java.util.function.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import prostredky.*;

public class SpojovySeznamTest {

    public SpojovySeznamTest() {
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
        SpojovySeznam instance = new SpojovySeznam();
        int expResult = 100;
        int result = instance.getVelikost();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetVelikost1() throws KolekceException {
        System.out.println("getVelikost");
        SpojovySeznam instance = new SpojovySeznam(20);
        int expResult = 20;
        assertEquals(expResult, instance.getVelikost());
    }

    @Test
    public void testGetPocet() throws KolekceException {
        System.out.println("getPocet");
        SpojovySeznam instance = new SpojovySeznam();
        int expResult = 3;

        instance.pridej(new Object());
        instance.pridej(new Object());
        instance.pridej(new Object());

        int result = instance.getPocet();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPocet1() throws KolekceException {
        System.out.println("getPocet");
        SpojovySeznam instance = new SpojovySeznam();
        int expResult = 3;
        instance.pridej(new Object(), new Object(), new Object());
        int result = instance.getPocet();
        assertEquals(expResult, result);
    }

    @Test
    public void testJePrazdny() {
        System.out.println("jePrazdny");
        SpojovySeznam instance = new SpojovySeznam();
        boolean expResult = true;
        boolean result = instance.jePrazdny();
        assertEquals(expResult, result);
    }

    @Test
    public void testJePrazdny1() throws KolekceException {
        System.out.println("jePrazdny");
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(new Object());
        boolean expResult = false;
        boolean result = instance.jePrazdny();
        assertEquals(expResult, result);
    }

    @Test
    public void testJePlny() {
        System.out.println("jePlny");
        SpojovySeznam instance = new SpojovySeznam();
        boolean expResult = false;
        boolean result = instance.jePlny();
        assertEquals(expResult, result);
    }

    @Test
    public void testPridej_1args_1() throws KolekceException {
        System.out.println("pridej");
        Object data = new Object();
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(data);
    }

    @Test(expected = NullPointerException.class)
    public void testPridej_1args_2() throws KolekceException {
        System.out.println("pridej");
        Object data = null;
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testPridej_1args_3() throws KolekceException {
        System.out.println("pridej");
        Object data = new Object();
        SpojovySeznam instance = new SpojovySeznam(1);
        instance.pridej(data, data);
        fail();
    }

    @Test
    public void testPridej_1args_4() throws KolekceException {
        System.out.println("pridej");
        Object[] data = new Object[0];
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(data);
    }

    @Test(expected = NullPointerException.class)
    public void testPridej_1args_5() throws KolekceException {
        System.out.println("pridej");
        Object[] data = null;
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testNastavPrvni() throws KolekceException {
        System.out.println("nastavPrvni");
        SpojovySeznam instance = new SpojovySeznam();
        instance.nastavPrvni();
        fail();
    }

    @Test
    public void testNastavPrvni1() throws KolekceException {
        System.out.println("nastavPrvni");
        SpojovySeznam instance = new SpojovySeznam();
        instance.pridej(new Object());
        instance.nastavPrvni();
    }

    @Test(expected = KolekceException.class)
    public void testPrejdiNaDalsi() throws KolekceException {
        System.out.println("prejdiNaDalsi");
        SpojovySeznam instance = new SpojovySeznam();
        instance.nastavPrvni();
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testZpristupni() throws KolekceException {
        System.out.println("zpristupni");
        SpojovySeznam instance = new SpojovySeznam();
        Object expResult = null;
        Object result = instance.zpristupni();
        assertEquals(expResult, result);
        fail();
    }

    @Test
    public void testZpristupni1() throws KolekceException {
        System.out.println("zpristupni");
        SpojovySeznam<Vozidlo> instance = new SpojovySeznam();
        Vozidlo vozidlo = new OsobniAutomobil(0, "abcdef", 0, 0);
        instance.pridej(vozidlo);

        assertEquals(vozidlo.getTyp(), instance.zpristupni().getTyp());
    }

    @Test
    public void testZpristupni2() throws KolekceException {
        System.out.println("zpristupni");
        SpojovySeznam<Vozidlo> instance = new SpojovySeznam();
        Vozidlo vozidlo = new NakladniAutomobil(10, "abcdef", 10, 10);
        instance.pridej(new OsobniAutomobil(0, "abcdef", 0, 0),
                new Dodavka(20, "abcdef", 20, 20),
                new Traktor(30, "abcdef", 30, 30),
                new NakladniAutomobil(40, "abcdef", 40, 40));
        instance.nastavPrvni();
        while (instance.jeDalsi()) {
            instance.prejdiNaDalsi();
        }
        assertEquals(vozidlo.getTyp(), instance.zpristupni().getTyp());
    }

    @Test(expected = KolekceException.class)
    public void testJeDalsi() throws KolekceException {
        System.out.println("jeDalsi");
        SpojovySeznam instance = new SpojovySeznam();

        boolean expResult = false;
        boolean result = instance.jeDalsi();
        assertEquals(expResult, result);
        fail();
    }

    @Test
    public void testJeDalsi1() throws KolekceException {
        System.out.println("jeDalsi");
        SpojovySeznam instance = new SpojovySeznam();

        instance.pridej(new Object(), new Object());
        instance.nastavPrvni();

        boolean expResult = true;
        boolean result = instance.jeDalsi();
        assertEquals(expResult, result);
    }

    @Test(expected = KolekceException.class)
    public void testVlozZa() throws KolekceException {
        System.out.println("vlozZa");
        Object data = null;
        SpojovySeznam instance = new SpojovySeznam();
        instance.vlozZa(data);
        fail();
    }

    @Test
    public void testVlozZa1() throws KolekceException {
        System.out.println("vlozZa");
        Object data = new Object();
        SpojovySeznam instance = new SpojovySeznam();
        instance.vlozZa(data);
    }

    @Test(expected = KolekceException.class)
    public void testVlozPred() throws KolekceException {
        System.out.println("vlozPred");
        Object data = null;
        SpojovySeznam instance = new SpojovySeznam();
        instance.vlozPred(data);
        fail();
    }

    @Test
    public void testVlozPred1() throws KolekceException {
        System.out.println("vlozPred");
        Object data = new Object();
        SpojovySeznam instance = new SpojovySeznam();
        instance.vlozPred(data);
    }

    @Test(expected = KolekceException.class)
    public void testOdeber() throws KolekceException {
        System.out.println("odeber");
        SpojovySeznam instance = new SpojovySeznam();
        Object expResult = null;
        Object result = instance.odeber();
        assertEquals(expResult, result);
        fail();
    }

    @Test
    public void testOdeber1() throws KolekceException {
        System.out.println("odeber");
        SpojovySeznam instance = new SpojovySeznam();
        Object object = new Object();
        instance.pridej(object);
        instance.nastavPrvni();
        instance.odeber();
        Object expResult = object;
        Object result = instance.zpristupni();
        assertEquals(expResult, result);
    }

    @Test
    public void testZrus() throws KolekceException {
        System.out.println("zrus");
        SpojovySeznam instance = new SpojovySeznam();
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
        SpojovySeznam instance = new SpojovySeznam();
        Object[] result = instance.toArray();
        assertNotNull(result);
    }

    @Test
    public void testToArray_1args() throws KolekceException, CloneNotSupportedException {
        System.out.println("toArray");
        SpojovySeznam<Vozidlo> instance = new SpojovySeznam<>();
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
        SpojovySeznam instance = new SpojovySeznam();
        Vozidlo[] v = new Vozidlo[0];
        Object[] result = instance.toArray(v);
        assertNotNull(result);
    }

    @Test(expected = ClassCastException.class)
    public void testToArray_GenericType1() throws Exception {
        System.out.println("toArray");
        SpojovySeznam<Vozidlo> instance = new SpojovySeznam();
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
        SpojovySeznam instance = new SpojovySeznam();
        Object[] expResult = null;
        Object[] result = instance.toArray(createFunction);
        assertArrayEquals(expResult, result);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testToArray_Function1() throws Exception {
        System.out.println("toArray");
        Function createFunction = null;
        SpojovySeznam instance = new SpojovySeznam();
        Object[] expResult = new Object[instance.getPocet()];
        Object[] result = instance.toArray(createFunction);
        assertArrayEquals(expResult, result);
        fail();
    }

    @Test
    public void testToArray_Function2() throws Exception {
        System.out.println("toArray");
        Function<Integer, Vozidlo[]> createFunction = (size) -> new Vozidlo[size];
        SpojovySeznam instance = new SpojovySeznam();
        Object[] expResult = new Object[instance.getPocet()];
        Object[] result = instance.toArray(createFunction);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testIterator() {
        System.out.println("iterator");
        SpojovySeznam instance = new SpojovySeznam();
        Iterator result = instance.iterator();
        assertNotNull(result);
    }

}
