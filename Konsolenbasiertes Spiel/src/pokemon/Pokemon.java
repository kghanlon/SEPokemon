package pokemon;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pokemon {

	private HashMap<Integer, Attacke> moeglicheAttacken = new HashMap<>();
	private PokeNamen name;
	private Attacke [] attacken = new Attacke[4];
	private float fangrate;
	private Typ[] typ = new Typ[2];
	private int lvl, exp, maxkp, kp, staerke, vert, tempo;
	
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
		String [] moeg = Statisches.getPokehash().get(name).split("#")[3].split("$");
		for(int i=4; i<moeg.length; i++){
			Attacke a = new Attacke(Statisches.strToAttName(moeg[i].split(";")[1]));
			hashtmp.put(Integer.parseInt(moeg[i].split(";")[0].trim()), a);			
		}
		this.moeglicheAttacken=hashtmp;
		this.lvl=lvl;
		this.exp=exp;
		this.maxkp=maxkp;
		this.kp=kp;
		this.staerke=staerke;
		this.vert=vert;
		this.tempo=tempo;
		
	}
	
	public Pokemon(PokeNamen name, int lvl){
		this.name = name;
		Typ[] typtmp = new Typ[2];
		typtmp[0] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[0].trim());
		typtmp[1] = Statisches.stringToTyp(Statisches.getPokehash().get(name).split("#")[1].trim());
		this.typ=typtmp;
		this.lvl=lvl;
		this.fangrate=Float.parseFloat(Statisches.getPokehash().get(name).split("#")[2].trim());		
		HashMap<Integer, Attacke> hashtmp = new HashMap<>();
		String [] moeg = Statisches.getPokehash().get(name).split("#")[3].split("%");
		for(int i=0; i<moeg.length; i++){
			Attacke a = new Attacke(Statisches.strToAttName(moeg[i].split(";")[1]));
			hashtmp.put(Integer.parseInt(moeg[i].split(";")[0].trim()), a);			
		}
		this.moeglicheAttacken=hashtmp;
		zufallsWerteWild(lvl);
	}
	
	private void zufallsWerteWild(int lvl){
		int attackenanzahl=0;
		Attacke[] a = new Attacke[4];
		for(int i=lvl;i>0; i--){
			if(moeglicheAttacken.containsKey(i))
			//sucht die ältesten attacken, die das pokemon hätte lernen können und die einem seiner typen entsprechen
			{
				attacken[attackenanzahl]=moeglicheAttacken.get(i);
				attackenanzahl++;
			}
			if(attackenanzahl==4){
				break;
			}
		}
		exp=(int)(aufstiegsgrenze(lvl)/5);//mache ich damit man als exp gewinn des trainers einfach die nehmen kann die das fremde pokemon hat so steigt es auch wenn die level höher gehen
		maxkp = (int)(lvl*Math.pow(1.035, lvl)*2);
		kp=maxkp;
		double ran = Math.random()*3.14;
		staerke=(int)(10+lvl+Math.sin(ran)*lvl);//ausgeglichen angriff und verteidigung
		vert = (int)(10+lvl+Math.abs(Math.cos(ran))*lvl);
		tempo=(int)((6*staerke+vert)/7);//starkes pokemon ist schneller einfach festgelegt		
	}
	
	
	public void expGewinn(int i){
		exp+=i;
		if(exp >= aufstiegsgrenze(lvl)){
			levelaufstieg();
		}
	}
	
	private double aufstiegsgrenze(int i){
		return 300*Math.pow(1.035, i);// ist eine Kurve die in etwa die benötigen Exp gut bescheribt zunächst eher wenig und gegen ende was mehr
	}
	
	private void levelaufstieg(){
		lvl++;		
		exp = (int)Statisches.max(exp-aufstiegsgrenze(lvl-1), 0);
		System.out.println("\nLEVELAUFSTIEG\n" +name + " ist nun auf Level " + lvl + "!");
		if(gibtAttackeLvl(lvl)){
			attackeLernen(lvl);
		}
		Statisches.sleep();
		if(exp >= aufstiegsgrenze(lvl)){//wenn man so viele exp bekommt, dass direkt mehrere Level steigt dann geht er hier rein
			levelaufstieg();
		}
	}
	

	
	private boolean gibtAttackeLvl(int l){
		if(moeglicheAttacken.containsKey(l)){
			return true;
		}
		return false;
	}
	
	private void attackeLernen(int l){
		Attacke tmp = moeglicheAttacken.get(l);		
		if(attacken[3]!=null){//keine freien Attackenplätze
			Statisches.sleep();
			System.out.println("\nNEUE ATTACKE " + tmp.getName() + " erlernbar:\nTyp: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen());
			Statisches.sleep();
			System.out.println("\nWenn du diese Attacke erlernen möchtest,\nwähle aus welche Attacke weichen soll");
			Statisches.sleep();
			Statisches.sleep();
			System.out.println(ausgabeAttacken() + "\n(Wenn die Attacke nicht erlernt werden soll gib 0 ein)\n");
			String t;
			try{
				Scanner sc = new Scanner(System.in);
				t = sc.next();
				sc.close();
			}catch(InputMismatchException e){
				System.out.println("Ungültige Eingabe wird als nein gewertet.");//todo evtl. hier ne schleife bis gültige eingabe
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
		}else{
			int i=0;
			if(attacken[0]==null){//evtl. nicht sinnvoll weil eig jedes pokemon min eine attacke haben sollte aber so ist es vollständig
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
			Statisches.sleep();
			System.out.println("\nNEUE ATTACKE " + name + " hat " + tmp.getName() +" an Position "+ (i+1) +" gelernt.\n");
			Statisches.sleep();
			System.out.println("Typ: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen() );
		}
	}
	
	
	/*public Attacke befehlToAttacke(String s){
		String t = s.toUpperCase();
		for(int i=0; i<4; i++){
			if(attacken[i] !=null){
				String v = attacken[i].getName();//eingabe des namens auch möglich
				v=v.toUpperCase();
				if(t.equals(v)){
					return attacken[i];
				}
			}
			if(s.equals(i+1)){
				return attacken[i];
			}
		}
		return null;
	} */
	
	/*public static AttackenNamen strToAtt(String s){		
		Typ typ = Pokemon.stringToTyp(s.split("#")[0].trim());
		float gen = Float.parseFloat(s.split("#")[1].trim());
		int schaden = Integer.parseInt(s.split("#")[2]);
		for(AttackenNamen t: Attacke.getAtthash().keySet()){
			if(Attacke.getAtthash().get(t).equals(typ+"#"+gen+"#"+schaden+"#")){
				return t;
			}
		}
		return AttackenNamen.PLATSCHER;//wird eig nicht erreicht aber defualt ist platscher
	}*/
	
	
	
	
	public String ausgabeAttacken(){
		StringBuilder sb =new StringBuilder();
		sb.append("\n" + name + " kennt folgende Attacken:\n");
		for(int i=0; i<4; i++){
			if(attacken[i]!=null){
				sb.append((i+1) + ") " + attacken[i].getName());//todo: evtl. erweiterung dass hier direkt typ stärke und gen auch ausgegeben wird
				sb.append("\n");		
			}
		}
		sb.append("Bitte Attacke mittels Zahl wählen:\n");
		return sb.toString();
	}
	
	public String toString(){
		StringBuilder sb =new StringBuilder();
		sb.append("\n" + name + "\nAttacken:");
		for(int i=0; i<4; i++){
			if(attacken[i]!=null){
				sb.append("\t");
				sb.append(attacken[i].getName());//todo: evtl. erweiterung dass hier direkt typ stärke und gen auch ausgegeben wird
			}
		}
		if(lvl<100){
		sb.append("\nLevel: " + lvl + "\tExp: " + exp + "\tbenötigte Exp: " + (int)aufstiegsgrenze(lvl) +"\n");
		}else{
			sb.append("\nLevel: " + lvl + "\n");
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	public String getName() {
		return ""+name;
	}
	

	public Attacke [] getAttacken() {
		return attacken;
	}
	public float getFangrate() {
		return fangrate;
	}
	public Typ[] getTyp() {
		return typ;
	}
	public int getLvl() {
		return lvl;
	}
	public int getExp() {
		return exp;
	}
	public int getKp() {
		return kp;
	}
	public void setKp(int kp) {
		this.kp = kp;
	}
	public int getMaxkp() {
		return maxkp;
	}
	public int getStaerke() {
		return staerke;
	}
	public int getVert() {
		return vert;
	}
	public int getTempo() {
		return tempo;
	}







}
