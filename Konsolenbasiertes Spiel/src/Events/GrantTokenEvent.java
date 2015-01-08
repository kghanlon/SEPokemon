package Events;

import java.util.List;

import pokemon.Trainer;

/**Event das ein Token zur Token Liste des Trainers hinzufuegt
 * @author hanlonk
 *
 */
public class GrantTokenEvent extends Event {
	
	private String token;
	
	/**
	 * Konstruktor fuer ein GrantTokenEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param token - Token das dem Trainer hinzugefuegt wird
	 */
	public GrantTokenEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String token){
		super(reqTokens, reqNonTokens, command);
		this.token = token;
	}
	
	public void runEvent(Trainer t){
		//Add the specified Token to the Trainers Token list
		t.addToken(token);
	}

}
