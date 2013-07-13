package ie.simo.movies.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class to represent a production company, ie a 'game'
 * 
 * @author Simon Wielens
 * 
 */
public class ProductionCompany implements Serializable {

	private static final long serialVersionUID = -4216556001748347643L;
	// level of fame for company, will be used for determining who can be hired
	// and also the distribution of the movie, film festivals etc
	private int reputation;
	private String name;
	private int budget;
	private String lastModifiedDate;
	private UUID uuid = UUID.randomUUID();
	private MovieInfo currentProject = null;
	// previous releases
	private List<MovieSummary> backCatalogue = new ArrayList<MovieSummary>();

	public ProductionCompany(String name) {
		SimpleDateFormat fmt = new SimpleDateFormat("kk:mm:ss dd-MM-yyyy");
		Date date = new Date();
		String dateString = fmt.format(date);
		this.name = name;
		this.reputation = 0;
		this.budget = 0;
		this.setLastAccessedDate(dateString);
	}

	public ProductionCompany(String name, int budget, int reputation, String lastModifiedDate,
			UUID uuid, MovieInfo currentProject,
			List<MovieSummary> backCatalogue) {
		this.name = name;
		this.budget = budget;
		this.reputation = reputation;
		this.lastModifiedDate = lastModifiedDate;
		this.uuid = uuid;
		this.currentProject = currentProject;
		this.backCatalogue = backCatalogue;
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

	public List<MovieSummary> getBackCatalogue() {
		return backCatalogue;
	}

	public void setBackCatalogue(List<MovieSummary> backCatalogue) {
		this.backCatalogue = backCatalogue;
	}

	public MovieInfo getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(MovieInfo currentProject) {
		this.currentProject = currentProject;
	}

	public String getLastAccessedDate() {
		return lastModifiedDate;
	}

	public void setLastAccessedDate(String lastAccessedDate) {
		this.lastModifiedDate = lastAccessedDate;
	}

	public Director getCurrentDirector() {
		
		return this.currentProject.getDirector();
	}

	public Cast getCurrentCast() {
		return this.currentProject.getCast();
	}

	public void setCurrentDirector(Director director) {
		this.currentProject.setDirector(director);
	}
}
