package Events;

import java.util.List;

import pokemon.Trainer;

public class GrantMoneyEvent extends Event {
	
	private int amount;
	
	public GrantMoneyEvent(List<String> reqTokens, List<String> reqNonTokens, String command, int amount){
		super(reqTokens, reqNonTokens, command);
		this.amount = amount;
	}
	
	public void runEvent(Trainer t){
		int newAmount = t.getGeld()+amount;
		t.setGeld(newAmount);
		System.out.println("Du besitzt jetzt: "+t.getGeld()+" Pokedollar.");
	}

}
