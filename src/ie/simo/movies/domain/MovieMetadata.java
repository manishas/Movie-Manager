package ie.simo.movies.domain;

/**
 * Class to track hype and date released of a movie
 * @author Simon Wielens
 *
 */
public class MovieMetadata {
	//
	private String dateReleased;
	private int positiveHype;
	private int negativeHype;
	
	public String getDateReleased() {
		return dateReleased;
	}
	public void setDateReleased(String dateReleased) {
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
