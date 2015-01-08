package Events;

import java.util.List;

import pokemon.Trainer;

/** Event das einen Text auf der Konsole ausgibt
 * @author hanlonk
 *
 */
public class InformationEvent extends Event {
	private String info;
	
	/**
	 * Konstuktor fuer ein InformationEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Liste aller Tokens die der Trainer nciht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param info - Der Text der auf der Konsole ausgegeben wird
	 */
	public InformationEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String info){
		super(reqTokens, reqNonTokens, command);
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
	}

}
