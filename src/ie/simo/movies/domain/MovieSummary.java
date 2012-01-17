package ie.simo.movies.domain;

import java.util.Date;

public class MovieSummary {
	private int totalCost;
	private int totalEarnings;
	private MovieInfo info;
	private MovieMetadata metadata;
	
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
