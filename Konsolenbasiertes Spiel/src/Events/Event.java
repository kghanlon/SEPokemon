package Events;

import java.util.List;

import pokemon.Trainer;

/** Die Abstrakte Event Klasse
 * @author hanlonk
 *
 */
public abstract class Event {
	protected List<String> reqTokens;
	protected List<String> reqNonTokens;
	protected String command;
	
	/**
	 * Konstruktor fuer ein Event
	 * @param reqTokens - Liste aller Tokens die der Trainer zur Ausfuehrung des Events haben muss
	 * @param reqNonTokens - Liste aller Tokens die der Trainer zur Ausfuehrung des Events nicht haben darf
	 * @param command - Befehl zur Ausfuehrung des Events
	 */
	public Event(List<String> reqTokens, List<String> reqNonTokens, String command){
		this.reqTokens = reqTokens;
		this.command = command;
		this.reqNonTokens = reqNonTokens;
	}
	
	/**
	 * Was passiert wenn das Event ausgefuehrt wird
	 * @param t
	 */
	public abstract void runEvent(Trainer t);
	
	/**
	 * Getter fuer den Befehl des Events
	 * @return Den Befehl zur Ausfuehrung des Events
	 */
	public String getCommand(){
		return this.command;
	}
	
	/**
	 * Getter fuer die benoetigten Tokens des Events
	 * @return Liste der zur Ausfuehrung benoetigten Tokens
	 */
	public List<String> getReqTokens(){
		return reqTokens;
	}
	
	/**
	 * Getter fuer die Tokens die der Trainer nicht haben darf
	 * @return Liste aller Tokens die fuer die Ausfuehrung nicht vorhanden sein duerfen
	 */
	public List<String> getReqNonTokens(){
		return reqNonTokens;
	}

}
