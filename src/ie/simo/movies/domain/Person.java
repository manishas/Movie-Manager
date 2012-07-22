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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + priceToHire;
		result = prime * result + reputation;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (priceToHire != other.priceToHire) {
			return false;
		}
		if (reputation != other.reputation) {
			return false;
		}
		return true;
	}
}
