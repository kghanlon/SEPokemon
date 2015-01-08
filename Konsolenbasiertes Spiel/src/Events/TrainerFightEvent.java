package Events;

import java.util.ArrayList;
import java.util.List;

import pokemon.Kampf;
import pokemon.LocationFactory;
import pokemon.PokeNamen;
import pokemon.Pokemon;
import pokemon.Trainer;

/**Event fuer den Kampf gegen einen Trainer
 * @author hanlonk
 *
 */
public class TrainerFightEvent extends Event {
	
	private List<PokeNamen> poke;
	private List<Integer> levels;
	//The same indecies belong together
	//So pikachu, glumanda and 5,6 means pikachu lvl 5 and glumanda lvl 6
	private int nextPokeCenterLocId;
	private int money;
	private String tokenAtWin;
	
	/**
	 * Konstruktor fuer ein TrainerFightEvent
	 * @param reqTokens - Liste aller benoetigten Tokens
	 * @param reqNonTokens - Lister aller Tokens die der Trainer nicht haben darf
	 * @param tokenAtWin - Das Token das man erhaelt sollte man den Kampf gewinnen
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param poke - Die Pokemon des Gegners
	 * @param levels - Die Level der Pokemon des Gegners
	 * @param nextPokeCenterLocId - Der Ort an dem man geheilt wird sollte man den Kampf verlieren
	 * @param money - Das moegliche Preisgeld
	 */
	public TrainerFightEvent(List<String> reqTokens, List<String> reqNonTokens, String tokenAtWin, String command,
							List<PokeNamen> poke, List<Integer> levels, int nextPokeCenterLocId, int money){
		super(reqTokens, reqNonTokens, command);
		this.poke = poke;
		this.levels = levels;
		this.nextPokeCenterLocId = nextPokeCenterLocId;
		this.money = money;
		this.tokenAtWin = tokenAtWin;
	}
	
	public void runEvent(Trainer t){
		//Liste der Pokemon des Gegners fuellen:
		List<Pokemon> pokemon = new ArrayList<Pokemon>();
		for(int i = 0; i < poke.size(); i++){
			pokemon.add(new Pokemon(poke.get(i), levels.get(i)));
		}
		int result = Kampf.start(t, pokemon, false);
		if(result == 0){
			//Kampf gewonnen! Geld und token hinzufuegen:
			Event getMoney = new GrantMoneyEvent(new ArrayList<String>(), new ArrayList<String>(), "", money);
			getMoney.runEvent(t);
			t.addToken(tokenAtWin);
		} else {
			//verloren, zürück zum nächsten pokecenter und team heilen und geld verlieren:
			
			//Wie viel Geld wird verloren? Zwei Formeln, niedrigere zählt:
			//Formel1: lvl vom oberstem Pokemon * geld was man gewinnen würde:
			int amountA = t.getTeam()[0].getLvl() * money;
			//Formel2: ein Viertel vom eigenem Geld:
			int amountB = (int) (t.getGeld()*0.25);
			int amount;
			if(amountA <= amountB){
				amount = amountA;
			} else {
				amount = amountB;
			}
			
			Event loseMoney = new RemoveMoneyEvent(new ArrayList<String>(), new ArrayList<String>(), "", amount);
			loseMoney.runEvent(t);
			
			System.out.println("Du hast den Kampf verloren und verlierst "+amount+" Pokedollar.\nDanach faellst du in Ohnmacht!");
			System.out.println("Nach einiger Zeit wachst du am PokeCenter in "
								+LocationFactory.locations.get(nextPokeCenterLocId).getName()+" wieder auf");
			t.setLocation(nextPokeCenterLocId);
			t.teamHeilen();
		}
	}

}
