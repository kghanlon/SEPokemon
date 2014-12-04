package Events;

import java.util.ArrayList;

import pokemon.Trainer;

public class CheckMoneyEvent extends Event {
	
	public CheckMoneyEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "geld");
	}
	
	public void runEvent(Trainer t){
		System.out.println("Du besitzt zurzeit: "+t.getGeld()+" Pokedollar." );
	}

}
