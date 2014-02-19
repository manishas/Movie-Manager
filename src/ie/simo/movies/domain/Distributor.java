package ie.simo.movies.domain;

import ie.simo.movies.util.RandomNumberProvider;

import java.io.Serializable;
import java.util.Random;

public class Distributor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8589354959041174785L;
	private String name;
	private String description;
	private Random random = RandomNumberProvider.getInstance();
	
	public Distributor(){
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	//TODO create composite object for rating + detailed levels
	public int makeOffer(Genre genre, RatingDetails r){
		int offer = 0;
		switch(genre){
			case Kids:		if(r.getSex() > 1 || r.getViolence() > 1 || r.getLanguage() > 1){
								return 0;
							} 
							else return defaultOffer();
						
			case Romance: 	offer = (defaultOffer() - 3*r.getViolence());
							return (offer > 0)? offer : 0;
	
			case Action:    offer = (defaultOffer() - 3*r.getViolence());
							return (offer > 0)? offer : 0;
							
			case Horror:	if(r.getSex() < 1 || r.getViolence() < 1 || r.getLanguage() < 1){
								return defaultOffer()/4;
							} 
							else return defaultOffer();
							
			default:  return defaultOffer();
		}
	}
	
	private int defaultOffer(){
		return random.nextInt(35);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((random == null) ? 0 : random.hashCode());
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
		if (!(obj instanceof Distributor)) {
			return false;
		}
		Distributor other = (Distributor) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		return true;
	}
	
	
}
