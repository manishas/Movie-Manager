package ie.simo.movies.domain;

import java.util.List;

public class Cast {
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
}
