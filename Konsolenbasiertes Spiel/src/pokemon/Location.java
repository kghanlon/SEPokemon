package pokemon;

import Events.CheckMoneyEvent;
import Events.Event;
import Events.HelpEvent;
import Events.InformationEvent;
import Events.SaveGameEvent;
import Events.ShowItemsEvent;
import Events.ShowPokemonEvent;
import Events.ShowTokenEvent;

import java.util.*;

public class Location {
	private int id;
	private String name;
	private List<Event> events;
	private Scanner scan = Statisches.getScanner();
	
	/**
	 * Location Konstruktor - erstellt neue Location mit angegebenen Events und allen "ueberall" Events.
	 * @param id - Die Location ID
	 * @param name - Name der Location
	 * @param events - Alle Events die in der Location vorkommen sollen.
	 */
	public Location(int id, String name, List<Event> events){
		this.id = id;
		this.name = name;
		this.events = events;
		//Events hinzuf�gen die jede Location hat, und die nicht in der XML stehen sollten:
		events.add(0, new InformationEvent(new ArrayList<String>(), new ArrayList<String>(), "", "Unbekannter Befehl."));
		events.add(1, new CheckMoneyEvent());
		events.add(2, new ShowPokemonEvent());
		events.add(3, new ShowItemsEvent());
		events.add(4, new HelpEvent());
		events.add(5, new SaveGameEvent());
		events.add(5, new ShowTokenEvent());
	}
	
	/**
	 * Fuehrt die Location aus -> Wartet auf Eingaben und fuehrt passende Events aus bis ein Event die LocationId des Trainers aendert.
	 * @param t - Instanz des Trainers
	 * @return Die neue Location des Trainers
	 */
	public Location runLocation(Trainer t){
		//Wartet auf Eingabe, fuehrt dann das ensprechende Event aus.
		//Wenn die Location vom Trainer geaendert wird, gib die neue Loc zur�ck.
		while(true){
			
			System.out.println();
			String commandLine = scan.nextLine();
			int i = findCorrectEvent(commandLine, t.getTokens());
			events.get(i).runEvent(t);
			if(this.id!=t.getLocationId()){
				return LocationFactory.locations.get(t.getLocationId());
			}
		}
	}
	
	private int findCorrectEvent(String command, List<String> tokens){
		//Only one event can fit to a specified command+token combination. This Method finds
		//that event if it exists. If not, the default "empty" event is returned
		for(int i = 0; i < events.size(); i++){
			if(events.get(i).getCommand().equals(command)){
				//Commands are the same, does the Trainer have the required Tokens?
				boolean b = true;
				for(int j = 0; j < events.get(i).getReqTokens().size(); j++){
					if(!tokens.contains(events.get(i).getReqTokens().get(j))){
						//Trainer does not have a required Token:
						b = false;
					}
				}
				for(int j = 0; j < events.get(i).getReqNonTokens().size(); j++){
					if(tokens.contains(events.get(i).getReqNonTokens().get(j))){
						//Trainer has a required NonToken:
						b = false;
					}
				}
				if(b){
					//Trainer has all Required Tokens and no required NonTokens, so return the event:
					return i;
				}
			}
		}
		//No matching event was found, return default "empty" event
		return 0;
	}
	
	/**
	 * Getter fuer Location Name
	 * @return Name der Location
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Getter fuer die Events der Location
	 * @return Liste der Events in der Location
	 */
	public List<Event> getEvents(){
		return this.events;
	}

}

