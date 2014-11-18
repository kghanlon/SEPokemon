package pokemon;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
	
	private int locationId;
	List<Integer> tokens = new ArrayList<Integer>();
	
	public Trainer(int locationId, List<Integer> tokens){
		this.locationId = locationId;
		this.tokens = tokens;
	}
	
	public int getLocation(){
		return locationId;
	}
	
	public List<Integer> getTokens(){
		return tokens;
	}
	
	public void setLocation(int newLocId){
		locationId = newLocId;
	}
	
	public void addToken(int token){
		if(tokens.contains(token)){
			//Do nothing, Token already acquired.
		} else {
			tokens.add(token);
		}
	}
	
	public void removeToken(int token){
		if(tokens.contains(token)){
			tokens.remove((Integer)token); //Braucht man, damit nicht list.remove(index int) aufgerufen wird!
		} else {
			//Do nothing, token not in possession anyway.
		}
	}

}
