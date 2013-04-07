package ie.simo.movies.generator;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.generator.util.WordFactory;

import java.util.Random;

public class PlotGenerator {

	private PersonNameGenerator png = new PersonNameGenerator();
	private WordFactory wf = new WordFactory();

	public String romcom = "Life is tough for sexy %s %s until she meets %s, a hot %s. Things are going great until %s! Will they be able to overcome this? Will love find a way? Find out in %s!";
	public String action = "%s is a %s who needs to stop an evil %s, %s, from %s. %s is joined by a %s, %s. With the odds against them, can they prevent %s from %s?";
	public String sciFi = "He was a %s %s who %s, and who %s. She was a %s %s who %s and %s. %s...";
	public String drama = "He was a %s %s who %s, and who %s. She was a %s %s who %s and %s. %s...";
	public String comedy = "Funny stuff!";
	public String horror = "Spooky stuff";
	public String kids = "Some kids stuff";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlotGenerator pg = new PlotGenerator();
		System.out.println(pg.romcomPlot("The Notebook"));

		System.out.println(pg.actionPlot(""));
		
		System.out.println(pg.dramaPlot());

	}

	private Random r = new Random();

	public String romcomPlot(String movieName) {
		String prof1 = random(professions);
		String prof2 = random(professions);
		String complication = random(complications);

		return String.format(romcom, prof1, png.getfemaleName(),
				png.getMaleName(), prof2, complication, movieName);
	}

	public String actionPlot(String movieName) {
		String goodJob = random(goodGuyJob);
		String goodJob2 = random(goodGuyJob);
		String badjob = random(badGuyJob);
		String badAim = random(badGoal);
		String hero = png.getMaleName();
		String villain = png.getMaleName();
		String mate = png.getMaleName();
		String outcome = random(badResult);

		return String.format(action, hero, goodJob, badjob, villain, badAim,
				hero, goodJob2, mate, villain, outcome);
	}

	public String sciFiPlot() { 
		return String.format(sciFi, "");
	}

	public String comedyPlot() {
		return String.format(comedy, "");
	}

	public String dramaPlot() {
		//He was a {adjective} {profession} who {goal/achievement}, who {describe}. She was {adjective} {profession} who {goal/achievement} and {describe}. Together they {did something}. Fighting the corrupt banks and stopping androids from stealing our identities. ";
		
		return String.format(drama, wf.getLowerCaseAdjective(), random(professions), random(desires), random(description), wf.getLowerCaseAdjective(), random(professions), random(desires), random(description), random(dramaTwist));
	}

	public String horrorPlot() {
		return String.format(horror, "");
	}

	public String kidsPlot() {
		return String.format(kids, "");
	}

	public String[] kidsCharacter = {"friendly dog", "inquisitive young girl", "sarcastic cat", "nerdy boy", "super rich boy", "silly puppy", "hilarious tiger", "lonely fish"};
	public String[] professions = { "basketball player", "novelist",
			"librarian", "fashion designer", "Lawyer", "chef", "waitress",
			"News broadcaster", "journalist", "detective", "cop",
			"travel agent", "hotel receptionist", "dancer",
			"advertising executive", "daytime soap opera actress",
			"Real Estate agent", "Circus performer", "High School teacher",
			"student", "Dentist", "Zoo keeper", "Aquarium cleaner",
			"pool cleaner", "ski instructor", "janitor", "farmer",
			"software developer", "graphic designer", "exotic dancer" };

	public String[] goodGuyJob = { "rookie cop", "US Marine", "fighter pilot",
			"streetwise detective", "helicopter mechanic",
			"illegal street racer", "hot cop", "professional footballer",
			"bomb disposal expert", "police dog trainer", "retired cop",
			"army sniper" };

	public String[] badGuyJob = { "ninja", "drug trafficker", "Mafia don",
			"gang member", "hardened criminal", "football hooligan",
			"cat burglar", "cannibal", "insane doctor", "hells angel" };

	// stop an evil [something] from ....
	public String[] badGoal = { "dumping toxic waste in the sea",
			"dumping toxic waste in the water supply",
			"trafficking heroin into the country", "kicking a puppy",
			"taking candy from a baby", "assassinating the president",
			"printing millions of dollars of counterfeit money",
			"painting counterfeit Picassos",
			"pulling off the biggest art heist in history",
			"hijacking a plane", "killing all of the hostages",
			"covering up his best friend's murder",
			"bribing the police commissioner", "breaking into fort Knox",
			"getting away with murder, literally", "starting World War 3",
			"stealing the nuclear launch codes",
			"continuing his career as a mastermind pickpocket",
			"perform gruesome experiments" };

	// stop an evil [something] from ....
	public String[] badOutcome = { "dumping toxic waste in the water supply",
			"trafficking heroin into the country", "kicking a puppy",
			"taking candy from a baby", "assassinating the president",
			"printing millions of dollars of counterfeit money",
			"painting counterfeit Picassos",
			"pulling off the biggest art heist in history",
			"hijacking a plane", "killing all of the hostages",
			"covering up his best friend's murder",
			"bribing the police commissioner", "breaking into fort Knox",
			"getting away with murder, literally", "starting World War 3",
			"stealing the nuclear launch codes",
			"continuing his career as a mastermind pickpocket" };

	// things are going great until [blank]!.
	public String[] complications = {
			"she ends up pregnant, with triplets",
			"his weird relatives come to stay",
			"her weird relatives come to stay",
			"scandal hits and they are hounded by paparazzi",
			"her strict parents have their say",
			"she gets declared bankrupt",
			"their relationship puts a billion-dollar deal at risk",
			"they are forced to go on a road trip together",
			"she gets put into a Witness Protection program after witnessing a mafia hit gone wrong",
			"it turns out she doesn't know him at all, it was a case of mistaken identity",
			"their holiday is ruined by having their luggage switched at the airport",
			"they get stranded on an Island",
			"they have to look after her Nephews and Nieces for the weekend",
			"she finds out he needs to marry her within 48 hours to get his grandfathers inheritance",
			"she is in a car accident and wakes up with amnesia",
			"he is hired as her new assistant", "it turns out he a ghost",
			"she finds out he is a thief",
			"she finds out he is a Secret Agent",
			"the Mayans predict the end of the world" };
	// can they prevent [person] from ..........?
	private String[] badResult = { "achieving his goal",
			"getting away with it", "destroying the world", "breaking the law",
			"escaping across the border",
			"becoming the biggest drug lord in the world",
			"killing millions of innocent people",
			"bribing the judges and winning X-Factor" };
	
	private String [] dramaTwist = {
			"They must make the choice of which one of their children to give up for adoption",
			"They then find out they have been framed for a crime they didn't commit",
			"They are forced into witness protection after witnessing something no one should ever see",
			"They have to come to terms with their dog getting sick",
			"He must keep the family together while she struggles to get clean",
			"She must take the kids when he becomes an abusive drunk"
	};
	
	private String [] scifiTwist = {
			"Together they joined a futuristic elite fighting force, fighting the corrupt banks and stopping androids from stealing our identities. "
	};

	// and who ------
	private String[] description = { "had a a mullet and a heart of gold",
			"had an amazing collection of pogs",
			"was a former olympic gymnast", 
			"played a mean ukelele", 
			"won the vietnam war for America" };

	// He was a {adjective} {profession} who ....
	private String[] desires = { 
			"yearned to be human", 
			"wished upon a star",
			"wanted to be a real boy", 
			"wanted to rock", 
			"had a lovely bottom" };

	private String random(String[] src) {
		return src[r.nextInt(src.length - 1)];
	}

	public String createPlot(Genre g, String filmName) {
		switch(g){
		case Romance: return romcomPlot(filmName);
		case Action: return actionPlot(filmName);
		case Drama: return dramaPlot();
		case Comedy: return comedyPlot();
		case Horror: return horrorPlot();
		case Kids: return kidsPlot();
		case ScienceFiction: return sciFiPlot();
		default: return "Unrecognised Genre...";
		}
		
	}
}
