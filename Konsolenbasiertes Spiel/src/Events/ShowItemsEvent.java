package Events;

import java.util.ArrayList;

import pokemon.Pokemon;
import pokemon.Statisches;
import pokemon.Trainer;

public class ShowItemsEvent extends Event {
	
	public ShowItemsEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "item info");
	}
	
	public void runEvent(Trainer t){
		//Pokecount, damit klar ist wie viele pokemon dabei sind.
		int count = 0;
		for(int i = 0; i < t.getTeam().length; i++){
			if(t.getTeam()[i] == null){
				//Nichts
			} else {
				//Hier ist ein pokemon, also:
				count++;
			}
		}
		
		boolean b = true;
		while(b){
			for(int i = 0; i < t.getItems().size(); i++){
				System.out.println((i+1)+": "+t.getItems().get(i));
			}
			System.out.println("Welches Item willst du benutzen? (0 für Abbruch)");
			int number = Statisches.getScanner().nextInt();
			//Was fuer ein Item wurde gewaehlt?
			if(number < 1 || number > t.getItems().size()){
				b = false;
				continue;
			}
			if(t.getItems().get(number-1).istBall()){
				//Pokeball
				System.out.println("Du kannst jetzt keinen Pokeball benutzen!");
				continue;
			} else if(t.getItems().get(number-1).kannHeilen()){
				//Trank:
				System.out.println("Welches Pokemon soll den Trank bekommen?");
				for(int i = 0; i < count; i++){
					System.out.println((i+1)+": "+t.getTeam()[i].getName()+" KP: "+t.getTeam()[i].getKp()+"/"+t.getTeam()[i].getMaxkp());
				}
				int numberB = Statisches.getScanner().nextInt();
				if(numberB<1 || numberB > count){
					System.out.println("Falsche Eingabe...");
					continue;
				} else {
					int heilwert = t.getItems().get(number-1).getWert();
					Pokemon poke = t.getTeam()[numberB-1];
					if(poke.getKp() == 0){
						//Pokemon ist besiegt und kann nicht geheilt werden
						System.out.println(poke.getName()+" ist besiegt und kann nicht geheilt werden.");
						continue;
					} else if(poke.getKp()+heilwert >= poke.getMaxkp()){
						//Pokemon vollständig geheilt
						System.out.println(poke.getName()+" wurde um "+(poke.getMaxkp()-poke.getKp())+" KP geheilt.\n");
						poke.setKp(poke.getMaxkp());
					}
					else{
						//Pokemon um Heilwert heilen
						System.out.println(poke.getName()+" wurde um "+heilwert+" KP geheilt.\n");
						poke.setKp(poke.getKp()+heilwert);
					}
					//Anzahl des Items um 1 verringern:
					t.getItems().get(number-1).setAnzahl(t.getItems().get(number-1).getAnzahl()-1);
				}
				
			}
		}
	}

}
