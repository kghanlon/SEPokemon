package pokemon;

public class Ball extends Item {
	private int wert;
	public Ball(int id){
		super(id);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(id).split("#")[1].trim());;
	}
	
	public Ball(int id, int anzahl){
		super(id, anzahl);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(id).split("#")[1].trim());
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
}
