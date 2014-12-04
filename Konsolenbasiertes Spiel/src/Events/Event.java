package Events;

import java.util.List;

import pokemon.Trainer;

public abstract class Event {
	protected List<String> reqTokens;
	protected List<String> reqNonTokens;
	protected String command;
	
	public Event(List<String> reqTokens, List<String> reqNonTokens, String command){
		this.reqTokens = reqTokens;
		this.command = command;
		this.reqNonTokens = reqNonTokens;
	}
	
	public abstract void runEvent(Trainer t);
	
	public String getCommand(){
		return this.command;
	}
	
	public List<String> getReqTokens(){
		return reqTokens;
	}
	
	public List<String> getReqNonTokens(){
		return reqNonTokens;
	}

}
