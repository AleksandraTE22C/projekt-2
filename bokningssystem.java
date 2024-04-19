import java.util.Scanner;
public class bokningssystem{
    private static final int antal_platser = 20;
    private static final double pris = 299.90;
    private static int[] bokadePlatser = new int[antal_platser];

    public static void main(String[] args) {
        Scanner tb = new Scanner (System.in);

        while(true){
            System.out.println("1. Boka plats");
            System.out.println("2. Visa lediga platser");
            System.out.println("3. Visa vinsten av sålda biljetter");
            System.out.println("4. Avsluta");
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
                System.out.println("Tack för besöket!");
                break;
            }
            else{
                System.out.println("Välj ett giltigt val.");
            }
        } 
    }
    private static void bokaPlats() { //Metod för att boka plats
        Scanner tb = new Scanner (System.in);
        System.out.println("Ange ett platsnummer (1-20):");
        int platsnummer = tb.nextInt();

        if(bokadePlatser[platsnummer - 1] == 0){ //Kontrollera att platsen är ledig
            System.out.println("Ange ditt födelsedatum (YYYYMMDD):");
            String födelsedatum = tb.next();

            if (födelsedatum.length() != 8 || !födelsedatum.matches("[0-9]+")) { //Kollar om födelsedatumet inte är exakt 8 tecken eller innehåller tecken som inte är siffror
                System.out.println("Ogiltigt födelsedatum");
                return;
            }

            bokadePlatser[platsnummer - 1] = 1; //Markera platsen som bokad
            System.out.println("Plats " +platsnummer+ " är bokad för " +födelsedatum);
        } else{
            System.out.println("Platsen är redan bokad"); //Skriv ut "platsen är redan bokad" om värdet i arrayen är 1.
        }

        if(platsnummer < 1 || platsnummer > antal_platser){ //Skriv ut "ogiltigt platsnummer" om talet är utanför sträckan 1-20
            System.out.println("Ogiltig platsnummer");
            return;
        }
    }

    private static int visaPlatser() { //Metod för att visa lediga platser
        int ledigaPlatser = 0;
        for(int plats: bokadePlatser){ //Går igenom varje nummer i arrayen
            if(plats == 0){
                ledigaPlatser++; //+1 för varje plats med värdet 0
            }
        }
        System.out.println("Antal lediga platser: "+ledigaPlatser); //Skriv ut antalet lediga platser
        return ledigaPlatser;
    }

    private static void vinsten(){ //Metod för att visa vinsten
        int antalBokadePlatser = antal_platser - visaPlatser(); //Beräknar antalet bokade platser 
        double vinst = antalBokadePlatser * pris; //Multiplicerar antalet platser med pris
        System.out.println("Vinsten är: " + vinst + " kr"); //skriv ut vinsten
    }
}
