package ie.simo.movies.scoring.rating;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.Director;

import java.util.Random;

public class RatingCalculator {
	public float getRating(Director director, Cast cast){
		Random r = new Random();
		//need to implement a fennell function
		double rating = (r.nextGaussian() + 5) *.5; //will USUALLY give num between 0-5, so need to normalise it
		if(rating < 0) rating = 0;
		
		//director rep = 0 - 100
		double repBonus = director.getReputation() / 100;
		rating = rating + repBonus;
		
		int count = 1;
		double castRep = 0.0;
		
		for(Actor actor : cast.getActors())
		{
			castRep = castRep + ((actor.getReputation()/100)/count);
			count++;
		}
		
		rating = rating + castRep;
		
		if(rating > 5) rating = 5.0;
		
		//need to get into 10 distinct steps rather than
		//the random digit we have now
		rating = rating * 2;
		rating = Math.round(rating);
		
		rating = rating * 0.5;	
		
		return (float)rating;
	}
}
