package pokemon;

import java.util.HashMap;

public abstract class Item {
	private static HashMap<Integer, String> itemhash = new HashMap<>();
	protected String name;
	private int anzahl;
	
	public Item(int id){
		this.name=Item.itemhash.get(id).split("#")[0].trim();
		setAnzahl(1);
	}
	
	public Item(int id, int anzahl){
		this.name=Item.itemhash.get(id).split("#")[0].trim();
		this.setAnzahl(anzahl);
	}
	
	public String getName(){
		return name;
	}
	
	public abstract boolean istBall();
	public abstract boolean kannHeilen();
	public abstract boolean anwenden(Pokemon p);
	public abstract String toString();

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public static HashMap<Integer, String> getItemhash() {
		return itemhash;
	}

	public static void setItemhash(HashMap<Integer, String> itemhash) {
		Item.itemhash = itemhash;
	}
}
