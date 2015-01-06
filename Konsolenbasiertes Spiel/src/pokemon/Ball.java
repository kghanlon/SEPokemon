package pokemon;

/**
 * 
 * @author Kai
 *	
 *	@class Ball Spezialisiertes Item mit dem man Pokemon fangen kann
 */
public class Ball extends Item {
	
	/**
	 * 
	 * @param name Enum ItemNamen zur Identifizierung des Items 
	 */
	public Ball(ItemNamen name){
		super(name);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());;
	}
	
	/**
	 * 
	 * @param name Enum ItemNamen zur Identifizierung des Items 
	 * @param anzahl int Anzahl die dem Trainer hinzugefügt werden sollen
	 */
	public Ball(ItemNamen name, int anzahl){
			super(name, anzahl);
			this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
	}
	
	/**
	 * 
	 * @return Fangwert des Balls
	 */
	public int getWert(){
		return wert;
	}
	
	/**
	 * 
	 * @return Ist Item ein Ball?
	 */
	public boolean istBall(){
		return true;
	}

	/**
	 * 
	 * @return Ist Item eins was heilen kann?
	 */
	public boolean kannHeilen() {		
		return false;
	}

	/**
	 * 
	 * @return Kann Item beleben?
	 */
	public boolean kannBeleben() {		
		return false;
	}
	
	/**
	 * 
	 * @return Standardausgabe eines Balls Name und Anzahl
	 */
	public String toString() {
		return name + " Anzahl: " + anzahl;
	}

	/**
	 * 
	 * @return Typ des Items als String
	 */
	public String getTyp() {
		return "BALL";
	}
}
