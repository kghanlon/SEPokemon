package pokemon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Pokemon {
	private static HashMap<Integer, String> pokehash = new HashMap<>();
	private HashMap<Integer, Attacke> moeglicheAttacken = new HashMap<>();
	private String name;
	private Attacke [] attacken = new Attacke[4];
	private float fangrate;
	private Typ[] typ = new Typ[2];
	private int lvl, exp, maxkp, kp, staerke, vert, tempo;
	
	public Pokemon(int id, int lvl, int exp, int[] attackenid, int maxkp, 
			int kp, int staerke, int vert, int tempo, float fangrate, HashMap<Integer, Attacke> moeglicheAttacken){
		
		
		this.name = pokehash.get(id).split("#")[0].trim();
		Typ[] typtmp = new Typ[2];
		typtmp[0] = Pokemon.stringToTyp(pokehash.get(id).split("#")[1].trim());
		typtmp[1] = Pokemon.stringToTyp(pokehash.get(id).split("#")[2].trim());
		this.typ=typtmp;
		attacken= new Attacke[4];
		for(int i=0; i<attackenid.length; i++){
			attacken[i] = new Attacke(attackenid[i]);
		}
		this.fangrate=fangrate;
		this.lvl=lvl;
		this.exp=exp;
		this.maxkp=maxkp;
		this.kp=kp;
		this.staerke=staerke;
		this.vert=vert;
		this.tempo=tempo;
		this.moeglicheAttacken=moeglicheAttacken;
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
		exp = (int)Pokemon.max(exp-aufstiegsgrenze(lvl-1), 0);
		System.out.println("\nLEVELAUFSTIEG\n" +name + " ist nun auf Level " + lvl + "!");
		if(gibtAttackeLvl(lvl)){
			attackeLernen(lvl);
		}
		Pokemon.sleep();
		if(exp >= aufstiegsgrenze(lvl)){//wenn man so viele exp bekommt, dass direkt mehrere Level steigt dann geht er hier rein
			levelaufstieg();
		}
	}
	
	public static void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
			Pokemon.sleep();
			System.out.println("\nNEUE ATTACKE " + tmp.getName() + " erlernbar:\nTyp: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen());
			Pokemon.sleep();
			System.out.println("\nWenn du diese Attacke erlernen möchtest,\nwähle aus welche Attacke weichen soll");
			Pokemon.sleep();
			Pokemon.sleep();
			System.out.println(ausgabeAttacken() + "\n(Wenn die Attacke nicht erlernt werden soll gib 0 ein)\n");
			int t;
			try{
				Scanner sc = new Scanner(System.in);
				t = sc.nextInt();
				sc.close();
			}catch(Exception e){
				e.printStackTrace();
				t=0;
			}
			switch(t){
				case 1: attacken[0] = tmp;
				System.out.println(attacken[0].getName() + " wurde verlernt.");
				break; 
				case 2:attacken[1] = tmp;
				System.out.println(attacken[1].getName() + " wurde verlernt.");
				break;
				case 3:attacken[2] = tmp;
				System.out.println(attacken[2].getName() + " wurde verlernt.");
				break;
				case 4:attacken[3] = tmp;
				System.out.println(attacken[3].getName() + " wurde verlernt.");
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
			Pokemon.sleep();
			System.out.println("\nNEUE ATTACKE " + name + " hat " + tmp.getName() +" an Position "+ (i+1) +" gelernt.\n");
			Pokemon.sleep();
			System.out.println("Typ: " + tmp.getTyp() + "\tSchaden:" + tmp.getSchaden()+ "\tGen: " + tmp.getGen() );
		}
	}
	
	
	public Attacke befehlToAttacke(String s){
		String t = s.toUpperCase();
		for(int i=0; i<4; i++){
			if(attacken[i] !=null){
				String v = attacken[i].getName().toUpperCase();//eingabe des namens auch möglich
				if(t.equals(v)){
					return attacken[i];
				}
			}
			if(s.equals(i+1)){
				return attacken[i];
			}
		}
		return null;
	} 
	
	public static Typ stringToTyp(String s){
		switch(s){
		case "wasser": return Typ.wasser;
		case "elektro" : return Typ.elektro;
		case "feuer" : return Typ.feuer;
		case "pflanze" : return Typ.pflanze;
		case "psycho" : return Typ.psycho;
		case "gestein" : return Typ.gestein;
		case "flug" : return Typ.flug;
		case "normal" : return Typ.normal;
		}
		return null;
	}
	public static String typToString(Typ t){
		switch(t){
		case wasser : return "wasser";
		case elektro : return "elektro";
		case feuer : return "feuer";
		case pflanze : return "pflanze";
		case psycho : return "psycho";
		case gestein : return "gestein";
		case flug : return "flug";
		case normal : return "normal";
		}
		return null;
	}
	 
	public static double min(double a, double b){
		if(a<=b){
			return a;
		}
		return b;
	}
	
	public static double max(double a, double b){
		if(a>=b){
			return a;
		}
		return b;
	}
	
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
		return name;
	}
	public void setName(String s){//evtl nützlich wenn man einen namen anders haben möchte
		if(s.length()>0){
			name = s;
		}
	}	
	public static HashMap<Integer, String> getPokehash() {
		return pokehash;
	}
	public static void setPokehash(HashMap<Integer, String> pokehash) {
		Pokemon.pokehash = pokehash;
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
