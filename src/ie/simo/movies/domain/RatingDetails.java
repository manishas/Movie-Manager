package ie.simo.movies.domain;

import java.io.Serializable;
/**
 * Class which maintains info about the censor rating details.
 * @author Simon Wielens
 *
 */
public class RatingDetails implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808451826628296465L;
	private Rating rating;
	private int sex;
	private int violence;
	private int language;
	
	private void updateRating(){
		
		int total = sex + violence + language;
		
		if(total == 0){
			rating = Rating.GENERAL;
		}
		else if(total == 1){
			rating = Rating.PG;
		}
		else if(sex < 2 && violence < 2 && language < 2){
			rating = Rating.TWELVES;
		}
		else if(total >= 7)
		{
			rating = Rating.EIGHTEENS;
		}
		else{
			rating = Rating.FIFTEENS;
		}
	}
	
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
		updateRating();
	}
	public int getViolence() {
		return violence;
	}
	public void setViolence(int violence) {
		this.violence = violence;
		updateRating();
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
		updateRating();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RatingDetails)) {
			return false;
		}
		RatingDetails other = (RatingDetails) obj;
		if (language != other.language) {
			return false;
		}
		if (rating != other.rating) {
			return false;
		}
		if (sex != other.sex) {
			return false;
		}
		if (violence != other.violence) {
			return false;
		}
		return true;
	}
}
