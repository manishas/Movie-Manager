package ie.simo.movies.earnings;

import ie.simo.movies.domain.MovieInfo;

public interface EarningsCalculator {
	public int calculate(MovieInfo movie, float starRating);
}