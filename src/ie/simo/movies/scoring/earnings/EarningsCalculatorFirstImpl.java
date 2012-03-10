package ie.simo.movies.scoring.earnings;

import java.util.Random;

import ie.simo.movies.domain.MovieInfo;

/**
 * This impl only takes into account movie rating, director price to hire, and genre
 * @author Simon
 *
 */
public class EarningsCalculatorFirstImpl implements EarningsCalculator {
	public int calculate(MovieInfo movie, float starRating){
		Random r = new Random();
		return (int) (((20 + r.nextInt(80) * starRating)*(movie.getDirector().getPriceToHire()) * (movie.getGenre().boxOffice())))/10;
	}
}
