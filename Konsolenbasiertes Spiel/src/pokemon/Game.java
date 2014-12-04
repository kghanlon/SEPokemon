/*
 * Die Haupt Klasse
 * Von hier aus werden alle anderen Klassen gestartet.
 */

package pokemon;

public class Game {
		
	public static void main(String args[]){
		Statisches.setScanner();
		Statisches.einlesen();
		
		
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
		    
	}
}
