package pokemon;

import java.util.*;

/**
 * 
 * @author Kai
 *
 * @class Trainer Trainer ist der Hauptcharakter der das Abenteuer bestreitet, Pokemon besitzt oder fangen kann und Items kaufen, finden oder benutzen kann 
 */
public class Trainer {
	private String name;
	private Pokemon[] team = new Pokemon[3];
	private int locationId;
	private int geld;
	List<String> tokens = new ArrayList<String>();
	private List<Item> items = new ArrayList<Item>();
	private Scanner sc = Statisches.getScanner();
	
	/**
	 * 
	 * @param locationId int Standort in der Welt
	 * @param tokens List von Dingen die er in der Welt schon erledigt hat
	 * @param name String Name dem man ihm gegeben hat
	 * @param items List Items die er benutzten kann
	 * @param team Pokemon[3] Seine Pokemon die er nutzt
	 * @param geld int Geld
	 */
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
	/**
	 * Unterscheidet automatisch ob davon schon Items vorhanden sind oder nach der Aktion noch welche vorhanden sind oder alle aufgebraucht
	 * 
	 * @param item das betreffende Item das genutzt wird
	 * @param anzahl Anzahl der Items die hinzu oder weg kommen sprich gekauft gefunden oder verbraucht werden
	 * @return Anzahl der verbleibenden items der gleichen sorte
	 */
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
	
	
	
	/**
	 * setzt bei allen Pokemon die Kp auf MaxKp
	 */
	public void teamHeilen(){
		//Pokemoncount, damit wir wissen wie viele Pokemon wirklich dabei sind:
		int count = 0;
		for(int i = 0; i < this.getTeam().length; i++){
			if(this.getTeam()[i] == null){
				//Nichts
			} else {
				//Hier ist ein pokemon, also:
				count++;
			}
		}
		
		for(int i=0; i<count; i++){
			team[i].setKp(team[i].getMaxkp());
		}
		System.out.println("Deine Pokemon sind vollstaendig geheilt.");
		Statisches.sleep();
	}
	
	
	/**
	 * 
	 * @param zutauschen int position an dem das Pokemon eingefuegt werden soll
	 */
	public void pokemonTauschen(int zutauschen){
		System.out.println(team[0].getName() + " wurde gegen " + team[zutauschen].getName() + " ausgetauscht.\n");
		Statisches.sleep();
		Pokemon tmp = team[0];
		team[0] = team[zutauschen];
		team[zutauschen] = tmp;
	}
	
	
	/**
	 * 
	 * @param p Pokemon das ins Team eingefuegt werden soll, oder eben nicht
	 */
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
		System.out.println(p2.getName() + "gehoert nun zu deinem Team.");
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
			
			System.out.println(p2.getName() + " wurde an " + (j+1) +". Stelle im Team eingefuegt.");			
			Statisches.sleep();
		}
	}
	
	
	/**
	 * 
	 * @return Liste der Items die der Trainer besitzt
	 */
	public List<Item> getItems(){
		itemsAusfiltern();
		return items;
	} 
	
	/**
	 * filtert die elemente raus deren anzahl 0 ist es reicht eins auszuschmeissen, da maximal eins gleichezit durch benutzen auf 0 gesetzt werden kann
	 */
	private void itemsAusfiltern(){
		for(int i=0; i<items.size(); i++){
			if(items.get(i).getAnzahl()<1){
				items.remove(i);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @return Name, LocId, Pokemons und Items mit Anzahl
	 */
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
		return sb.toString();
	}
	
	
	
	/**
	 * 
	 * @return Pokemon im Team
	 */
	public Pokemon[] getTeam(){
		return team;
	}
	
	/**
	 * 
	 * @param i int Stelle an der das Pokemon im Team gesetzt wird
	 * @param p Pokemon das gesetzt wird
	 */
	 public void setTeamMitglied(int i, Pokemon p){
		 team[i]=p;
	 }
	
	 /**
	  * 
	  * @return aktuelle Location id
	  */
	public int getLocationId(){
		return locationId;
	}
	
	/**
	 * 
	 * @return Liste der bisher erfuellten Tokens
	 */
	public List<String> getTokens(){
		return tokens;
	}
	
	/**
	 * 
	 * @param newLocId int an die Trainer nun kommt
	 */
	public void setLocation(int newLocId){
		locationId = newLocId;
	}
	
	/**
	 * 
	 * @param token String ein Ziel bzw. Event was erledigt wurde
	 */
	public void addToken(String token){
//		if(tokens!=null&&!tokens.contains(token)){			
//			tokens.add(token);
//		}else {
//			tokens=new ArrayList<String>();
//			tokens.add(token);
//		}
		if(tokens==null){
			//Liste existiert noch nicht, also anlegen und rein mit dem token
			tokens=new ArrayList<String>();
			tokens.add(token);
		} else if(!tokens.contains(token)){
			//Liste ist da und hat token noch tnicht: rein mit dem token
			tokens.add(token);
		} //Sonst nichts machen (eg token ist schon drinnen.
	}
	
	/**
	 * 
	 * @param token String der geluescht werden soll
	 */
	public void removeToken(String token){
		if(tokens.contains(token)){
			tokens.remove(token); 
		} else {
			//Do nothing, token not in possession anyway.
		}
	}

	/**
	 * 
	 * @return Name des Trainers
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name String der als Name gespeichert wird
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return Geld des Trainers
	 */
	public int getGeld() {
		return geld;
	}

	/**
	 * 
	 * @param geld int Geld
	 */
	public void setGeld(int geld) {
		this.geld = geld;
	}

}
