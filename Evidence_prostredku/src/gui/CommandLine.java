package gui;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Function;
import kolekce.SpojovySeznam;
import prostredky.Dodavka;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import prostredky.Traktor;
import prostredky.TypVozidlaEnum;
import prostredky.Vozidlo;
import sprava.EvidenceAut;
import sprava.OvladaniException;

public class CommandLine {

    private static final String SOUBOR_TXT = "prostredky.txt";
    private static final String SOUBOR_BIN = "zaloha.bin";
    
    public static int generateCLnumber(final String comment, final int topBorder) {
        System.out.println(comment);
        final Scanner input = new Scanner(System.in);

        while (true) {
            try {
                int number = input.nextInt();
                return number;
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Wrong number format.");
                input.nextLine();
            }
            System.out.print("Please, enter the number again from specified interval[0;" + topBorder + "]");
        }
    }

    public static void main(String[] args) throws OvladaniException {
        EvidenceAut<Vozidlo> evidenceAutomobilu = new EvidenceAut<>(new SpojovySeznam<>());
        final Scanner input = new Scanner(System.in);

        while (true) {
            int choose = generateCLnumber("Choose the command from offered list:\n\t"
                    + "1 --> test\n\t"
                    + "2 --> generuj\n\t"
                    + "3 --> novy\n\t"
                    + "4 --> zmen\n\t"
                    + "5 --> zrus\n\t"
                    + "6 --> uloz\n\t"
                    + "7 --> obnov\n\t"
                    + "8 --> ulozTXT\n\t"
                    + "9 --> obnovTXT\n\t"
                    + "10 --> filtruj\n\t"
                    + "11 --> zrus filtrace\n\t"
                    + "0 --> exit\n", 11);

            switch (choose) {
                case 1:
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.nactiTextSoubor("test.txt", EvidenceProstredkuYaroshNikita.func);
                    System.out.println();
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 2:
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.getSeznam().zrus();
                    int pocetProstredku = generateCLnumber("Enter number of vehicles", evidenceAutomobilu.dejPocetPolozekMax());
                    evidenceAutomobilu.generujData(pocetProstredku);
                    System.out.println();
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 3:
                    System.out.println("Your choice " + choose);

                    System.out.println("Enter type of vehicle: oa(osobni automobil), na(nakladni autombil), do(dodavka), tr(traktor)");
                    System.out.println("Warning! Only entered abbreviations in any register will work!");
                    String typProstredku = input.nextLine();

                    Scanner inpuScanner = new Scanner(System.in);
                    
                    System.out.println("Enter SPZ");
                    String SPZ = inpuScanner.nextLine();

                    System.out.println("Enter hmotnost");
                    double hmotnost = inpuScanner.nextDouble();

                    System.out.println("Enter max speed");
                    double maxSpeed = inpuScanner.nextDouble();

                    switch (typProstredku.toLowerCase()) {
                        case "oa":
                            System.out.println("Enter pocet sedadel");
                            int pocetSedadel = inpuScanner.nextInt();
                            Vozidlo vozidlo = new OsobniAutomobil(pocetSedadel, SPZ, hmotnost, maxSpeed);
                            evidenceAutomobilu.vlozPolozku(vozidlo);
                            break;
                        case "na":
                            System.out.println("Enter pocet kol");
                            int pocetKol = inpuScanner.nextInt();
                            vozidlo = new NakladniAutomobil(pocetKol, SPZ, hmotnost, maxSpeed);
                            evidenceAutomobilu.vlozPolozku(vozidlo);
                            break;
                        case "do":
                            System.out.println("Enter nosnost");
                            double nosnost = inpuScanner.nextDouble();
                            vozidlo = new Dodavka(nosnost, SPZ, hmotnost, maxSpeed);
                            evidenceAutomobilu.vlozPolozku(vozidlo);
                            break;
                        case "tr":
                            System.out.println("Enter tah");
                            double tah = inpuScanner.nextDouble();
                            vozidlo = new Traktor(tah, SPZ, hmotnost, maxSpeed);
                            evidenceAutomobilu.vlozPolozku(vozidlo);
                            break;
                        default:
                            System.out.println("Unknown typ");
                    }
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 4:
                    System.out.println("Your choice " + choose);
                    Function<Vozidlo, Vozidlo> editVehicle = vozidlo -> {
                        System.out.println("Enter SPZ");
                        String newSPZ = input.nextLine();

                        System.out.println("Enter hmotnost");
                        double newHmotnost = input.nextDouble();

                        System.out.println("Enter max speed");
                        double newMaxSpeed = input.nextDouble();

                        switch (vozidlo.getTyp()) {
                            case OSOBNI_AUTOMOBIL:
                                System.out.println("Enter pocet sedadel");
                                int newPocetSedadel = input.nextInt();
                                OsobniAutomobil oa = (OsobniAutomobil) vozidlo;
                                oa.setSPZ(newSPZ);
                                oa.setHmotnost(newHmotnost);
                                oa.setMaxSpeed(newMaxSpeed);
                                oa.setPocetSedadel(newPocetSedadel);
                                return oa;
                            case NAKLADNI_AVTOMOBIL:
                                System.out.println("Enter pocet kol");
                                int newPocetKol = input.nextInt();
                                NakladniAutomobil na = (NakladniAutomobil) vozidlo;
                                na.setSPZ(newSPZ);
                                na.setHmotnost(newHmotnost);
                                na.setMaxSpeed(newMaxSpeed);
                                na.setPocetKol(newPocetKol);
                                return na;
                            case TRAKTOR:
                                System.out.println("Enter tah");
                                double newTah = input.nextDouble();
                                Traktor tr = (Traktor) vozidlo;
                                tr.setSPZ(newSPZ);
                                tr.setHmotnost(newHmotnost);
                                tr.setMaxSpeed(newMaxSpeed);
                                tr.setTah(newTah);
                                return tr;
                            case DODAVKA:
                                System.out.println("Enter nosnost");
                                double newNosnost = input.nextDouble();
                                Dodavka dod = (Dodavka) vozidlo;
                                dod.setSPZ(newSPZ);
                                dod.setHmotnost(newHmotnost);
                                dod.setMaxSpeed(newMaxSpeed);
                                dod.setNosnost(newNosnost);
                                return dod;
                            default:
                                return vozidlo;
                        }
                    };
                    int numberEdittingVehicle = generateCLnumber(
                            "Enter the number of element which you want to edit. "
                            + "Choose from the interval [0;"
                            + evidenceAutomobilu.dejAktualniPocetPolozek() + "]",
                            evidenceAutomobilu.dejAktualniPocetPolozek());

                    evidenceAutomobilu.prejdiNaPrvniPolozku();
                    int i = 0;
                    while (i != numberEdittingVehicle) {
                        evidenceAutomobilu.prejdiNaDalsiPolozku();
                        i++;
                    }

                    evidenceAutomobilu.editujAktualniPolozku(editVehicle);
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 5:
                    System.out.println("Your choice " + choose);
                    int numberOfDeletingVehicle = generateCLnumber(
                            "Enter the number of element which you want to edit. "
                            + "Choose from the interval [0;"
                            + evidenceAutomobilu.dejAktualniPocetPolozek() + "]",
                            evidenceAutomobilu.dejAktualniPocetPolozek());
                    evidenceAutomobilu.prejdiNaPrvniPolozku();
                    i = 0;
                    while (i != numberOfDeletingVehicle) {
                        evidenceAutomobilu.prejdiNaDalsiPolozku();
                        i++;
                    }
                    Vozidlo v = evidenceAutomobilu.vyjmiAktualniPolozku();
                    System.out.println("Deleting vehicle is " + v);
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 6:
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.ulozDoSouboru(SOUBOR_BIN);
                    break;
                case 7:
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.nactiZeSouboru(SOUBOR_BIN);
                    break;
                case 8:
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.ulozTextSoubor(SOUBOR_TXT, EvidenceProstredkuYaroshNikita.mapper);
                    break;
                case 9:
                    evidenceAutomobilu.getSeznam().zrus();
                    System.out.println("Your choice " + choose);
                    evidenceAutomobilu.nactiTextSoubor(SOUBOR_TXT, EvidenceProstredkuYaroshNikita.func);
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 10:
                    System.out.println("Your choice " + choose);
                    int variableForFiltering = generateCLnumber("Enter number of filter:\n\t 1 - oa,\n\t 2 - na,\n\t 3 - tr,\n\t 4 - do", 4);
                    EvidenceAut<Vozidlo> filtredEvidenceAut = new EvidenceAut<>(new SpojovySeznam<>());
                    Iterator<Vozidlo> it = evidenceAutomobilu.getSeznam().iterator();
                    switch (variableForFiltering) {
                        case 1:
                            while (it.hasNext()) {
                                Vozidlo voz = it.next();
                                if (voz.getTyp() == TypVozidlaEnum.OSOBNI_AUTOMOBIL) {
                                    filtredEvidenceAut.vlozPolozku(voz);
                                }
                            }
                            filtredEvidenceAut.vypis(System.out::println);
                            break;
                        case 2:
                            while (it.hasNext()) {
                                Vozidlo voz = it.next();
                                if (voz.getTyp() == TypVozidlaEnum.NAKLADNI_AVTOMOBIL) {
                                    filtredEvidenceAut.vlozPolozku(voz);
                                }
                            }
                            filtredEvidenceAut.vypis(System.out::println);
                            break;
                        case 3:
                            while (it.hasNext()) {
                                Vozidlo voz = it.next();
                                if (voz.getTyp() == TypVozidlaEnum.TRAKTOR) {
                                    filtredEvidenceAut.vlozPolozku(voz);
                                }
                            }
                            filtredEvidenceAut.vypis(System.out::println);
                            break;
                        case 4:
                            while (it.hasNext()) {
                                Vozidlo voz = it.next();
                                if (voz.getTyp() == TypVozidlaEnum.DODAVKA) {
                                    filtredEvidenceAut.vlozPolozku(voz);
                                }
                            }
                            filtredEvidenceAut.vypis(System.out::println);
                            break;
                        default:
                            System.out.println("Unknown typ");
                    }
                    break;
                case 11:
                    evidenceAutomobilu.vypis(System.out::println);
                    break;
                case 0:
                    System.out.println("Your choice " + choose);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command. Choose number from offered list.\n"
                            + "Your choice is " + choose + "\n");
            }
        }
    }

}
