package Events;

import java.util.List;

import pokemon.Trainer;

public class HealPokemonEvent extends Event {
	
	public HealPokemonEvent(List<String> reqTokens, List<String> reqNonTokens, String command){
		super(reqTokens, reqNonTokens, command);
	}
	
	public void runEvent(Trainer t){
		t.teamHeilen();
	}

}
