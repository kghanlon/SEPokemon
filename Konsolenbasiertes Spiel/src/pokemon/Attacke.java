package pokemon;

/**
 * 
 * @author Kai
 *
 *	@class Attacke Pokemon haben Attacken und diese Werden im Kampf eingesetzt
 */
public class Attacke {
	private AttackenNamen name;
	private Typ typ;
	private float gen;
	private int schaden;
	
	/**
	 * 
	 * @param name Enum AttackenName über die die Attacke eindeutig identifiziert wird.
	 */
	public Attacke(AttackenNamen name){
		this.name = name;
		typ = Statisches.stringToTyp(Statisches.getAtthash().get(name).split("#")[0].trim());
		gen = Float.parseFloat(Statisches.getAtthash().get(name).split("#")[1].trim());
		schaden = Integer.parseInt(Statisches.getAtthash().get(name).split("#")[2].trim());
		if(schaden==1){
			schaden++;// 1 ist als staerkewert nicht erlaubt, siehe kampf attackenwert
		}
	}
	
	/**
	 * 
	 * @return gibt den Ausgabe String zurück der aus name, Typ, Schaden und Genauigkeit besteht
	 */
	public String toString(){
		return name +"\t" + Statisches.typToString(typ) + "\tSchaden: "+ schaden + "\tGenauigkeit: " + gen;
	}
	
	/**
	 * 
	 * @return Name der Attacke
	 */
	public String getName() {
		return name+"";
	}
	
	/**
	 * 
	 * @return Typ der Attacke
	 */
	public Typ getTyp() {
		return typ;
	}
	
	/**
	 * 
	 * @return Genauigkeit der Attacke
	 */
	public float getGen() {
		return gen;
	}
	
	
	/**
	 * 
	 * @return Schadenswert der Attacke
	 */
	public int getSchaden() {
		return schaden;
	}
}
