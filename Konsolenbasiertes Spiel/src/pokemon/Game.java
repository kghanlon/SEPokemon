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
		System.out.println("Hauptmenü");
		System.out.println("Spiel (f)ortsetzen?");
		System.out.println("(N)eues Spiel Starten? (Alter Spielstand geht verloren!)");
		//hier noch als Möglichkeit wenn genug Zeit bleibt Spielstand löschen, wenn dann mehrere Spielstände möglich sind
		Trainer t = Statisches.gespeicherterTrainer();
		String c = sc.next();
		switch(c){
		case "N":
		case "n": 
			System.out.println("Neues Spiel!");
			t=neuerSpieler(sc); 
			//Start Tokens laden:
			for(int key : LocationFactory.locations.keySet()){
				t.addToken(LocationFactory.locations.get(key).getName()+"Default");
			}
			t.setLocation(1);
		break;
		default: System.out.println("Spiel fortsetzen " + t);
		}//ende Hauptmenue
				
		//Spiel starten:
		for(int i = 0; i < t.getTokens().size(); i++){
			System.out.println(t.getTokens().get(i));
		}
		
		while(true){
			try{
				System.out.println(t.getLocationId());
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
		System.out.println("Du Bekommst die Wahl möchtest du");
		System.out.println("(B)isasam?");
		System.out.println("(G)lumanda?");
		System.out.println("(S)chiggy?");
		String poki = sc.next().trim();
		Pokemon starter;
		switch(poki){
		case "B": 
		case "b":
			starter = new Pokemon(PokeNamen.BISASAM, 5);
			System.out.println("Du hast Bisasam gewählt.");
			break;
		case "G":
		case "g":
			starter = new Pokemon(PokeNamen.GLUMANDA, 5);
			System.out.println("Du hast Glumanda gewählt.");
			break;
		case "S":
		case "s":
			starter = new Pokemon(PokeNamen.SCHIGGY, 5);
			System.out.println("Du hast Schiggy gewählt.");
			break;
		default: starter = new Pokemon(PokeNamen.PIKACHU, 5);
		System.out.println("Da deine Eingabe ungültig war bekommst du ein Pikachu von mir.");
		}
		Pokemon [] team = new Pokemon[3];
		team[0]=starter;
		return new Trainer(0, null, name, null, team, 0);
	}
	
	
}
