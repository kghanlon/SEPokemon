package Events;

import java.util.ArrayList;

import pokemon.Trainer;

public class ShowTokenEvent extends Event {
	
	public ShowTokenEvent(){
		super(new ArrayList<String>(), new ArrayList<String>(), "token debug");
	}
	
	public void runEvent(Trainer t){
		for(int i = 0; i < t.getTokens().size(); i++){
			System.out.println(t.getTokens().get(i));
		}
	}

}
