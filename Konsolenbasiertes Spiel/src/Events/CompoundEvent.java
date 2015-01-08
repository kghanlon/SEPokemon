package Events;

import java.util.List;

import pokemon.Trainer;

/** Ein Event das es moeglich macht mehrere Events mit nur einem Befehl auszufuehren
 * @author hanlonk
 *
 */
public class CompoundEvent extends Event {
	
	private List<Event> events;
	
	/**
	 * Konstruktor fuer ein CompoundEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuehrung des Events
	 * @param events - Alle Events die verbunden werden, und hintereinander ausgefuehrt werden
	 */
	public CompoundEvent(List<String> reqTokens, List<String> reqNonTokens, String command, List<Event> events){
		super(reqTokens, reqNonTokens, command);
		this.events = events;
	}
	
	public void runEvent(Trainer t){
		for(int i = 0; i < events.size(); i++){
			if(testReqTokens(t, events.get(i))){ //Hat der Trainer die benötigten Tokens?
				//Wenn ja:
				events.get(i).runEvent(t);
			}
		}
	}
	
	//Testet ob das aufgerufene Event ausgefuehrt werden darf(Tokens)
	private boolean testReqTokens(Trainer t, Event e){
		List<String> reqTokens = e.getReqTokens();
		for(int i = 0; i < reqTokens.size(); i++){
			if(!t.getTokens().contains(reqTokens.get(i))){
				//Trainer hat ein benoetiges token nicht, also:
				return false;
			}
		}
		//Wenn wir hier angekommen sind hat der trainer schonmal alle reqTokens. Was ist mit den reqNonTokens?
		List<String> reqNonTokens = e.getReqNonTokens();
		for(int i = 0; i < reqNonTokens.size(); i++){
			if(t.getTokens().contains(reqNonTokens.get(i))){
				//Der Trainer hat ein Token dass er nicht haben darf, also:
				return false;
			}
		}
		//wenn wir hier sind hat er alle die er braucht und keins das er nicht haben darf. Also:
		return true;
	}

}
