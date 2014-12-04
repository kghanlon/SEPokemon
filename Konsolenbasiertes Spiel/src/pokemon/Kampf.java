package pokemon;

import java.util.List;
import java.util.Scanner;

public class Kampf {
	private static Scanner sc = Statisches.getScanner();
	
	/**
	 * 
	 * @return 0 Spieler hat noch kampffähige Pokemon !=0 spieler hat verloren also heilen an nächster stelle
	 */
	public static int start(Trainer trainer, List<Pokemon> gegner, boolean wild){
		while(kampffaehig(trainer) && kampffaehigGegner(gegner)){
			kaempfen(trainer, gegner, wild);			
		}	
		if(!kampffaehig(trainer)){		
			return 1;
		}else{
			return 0;
		}
	}
	
	private static boolean kampffaehig(Trainer t){
		for(int i=0; i<t.getTeam().length; i++)
		{
			if(t.getTeam()[i]!=null &&t.getTeam()[i].getKp()>0){
				return true;
			}			
		}
		return false;
	}
	
	private static boolean kampffaehigGegner(List<Pokemon> g){
		for(int i=0; i<g.size(); i++)
		{
			if(g.get(i).getKp()>0){
				return true;
			}			
		}
		return false;
	}
	
	private static void setUnfaehigGegner(List<Pokemon> g){
		for(int i=0; i<g.size(); i++)
		{
			g.get(i).setKp(0);
		}
	}
	
	private static void kaempfen(Trainer t, List<Pokemon> g, boolean w){
		//ich wähle immer das erste pokemon, denn sollte getauscht werden, dann ist das aktive pokemon immer an stelle 1 bzw 0
		System.out.println(t.getName() + ": " + t.getTeam()[0].getName() + " lvl:" + t.getTeam()[0].getLvl() + " kp:" + t.getTeam()[0].getKp() + "/" + t.getTeam()[0].getMaxkp());
		System.out.println("Gegner: " + g.get(0).getName() + " lvl:" + g.get(0).getLvl() + " kp:" + g.get(0).getKp() + "/" + g.get(0).getMaxkp());
		Statisches.sleep();
		int wahl = auswahlMenue(t);
		switch(wahl){
		case 0:
			attacke(t, g);
			break;
		case 1:
			pokeWahl(t, g, false);			
			break;
		case 2:
			itemWahl(t, g, w);			
			break;
		case 3:
			flucht(t, g, w);			
			break;
		}
	}
	
	private static void flucht(Trainer t, List<Pokemon> g, boolean w) {
		
		if(!w){
			System.out.println("\nDies ist ein Trainerkampf du kannst nicht fliehen!\n");
			Statisches.sleep();
		}else{
			if(t.getTeam()[0].getTempo()+2>=g.get(0).getTempo()){// man soll nicht immer fliehen können
				System.out.println("Du bist geflohen.");
				Statisches.sleep();
				setUnfaehigGegner(g);
			}else{
				System.out.println("Flucht gescheitert!");
				Statisches.sleep();
				if(gegnerAngriff(t, g)){
					pokemonKO(t, g, w);
				}
			}
		}		
	}

	private static void pokemonKO(Trainer t, List<Pokemon> g, boolean w){	
		System.out.println("Dein " + t.getTeam()[0].getName() + " wurde besiegt.");
		Statisches.sleep();
		if(kampffaehig(t)){			
			
			pokeWahl(t, g, true);
		}else{			
			System.out.println("Alle deine Pokemon sind besiegt!");
			Statisches.sleep();
		}
	}
	
	private static void pokemonGegnerKO(List<Pokemon> g){	
		if(kampffaehigGegner(g)){
			System.out.println("Gegner schickt " +g.get(getNextGegnerInt(g)).getName() + " in den Kampf.");
		}else{
			System.out.println("Gegner komplett besiegt!");
		}
	}

