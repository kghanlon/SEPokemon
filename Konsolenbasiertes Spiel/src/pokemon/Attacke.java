package pokemon;


public class Attacke {
	private AttackenNamen name;
	private Typ typ;
	private float gen;
	private int schaden;
	
	
	public Attacke(AttackenNamen name){
		this.name = name;
		typ = Statisches.stringToTyp(Statisches.getAtthash().get(name).split("#")[0].trim());
		gen = Float.parseFloat(Statisches.getAtthash().get(name).split("#")[1].trim());
		schaden = Integer.parseInt(Statisches.getAtthash().get(name).split("#")[2].trim());
		if(schaden==1){
			schaden++;// 1 ist als staerkewert nicht erlaubt, siehe kampf attackenwert
		}
	}
	
	/*public Attacke(AttackenNamen name, Typ typ, float gen, int schaden){
		this.name = name;
		this.typ = typ;
		this.gen = gen;
		this.schaden = schaden;
	}*/
	
	public String toString(){
		return name +"\t" + Statisches.typToString(typ) + "\tSchaden: "+ schaden + "\tGenauigkeit: " + gen;
	}
	

	public String getName() {
		return name+"";
	}
	public Typ getTyp() {
		return typ;
	}
	public float getGen() {
		return gen;
	}
	public int getSchaden() {
		return schaden;
	}
}
