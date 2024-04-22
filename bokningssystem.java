import java.util.Scanner;
public class bokningssystem{
    private static final int antal_platser = 20;
    private static final double prisV = 299.90;
    private static final double prisB = 149.90;
    private static double vinst = 0;
    private static int[] bokadePlatser = new int[antal_platser];

    public static void main(String[] args) throws InterruptedException {
        Scanner tb = new Scanner (System.in);

        while(true){
            System.out.println("1. Boka plats");
            System.out.println("2. Visa lediga platser");
            System.out.println("3. Visa vinsten av sålda biljetter");
            System.out.println("4.");
            System.out.println("5. Avsluta");
            int val= tb.nextInt();
            if(val == 1){
                bokaPlats();
            }
            else if(val == 2){
                visaPlatser();
            }
            else if(val == 3){
                vinsten();
            }
            else if(val == 4){

            }
            else if(val == 5){
                System.out.println("Tack för besöket!");
                break;
            }
            else{
                System.out.println("Välj ett giltigt val.");
            }
        } 
    }

    private static void laddar() throws InterruptedException{
        System.out.print("Laddar");

        for(int i =0; i < 3; i++){
            System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("\n");
    }

    private static void bokaPlats() throws InterruptedException { //Metod för att boka plats
        Scanner tb = new Scanner(System.in);
        laddar();
        System.out.println("Välj typ av biljett:");
        System.out.println("1. Vuxenbiljett (299.90 kr)");
        System.out.println("2. Barnbiljett (149.90 kr)");
        int biljettTyp = tb.nextInt();

        if (biljettTyp != 1 && biljettTyp != 2) {
            System.out.println("Ogiltig biljetttyp");
            laddar();
            return;
        }

        System.out.println("Ange ett platsnummer (1-20):");
        int platsnummer = tb.nextInt();
        if (platsnummer < 1 || platsnummer > antal_platser) { //Skriv ut "ogiltigt platsnummer" om talet är utanför sträckan 1-20
            System.out.println("Ogiltig platsnummer");
            laddar();
            return;
        }
        
        if (bokadePlatser[platsnummer - 1] == 0) { //Kontrollera att platsen är ledig
            System.out.println("Ange ditt födelsedatum (YYYYMMDD):");
            String födelsedatum = tb.next();

            if (födelsedatum.length() != 8 || !födelsedatum.matches("[0-9]+")) { //Kollar om födelsedatumet inte är exakt 8 tecken eller innehåller tecken som inte är siffror
                System.out.println("Ogiltigt födelsedatum");
                laddar();
                return;
            }

            bokadePlatser[platsnummer - 1] = 1; //Markera platsen som bokad
            if (biljettTyp == 1) {
                vinst += prisV;
                laddar();
                System.out.println("Plats " + platsnummer + " är bokad för en vuxen för " + födelsedatum);
                System.out.println();
            } else {
                vinst += prisB;
                System.out.println("Plats " + platsnummer + " är bokad för ett barn för " + födelsedatum);
                System.out.println();
            }
        } else {
            System.out.println("Platsen är redan bokad"); //Skriv ut "platsen är redan bokad" om värdet i arrayen är 1.
        }
    }

    private static int visaPlatser() throws InterruptedException { //Metod för att visa lediga platser
        int ledigaPlatser = 0;
        for(int plats: bokadePlatser){ //Går igenom varje nummer i arrayen
            if(plats == 0){
                ledigaPlatser++; //+1 för varje plats med värdet 0
            }
        }
        laddar();
        System.out.println("Antal lediga platser: "+ledigaPlatser); //Skriv ut antalet lediga platser
        return ledigaPlatser;
    }

    private static void vinsten() throws InterruptedException{ //Metod för att visa vinsten
        System.out.println();
        System.out.println("Vinsten är: " + vinst + " kr"); //skriv ut vinsten
    }

}
