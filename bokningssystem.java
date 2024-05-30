import java.util.InputMismatchException;
import java.util.Scanner;

public class bokningssystem {
    private static final int ANTAL_PLATSER = 20;
    private static final double PRIS_VUXEN = 299.90;
    private static final double PRIS_UNGDOM = 149.90;
    private static int[] bokadePlatser = new int[ANTAL_PLATSER];
    private static String[] persnr = new String[ANTAL_PLATSER];
    private static String[] namn = new String[ANTAL_PLATSER];
    private static String[] biljettyp = new String[ANTAL_PLATSER];

    public static void main(String[] args) throws InterruptedException {
        Scanner tb = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Boka plats");
            System.out.println("2. Visa lediga platser");
            System.out.println("3. Visa vinsten av sålda biljetter");
            System.out.println("4. Sök på bokad plats");
            System.out.println("5. Avboka");
            System.out.println("6. Skriv ut bokningar");
            System.out.println("7. Avsluta");
            try {
                int val = tb.nextInt();
                if (val == 1) {
                    bokaPlats();
                } else if (val == 2) {
                    visaPlatser();
                } else if (val == 3) {
                    laddar();
                    double vinst = vinsten(0);
                    System.out.println("Vinsten är: " + vinst + " kr");
                } else if (val == 4) {
                    sokPlats();
                } else if (val == 5) {
                    taBort();
                } else if (val == 6) {
                    laddar();
                    visaBokningar();
                } else if (val == 7) {
                    System.out.println("Tack för besöket!");
                    break;
                } else {
                    System.out.println("Ogiltigt val. Ange ett nummer mellan 1-7.");
                }
            } catch (InputMismatchException e){ 
                System.out.println("Ogiltigt val. Ange ett nummer mellan 1-7.");
                tb.next();
            }
        }
    }

    private static void laddar() throws InterruptedException {
        System.out.print("Laddar");

        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("\n");
    }

    private static int hittaFonsterplats() { // Metod för att automatiskt hitta en ledig fönsterplats
        int[] fonsterplatser = { 1, 4, 5, 8, 9, 12, 13, 16, 17, 20 };
        for (int plats : fonsterplatser) { // Går igenom varje nummer i arrayen
            if (bokadePlatser[plats - 1] == 0) { // Om platsen är ledig, returnera platsnumret
                return plats;
            }
        }
        System.out.println("Inga lediga fönsterplatser tillgängliga, välj en annan plats.");
        return -1;
    }

    private static void bokaPlats() throws InterruptedException { // Metod för att boka plats
        Scanner tb = new Scanner(System.in);
        laddar();
        System.out.println("Välj typ av biljett:");
        System.out.println("1. Vuxenbiljett (299.90 kr)");
        System.out.println("2. Barnbiljett (149.90 kr)");
        String biljett = tb.next();

        if (biljett.equals("1")|| biljett.equals("2")) {
            laddar();
            System.out.println("----------------------------------------------\r\n" +
                    "|   1    |   2    |        |   3    |   4    |\r\n" +
                    "----------------------------------------------\r\n" +
                    "|   5    |   6    |        |   7    |   8    |\r\n" +
                    "----------------------------------------------\r\n" +
                    "|   9    |   10   |        |   11   |   12   |\r\n" +
                    "----------------------------------------------\r\n" +
                    "|   13   |   14   |        |   15   |   16   |\r\n" +
                    "----------------------------------------------\r\n" +
                    "|   17   |   18   |        |   19   |   20   |\r\n" +
                    "----------------------------------------------");

            System.out.println();
            System.out.println("Vill du boka en fönsterplats? Platsen väljs automatiskt.\r\n" +
                    "1. Ja\r\n" +
                    "2. Nej");
            int svar = tb.nextInt();

            int platsnummer;
            if (svar == 1) {
                laddar();
                platsnummer = hittaFonsterplats();
                if (platsnummer == -1) {
                    System.out.println("Inga tillgängliga fönsterplatser.");
                    return; // Om inga fönsterplatser är tillgängliga, avsluta metoden.
                }
            } else if (svar == 2) {
                laddar();
                System.out.println("Ange ett platsnummer (1-20):");
                platsnummer = tb.nextInt();
            } else {
                System.out.println("Ogiltigt val. Vänligen försök igen.");
                return;
            }
            if (platsnummer < 1 || platsnummer > ANTAL_PLATSER) { // Skriv ut "ogiltigt platsnummer" om talet är utanför sträckan 1-20.
                System.out.println("Ogiltig platsnummer.");
                laddar();
                return;
            }

            if (bokadePlatser[platsnummer - 1] == 0) { // Kontrollera att platsen är ledig
                System.out.println("Ange ditt namn:");
                String angeNamn = tb.next();
                System.out.println("Ange ditt födelsedatum (YYYYMMDD):");
                String födelsedatum = tb.next();

                if (födelsedatum.length() != 8 || !födelsedatum.matches("[0-9]+")) { // Kollar om födelsedatumet inte är exakt 8 tecken eller innehåller tecken som inte är siffror.
                    System.out.println("Ogiltigt födelsedatum.");
                    laddar();
                    return;
                }

                bokadePlatser[platsnummer - 1] = 1; // Markera platsen som bokad
                namn[platsnummer - 1] = angeNamn;
                persnr[platsnummer - 1] = födelsedatum;
                biljettyp[platsnummer - 1] = biljett;
                if (biljett.equals("1")) { 
                    System.out.println("En vuxenbiljett för plats " +platsnummer+ " är bokad för "+angeNamn);
                    System.out.println();
                } else {
                    System.out.println("En barnbiljett för plats " +platsnummer+ " är bokad för "+angeNamn);
                    System.out.println();
                }
            } else {
                laddar();
                System.out.println("Platsen är redan bokad."); // Skriv ut "platsen är redan bokad" om värdet i arrayen är 1.
            }
        } else {
            laddar();
            System.out.println("Ogiltig biljetttyp.");
            return;
        }
    }

    private static void taBort() throws InterruptedException {
        Scanner tb = new Scanner(System.in);
        System.out.println("Ange namn eller födelsedatum (YYYYMMDD):");
        String input = tb.next();

        if (input.matches("[0-9]+")) {
            for (int i = 0; i < ANTAL_PLATSER; i++) {
                if (bokadePlatser[i] == 1 && input.equals(persnr[i])) {
                    laddar();
                    bokadePlatser[i] = 0;
                    namn[i] = null;
                    persnr[i] = null;
                    biljettyp[i] = null;
                    System.out.println("Plats " + (i + 1) + " är avbokad.");
                    return;
                }
            }
            System.out.println("Ingen plats är bokad med det angivna födelsedatumet.");
        } else {
            for (int i = 0; i < ANTAL_PLATSER; i++) {
                if (bokadePlatser[i] == 1 && input.equals(namn[i])) {
                    laddar();
                    bokadePlatser[i] = 0;
                    namn[i] = null;
                    persnr[i] = null;
                    biljettyp[i] = null;
                    System.out.println("Plats " + (i + 1) + " är avbokad.");
                    return;
                }
            }
            System.out.println("Ingen plats är bokad med det angivna namnet.");
        }
    }

    private static void visaBokningar() throws InterruptedException {
        boolean finns = false;

        int[] sortArray = new int[ANTAL_PLATSER]; // Skapa en temporär array för att hålla index 0 - ANTAL_PLATSER-1 och för att hålla de andra arrayerna intakta.
        for (int i = 0; i < ANTAL_PLATSER; i++) {
            sortArray[i] = i;
        }

        // Bubble sort på sortArray baserat på persnr och index
        boolean bytPlats;
        for (int i = 0; i < ANTAL_PLATSER - 1; i++) {
            bytPlats = false;
            for (int j = 0; j < ANTAL_PLATSER - i - 1; j++) {
                // Kontrollera om båda platserna är bokade innan jämförelse
                if (bokadePlatser[sortArray[j]] == 1 && bokadePlatser[sortArray[j + 1]] == 1) {
                    // Konvertera personnumren till numerisk typ för korrekt jämförelse (persnr sparas som string i bokaPlats metoden eftersom det inte gick att kolla längden av numret annars)
                    long nummer1 = Long.parseLong(persnr[sortArray[j]]);
                    long nummer2 = Long.parseLong(persnr[sortArray[j + 1]]);
                    // Jämför personnumren och byt plats om det första är större än det andra, eller om det första är lika och index för det andra är mindre
                    if (nummer1 > nummer2 || (nummer1 == nummer2 && sortArray[j] < sortArray[j + 1])) {
                        int tempIndex = sortArray[j]; // Byt plats på index i sortArray
                        sortArray[j] = sortArray[j + 1];
                        sortArray[j + 1] = tempIndex;
                        bytPlats = true;
                    }
                }
            }
            if (!bytPlats) {
                break; // Avbryt sorteringen om det inte fanns några ombyten
            }
        }

        // Skriv ut bokningarna baserat på den sorterade arrayen
        for (int i = 0; i < ANTAL_PLATSER; i++) {
            int sortedIndex = sortArray[i];
            if (bokadePlatser[sortedIndex] == 1) {
                finns = true;
                System.out.println(namn[sortedIndex] + " " + persnr[sortedIndex] + " " + "Plats " + (sortedIndex + 1));
            }
        }

        if (!finns) {
            System.out.println("Inga bokningar än.");
        }
    }

    private static void sokPlats() throws InterruptedException {
        Scanner tb = new Scanner(System.in);
        System.out.println("Ange ditt namn eller födelsedatum (YYYYMMDD):");
        String input = tb.next();

        if (input.matches("[0-9]+")) {
            for (int i = 0; i < ANTAL_PLATSER; i++) {
                if (bokadePlatser[i] == 1 && input.equals(persnr[i])) {
                    laddar();
                    System.out.println("Plats " + (i + 1) + " är bokad för " + input + ".");
                    return;
                }
            }
            System.out.println("Ingen plats bokad med det angivna födelsedatumet.");
        } else {
            for (int i = 0; i < ANTAL_PLATSER; i++) {
                if (bokadePlatser[i] == 1 && input.equals(namn[i])) {
                    laddar();
                    System.out.println("Plats " + (i + 1) + " är bokad för " + input + ".");
                    return;
                }
            }
            System.out.println("Ingen plats bokad med det angivna namnet.");
        }
    }

    private static int visaPlatser() throws InterruptedException { // Metod för att visa lediga platser
        int ledigaPlatser = 0;
        for (int plats : bokadePlatser) { // Går igenom varje nummer i arrayen
            if (plats == 0) {
                ledigaPlatser++; // +1 för varje plats med värdet 0
            }
        }
        laddar();
        System.out.println("Antal lediga platser: " + ledigaPlatser); // Skriv ut antalet lediga platser
        return ledigaPlatser;
    }

    private static double vinsten(int index) {
        // Basfall för rekursionen
        if (index >= ANTAL_PLATSER) {
            return 0;
        }
        
        if (bokadePlatser[index] == 1) { // Kontrollera om platsen är bokad
            if (biljettyp[index].equals("1")) { // Kontrollera biljetttyp och lägg till priset till vinsten
                return PRIS_VUXEN + vinsten(index + 1);
            } else { // Annars, lägg till barnbiljettpriset
                return PRIS_UNGDOM + vinsten(index + 1);
            }
        } else {
            return vinsten(index + 1); // Om platsen inte är bokad, gå bara vidare
        }
    }
}