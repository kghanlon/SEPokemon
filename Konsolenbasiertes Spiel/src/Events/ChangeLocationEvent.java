package Events;

import java.util.List;

import pokemon.Trainer;

public class ChangeLocationEvent extends Event {
	
	private int nextLocId;
	private String info;
	
	public ChangeLocationEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String info, int nextLocId){
		super(reqTokens, reqNonTokens, command);
		this.nextLocId = nextLocId;
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
		t.setLocation(nextLocId);
	}

}
