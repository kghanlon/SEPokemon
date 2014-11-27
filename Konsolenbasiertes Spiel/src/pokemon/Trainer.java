package pokemon;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Trainer {
	private String name;
	private Pokemon[] team = new Pokemon[3];
	private int locationId;
	List<Integer> tokens = new ArrayList<Integer>();
	private List<Item> items = new ArrayList<Item>();
	private Scanner sc = Statisches.getScanner();
	
	public Trainer(int locationId, List<Integer> tokens, String name, List<Item> items, Pokemon[] team){
		this.locationId = locationId;
		this.tokens = tokens;
		if(!tokens.contains(0)){
			tokens.add(0);
		}
		this.name=name;
		this.items=items;
		this.team=team;
	}
	
	//rückgabewert ist die Anzahl der verbleibenden items der gleichen sorte
	public int setItemHinzu(Item item, int anzahl){// dadurch ist es möglich auch dinge abzuziehen oder mehrere hinzuzufügen
		if(anzahl>0){
			for(int i=0; i<items.size(); i++){
				if(item.getName().equals(items.get(i).getName())){//item schonmal vorhanden
					items.get(i).setAnzahl(items.get(i).getAnzahl()+anzahl);
					return items.get(i).getAnzahl();
				}
			}
			item.setAnzahl(anzahl);//item noch nicht in der liste
			items.add(item);
			return anzahl;
		}else
		{
			for(int i=0; i<items.size(); i++){
				if(item.getName().equals(items.get(i).getName())&&anzahl<=items.get(i).getAnzahl()){//item schonmal vorhanden
					items.get(i).setAnzahl(items.get(i).getAnzahl()+anzahl);
					if(items.get(i).getAnzahl()==0){
						items.remove(i);
						return 0;//items aufgebraucht
					}
					return items.get(i).getAnzahl();
				}
			}			
			return 0; //eigentlich sollte der fall nie eintreten da man ja nichts von einem item benutzen kann was man nicht hat
		}
	}
	
	public void pokemonTauschen(int zutauschen){
		
		Statisches.sleep();
		System.out.println(team[0].getName() + " wurde gegen " + team[zutauschen].getName() + " ausgetauscht.");
		Pokemon tmp = team[0];
		team[0] = team[zutauschen];
		team[zutauschen] = tmp;
	}
	
	public void pokemonErsetzen(Pokemon p){
		Statisches.sleep();
		System.out.println("GEFANGEN: Du hast " + p.getName() + " gefangen.");
		if(team[team.length-1]!=null){
			Statisches.sleep();
			System.out.println("Welches Pokemon soll durch " + p.getName() + " ersetzt werden?");
		for(int i=0; i< team.length; i++){
			System.out.println((i+1) + ") " + team[i].getName());
		}
		int t;
		try{
			t = sc.nextInt();
			
		}catch(InputMismatchException e){
			System.out.println("Ungültige Eingabe wird als nein gewertet.");//todo evtl. hier ne schleife bis gültige eingabe
			t=0;
		}
		switch(t){
			case 1: 
				System.out.println(team[0].getName() + " wurde freigelassen.");
				team[0] = p;
			break; 
			case 2:
				System.out.println(team[1].getName() + " wurde freigelassen.");
				team[1] = p;
			break;
			case 3:
				System.out.println(team[2].getName() + " wurde freigelassen.");
				team[2] = p;
			break;			
		default:;
		System.out.println(p.getName() + "gehört nun zu deinem Team.");
		}
		}
		else{
			int j=0;
			for(int i=0; i<team.length; i++){
				if(team[i]==null){
					team[i]=p;
					j=i;
					break;
				}
			}
			Statisches.sleep();
			System.out.println("Pokemon wurde an " + (j+1) +". Stelle im Team eingefügt.");			
		}
	}
	
	
	
	public List<Item> getItems(){
		itemsAusfiltern();
		return items;
	} 
	
	/**
	 * filtert die elemente raus deren anzahl 0 ist es reicht eins auszuschmeißen,
	 *  da maximal eins gleichezit durch benutzen auf 0 gesetzt werden kann
	 */
	private void itemsAusfiltern(){
		for(int i=0; i<items.size(); i++){
			if(items.get(i).getAnzahl()<1){
				items.remove(i);
				break;
			}
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + "\n");
		sb.append("LocId: " + locationId + "\n");
		for(int i=0; i<3; i++){
			if(team[i]!=null){
				sb.append("POKEMON " + (i+1) + ": " + team[i].getName() + "\n");
			}
		}
		for(int i=0; i<items.size(); i++){
			sb.append("ITEM " + i + ": " + items.get(i).getName() + " Anzahl: " + items.get(i).getAnzahl() +"\n");
		}
		/*for(int i=0; i<tokens.size(); i++){
			sb.append("TOKEN " + i + ": " + tokens.get(i) + "\n");
		}*/
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Pokemon[] getTeam(){
		return team;
	}
	 public void setTeamMitglied(int i, Pokemon p){
		 team[i]=p;
	 }
	
	public int getLocationId(){
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
