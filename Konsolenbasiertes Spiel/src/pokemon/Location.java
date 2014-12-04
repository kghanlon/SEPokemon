package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Events.CheckMoneyEvent;
import Events.Event;
import Events.InformationEvent;

public class Location {
	private int id;
	private String name;
	private List<Event> events;
	private Scanner scan = Statisches.getScanner();
	
	public Location(int id, String name, List<Event> events){
		this.id = id;
		this.name = name;
		this.events = events;
		//Events hinzufügen die jede Location hat, und die nicht in der XML stehen sollten:
		events.add(0, new InformationEvent(new ArrayList<String>(), new ArrayList<String>(), "", "Kein passendes Event gefunden"));
		events.add(1, new CheckMoneyEvent());
	}
	
	public Location runLocation(Trainer t){
		//Wartet auf Eingabe, fuehrt dann das ensprechende Event aus.
		//Wenn die Location vom Trainer geaendert wird, gib die neue Loc zurück.
		while(true){
			
			System.out.println("Next Command: ");
			String commandLine = scan.nextLine();
			int i = findCorrectEvent(commandLine, t.getTokens());
			events.get(i).runEvent(t);
			if(this.id!=t.getLocationId()){
				return LocationFactory.locations.get(t.getLocationId());
			}
		}
	}
	
	public int findCorrectEvent(String command, List<String> tokens){
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
	
	public String getName(){
		return this.name;
	}

}

