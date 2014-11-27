package pokemon;

import java.util.List;

public class GrantTokenEvent extends Event {
	
	private int token;
	
	public GrantTokenEvent(List<Integer> reqTokens, String command, int token){
		super(reqTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Add the specified Token to the Trainers Token list
		t.getTokens().add(token);
	}

}
