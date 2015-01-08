package Events;

import java.util.List;

import pokemon.Trainer;

/** Event das die LocationID des Trainers aendert und einen Text auf der Konsole ausgibt
 * @author hanlonk
 *
 */
public class ChangeLocationEvent extends Event {
	
	private int nextLocId;
	private String info;
	
	/**
	 * Konstruktor fuer ein ChangeLocationEvent
	 * @param reqTokens - Alle benoetigten Tokens
	 * @param reqNonTokens - Alle Tokens die der Trainer nicht haben darf
	 * @param command - Befehl zur Ausfuerung des Events
	 * @param info - Text der auf der Konsole ausgegeben wird
	 * @param nextLocId - Die neue Location ID des Trainers
	 */
	public ChangeLocationEvent(List<String> reqTokens, List<String> reqNonTokens, String command, String info, int nextLocId){
		super(reqTokens, reqNonTokens, command);
		this.nextLocId = nextLocId;
		this.info = info;
	}
	
	public void runEvent(Trainer t){
		System.out.println(info);
		t.setLocation(nextLocId);
	}

}
