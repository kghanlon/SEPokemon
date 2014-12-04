package Events;

import java.util.ArrayList;
import java.util.List;

import pokemon.Kampf;
import pokemon.LocationFactory;
import pokemon.PokeNamen;
import pokemon.Pokemon;
import pokemon.Trainer;

public class WildPokemonEvent extends Event {
	
	private List<PokeNamen> pokemon;
	int minlvl, maxlvl;
	int nextPokeCenterLocId;
	
	public WildPokemonEvent(List<String> reqTokens, List<String> reqNonTokens,
			String command, List<PokeNamen> pokemon, int minlvl, int maxlvl, int nextPokeCenterLocId) {
		super(reqTokens, reqNonTokens, command);
		this.pokemon = pokemon;
		this.minlvl = minlvl;
		this.maxlvl = maxlvl;
		this.nextPokeCenterLocId = nextPokeCenterLocId;
		
	}

	public void runEvent(Trainer t){
		//Fight a random Pokemon that is available here:
		
		//Zufälliges Pokemon holen:
		int pokeIndex = (int)((Math.random()) * pokemon.size());
		//Zufälliges level für das Pokemon:
		int lvl = minlvl + (int)(Math.random() * ((maxlvl+1) - minlvl));
		//Create the wild Pokemon:
		List <Pokemon> wildPokemon = new ArrayList<Pokemon>();
		wildPokemon.add(new Pokemon(pokemon.get(pokeIndex), lvl));
		
		//Start the fight:
		System.out.println("Ein wildes "+wildPokemon.get(0).getName()+" erscheint!");
		System.out.println("Los "+t.getTeam()[0].getName()+"!");
		int result = Kampf.start(t, wildPokemon, true);
		
		//Hat man den kampf gewonnen(oder geflohen) oder nicht:
		if(result == 0){
			//gewonnen, da wilder Kampf passiert nichts spannendes.
		} else {
			//verloren, zürück zum nächsten pokecenter und team heilen.
			System.out.println("Du fällst in Ohnmacht...");
			System.out.println("Nach einiger Zeit wachst du am PokeCenter in "
								+LocationFactory.locations.get(nextPokeCenterLocId).getName()+" wieder auf");
			t.setLocation(nextPokeCenterLocId);
			t.teamHeilen();
		}
	}

}
