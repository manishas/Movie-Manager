package ie.simo.movies.generator;

import ie.simo.movies.domain.Genre;
import ie.simo.movies.generator.util.KindOfExplicitPhraseProvider;
import ie.simo.movies.generator.util.PhraseProvider;
import ie.simo.movies.generator.util.WordProvider;

public class PlotGenerator {

	private PersonNameGenerator png = new PersonNameGenerator();
	private WordProvider wf = new WordProvider();
	private PhraseProvider pp;

	public String romcom = "Life is tough for sexy %s %s until she meets %s, a hot %s. Things are going great until %s! Will they be able to overcome this? Will love find a way? Find out in %s!";
	public String action = "%s is a %s who needs to stop an evil %s, %s, from %s. %s is joined by a %s, %s. With the odds against them, can they prevent %s from %s?";
	public String sciFi = "He was a %s %s who %s, and who %s. She was a %s %s who %s and %s. %s...";
	public String drama = "He was a %s %s who %s, and who %s. She was a %s %s who %s and %s. %s...";
	public String comedy = "Funny stuff!";
	public String horror = "Spooky stuff";
	public String kids = "When %s, the %s meets %s the %s, it looks like they will never be friends. But they must %s, and things will never be the same!";
	
	public PlotGenerator(String contentType){
		if(contentType != "PLAIN"){
			pp = new KindOfExplicitPhraseProvider();
		}
		else {
			pp = new PhraseProvider();
		}
	}

	public String romcomPlot(String movieName) {
		String prof1 = pp.getProfession();
		String prof2 = pp.getProfession();
		String complication = pp.getRomanticComplication();

		return String.format(romcom, prof1, png.getfemaleName(),
				png.getMaleName(), prof2, complication, movieName);
	}

	public String actionPlot(String movieName) {
		String goodJob = pp.getGoodGuyJob();
		String goodJob2 = pp.getGoodGuyJob();
		String badjob = pp.getBadGuyJob();
		String badAim = pp.getBadGoal();
		String hero = png.getMaleName();
		String villain = png.getMaleName();
		String mate = png.getMaleName();
		String outcome = pp.getBadResult();

		return String.format(action, hero, goodJob, badjob, villain, badAim,
				hero, goodJob2, mate, villain, outcome);
	}

	public String sciFiPlot() {
		return String.format(sciFi, wf.getLowerCaseAdjective(),
				pp.getSciFiProfession(), pp.getPersonDescription(),
				pp.getPersonDescription(), wf.getLowerCaseAdjective(),
				pp.getSciFiProfession(), pp.getPersonDescription(),
				pp.getPersonDescription(), pp.getSciFiTwist());
	}

	public String comedyPlot() {
		return String.format(comedy, "");
	}

	public String dramaPlot() {
		return String.format(drama, wf.getLowerCaseAdjective(),
				pp.getProfession(), pp.getPersonDescription(), pp.getPersonDescription(),
				wf.getLowerCaseAdjective(), pp.getProfession(),
				pp.getPersonDescription(), pp.getPersonDescription(), pp.getDramaTwist());
	}

	public String horrorPlot() {
		return String.format(horror, "");
	}

	public String kidsPlot() {
		return String.format(kids, png.getFirstName(), pp.getKidsCharacter(),
				png.getFirstName(), pp.getKidsCharacter(), pp.getKidsGoal());
	}

	public String createPlot(Genre g, String filmName) {
		switch (g) {
		case Romance:
			return romcomPlot(filmName);
		case Action:
			return actionPlot(filmName);
		case Drama:
			return dramaPlot();
		case Comedy:
			return comedyPlot();
		case Horror:
			return horrorPlot();
		case Kids:
			return kidsPlot();
		case ScienceFiction:
			return sciFiPlot();
		default:
			return "Unrecognised Genre...";
		}
	}
}