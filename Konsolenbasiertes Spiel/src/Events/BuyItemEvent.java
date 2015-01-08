package Events;

import java.util.List;

import pokemon.Item;
import pokemon.Statisches;
import pokemon.Trainer;

/** Ein Event das ein bestimmtes Item zum Inventar hinzufuegt und dafuer die entsprechenden Pokedollar abzieht
 * @author hanlonk
 *
 */
public class BuyItemEvent extends Event {
	
	private int anzahl;
	private int kosten;
	private Item item;
	
	/**
	 * Konstruktor fuer ein BuyItemEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuehrung des Events
	 * @param kosten - Die Kosten fuer eins der gekauften Items
	 * @param item - Das Item das gekauft werden kann
	 */
	public BuyItemEvent (List<String> reqTokens, List<String> reqNonTokens, String command, int kosten, Item item){
		super(reqTokens, reqNonTokens, command);
		this.kosten = kosten;
		this.item = item;
	}
	
	public void runEvent(Trainer t){
		//Wie viele Items?
		System.out.println("Wie viele moechtest du?");
		anzahl = Statisches.getScanner().nextInt();
		//Wie viel kostet der Einkauf?
		int totalCost = anzahl*kosten;
		//Hat der Trainer genug Geld?
		if(t.getGeld() >= totalCost){
			//Geld abziehen:
			t.setGeld(t.getGeld() - totalCost);
			//Items hinzufuegen:
			t.setItemHinzu(item, anzahl);
			System.out.println(anzahl+" "+item.getName()+" fuer "+totalCost+" Pokedollar erhalten.");
		} else {
			//Der Trainer hat nicht genug Geld fuer den Einkauf
			System.out.println("Du hast nicht genug Geld dafuer.");
		}
	}

}
