package ie.simo.movies.generator;

import ie.simo.movies.censor.Censor;
import ie.simo.movies.censor.IrishCensor;
import ie.simo.movies.censor.UkCensor;
import ie.simo.movies.censor.UsaCensor;

public class GeneratorFactory {
	public ReviewGenerator getReviewGenerator(String generatorType){
		if("mad".equals(generatorType)){
			return new ReviewGenerator();
		}
		else {
			return new ReviewGenerator();
		}
	}
}