	private static void itemWahl(Trainer t, List<Pokemon> g, boolean w) {
		if(w){
			System.out.println("Bitte die Art des Items auswählen, das genutzt werden soll:");
			Statisches.sleep();
			System.out.println("1) Heilung");
			System.out.println("2) Ball");
			int h =0;
			try{
			h = sc.nextInt();
			}catch(Exception e){
				
			}
				switch(h-1){
					case 1:
						if(ballGewaehlt(t, g)){
							System.out.println("Gefangen!");							
							t.pokemonErsetzen(g.get(0));
							g.get(0).setKp(0);
						}else{
							System.out.println("Nicht gefangen!");
							if(gegnerAngriff(t, g)){
								pokemonKO(t, g, w);
							}
						}; break;
					default: 
						if(heilenGewaehlt(t)){
						if(gegnerAngriff(t, g)){
							pokemonKO(t, g, w);
						}
						}
				}
			}else{
				if(heilenGewaehlt(t)){
				if(gegnerAngriff(t, g)){
					pokemonKO(t, g, w);
				}
				}
			}
		}
	
	
	

/**
 * Leitet die Wahl des Items 
 * auf welches Pokemon es angewendet werden soll
 * und ob es Sinn macht.
 * Unsinnig Beispiel: lebendiges Pokemon wiederbeleben
 * @param t
 */
	private static boolean heilenGewaehlt(Trainer t) {
		System.out.println("Bitte das Item wählen:");
		Statisches.sleep();
		int counter=0;		
		for(int i=0; i< t.getItems().size(); i++){
			if(t.getItems().get(i).kannHeilen()){//zählen der ballitems in liste
				counter++;
			}
		}
		if(counter >0){
		int [] woHeilung = new int[counter];
		counter =0;
		for(int i=0; i< t.getItems().size(); i++){
			if(t.getItems().get(i).kannHeilen()){
				woHeilung[counter++] = i;
			}
		}//hiernach alle stellen der bälle in woBall
		
		counter = 1;
		for(int b:woHeilung){
			System.out.println(counter++ + ") " + t.getItems().get(b));//anzeigen der optionen man wählen kann
		}
		counter=0;
		int h=1; 
		try{
			h = sc.nextInt();
		}catch(Exception e){
			System.out.println("Es wurde wegen Falscheingabe " + t.getItems().get(woHeilung[0]).getName() + " gewählt.");
			Statisches.sleep();
		}//abfangen von falschen eingaben die kein int sind dann soll nichts passieren dann wird im zweifel der oberste ball geworfen
		Heilungsitem it = new Heilungsitem(ItemNamen.TRANK);
		if((h-1)<woHeilung.length){
			for(int i:woHeilung){
				if(counter==h-1){
					it = (Heilungsitem)t.getItems().get(i);
					break;
				}else{
					counter++;
				}
			}			
		}
		System.out.println("Bitte Pokemon wählen aus das es angewendet werden soll:");
		for(int i=0; i<t.getTeam().length; i++){
			if(t.getTeam()[i]!=null){
				System.out.println((i+1) + ") " + t.getTeam()[i].kampfAusgabe());
			}
		}
		h=0; 
		try{
			h = sc.nextInt();
		}catch(Exception e){
			h = 0;
		}
		for(int i=0; i<t.getTeam().length; i++){
			if(i==h-1){
				if(it.kannBeleben()){
					if(t.getTeam()[i].getKp()==0){
						t.getTeam()[i].setKp((int)(0.5*t.getTeam()[i].getMaxkp()));
						it.setAnzahl(it.getAnzahl()-1);
						System.out.println(t.getTeam()[i].kampfAusgabe());
						Statisches.sleep();
						return true;
					}else{
						System.out.println("Das Pokemon ist noch am leben!");
						Statisches.sleep();		
						return false;
					}
				}else{
					if(t.getTeam()[i].getKp()!=t.getTeam()[i].getMaxkp()&&t.getTeam()[i].getKp()>0){
						t.getTeam()[i].setKp((int)Statisches.min(t.getTeam()[i].getMaxkp(), t.getTeam()[i].getKp()+it.getWert()));
						it.setAnzahl(it.getAnzahl()-1);
						System.out.println(t.getTeam()[i].kampfAusgabe());
						return true;
					}else{
						if(t.getTeam()[i].getKp()==t.getTeam()[i].getMaxkp()){
							System.out.println("Das Pokemon hat die maximale Anzahl an kp!");
							Statisches.sleep();	
							return false;
						}else{
							System.out.println("Das Pokemon ist nicht mehr am leben.");
							Statisches.sleep();	
							return false;
						}
					}
				}
			}
			
		}
		
		Statisches.sleep();
		}else{
			System.out.println("Es gibt keine Items die man verwenden könnte.");
			Statisches.sleep();
			return false;
		}
		return false;
		}
			
		
	

