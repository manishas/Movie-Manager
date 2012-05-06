package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producer implements Serializable{

	private static final long serialVersionUID = -2834415387607350745L;
	
	private String companyName;
	private String chairmanName;
	private int reputation;
	private int cash;
	
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
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
}
