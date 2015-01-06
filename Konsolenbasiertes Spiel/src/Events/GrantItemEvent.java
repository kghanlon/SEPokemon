package Events;

import java.util.List;

import pokemon.Item;
import pokemon.Trainer;

public class GrantItemEvent extends Event {
	
	private int anzahl;
	private Item item;
	
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
