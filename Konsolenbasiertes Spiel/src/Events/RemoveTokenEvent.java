package Events;

import java.util.List;

import pokemon.Trainer;

public class RemoveTokenEvent extends Event {
	
	private String token;
	
	public RemoveTokenEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String token){
		super(reqTokens, reqNonTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Removes the specified token from the Trainers token List:
		t.getTokens().remove(token);
	}

}
