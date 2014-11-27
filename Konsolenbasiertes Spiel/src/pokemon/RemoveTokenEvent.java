package pokemon;

import java.util.List;

public class RemoveTokenEvent extends Event {
	
	private int token;
	
	public RemoveTokenEvent(List<Integer> reqTokens, String command, int token){
		super(reqTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Removes the specified token from the Trainers token List:
		t.getTokens().remove((Integer)token);
	}

}
