package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Location {
	private int id;
	private String name;
	private List<Event> events;
	private Scanner scan = Statisches.getScanner();
	
	public Location(int id, String name, List<Event> events){
		this.id = id;
		this.name = name;
		this.events = events;
		//Default leeres Event ganz am Anfang einf�gen:
		events.add(0, new InformationEvent(new ArrayList<Integer>(), "", "Default Event"));
	}
	
	public Location runLocation(Trainer t){
		//Wartet auf Eingabe, f�hrt dann das ensprechende Event aus.
		//Wenn die Location vom Trainer ge�ndert wird, gib die neue Loc zur�ck.
		while(true){
			
			System.out.println("Next Command: ");
			String commandLine = scan.nextLine();
			int i = findCorrectEvent(commandLine, t.getTokens());
			events.get(i).runEvent(t);
			if(this.id!=t.getLocationId()){
				return Game.locations.get(t.getLocationId());
			}
		}
	}
	
	public int findCorrectEvent(String command, List<Integer> tokens){
		//Only one event can fit to a specified command+token combination. This Method finds
		//that event if it exists. If not, the default "empty" event is returned
		for(int i = 0; i < events.size(); i++){
			if(events.get(i).getCommand().equals(command)){
				//Commands are the same, does the Trainer have the required Tokens?
				boolean b = true;
				for(int j = 0; j < events.get(i).getReqTokens().size(); j++){
					if(!tokens.contains(events.get(i).getReqTokens().get(j))){
						b = false;
					}
				}
				if(b){
					//Trainer has all Required Tokens, so return the event:
					return i;
				}
			}
		}
		//No matching event was found, return default "empty" event
		return 0;
	}

}

