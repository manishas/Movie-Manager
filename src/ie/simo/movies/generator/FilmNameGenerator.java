package ie.simo.movies.generator;

import ie.simo.movies.generator.util.WordFactory;
import java.util.Random;

public class FilmNameGenerator {

	private WordFactory wf = new WordFactory();
	
	public String newFilmTitle() {

		String[] types = {
				String.format("The %s %s", wf.getAdjective(), wf.getNoun()),
				String.format("The %s", wf.getNoun()),
				String.format("%s", wf.getNoun()),
				String.format("%s of %s", wf.getNoun(), wf.getPlace()),
				String.format("%s in %s", wf.getPluralNoun(), wf.getPlace()),
				String.format("The %s from %s", wf.getNoun(), wf.getPlace()),
				String.format("The %s And The %s %s", wf.getNoun(),
						wf.getAdjective(), wf.getNoun()) };

		Random r = new Random();
		return types[r.nextInt(types.length)];
	}
}