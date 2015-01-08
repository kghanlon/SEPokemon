package Events;

import java.util.List;
import java.util.ArrayList;

import pokemon.LocationFactory;
import pokemon.Trainer;

/**"UeberallEvent" das alle zurzeit moeglichen Befehle auf der Konsole ausgibt
 * @author hanlonk
 *
 */
public class HelpEvent extends Event {
	
	/**
	 * Konsturktor fuer ein HelpEvent (Befehl = "hilfe")
	 */
	public HelpEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "hilfe");
	}
	
	public void runEvent(Trainer t){
		List<Event> events = LocationFactory.locations.get(t.getLocationId()).getEvents();
		System.out.println("Alle zurzeit moeglichen Befehle:");
		outer : for(int i = 0; i < events.size(); i++){
			//Hat der Trainer alle required Tokens?
			for(int j = 0; j < events.get(i).getReqTokens().size(); j++){
				if(!t.getTokens().contains(events.get(i).getReqTokens().get(j))){
					//Er hat ein ReqToken nicht:
					continue outer;
				}
			}
			//Wenn wir hier angekommen hat er alle benoetigten Tokens.
			//Hat er welche die er nicht haben darf??
			for(int j = 0; j < events.get(i).getReqNonTokens().size(); j++){
				if(t.getTokens().contains(events.get(i).getReqNonTokens().get(j))){
					//Er hat ein Token das er nicht haben darf:
					continue outer;
				}
			}
			//Wenn wir hier angkommen sind kann er das Event ausführen, also den Befehl ausgeben:
			System.out.println(events.get(i).getCommand());
		}
	}

}
