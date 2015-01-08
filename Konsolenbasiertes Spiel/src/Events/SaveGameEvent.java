package Events;

import java.util.ArrayList;

import pokemon.Statisches;
import pokemon.Trainer;

/**"UeberallEvent" mit dem man den Spielstand speichern kann
 * @author hanlonk
 *
 */
public class SaveGameEvent extends Event {
	
	/**
	 * Konstruktor fuer ein SaveGameEvent (Befehl = "speichern")
	 */
	public SaveGameEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "speichern");
	}
	
	public void runEvent(Trainer t){
		Statisches.trainerSpeichern(t);
	}

}