	/**
	 * leitet die Auswahl des Balles und berechnet ob Pokemon gefangen wird 
	 * @param t Trainer
	 * @param g Gegnerliste von Pokemon
	 * @return ob gefangen oder nicht
	 */
	private static boolean ballGewaehlt(Trainer t, List<Pokemon> g) {
		System.out.println("Bitte den Ball wählen:");
		int counter=0;		
		for(int i=0; i< t.getItems().size(); i++){
			if(t.getItems().get(i).istBall()){//zählen der ballitems in liste
				counter++;
			}
		}
		int [] woBall = new int[counter];
		counter =0;
		for(int i=0; i< t.getItems().size(); i++){
			if(t.getItems().get(i).istBall()){
				woBall[counter++] = i;
			}
		}//hiernach alle stellen der bälle in woBall
		
		counter = 1;
		for(int b:woBall){
			System.out.println(counter++ + ") " + t.getItems().get(b));//anzeigen der optionen man wählen kann
		}
		counter=0;
		int h=1; 
		try{
			h = sc.nextInt();
		}catch(Exception e){
			System.out.println("Es wurde wegen Falscheingabe " + t.getItems().get(woBall[0]).getName() + " gewählt.");
			Statisches.sleep();
		}//abfangen von falschen eingaben die kein int sind dann soll nichts passieren dann wird im zweifel der oberste ball geworfen
		Ball ball = new Ball(ItemNamen.DUMMYBALL);
		if((h-1)<woBall.length){
			for(int i:woBall){
				if(counter==h-1){
					ball = (Ball)t.getItems().get(i);
					break;
				}else{
					counter++;
				}
			}
		}
		
		//ab hier eigentliches fangen
		double i = Math.random();
		Statisches.sleep();
		ball.setAnzahl(ball.getAnzahl()-1);
		Statisches.sleep();
		if(ball.getWert()*g.get(0).getFangrate()>=i){
			return true;
		}
		return false;
	}
	

	
	
	
	private static void pokeWahl(Trainer t, List<Pokemon> g, boolean besiegt) {
		if(t.getTeam().length>1){
		System.out.println("Bitte Pokemon wählen mit dem es getauscht werden soll:");
		int ersteslebendige=-1;
		for(int i=1; i<t.getTeam().length; i++){
			if(t.getTeam()[i]!=null && t.getTeam()[i].getKp()>0){
				System.out.println((i+1) + ") " + t.getTeam()[i].getName());
				if(ersteslebendige==-1){
					ersteslebendige = i;
				}
			}
		}
		int h=0; 
		try{
			h = sc.nextInt();
		}catch(Exception e){
			h = ersteslebendige;
		}
		for(int i=1; i<t.getTeam().length; i++){
			if(i==h-1){
				t.pokemonTauschen(i);
				break;
			}
		}
		if(!besiegt&&gegnerAngriff(t, g)){
			pokemonKO(t, g, true);//der bool ist hier egal wollte nur die signatur der obrigen methode nicht ändern
		}
	}else{
		System.out.println("Diese Option macht wenig Sinn solange man nur 1 Pokemon besitzt.");
		Statisches.sleep();
	}
		
	}

	
	
	
	
	
	private static void attacke(Trainer t, List<Pokemon> g) {
		System.out.println(t.getTeam()[0].ausgabeAttacken());
		int h=0; 
		try{
			h = sc.nextInt();
		}catch(Exception e){
			h = 0;			
		}
		
		switch(h-1){
		case 3: h=3; break;
		case 2: h=2; break;
		case 1: h=1; break;
		case 0: h=0; break;
		default: h=0;
		}
		if(t.getTeam()[0].getTempo()>g.get(0).getTempo()){
			if(trainerAngriff(t, g, h))
			{
				gegnerWechsel(t, g);
			}else{
				System.out.println(t.getName() + ": " + t.getTeam()[0].kampfAusgabe());
				System.out.println("Gegner: " + g.get(0).kampfAusgabe());
				Statisches.sleep();
				if(gegnerAngriff(t, g)){
					pokemonKO(t, g, true);//bool egal
				}
			}					
		}else{//gegner schneller
			if(gegnerAngriff(t, g)){
				pokemonKO(t, g, true);//bool egal
			}else{
				System.out.println(t.getName() + ": " + t.getTeam()[0].kampfAusgabe());
				System.out.println("Gegner: " + g.get(0).kampfAusgabe());
				Statisches.sleep();
				if(trainerAngriff(t, g, h)){
						gegnerWechsel(t, g);					
				}
			}
		}
	}
	
	
	private static void gegnerWechsel(Trainer t, List<Pokemon>g){
		System.out.println("Du hast " + g.get(0).getName() + " besiegt.");
		Statisches.sleep();
		t.getTeam()[0].expGewinn(g.get(0).getExp());//exp gewinnen
		pokemonGegnerKO(g);
		Pokemon a = g.get(0);//tauschen der Pokemon mit dem nächsten was fit ist
		int c = getNextGegnerInt(g);//auch wenn keins mehr frei ist ausführen beim nächsten durchlauf der while schleife bemerkt das system das g keine kampfähiogen pokemn mehr hat
		Pokemon b = g.get(c);
		g.set(0, b);
		g.set(c, a);	
	}
	
	private static int getNextGegnerInt(List<Pokemon> g){
		if(kampffaehigGegner(g)){
			for(int i=1; i<g.size(); i++){
				if(g.get(i).getKp()>0){
					return i;
				}
			}
		}
		return 0;
	}
	
	
	
	
	
