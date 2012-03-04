package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cast implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8756845311371945115L;
	private List<Actor> actors;

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	public int getCostOfActors(){
		int total = 0;
		for(Actor a : actors){
			total += a.getPriceToHire();
		}
		return total;
	}
	
	public Cast(){
		this.actors = new ArrayList<Actor>();
	}
}
