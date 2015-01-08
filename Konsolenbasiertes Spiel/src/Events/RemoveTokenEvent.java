package Events;

import java.util.List;

import pokemon.Trainer;

/** Event das ein Token aus der Trainer Token Liste entfernt
 * @author hanlonk
 *
 */
public class RemoveTokenEvent extends Event {
	
	private String token;
	
	/**
	 * Konstruktor fuer ein RemoveTokenEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Liste aller Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param token - Token das entfernt werden soll
	 */
	public RemoveTokenEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String token){
		super(reqTokens, reqNonTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Removes the specified token from the Trainers token List:
		t.removeToken(token);
	}

}
