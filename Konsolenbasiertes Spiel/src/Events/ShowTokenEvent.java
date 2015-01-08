package Events;

import java.util.ArrayList;

import pokemon.Trainer;

/**"Ueberall Event" das alle vorhandenen Tokens des Trainers anzeigt (zum debuggen)
 * @author hanlonk
 *
 */
public class ShowTokenEvent extends Event {
	
	/**
	 * Konstruktor fuer ein ShowTokenEvent (Befehl = "token debug")
	 */
	public ShowTokenEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "token debug");
	}
	
	public void runEvent(Trainer t){
		for(int i = 0; i < t.getTokens().size(); i++){
			System.out.println(t.getTokens().get(i));
		}
	}

}
