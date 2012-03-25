package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.Random;

public class Distributor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8589354959041174785L;
	private String name;
	private String description;
	private Random random;
	
	public Distributor(){
		random = new Random();
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
	public int makeOffer(Genre genre, int sex, int violence, int language){
		
		switch(genre){
			case Kids:		if(sex > 1 || violence > 1 || language > 1){
								return 0;
							} 
							else return defaultOffer();
						
			case Romance: 	int offer = (defaultOffer() - 3*violence);
							return (offer > 0)? offer : 0;
	
			//case Action: 	
			
			default:  return defaultOffer();
		}
	}
	
	private int defaultOffer(){
		return random.nextInt(35);
	}
}
