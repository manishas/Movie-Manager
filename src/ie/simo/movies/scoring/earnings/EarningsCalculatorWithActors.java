package ie.simo.movies.scoring.earnings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.RandomNumberProvider;
/**
 * Earnings Calculator which uses Actors, Director and movie Rating 
 * @author Simon Wielens
 *
 */
public class EarningsCalculatorWithActors implements EarningsCalculator{

	@Override
	public int calculate(MovieInfo movie, float starRating) {
		Random r = RandomNumberProvider.getInstance();
		
		double cast = getTotalCastReputation(movie.getCast());
		cast = cast/100;
		int director = movie.getDirector().getReputation() / 10;
		double genreBonus = movie.getGenre().boxOffice();
		
		int total = (int) ((cast)*(starRating)*(director)*(genreBonus));
		int screens = r.nextInt(10) + 5;
		return total * screens;

	}
	
	/**
	 * get the total cast reputation, with diminishing returns.
	 * Each reputation gets divided by n, where n is the index of that actor
	 * @param cast
	 * @return total cast reputation
	 */
	private double getTotalCastReputation(Cast cast){
		cast.sortCastByRep();
		double denominator = 1.0;
		double total = 0.0;
		for(Actor a : cast.getActors()){
			total += ((a.getReputation())/denominator);
			denominator *= 2;
		}
		
		return total;
		
	}

}
