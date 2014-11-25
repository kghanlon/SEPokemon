package pokemon;

public class Heilungsitem extends Item {
	private int wert;
	public Heilungsitem(int id){
		super(id);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(id).split("#")[1].trim());
	}
	
	public Heilungsitem(int id, int anzahl){
		super(id, anzahl);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(id).split("#")[1].trim());
	}
	
	public int getWert(){
		return wert;
	}
	
	public boolean istBall(){
		return false;
	}

	
	public boolean kannHeilen() {		
		return true;
	}

	
	public boolean anwenden(Pokemon p) {
		if(p.getKp()!=p.getMaxkp()){
			p.setKp((int)Statisches.min(p.getKp()+wert, p.getMaxkp()));
			return true;
		}
		return false;
	}
	
	public String toString(){		
		return name + " Heilungswert: " +wert + "kp";		
	}
}
