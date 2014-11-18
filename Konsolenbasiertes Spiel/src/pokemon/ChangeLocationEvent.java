package pokemon;

import java.util.List;

public class ChangeLocationEvent extends Event {
	
	private int nextLocId;
	private String info;
	
	public ChangeLocationEvent(List<Integer> reqTokens, String command, String info, int nextLocId){
		super(reqTokens, command);
		this.nextLocId = nextLocId;
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
		t.setLocation(nextLocId);
	}

}
