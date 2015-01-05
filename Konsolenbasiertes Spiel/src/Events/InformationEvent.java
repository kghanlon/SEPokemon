package Events;

import java.util.List;

import pokemon.Trainer;

public class InformationEvent extends Event {
	private String info;
	
	public InformationEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String info){
		super(reqTokens, reqNonTokens, command);
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
	}

}