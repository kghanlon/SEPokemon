package pokemon;

import java.util.HashMap;

public class Attacke {
	private static HashMap <Integer, String> atthash = new HashMap<>();
	private String name;
	private Typ typ;
	private float gen;
	private int schaden;
	
	
	public Attacke(int id){
		name = atthash.get(id).split("#")[0].trim();
		typ = Pokemon.stringToTyp(atthash.get(id).split("#")[1].trim());
		gen = Float.parseFloat(atthash.get(id).split("#")[2].trim());
		schaden = Integer.parseInt(atthash.get(id).split("#")[3]);
	}
	
	public String toString(){
		return name +"\t" + Pokemon.typToString(typ) + "\tSchaden: "+ schaden + "\tGenauigkeit: " + gen;
	}
	
	public static HashMap <Integer, String> getAtthash() {
		return Attacke.atthash;
	}
	public static  void setAtthash(HashMap <Integer, String> atthash) {
		Attacke.atthash = atthash;
	}
	public String getName() {
		return name;
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
