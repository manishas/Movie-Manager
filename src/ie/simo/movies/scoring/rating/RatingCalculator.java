package ie.simo.movies.scoring.rating;

import java.util.Random;

public class RatingCalculator {
	public double getRating(int directorReputation){
		Random r = new Random();
		double rating = (r.nextGaussian() + 5) *.5; //will USUALLY give num between 0-5, so need to normalise it
		if(rating < 0) rating = 0;
		
		//director rep = 0 - 100
		double repBonus = directorReputation / 100;
		rating = rating + repBonus;
		if(rating > 5) rating = 5.0;
		
		//need to get into 10 distinct steps rather than
		//the random digit we have now
		rating = rating * 2;
		rating = Math.round(rating);
		
		rating = rating * 0.5;	
		
		return rating;
	}
}
