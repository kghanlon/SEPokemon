package Events;

import java.util.List;

import pokemon.Trainer;

/** Event das dem Trainer einen bestimmten Betrag an Pokedollarn abzieht
 * @author hanlonk
 *
 */
public class RemoveMoneyEvent extends Event {
	
	private int amount;
	
	/**
	 * Konstruktor fuer ein RemoveMoneyEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Liste aller Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param amount - Betrag des abgezogenen Gelds
	 */
	public RemoveMoneyEvent(List<String> reqTokens, List<String> reqNonTokens, String command, int amount){
		super(reqTokens, reqNonTokens, command);
		this.amount = amount;
	}
	
	public void runEvent(Trainer t){
		int newAmount = t.getGeld()-amount;
		t.setGeld(newAmount);
		System.out.println("Du besitzt jetzt: "+t.getGeld()+" Pokedollar.");
	}

}
