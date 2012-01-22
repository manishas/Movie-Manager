package ie.simo.movies.domain;

import java.io.Serializable;

public abstract class Person implements Serializable{

	private static final long serialVersionUID = -5518642644274052849L;
	private String name;
	private int priceToHire;
	//persons reputation - affects box office score
	private int reputation;
	
	
	public int getPriceToHire() {
		return priceToHire;
	}
	public void setPriceToHire(int priceToHire) {
		this.priceToHire = priceToHire;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getReputation() {
		return reputation;
	}
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
}
