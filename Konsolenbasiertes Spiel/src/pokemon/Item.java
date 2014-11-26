package pokemon;

import java.util.HashMap;

public abstract class Item {

	protected ItemNamen name;
	private int anzahl;
	
	public Item(ItemNamen name){
		this.name=name;
		setAnzahl(1);
	}
	
	public Item(ItemNamen namen, int anzahl){
		this.name=namen;
		this.setAnzahl(anzahl);
	}
	
	public String getName(){
		return name+"";
	}
	
	public abstract boolean istBall();
	public abstract boolean kannHeilen();
	public abstract boolean anwenden(Pokemon p);
	public abstract String toString();
	public abstract String getTyp();

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}


}
