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

	
	public boolean anwenden(Pokemon p) {
		double r = Math.random();//liefert Zahl zwischen 1 und 0
		if(p.getFangrate()*wert>r){
			return true;
		}
		return false;
	}

	
	public String toString() {
		return name + " Fangwahrscheinlichkeit: " +(wert*100) + "%";
	}

	@Override
	public String getTyp() {
		return "BALL";
	}
}
