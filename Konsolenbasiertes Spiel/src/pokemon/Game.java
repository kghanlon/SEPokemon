/*
 * Die Haupt Klasse
 * Von hier aus werden alle anderen Klassen gestartet.
 */

package pokemon;
import java.util.*;

public class Game {
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		Scanner sc = Statisches.getScanner();
		hauptmenue(sc);		
		Statisches.closeScanner();			
	}
	
	private static  boolean hauptmenue(Scanner sc){
		//Locations aus XML laden:
		LocationFactory factory = new LocationFactory("./Locations.xml");
		factory.getAllLocations();
		
		
		//Hauptmenue
		System.out.println("Hauptmen�");
		System.out.println("Spiel (f)ortsetzen?");
		System.out.println("(N)eues Spiel Starten? (Alter Spielstand geht verloren!)");
		//hier noch als M�glichkeit wenn genug Zeit bleibt Spielstand l�schen, wenn dann mehrere Spielst�nde m�glich sind
		Trainer t = Statisches.gespeicherterTrainer();
		String c = sc.next();
		switch(c){
		case "N":
		case "n": 
			System.out.println("Neues Spiel!");
			t=neuerSpieler(sc) ; 
		break;
		default: System.out.println("Spiel fortsetzen " + t);
		}//ende Hauptmenue
		
		
		
		t.setLocation(1);
		//Start Tokens laden:
		for(int key : LocationFactory.locations.keySet()){
			t.addToken(LocationFactory.locations.get(key).getName()+"Default");
		}
		
		
		//Spiel starten:
		while(true){
			try{
				LocationFactory.locations.get(t.getLocationId()).runLocation(t);
			} catch (InputMismatchException e){
				System.out.println("Unpassende Eingabe...");
				continue;
			}
		}
	}
	
	private static Trainer  neuerSpieler(Scanner sc){
		System.out.println("Wie lautet dein Name?");
		String name = sc.next().trim();
		System.out.println("Du Bekommst die Wahl m�chtest du");
		System.out.println("(B)isasam?");
		System.out.println("(G)lumanda?");
		System.out.println("(S)chiggy?");
		String poki = sc.next().trim();
		Pokemon starter;
		switch(poki){
		case "B": 
		case "b":
			starter = new Pokemon(PokeNamen.BISASAM, 5);
			System.out.println("Du hast Bisasam gew�hlt.");
			break;
		case "G":
		case "g":
			starter = new Pokemon(PokeNamen.GLUMANDA, 5);
			System.out.println("Du hast Glumanda gew�hlt.");
			break;
		case "S":
		case "s":
			starter = new Pokemon(PokeNamen.SCHIGGY, 5);
			System.out.println("Du hast Schiggy gew�hlt.");
			break;
		default: starter = new Pokemon(PokeNamen.BISASAM, 5);
		System.out.println("Da deine Eingabe ung�ltig war bekommst du ein Pikachu von mir.");
		}
		Pokemon [] team = new Pokemon[3];
		team[0]=starter;
		return new Trainer(0, null, name, null, team, 0);
	}
	
	
}
