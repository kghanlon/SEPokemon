package pokemon;

import java.util.List;

public class CompoundEvent extends Event {
	
	private List<Event> events;
	
	public CompoundEvent(List<String> reqTokens, List<String> reqNonTokens, String command, List<Event> events){
		super(reqTokens, reqNonTokens, command);
		this.events = events;
	}
	
	public void runEvent(Trainer t){
		for(int i = 0; i < events.size(); i++){
			events.get(i).runEvent(t);
		}
	}

}
