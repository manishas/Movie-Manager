package ie.simo.movies.scoring.rating;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.MovieInfo;

import java.util.Random;

public class RatingCalculator {
	public float getRating(MovieInfo currentProject){
		
		double rating = falseFennellFunction();

		double directorRepBonus = getDirectorRating(currentProject);
		
		rating = rating + directorRepBonus;
		
		double castRep = getCastRating(currentProject);
		
		rating = rating + castRep;
		
		if(rating > 5) rating = 5.0;
		
		//need to get into 10 distinct steps rather than
		//the random digit we have now
		rating = rating * 2;
		rating = Math.round(rating);
		
		rating = rating * 0.5;	
		
		return (float)rating;
	}

	/**
	 * Classic false Fennell function implementation
	 * @return rating
	 */
	private double falseFennellFunction() {
		Random r = new Random();
		//need to implement a fennell function
		double rating = (r.nextGaussian() + 5) *.5; //will USUALLY give num between 0-5, so need to normalise it
		if(rating < 0) rating = 0;
		return rating;
	}

	private double getDirectorRating(MovieInfo currentProject) {
		Director director = currentProject.getDirector();
		double directorRepBonus = director.getReputation() / 100;
		
		if(director.isGoodAt(currentProject.getGenre())){
			directorRepBonus *= 1.1;
		}
		return directorRepBonus;
	}

	private double getCastRating(MovieInfo currentProject) {
		int count = 1;
		double castRep = 0.0;
		
		for(Actor actor : currentProject.getCast().getActors())
		{
			double rep =  ((actor.getReputation()/100)/count);
			if(actor.isGoodAt(currentProject.getGenre())){
				rep *= 1.1;
			}
			
			castRep += rep;
				
			count*=2;
		}
		return castRep;
	}
}
