package ie.simo.movies.domain;

import java.io.Serializable;

/**
 * Class to represent all finalised details of a movie
 * @author Simon Wielens
 *
 */
public class MovieSummary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4120493963645386036L;
	private int totalCost;
	private int totalEarnings;
	private MovieInfo info;
	private MovieMetadata metadata;
	
	public MovieSummary(){
		metadata = new MovieMetadata();
		info = new MovieInfo();
	}
	
	public MovieInfo getInfo() {
		return info;
	}
	public void setInfo(MovieInfo info) {
		this.info = info;
	}
	public int getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(int totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	public MovieMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(MovieMetadata metadata) {
		this.metadata = metadata;
	}
	
	
}
