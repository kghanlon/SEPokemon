package pokemon;

public class Heilungsitem extends Item {
	private int wert;
	public Heilungsitem(String name, int wert){
		super(name);
		this.wert = wert;
	}
	
	public int getWert(){
		return wert;
	}
}
