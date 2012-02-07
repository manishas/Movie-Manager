package ie.simo.movies.domain;

import java.io.Serializable;

public class Producer implements Serializable{

	private static final long serialVersionUID = -2834415387607350745L;
	
	private String companyName;
	private String chairmanName;
	private int reputation;
	private int cash;
	private SavedGame[] savedGames;
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getChairmanName() {
		return chairmanName;
	}
	public void setChairmanName(String chairmanName) {
		this.chairmanName = chairmanName;
	}
	public int getReputation() {
		return reputation;
	}
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
	public SavedGame[] getSavedGames() {
		return savedGames;
	}
	public void setSavedGames(SavedGame[] savedGames) {
		this.savedGames = savedGames;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
}
