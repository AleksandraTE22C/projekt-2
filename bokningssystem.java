import java.util.Scanner;

public class bokningssystem {
    private static final int antal_platser = 20;
    private static final double prisV = 299.90;
    private static final double prisB = 149.90;
    private static double vinst = 0;
    private static int[] bokadePlatser = new int[antal_platser];
    private static String[] persnr = new String[antal_platser];
    private static String[] namn = new String[antal_platser];

    public static void main(String[] args) throws InterruptedException {
        Scanner tb = new Scanner(System.in);

        while (true) {
            System.out.println("1. Boka plats");
            System.out.println("2. Visa lediga platser");
            System.out.println("3. Visa vinsten av sålda biljetter");
            System.out.println("4. Sök på bokad plats");
            System.out.println("5. Ta bort bokning");
            System.out.println("6. Skriv ut bokningar");
            System.out.println("7. Avsluta");
            int val = tb.nextInt();
            if (val == 1) {
                bokaPlats();
            } else if (val == 2) {
                visaPlatser();
            } else if (val == 3) {
                vinsten();
            } else if (val == 4) {
                sokPlats();
            } else if (val == 5) {
                taBort();
            } else if (val == 6) {
                skrivUtBokningar();
            } else if (val == 7) {
                System.out.println("Tack för besöket!");
                break;
            } else {
                System.out.println("Välj ett giltigt val.");
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
        int biljettTyp = tb.nextInt();

        if (biljettTyp == 1 || biljettTyp == 2) { 
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
            if (platsnummer < 1 || platsnummer > antal_platser) { // Skriv ut "ogiltigt platsnummer" om talet är utanför sträckan 1-20.
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
                if (biljettTyp == 1) {
                    vinst += prisV;
                    laddar();
                    System.out.println("Plats " + platsnummer + " är bokad för en vuxen för " + angeNamn + ", " + födelsedatum +".");
                    System.out.println();
                } else {
                    vinst += prisB;
                    laddar();
                    System.out.println("Plats " + platsnummer + " är bokad för ett barn för " + angeNamn + ", " + födelsedatum +".");
                    System.out.println();
                }
            } else {
                laddar();
                System.out.println("Platsen är redan bokad."); // Skriv ut "platsen är redan bokad" om värdet i arrayen är 1.
            }
        } else {
            System.out.println("Ogiltig biljetttyp.");
            laddar();
            return;
        }
    }

    private static void taBort() throws InterruptedException {
        Scanner tb = new Scanner(System.in);
        System.out.println("Ange namn eller födelsedatum (YYYYMMDD):");
        String input = tb.next();

        if(input.matches("[0-9]+")){
            for (int i = 0; i < antal_platser; i++) {
                if (bokadePlatser[i] == 1 && input.equals(persnr[i])) {
                    laddar();
                    bokadePlatser[i] = 0;
                    System.out.println("Plats "+ (i+1) +" är avbokad.");
                    
                }
            }
            System.out.println("Ingen plats är bokad med det angivna födelsedatumet.");
            } else{
                for (int i = 0; i < antal_platser; i++) {
                    if (bokadePlatser[i] == 1 && input.equals(namn[i])) {
                        laddar();
                        bokadePlatser[i] = 0;
                        System.out.println("Plats "+ (i+1) +" är avbokad.");
                    }
                }
                System.out.println("Ingen plats är bokad med det angivna namnet.");
            }
    }

    private static void skrivUtBokningar() {
        Scanner tb = new Scanner(System.in);
    }

    private static void sokPlats() throws InterruptedException {
        Scanner tb = new Scanner(System.in);
        System.out.println("Ange ditt namn eller födelsedatum (YYYYMMDD):");
        String input = tb.next();

        if(input.matches("[0-9]+")){
        for (int i = 0; i < antal_platser; i++) {
            if (bokadePlatser[i] == 1 && input.equals(persnr[i])) {
                laddar();
                System.out.println("Plats " + (i + 1) + " är bokad för " + input + ".");
                return;
            }
        }
        System.out.println("Ingen plats bokad med det angivna födelsedatumet.");
        } else{
            for (int i = 0; i < antal_platser; i++) {
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

    private static void vinsten() throws InterruptedException { // Metod för att visa vinsten
        laddar();
        System.out.println("Vinsten är: " + vinst + " kr"); // skriv ut vinsten
    }

}