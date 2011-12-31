package ie.simo.movies.domain;

public abstract class Person {
	private String firstName;
	private String surname;
	private int priceToHire;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getPriceToHire() {
		return priceToHire;
	}
	public void setPriceToHire(int priceToHire) {
		this.priceToHire = priceToHire;
	}
}
