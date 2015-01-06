package pokemon;


/**
 * 
 * @author Kai
 *
 *	@class Heilungsitem Spezialisiertes Item mit dem man entweder Heilen oder Beleben kann
 */
public class Heilungsitem extends Item {
	private int wert;
	
	/**
	 * 
	 * @param name Enum ItemName zur Identifizierung
	 */
	public Heilungsitem(ItemNamen name){
		super(name);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
	}
	
	/**
	 * 
	 * @param name Enum ItemName zur Identifizierung
	 * @param anzahl int Anzahl die dem Trainer hinzugef�gt werden sollen
	 */
	public Heilungsitem(ItemNamen name, int anzahl){
		super(name, anzahl);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
	}
	
	
	/**
	 * 
	 * @return Wert um den die Kp erh�ht werden sollen
	 */
	public int getWert(){
		return wert;
	}
	
	/**
	 * 
	 * @return Ist Item ein Ball?
	 */
	public boolean istBall(){
		return false;
	}

	/**
	 * 
	 * @return Kann das Item heilen?
	 */
	public boolean kannHeilen() {		
		return true;
	}
	
	
	/**
	 * pr�ft ob "Beleber im Namen vorkommt, da Itemname Enum ist BELEBER zu suchen"
	 * @return kann das Item beleben?
	 */
	public boolean kannBeleben(){
		String i = name +"";
		String v = "BELEBER";
		if(i.contains(v)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @return Standardausgabe Name [Heilungswert] und Anzahl
	 */
	public String toString(){		
		if(!kannBeleben()){
			return name + " Heilungswert: " +wert + "kp " + " Anzahl: " + anzahl ;		
		}else{
			return name + " Anzahl: " + anzahl ;	
		}
	}

	/**
	 * 
	 * @return HEILEN oder BELEBEN
	 */
	public String getTyp() {
		if(!kannBeleben()){
			return "HEILEN";
		}else{
			return "BELEBEN";
		}
	}
}