	/**
	 * x³(1-attackenwert)/-27000 + attackenwert ist die funktion die die kp berechnet 
	 * 1 ist als staerke wert nicht erlaubt!
	 * es soll aber mindestens einer abgezogen werden
	 * @param staerkA angreifendes Pokemon staerke
	 * @param vertG verteidigendes Pokemon vert
	 * @param a attacke
	 * @return kp die vert pokemon abgezogen werden
	 */
	private static int attackenwert(int staerkA, int vertG, Attacke a){
		if(a.getSchaden()==0){
			return 0;
		}
		int x = staerkA-vertG;
		int wert = ((1-(a.getSchaden()/2)/(-27000)*(x*x*x))+(a.getSchaden()/2)); //ax³ + d
		if(wert<1){//mindestens 1 wird immer abgezogen
			wert=1;
		}
		return wert;
	}
	
	private static boolean trainerAngriff(Trainer t, List<Pokemon> g, int h){
		System.out.println(t.getName() + ": " + t.getTeam()[0].getName() + " greift mit " + t.getTeam()[0].getAttacken()[h].getName() + " an!\n");
		Statisches.sleep();
		if(attackenTreffer(t.getTeam()[0].getAttacken()[h])){
			double vorNachteil = g.get(0).getFschaden(t.getTeam()[0].getAttacken()[h].getTyp());//wird verändert sobald anfälligkeiten zwischen den typen drin sind
			if(vorNachteil>1.5){
				System.out.println("Das ist sehr effektiv!");
				Statisches.sleep();
			}
			if(vorNachteil<0.98){
				System.out.println("Das ist nicht sehr effektiv!");
				Statisches.sleep();
			}
			int verbleibendekp = (int)Statisches.max(g.get(0).getKp()-vorNachteil*attackenwert(t.getTeam()[0].getStaerke(),g.get(0).getVert(), t.getTeam()[0].getAttacken()[h]), 0);
			g.get(0).setKp(verbleibendekp);
			if(g.get(0).getKp()<1){						
				return true;
			}
			
		}
		return false;
	}
	
	private static boolean gegnerAngriff(Trainer t, List<Pokemon> g) {
		int attackenAnzahl=1;
		if(g.get(0).getAttacken()[1]!=null){//bestimmung der Anzahl der attacken des fremden pokemons
			attackenAnzahl = 2;
		}
		if(g.get(0).getAttacken()[2]!=null){
			attackenAnzahl = 3;
		}
		if(g.get(0).getAttacken()[3]!=null){
			attackenAnzahl=4;
		}
		
		int att=0;
		double r = Math.random()*attackenAnzahl;//zfällig auswählen einer attacke
		if(r<1){
			att=0;
		}else if(r<2){
			att=1;
		} else if(r<3){
			att=2;
		}else{
			att=3;
		}
		att=(int)att;
		System.out.println("GEGNER: " + g.get(0).getName() + " greift mit " + g.get(0).getAttacken()[att].getName()+ " an!\n");
		Statisches.sleep();
		if(attackenTreffer(g.get(0).getAttacken()[att])){
		double vorNachteil =t.getTeam()[0].getFschaden(g.get(0).getAttacken()[att].getTyp());//wird verändert sobald anfälligkeiten zwischen den typen drin sind
		if(vorNachteil>1.5){
			System.out.println("Das ist sehr effektiv!");
			Statisches.sleep();
		}
		if(vorNachteil<0.98){
			System.out.println("Das ist nicht sehr effektiv!");
			Statisches.sleep();
		}
		int verbleibendekp = (int)Statisches.max(t.getTeam()[0].getKp()-vorNachteil*attackenwert(g.get(0).getStaerke(), t.getTeam()[0].getVert(), g.get(0).getAttacken()[att]), 0);
		t.getTeam()[0].setKp(verbleibendekp);
		if(t.getTeam()[0].getKp()<1){
			return true;
		}
		}
		return false;
	}

	private static int auswahlMenue(Trainer t){
		System.out.println("Bitte die Option auswählen, die ausgeführt werden soll:");
		System.out.println("1) Angreifen");
		System.out.println("2) Pokemonwechsel");
		System.out.println("3) Items");
		System.out.println("4) Flucht");
		int h =0;
		try{
		h = sc.nextInt();
		}catch(Exception e){
			//könnte man ne anmerkung ausgeben aber ist auch gut default mäßig dann in die attacken zu gehen
		}
		switch(h){
			case 2: return 1;
			case 3: return 2;
			case 4: return 3;
		default: return 0;
		}
	}  
	
	private static boolean attackenTreffer(Attacke a){
		double tmp = a.getGen();
		double r = Math.random();
		if(r<tmp)
			return true;
		System.out.println(a.getName() + " verfehlt sein Ziel.");
		Statisches.sleep();
		return false;
	}
}
