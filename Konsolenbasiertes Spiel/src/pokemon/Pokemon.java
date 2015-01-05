package pokemon;
import java.util.*;
/**
 * 
 * @author Kai
 *
 *	@class Pokemon Ein Trainer hat Pokemonn die Kämpfen können, dafür haben sie Attacken ein Level und bestimmte Werte für den Kampf, je nach Anzahl von Exp die ein Pokemon insgesamt shon bekommen hat steigt es im Level bis max 100
 */
public class Pokemon {

	private HashMap<Integer, Attacke> moeglicheAttacken = new HashMap<>();
	private HashMap<Typ, Double> fschaden = new HashMap<>();
	private PokeNamen name;
	private Attacke [] attacken = new Attacke[4];
	private float fangrate;
	private Typ[] typ = new Typ[2];
	private int lvl, exp, maxkp, kp, staerke, vert, tempo;
	private Scanner sc = Statisches.getScanner();
	private int entwicklungslvl;
	
	
	
	
	
	/**
	 * Initiert ein neues Pokemon von dem man alle Werte kennt, weil es ein Trainerpokemon ist
	 * @param name Enum PokeNamen
	 * @param lvl int Level des Pokemons
	 * @param exp int Bisher gesammelte Anzahl Exp in diesem Level
	 * @param attackenid Enum AttackenNamen [4] 
	 * @param maxkp int höchst Anzahl der Kp die das Pokemon im vollkommen geheiltem Zustand hat
	 * @param kp int aktuelle Anzahl Kp
	 * @param staerke int Berechnungswert für Attacken auf andere Pokemon
	 * @param vert int Berechnungswert fur Attacken aus dieses Pokemon
	 * @param tempo int Zur Entscheidungsfindung welcher Pokemon zuerst angreifen darf
	 */
	public Pokemon(PokeNamen name, int lvl, int exp, AttackenNamen[] attackenid, int maxkp, 
			int kp, int staerke, int vert, int tempo){		
		this.name = name;
		Typ[] typtmp = new Typ[2];
		typtmp[0] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[0].trim());
		typtmp[1] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[1].trim());
		this.typ=typtmp;
		attacken= new Attacke[4];
		for(int i=0; i<attackenid.length; i++){
			attacken[i] = new Attacke(attackenid[i]);
		}
		this.fangrate=Float.parseFloat(Statisches.getPokehash().get(name).split("#")[2].trim());		
		HashMap<Integer, Attacke> hashtmp = new HashMap<>();
		String [] moeg = Statisches.getPokehash().get(name).split("#")[3].split("%");
		for(int i=0; i<moeg.length; i++){
			Attacke a = new Attacke(Statisches.strToAttName(moeg[i].split(";")[1]));
			hashtmp.put(Integer.parseInt(moeg[i].split(";")[0].trim()), a);			
		}
		HashMap<Typ, Double> fschadentmp = new HashMap<>();
		String [] faktor = Statisches.getPokehash().get(name).split("#")[4].split("%");
		for(int i=0; i<faktor.length; i++){
			fschadentmp.put(Statisches.stringToTyp(faktor[i].split(";")[0].trim()) ,Double.parseDouble(faktor[i].split(";")[1].trim()));			
		}
		
		this.moeglicheAttacken=hashtmp;
		this.fschaden=fschadentmp;
		this.entwicklungslvl = Integer.parseInt(Statisches.getPokehash().get(name).split("#")[5].trim());
		if(lvl>100){
			this.lvl=100;
		}else{
			this.lvl=lvl;
		}
		this.exp=exp;
		this.maxkp=maxkp;
		this.kp=kp;
		this.staerke=staerke;
		this.vert=vert;
		this.tempo=tempo;
		
		
	}
	
	/**
	 * 
	 * @param name Enum PokeNamen
	 * @param lvl int Level des Pokemons
	 */
	public Pokemon(PokeNamen name, int lvl){
		this.name = name;
		Typ[] typtmp = new Typ[2];
		typtmp[0] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[0].trim());
		typtmp[1] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[1].trim());
		this.typ=typtmp;
		if(lvl<100){
			this.lvl=lvl;
		}else{
			this.lvl=100;
		}
		this.fangrate=Float.parseFloat(Statisches.getPokehash().get(name).split("#")[2].trim());		
		HashMap<Integer, Attacke> hashtmp = new HashMap<>();
		String [] moeg = Statisches.getPokehash().get(name).split("#")[3].split("%");
		for(int i=0; i<moeg.length; i++){
			Attacke a = new Attacke(Statisches.strToAttName(moeg[i].split(";")[1]));
			hashtmp.put(Integer.parseInt(moeg[i].split(";")[0].trim()), a);			
		}
		HashMap<Typ, Double> fschadentmp = new HashMap<>();
		String [] faktor = Statisches.getPokehash().get(name).split("#")[4].split("%");
		for(int i=0; i<faktor.length; i++){
			fschadentmp.put(Statisches.stringToTyp(faktor[i].split(";")[0].trim()) ,Double.parseDouble(faktor[i].split(";")[1].trim()));			
		}
		this.moeglicheAttacken=hashtmp;
		this.fschaden=fschadentmp;
		this.entwicklungslvl = Integer.parseInt(Statisches.getPokehash().get(name).split("#")[5].trim());
		
		zufallsWerteWild(this.lvl);
	}
	
	private void zufallsWerteWild(int lvl){
		int attackenanzahl=0;
		for(int i=lvl;i>0; i--){
			if(moeglicheAttacken.containsKey(i))
			//sucht die Ã¤ltesten attacken, die das pokemon hÃ¤tte lernen kÃ¶nnen und die einem seiner typen entsprechen
			{
				attacken[attackenanzahl]=moeglicheAttacken.get(i);
				attackenanzahl++;
			}
			if(attackenanzahl==4){
				break;
			}
		}
		exp=(int)(aufstiegsgrenze(lvl)/5);//mache ich damit man als exp gewinn des trainers einfach die nehmen kann die das fremde pokemon hat so steigt es auch wenn die level hÃ¶her gehen
		maxkp = (int)(lvl*Math.pow(1.035, lvl)*2);
		kp=maxkp;
		double ran = Math.random()*3.14;
		staerke=(int)(10+lvl+Math.sin(ran)*lvl);//ausgeglichen angriff und verteidigung
		vert = (int)(10+lvl+Math.abs(Math.cos(ran))*lvl);
		tempo=(6*staerke+vert)/7;//starkes pokemon ist schneller einfach festgelegt		
	}
	
	
	/**
	 * erhöht die Exp des Pokemons um den Wert und initiiert intern einen Levelaufstieg wenn nötig
	 * @param i Anzahl Exp die hinzukommen
	 */
	public void expGewinn(int i){
		exp+=i;		
		System.out.println(name + " erhÃ¤lt " + i + " exp.");
		Statisches.sleep();
		if(exp >= aufstiegsgrenze(lvl) && lvl<100){
			levelaufstieg();
		}
	}
	
	private double aufstiegsgrenze(int i){
		return 300*Math.pow(1.035, i);// ist eine Kurve die in etwa die benÃ¶tigen Exp gut bescheribt zunÃ¤chst eher wenig und gegen ende was mehr
	}
	
	private void levelaufstieg(){
		lvl++;		
		exp = (int)Statisches.max(exp-aufstiegsgrenze(lvl-1), 0);
		System.out.println("\nLEVELAUFSTIEG\n" +name + " ist nun auf Level " + lvl + "!");
		Statisches.sleep();
		if(gibtAttackeLvl(lvl)){
			attackeLernen(lvl);
		}	
		if(lvl>=entwicklungslvl){
			entwicklung();
		}
		if(exp >= aufstiegsgrenze(lvl)){//wenn man so viele exp bekommt, dass direkt mehrere Level steigt dann geht er hier rein
			levelaufstieg();
		}
	}
	
	private void entwicklung(){
		System.out.println("ENTWICKUNG von " + name);
		Statisches.sleep();
		name = PokeNamen.values()[name.ordinal()+1];
		System.out.println("Du bist nun Trainer eines " + name);
		Statisches.sleep();
		maxkp +=20;
		kp+=20;
		staerke+=10;
		vert+=10;
		tempo+=15;
		entwicklungslvl = Integer.parseInt(Statisches.getPokehash().get(name).split("#")[5].trim());
	}
	
	private boolean gibtAttackeLvl(int l){
		if(moeglicheAttacken.containsKey(l)){
			return true;
		}
		return false;
	}
	
	private void attackeLernen(int l){
		Attacke tmp = moeglicheAttacken.get(l);		
		if(attacken[3]!=null){//keine freien AttackenplÃ¤tze
			System.out.println("\nNEUE ATTACKE " + tmp.getName() + " erlernbar:\nTyp: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen());
			Statisches.sleep();
			System.out.println("\nWenn du diese Attacke erlernen mÃ¶chtest,\nwÃ¤hle aus welche Attacke weichen soll");
			Statisches.sleep();
			System.out.println(ausgabeAttacken() + "\n(Wenn die Attacke nicht erlernt werden soll gib 0 ein)\n");
			String t;
			try{				
				t = sc.next();
			}catch(InputMismatchException e){
				System.out.println("UngÃ¼ltige Eingabe wird als nein gewertet.");//todo evtl. hier ne schleife bis gÃ¼ltige eingabe
				Statisches.sleep();
				t="0";
			}
			switch(t){
				case "1":
					System.out.println(attacken[0].getName() + " wurde verlernt."); 
					attacken[0] = tmp;				
				break; 
				case "2":
					System.out.println(attacken[1].getName() + " wurde verlernt.");
					attacken[1] = tmp;				
				break;
				case "3":
					System.out.println(attacken[2].getName() + " wurde verlernt.");
					attacken[2] = tmp;				
				break;
				case "4":
					System.out.println(attacken[3].getName() + " wurde verlernt.");
					attacken[3] = tmp;				
				break;
			default: System.out.println("Die Attacke wurde nicht erlernt");
			}	
			Statisches.sleep();
		}else{
			int i=0;
			if(attacken[0]==null){//evtl. nicht sinnvoll weil eig jedes pokemon min eine attacke haben sollte aber so ist es vollstÃ¤ndig
				attacken[0]=tmp;
				i=1;
			}else if(attacken[1]==null){
				attacken[1]=tmp;
				i=1;
			}else if(attacken[2]==null){
				attacken[2]=tmp;
				i=2;
			}else{
				attacken[3]=tmp;
				i=3;
			}
			
			System.out.println("\nNEUE ATTACKE " + name + " hat " + tmp.getName() +" an Position "+ (i+1) +" gelernt.\n");
			Statisches.sleep();
			System.out.println("Typ: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen() );
			Statisches.sleep();
		}
	}
	
	
	/**
	 * 
	 * @return Zeilenweise Ausgabe der Attacken mit Vorzahl um zu entscheiden welche man nutzen möchte
	 */
	public String ausgabeAttacken(){
		StringBuilder sb =new StringBuilder();
		sb.append("\n" + name + " kennt folgende Attacken:\n");
		for(int i=0; i<attacken.length; i++){
			if(attacken[i]!=null){
				sb.append((i+1) + ") " + attacken[i]);//todo: evtl. erweiterung dass hier direkt typ stÃ¤rke und gen auch ausgegeben wird
				sb.append("\n");		
			}
		}
		sb.append("Bitte Attacke mittels Zahl wÃ¤hlen:\n");
		return sb.toString();
	}
	
	/**
	 * 
	 * @return Name, Attacken, Level, Exp, benötigte Exp, wenn Level über 100: Name, Attacken und Level
	 */
	public String toString(){
		StringBuilder sb =new StringBuilder();
		sb.append("\n" + name + "\nAttacken:");
		for(int i=0; i<4; i++){
			if(attacken[i]!=null){
				sb.append("\t");
				sb.append(attacken[i].getName());//todo: evtl. erweiterung dass hier direkt typ stÃ¤rke und gen auch ausgegeben wird
			}
		}
		if(lvl<100){
		sb.append("\nLevel: " + lvl + "\tExp: " + exp + "\tbenÃ¶tigte Exp: " + (int)aufstiegsgrenze(lvl) +"\n");
		}else{
			sb.append("\nLevel: " + lvl + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * kp sind prozentual als Striche dargestellt und steigen bzw fallen nach jeder Aktion
	 * @return Zeile in der Alle für den Kampf relevanten Daten enthalten sind
	 */
	public String kampfAusgabe(){
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\tlvl " + lvl + "\t");
		for(int i=0; i< 100; i++){
			double tmo = (double)kp/maxkp*100.0+1;
			if(i< tmo){
				sb.append('|');
			}else{
				sb.append(' ');
			}
		}
		sb.append("kp");
		return  sb.toString() + kp +"/" + maxkp;
	}
	
	
	
	
	/**
	 * 
	 * @return Namen des Pokemons
	 */
	public String getName() {
		return ""+name;
	}
	

	/**
	 * 
	 * @return Attacken die das Pokemon beherscht
	 */
	public Attacke [] getAttacken() {
		return attacken;
	}
	
	/**
	 * 
	 * @return Fangwert für das Spezielle Pokemon
	 */
	public float getFangrate() {
		return fangrate;
	}
	/**
	 * Wenn ein Pokemon nur einen Typen hat dann dieser 2 mal
	 * @return Rückgabe der Typen die ein Pokemon hat
	 */
	public Typ[] getTyp() {
		return typ;
	}
	/**
	 * 
	 * @return Level des Pokemons
	 */
	public int getLvl() {
		return lvl;
	}
	
	/**
	 * 
	 * @return aktuelle Exp des Pokemons
	 */
	public int getExp() {
		return exp;
	}
	/**
	 * 
	 * @return aktuelle Kp des Pokemons
	 */
	public int getKp() {
		return kp;
	}
	/**
	 * 
	 * @param kp int Wert auf den die Kp gesetzt werden sollen
	 */
	public void setKp(int kp) {
		this.kp = kp;
	}
	
	/**
	 * 
	 * @return Maximale Anzahl Kp wenn vollständig geheilt
	 */
	public int getMaxkp() {
		return maxkp;
	}
	
	/**
	 * 
	 * @return Angriffswert für Kämpfe
	 */
	public int getStaerke() {
		return staerke;
	}
	/**
	 * 
	 * @return Verteigungswert für Kämpfe
	 */
	public int getVert() {
		return vert;
	}
	
	/**
	 * 
	 * @return Schnelligkeit für Kämpfe
	 */
	public int getTempo() {
		return tempo;
	}
	
	/**
	 * Gibt zurück ob der Typ anfällig gegen die Attacke ist
	 * @param typ Typ der angreifende Attacke
	 * @return Faktor um die eine Attacke auf dieses Pokemon mehr oder weniger effektiv ist
	 */
	public double getFschaden(Typ typ) {
		return fschaden.get(typ);
	}








}
