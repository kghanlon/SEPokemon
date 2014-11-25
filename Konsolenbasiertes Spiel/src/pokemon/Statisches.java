package pokemon;

import java.util.HashMap;

public class Statisches {
	private static HashMap<PokeNamen, String> pokehash = new HashMap<>();
	private static HashMap <AttackenNamen, String> atthash = new HashMap<>();
	private static HashMap<ItemNamen, String> itemhash = new HashMap<>();
	
	public static void einlesen(){		
		atthash.put(AttackenNamen.DONNERBLITZ, "elektro#0.9#80");
		atthash.put(AttackenNamen.FUNKENSPRUNG, "elektro#1.0#40");
		atthash.put(AttackenNamen.RUTENSCHLAG, "normal#1.0#0");
		atthash.put(AttackenNamen.RUCKZUCKHIEB, "normal#1.0#60");
		atthash.put(AttackenNamen.BODYSLAM, "normal#0.8#70");
		
		pokehash.put(PokeNamen.PIKACHU, "elektro#elektro#0.7#5;RUTENSCHLAG%10;RUCKZUCKHIEB%15;FUNKENSPRUNG%20;BODYSLAM%25;DONNERBLITZ");

	}
	
	public static HashMap <AttackenNamen, String> getAtthash() {
		return atthash;
	}

	
	public static HashMap<PokeNamen, String> getPokehash() {
		return pokehash;
	}
	
	public static HashMap<ItemNamen, String> getItemhash() {
		return itemhash;
	}

	
	public static void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Typ stringToTyp(String s){
		switch(s){
		case "WASSER": return Typ.WASSER;
		case "ELEKTRO" : return Typ.ELEKTRO;
		case "FEUER" : return Typ.FEUER;
		case "PFLANZE" : return Typ.PFLANZE;
		case "PSYCHO" : return Typ.PSYCHO;
		case "GESTEIN" : return Typ.GESTEIN;
		case "FLUG" : return Typ.FLUG;
		case "NORMAL" : return Typ.NORMAL;
		}
		return null;
	}
	
	
	public static AttackenNamen strToAttName(String s){
		for(AttackenNamen t :AttackenNamen.values()){
			if(s.equals(t+"")){
				return t;
			}
		}
		return AttackenNamen.PLATSCHER;
	}
	
	
	
	public static String typToString(Typ t){
		switch(t){
		case WASSER : return "WASSER";
		case ELEKTRO : return "ELEKTRO";
		case FEUER : return "FEUER";
		case PFLANZE : return "PFLANZE";
		case PSYCHO : return "PSYCHO";
		case GESTEIN : return "GESTEIN";
		case FLUG : return "FLUG";
		case NORMAL : return "NORMAL";
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
	
	
	
	
}
