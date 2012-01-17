package ie.simo.movies.domain;

import java.util.Date;

public class MovieMetadata {
	private Date dateReleased;
	private int positiveHype;
	private int negativeHype;
	
	public Date getDateReleased() {
		return dateReleased;
	}
	public void setDateReleased(Date dateReleased) {
		this.dateReleased = dateReleased;
	}
	public int getPositiveHype() {
		return positiveHype;
	}
	public void setPositiveHype(int positiveHype) {
		this.positiveHype = positiveHype;
	}
	public int getNegativeHype() {
		return negativeHype;
	}
	public void setNegativeHype(int negativeHype) {
		this.negativeHype = negativeHype;
	}

}
