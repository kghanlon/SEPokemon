package pokemon;

import java.util.List;

public class Location {
	private int id;
	private String name;
	private List<Event> events;
	
	public Location(int id, String name, List<Event> events){
		this.id = id;
		this.name = name;
		this.events = events;
	}

}

