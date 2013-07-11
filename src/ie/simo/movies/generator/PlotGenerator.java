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
	public String horror = "";
	public String kids = "When %s, the %s meets %s the %s, it looks like they will never be friends. But they must %s, and things will never be the same!";
	
	
	/*
	 * HORROR
	 * A ruthless criminal gang takes a young couple hostage and goes to ground in an abandoned house in the middle of nowhere. When the captive girl is killed, the tables are unexpectedly turned. The gang finds themselves outsmarted by an urbane and seasoned killer determined to ensure that no one lives.
	 * When a gang of masked, ax-wielding murderers descend upon the Davison family reunion, the hapless victims seem trapped...until an unlikely guest of the family proves to be the most talented killer of all.
	 * Two friends. One a junkie forced to go cold turkey in an isolated cabin. Mysterious forces. Personal demons. Dark humor. Genre-bending horror filmmaking that'll make your head explode.
	 * Francis must protect his very pregnant wife from a pack of murderous children and get the couple off the island alive.
	 * A clown comes back from the dead to haunt those who took his life during a fatal party mishap.
	 * Six high school seniors celebrating with day's excursion find themselves on rowboat attacked by man-eating fish and must decide who must be sacrificed as they fight their way back to shore.
	 */
	
	/*
	 * A veteran pot dealer creates a fake family as part of his plan to move a huge shipment of weed into the U.S. from Mexico.
	 * Fast-talking agent Jack's insincere patter is his chief weapon, but it irritates his assistant Aaron, threatens his marriage to Caroline and gets him into trouble repping guru/author Dr. Sinja. Jack suffers karmic repercussions from Sinja's magical Bodhi tree, which sheds one leaf for each word Jack speaks. After one thousand words, Jack will die.
	 * Big Earl, the owner of a Christmas tree lot in Compton, California runs into some trouble when his son Derrick crosses the line to prove to his father that he is a success.
	 * A day in the life of a party store along Detroit's Six Mile Road owned by a conservative Black father and his son and daughter. A lot can happen on the corner... especially on the hottest day of the summer.
	 * 
	 */
	
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