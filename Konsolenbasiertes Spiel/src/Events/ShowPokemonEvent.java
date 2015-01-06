package Events;

import java.util.ArrayList;

import pokemon.Pokemon;
import pokemon.Statisches;
import pokemon.Trainer;

public class ShowPokemonEvent extends Event {
	
	public ShowPokemonEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "pokemon info");
	}
	
	public void runEvent(Trainer t){
		System.out.println("Du hast folgende Pokemon dabei:");
		for(int i = 0; i < t.getTeam().length; i++){
			System.out.println((i+1)+": "+t.getTeam()[i].getName()+" LVL: "+t.getTeam()[i].getLvl()+" KP: "
									+t.getTeam()[i].getKp()+"/"+t.getTeam()[i].getMaxkp());
		}
		System.out.println("Ueber welches Pokemon willst du mehr wissen?");
		int i = Statisches.getScanner().nextInt();
		i = i-1; //da Iterator bei 0 beginnt.
		if(i>=0 && i<t.getTeam().length){
			Pokemon poke = t.getTeam()[i];
			System.out.println(poke+"KP: "+poke.getKp()+"/"+poke.getMaxkp());
		}
		
	}

}
