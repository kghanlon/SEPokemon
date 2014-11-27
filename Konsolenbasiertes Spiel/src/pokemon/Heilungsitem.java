package pokemon;

public class Heilungsitem extends Item {
	private int wert;
	public Heilungsitem(ItemNamen name){
		super(name);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
	}
	
	public Heilungsitem(ItemNamen name, int anzahl){
		super(name, anzahl);
		this.wert = Integer.parseInt(Statisches.getItemhash().get(name).split("#")[0].trim());
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
	
	public boolean kannBeleben(){
		String i = name +"";
		String v = "BELEBER";
		if(i.contains(v)){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString(){		
		return name + " Heilungswert: " +wert + "kp " + " Anzahl: " + anzahl ;		
	}

	@Override
	public String getTyp() {
		return "HEILEN";
	}
}
