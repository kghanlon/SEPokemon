/*
 * Die Haupt Klasse
 * Von hier aus werden alle anderen Klassen gestartet.
 */

package pokemon;
import java.util.*;

import tests.PokemonTest;

/** Die Hauptklasse, die das ganze Spiel startet und leitet
 * @author hanlonk
 *
 */
public class Game {
	/** Die Main Methode, startet den Scanner und das Hauptmenue
	 * @param args
	 */
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		Scanner sc = Statisches.getScanner();
		//PokemonTest.testKampfSchwach();
		hauptmenue(sc);
		Statisches.closeScanner();			
	}
	
	private static  boolean hauptmenue(Scanner sc){
		//Locations aus XML laden:
		LocationFactory factory = new LocationFactory("./Locations.xml");
		factory.getAllLocations();
		
		
		//Hauptmenue
		System.out.println("Hauptmenue");
		System.out.println("Spiel (f)ortsetzen?");
		System.out.println("(N)eues Spiel Starten? (Alter Spielstand geht verloren!)");
		//hier noch als Moeglichkeit wenn genug Zeit bleibt Spielstand lueschen, wenn dann mehrere Spielstuende mueglich sind
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
		
//		//Tokentest:
//		for(int i = 0; i < t.getTokens().size(); i++){
//			System.out.println(t.getTokens().get(i));
//		}
		
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
		System.out.println("Du Bekommst die Wahl moechtest du");
		System.out.println("(B)isasam?");
		System.out.println("(G)lumanda?");
		System.out.println("(S)chiggy?");
		String poki = sc.next().trim();
		Pokemon starter;
		List<String> startPokToken = new ArrayList<String>();
		switch(poki){
		case "B": 
		case "b":
			starter = new Pokemon(PokeNamen.BISASAM, 5);
			System.out.println("Du hast Bisasam gewaehlt.");
			startPokToken.add("starterBisasam");
			break;
		case "G":
		case "g":
			starter = new Pokemon(PokeNamen.GLUMANDA, 5);
			System.out.println("Du hast Glumanda gewaehlt.");
			startPokToken.add("starterGlumanda");
			break;
		case "S":
		case "s":
			starter = new Pokemon(PokeNamen.SCHIGGY, 5);
			System.out.println("Du hast Schiggy gewaehlt.");
			startPokToken.add("starterSchiggy");
			break;
		default: starter = new Pokemon(PokeNamen.PIKACHU, 5);
		System.out.println("Da deine Eingabe ungültig war bekommst du ein Pikachu von mir.");
		startPokToken.add("starterPikachu");
		}
		Pokemon [] team = new Pokemon[3];
		team[0]=starter;
		return new Trainer(0, startPokToken, name, null, team, 0);
	}
	
	
}
