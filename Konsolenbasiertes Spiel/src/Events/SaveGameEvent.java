package Events;

import java.util.ArrayList;

import pokemon.Statisches;
import pokemon.Trainer;

public class SaveGameEvent extends Event {
	
	public SaveGameEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "speichern");
	}
	
	public void runEvent(Trainer t){
		Statisches.trainerSpeichern(t);
	}

}
