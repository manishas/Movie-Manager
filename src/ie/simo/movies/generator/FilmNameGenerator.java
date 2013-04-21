package ie.simo.movies.generator;

import ie.simo.movies.generator.util.WordProvider;
import ie.simo.movies.util.RandomNumberProvider;

import java.util.Random;

public class FilmNameGenerator {

	private WordProvider wf = new WordProvider();
	
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

		Random r = RandomNumberProvider.getInstance();
		return types[r.nextInt(types.length)];
	}
}