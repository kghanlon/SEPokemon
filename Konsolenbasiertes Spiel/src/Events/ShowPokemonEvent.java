package Events;

import java.util.ArrayList;

import pokemon.Pokemon;
import pokemon.Statisches;
import pokemon.Trainer;

/**"Ueberall Event" das Infos ueber die Pokemon die man besitzt anzeigt
 * @author hanlonk
 *
 */
public class ShowPokemonEvent extends Event {
	
	/**
	 * Konstruktor fuer ein ShowPokemonEvent (Befehl = "pokemon info")
	 */
	public ShowPokemonEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "pokemon info");
	}
	
	public void runEvent(Trainer t){
		System.out.println("Du hast folgende Pokemon dabei:");
		//Wir brauchen einen count, um zu wissen wie viele Pokemon man wirklich besitzt:
		int count = 0;
		for(int i = 0; i < t.getTeam().length; i++){
			if(t.getTeam()[i] == null){
				//Nichts
			} else {
				//Hier ist ein pokemon, also:
				count++;
			}
		}
		
		for(int i = 0; i < count; i++){
			System.out.println((i+1)+": "+t.getTeam()[i].getName()+" LVL: "+t.getTeam()[i].getLvl()+" KP: "
									+t.getTeam()[i].getKp()+"/"+t.getTeam()[i].getMaxkp());
		}
		System.out.println("Ueber welches Pokemon willst du mehr wissen? (0 für Abbruch)");
		int i = Statisches.getScanner().nextInt();
		i = i-1; //da Iterator bei 0 beginnt.
		if(i>=0 && i<count){
			Pokemon poke = t.getTeam()[i];
			System.out.println(poke+"KP: "+poke.getKp()+"/"+poke.getMaxkp());
		}
		
	}

}
