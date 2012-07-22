package ie.simo.movies.scoring.earnings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.MovieInfo;

public class EarningsCalculatorWithActors implements EarningsCalculator{

	@Override
	public int calculate(MovieInfo movie, float starRating) {
		Random r = new Random();
		return (int) (((getTotalCastReputation(movie.getCast()))*(20 + r.nextInt(80) * starRating)*(movie.getDirector().getPriceToHire()) * (movie.getGenre().boxOffice())))/10;

	}
	/**
	 * get the total cast reputation, with diminishing returns.
	 * Each reputation gets divided by n, where n is the index of that actor
	 * @param cast
	 * @return total cast reputation
	 */
	private int getTotalCastReputation(Cast cast){
		cast.sortCastByRep();
		double denominator = 1.0;
		double total = 0.0;
		for(Actor a : cast.getActors()){
			total += a.getReputation()/denominator;
			denominator += 1;
		}
		
		return (int) total;
		
	}

}
