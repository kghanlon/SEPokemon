package pokemon;

public class Ball extends Item {
	private float wert;
	public Ball(String name, float wert){
		super(name);
		this.wert = wert;
	}
	
	public float getWert(){
		return wert;
	}
}
