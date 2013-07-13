package ie.simo.movies.domain;

import java.io.Serializable;

/**
 * Class to track hype and date released of a movie
 * @author Simon Wielens
 *
 */
public class MovieMetadata implements Serializable {

	private static final long serialVersionUID = 6891162565166144842L;
	
	private String dateReleased;
	private int positiveHype;
	private int negativeHype;
	private double starRating;
	private String criticReview;
	
	public String getCriticReview() {
		return criticReview;
	}
	public void setCriticReview(String criticReview) {
		this.criticReview = criticReview;
	}
	public double getStarRating() {
		return starRating;
	}
	public void setStarRating(double starRating) {
		this.starRating = starRating;
	}
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
