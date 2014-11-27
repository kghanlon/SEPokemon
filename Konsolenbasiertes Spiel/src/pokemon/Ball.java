package pokemon;

public class Ball extends Item {
	private int wert;
	public Ball(ItemNamen name){
		super(name);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());;
	}
	
	public Ball(ItemNamen name, int anzahl){
		super(name, anzahl);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
	}
	
	public float getWert(){
		return wert;
	}
	
	public boolean istBall(){
		return true;
	}

	
	public boolean kannHeilen() {		
		return false;
	}

	public boolean kannBeleben() {		
		return false;
	}
	
	public String toString() {
		return name + " Anzahl: " + anzahl;
	}

	@Override
	public String getTyp() {
		return "BALL";
	}
}
