package Events;

import java.util.List;

import pokemon.Item;
import pokemon.Trainer;

/** Ein Event das dem Trainer ein bestimmtes Item in bestimmter Anzahl hinzufuegt
 * @author hanlonk
 *
 */
public class GrantItemEvent extends Event {
	
	private int anzahl;
	private Item item;
	
	/**
	 * Konstruktor fuer ein GrantItemEvent
	 * @param reqTokens - alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param item - Das Item das dem Trainer hinzugefuegt wird
	 * @param anzahl - Anzahl der Items die der Trainer kriegt
	 */
	public GrantItemEvent(List<String> reqTokens, List<String> reqNonTokens, String command, Item item, int anzahl){
		super(reqTokens, reqNonTokens, command);
		this.item = item;
		this.anzahl = anzahl;
	}
	
	public void runEvent(Trainer t){
		t.setItemHinzu(item, anzahl);
		System.out.println(anzahl+" "+item.getName()+" erhalten.");
	}

}
