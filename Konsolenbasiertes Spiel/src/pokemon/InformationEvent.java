package pokemon;

import java.util.List;

public class InformationEvent extends Event {
	private String info;
	
	public InformationEvent(List<Integer> reqTokens, String command, String info){
		super(reqTokens, command);
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
	}

}
