package Events;

import java.util.List;

import pokemon.Trainer;

public class GrantTokenEvent extends Event {
	
	private String token;
	
	public GrantTokenEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String token){
		super(reqTokens, reqNonTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Add the specified Token to the Trainers Token list
		t.getTokens().add(token);
	}

}
