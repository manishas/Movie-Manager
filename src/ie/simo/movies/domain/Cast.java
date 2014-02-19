package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cast implements Serializable {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cast: [");
		for(Actor a : actors){
			builder.append(a.getName()+",");
		}
		builder.append("]");
		return builder.toString();
	}

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

	public void sortCastByRep() {
		//Collections.sort(actors, comparator)
	}

	public void add(Actor actor) {
		this.actors.add(actor);
	}

	public int size() {
		return this.actors.size();
	}

	public void clear() {
		this.actors.clear();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actors == null) ? 0 : actors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cast)) {
			return false;
		}
		Cast other = (Cast) obj;
		if (actors == null) {
			if (other.actors != null) {
				return false;
			}
		} else if (!actors.equals(other.actors)) {
			return false;
		}
		return true;
	}
	
}
