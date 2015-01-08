package Events;

import java.util.List;

import pokemon.Trainer;

/** Event das alle Pokemon im Besitz des Trainer komplett heilt (PokeCenter)
 * @author hanlonk
 *
 */
public class HealPokemonEvent extends Event {
	
	/**
	 * Konstruktor fuer ein HealPokemonEvent
	 * @param reqTokens - alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 */
	public HealPokemonEvent(List<String> reqTokens, List<String> reqNonTokens, String command){
		super(reqTokens, reqNonTokens, command);
	}
	
	public void runEvent(Trainer t){
		t.teamHeilen();
	}

}
