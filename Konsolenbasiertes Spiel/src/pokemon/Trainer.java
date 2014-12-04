package pokemon;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Trainer {
	private String name;
	private Pokemon[] team = new Pokemon[3];
	private int locationId;
	private int geld;
	List<String> tokens = new ArrayList<String>();
	private List<Item> items = new ArrayList<Item>();
	private Scanner sc = Statisches.getScanner();
	
	public Trainer(int locationId, List<String> tokens, String name, List<Item> items, Pokemon[] team, int geld){
		this.locationId = locationId;
		this.tokens = tokens;
		if(tokens!=null){
			this.tokens=tokens;
		}
		this.name=name;
		if(items!=null)
			this.items=items;
		if(team!=null)
			this.team=team;
		this.geld=geld;
	}
	
	//rueckgabewert ist die Anzahl der verbleibenden items der gleichen sorte
	public int setItemHinzu(Item item, int anzahl){// dadurch ist es moeglich auch dinge abzuziehen oder mehrere hinzuzufuegen
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
				if(item.getName().equals(items.get(i).getName())&& Math.abs(anzahl)<=items.get(i).getAnzahl()){//item mehr vorhanden genutzt werden soll
					items.get(i).setAnzahl(items.get(i).getAnzahl()+anzahl);
					if(items.get(i).getAnzahl()==0){
						items.remove(i);
						return 0;//items aufgebraucht
					}
					return items.get(i).getAnzahl();
				}
			}			
			return 0; //passiert nur wenn man mehr abziehen will als vorhanden sind
		}
	}
	
	
	public void teamHeilen(){
		for(int i=0; i<team.length; i++){
			team[i].setKp(team[i].getMaxkp());
		}
		System.out.println("Deine Pokemon sind vollstaendig geheilt.");
		Statisches.sleep();
	}
	public void pokemonTauschen(int zutauschen){
		
		
		System.out.println(team[0].getName() + " wurde gegen " + team[zutauschen].getName() + " ausgetauscht.\n");
		Statisches.sleep();
		Pokemon tmp = team[0];
		team[0] = team[zutauschen];
		team[zutauschen] = tmp;
	}
	
	public void pokemonErsetzen(Pokemon p){
		
		System.out.println("GEFANGEN: Du hast " + p.getName() + " gefangen.");
		Statisches.sleep();
		Pokemon p2 = new Pokemon(Statisches.strToPokeNamen(p.getName()), p.getLvl());
		p2.setKp(p.getKp());
		if(team[team.length-1]!=null){
			
			System.out.println("Welches Pokemon soll durch " + p.getName() + " ersetzt werden?\n");
		for(int i=0; i< team.length; i++){
			System.out.println((i+1) + ") " + team[i].getName());
		}
		int t;
		try{
			
			t = sc.nextInt();
			
		}catch(InputMismatchException e){
			System.out.println("Ungueltige Eingabe wird als nein gewertet.");//todo evtl. hier ne schleife bis gueltige eingabe
			t=0;
		}
		
		switch(t){
			case 1: 
				System.out.println(team[0].getName() + " wurde freigelassen.");
				team[0] = p2;
			break; 
			case 2:
				System.out.println(team[1].getName() + " wurde freigelassen.");
				team[1] = p2;
			break;
			case 3:
				System.out.println(team[2].getName() + " wurde freigelassen.");
				team[2] = p2;
			break;			
		default:;
		System.out.println(p2.getName() + "gehört nun zu deinem Team.");
		}
		}
		else{
			int j=0;
			for(int i=0; i<team.length; i++){
				if(team[i]==null){
					team[i]=p2;
					j=i;
					break;
				}
			}
			
			System.out.println(p2.getName() + " wurde an " + (j+1) +". Stelle im Team eingefügt.");			
			Statisches.sleep();
		}
	}
	
	
	
	public List<Item> getItems(){
		itemsAusfiltern();
		return items;
	} 
	
	/**
	 * filtert die elemente raus deren anzahl 0 ist es reicht eins auszuschmeissen,
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
	
	public List<String> getTokens(){
		return tokens;
	}
	
	public void setLocation(int newLocId){
		locationId = newLocId;
	}
	
	public void addToken(String token){
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

	public int getGeld() {
		return geld;
	}

	public void setGeld(int geld) {
		this.geld = geld;
	}

}
