package Events;

import java.util.ArrayList;

import pokemon.Trainer;

/** "UeberallEvent" das die Pokedollar im Besitz des Trainers auf der Konsole ausgibt
 * @author hanlonk
 *
 */
public class CheckMoneyEvent extends Event {
	
	/**
	 * Konstruktor fuer ein CheckMoneyEvent (Befehl = "geld")
	 */
	public CheckMoneyEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "geld");
	}
	
	public void runEvent(Trainer t){
		System.out.println("Du besitzt zurzeit: "+t.getGeld()+" Pokedollar." );
	}

}
