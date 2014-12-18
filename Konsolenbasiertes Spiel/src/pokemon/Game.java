/*
 * Die Haupt Klasse
 * Von hier aus werden alle anderen Klassen gestartet.
 */

package pokemon;

import java.util.ArrayList;

public class Game {
		
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		/*
		
		//Locations aus XML laden:
		LocationFactory factory = new LocationFactory("./Locations.xml");
		factory.getAllLocations();
		
		//Hier muss Abfrage stattfinden: erstes laden oder gibt es einen Speicherstand?
		//Trainer laden:
		Trainer t = Statisches.gespeicherterTrainer();
		t.setLocation(1);
		//Start Tokens laden:
		for(int key : LocationFactory.locations.keySet()){
			t.addToken(LocationFactory.locations.get(key).getName()+"Default");
		}
		
		
		//Spiel starten:
		while(true){
			LocationFactory.locations.get(t.getLocationId()).runLocation(t);
		}
		
		//Statisches.closeScanner();
		    */
		Pokemon [] tmp = {new Pokemon(PokeNamen.ARKANI,  100), null , null};
		ArrayList<Pokemon> tmp2 = new ArrayList<Pokemon>();
		tmp2.add(new Pokemon(PokeNamen.HABITAK, 20));
		ArrayList<Item> tmp3 = new ArrayList<>();
		tmp3.add(new Ball(ItemNamen.POKEBALL, 200));
		Kampf.start(new Trainer(0, null, "name", tmp3, tmp, 0), tmp2 ,true);
	}
}
