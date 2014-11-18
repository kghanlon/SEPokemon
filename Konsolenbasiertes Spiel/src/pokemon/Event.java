package pokemon;

import java.util.List;

public abstract class Event {
	protected List<Integer> reqTokens;
	protected String command;
	
	public Event(List<Integer> reqTokens, String command){
		this.reqTokens = reqTokens;
		this.command = command;
	}
	
	public abstract void runEvent(Trainer t);
	
	public String getCommand(){
		return this.command;
	}
	
	public List<Integer> getReqTokens(){
		return reqTokens;
	}

}
