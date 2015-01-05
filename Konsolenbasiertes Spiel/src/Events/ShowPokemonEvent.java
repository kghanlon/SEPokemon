package Events;

import java.util.ArrayList;

import pokemon.Trainer;

public class ShowPokemonEvent extends Event {
	
	public ShowPokemonEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "pokemon info");
	}
	
	public void runEvent(Trainer t){
		System.out.println("Du hast folgende Pokemon dabei:");
		for(int i = 0; i < t.getTeam().length; i++){
			System.out.println((i+1)+": "+t.getTeam()[i].getName()+" LVL: "+t.getTeam()[i].getLvl());
		}
		System.out.println("Ueber welches Pokemon willst du mehr wissen?");
		//WIE EINLESEN??!?!?!
		
	}

}
