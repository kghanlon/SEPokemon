package pokemon;

/**
 * 
 * @author Kai
 * @class Item Abstrakte Klasse von der spezialisierte Items erben
 */
public abstract class Item {

	protected ItemNamen name;
	protected int anzahl;
	
	/**
	 * 
	 * @param name Enum ItemNamen
	 */
	public Item(ItemNamen name){
		this.name=name;
		setAnzahl(1);
	}
	
	/**
	 * 
	 * @param namen Enum ItemNamen
	 * @param anzahl int Anzahl der einzufügenen Items beim Trainer
	 */
	public Item(ItemNamen namen, int anzahl){
		this.name=namen;
		this.setAnzahl(anzahl);
	}
	
	/**
	 * 
	 * @return Name des Items
	 */
	public String getName(){
		return name+"";
	}
	
	public abstract boolean istBall();
	public abstract boolean kannHeilen();
	public abstract boolean kannBeleben();
	public abstract String toString();
	public abstract String getTyp();

	/**
	 * 
	 * @return Anzahl des Items beim Trainer
	 */
	public int getAnzahl() {
		return anzahl;
	}

	/**
	 * 
	 * @param anzahl int Anzahl der Items die noch verbleiben von dem Item
	 */
	public void setAnzahl(int anzahl) {
		if(anzahl > 0){
			this.anzahl = anzahl;
		}
		else{
			this.anzahl=0;
		}
	}


}
