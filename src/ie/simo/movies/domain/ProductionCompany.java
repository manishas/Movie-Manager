package ie.simo.movies.domain;

import java.util.UUID;
/**
 * Class to represent a production company, ie a 'game'
 * 
 * @author Simon Wielens
 *
 */
public class ProductionCompany {
	
	// level of fame for company, will be used for determining who can be hired 
	// and also the distribution of the movie, film festivals etc 
	private int reputation;
	private String name;
	private final UUID uuid = UUID.randomUUID();
	
	public ProductionCompany(String name){
		this.name = name;
		this.reputation = 0;
	}
	
	
	/*
	 * Getters & Setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UUID getUuid() {
		return uuid;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
}
