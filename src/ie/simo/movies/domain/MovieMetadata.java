package ie.simo.movies.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to track hype, date released, and awards won of a movie
 * 
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
	private ArrayList<String> awards = new ArrayList<String>();

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

	public ArrayList<String> getAwards() {
		return awards;
	}

	public void setAwards(ArrayList<String> awards) {
		this.awards = awards;
	}

}
