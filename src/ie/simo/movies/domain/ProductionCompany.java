package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Class to represent a production company, ie a 'game'
 * 
 * @author Simon Wielens
 *
 */
public class ProductionCompany implements Serializable{
	
	private static final long serialVersionUID = -4216556001748347643L;
	// level of fame for company, will be used for determining who can be hired 
	// and also the distribution of the movie, film festivals etc 
	private int reputation;
	private String name;
	private int budget;
	private final UUID uuid = UUID.randomUUID();
	private MovieInfo currentProject = null;
	//previous releases
	private List<MovieInfo> backCatalogue = new ArrayList<MovieInfo>();
	
	public ProductionCompany(String name){
		this.name = name;
		this.reputation = 0;
		this.budget = 0;	
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


	public int getBudget() {
		return budget;
	}


	public void setBudget(int budget) {
		this.budget = budget;
	}


	public List<MovieInfo> getBackCatalogue() {
		return backCatalogue;
	}


	public void setBackCatalogue(List<MovieInfo> backCatalogue) {
		this.backCatalogue = backCatalogue;
	}


	public MovieInfo getCurrentProject() {
		return currentProject;
	}


	public void setCurrentProject(MovieInfo currentProject) {
		this.currentProject = currentProject;
	}
}
