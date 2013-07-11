package ie.simo.movies.generator.util;

import java.util.Random;

import ie.simo.movies.util.RandomNumberProvider;

public class PhraseProvider {

	private static Random r = RandomNumberProvider.getInstance();

	public String getProfession() {
		return random(profession);
	}

	public String getKidsCharacter() {
		return random(kidsCharacter);
	}

	public String getSciFiProfession() {
		return random(sciFiProfession);
	}

	public String getGoodGuyJob() {
		return random(goodGuyJob);
	}

	public String getPersonDescription() {
		return random(personDescription);
	}

	public String getRomanticComplication() {
		return random(romanticComplication);
	}

	public String getBadGuyJob() {
		return random(badGuyJob);
	}

	public String getBadGoal() {
		return random(badGoal);
	}

	public String getBadOutcome() {
		return random(badOutcome);
	}

	public String getBadResult() {
		return random(badResult);
	}

	public String getDramaTwist() {
		return random(dramaTwist);
	}

	public String getKidsGoal() {
		return random(kidsGoal);
	}

	public String getSciFiTwist() {
		return random(scifiTwist);
	}

	public String getBadFilmReview() {
		return random(badQualities);
	}

	public String getGoodFilmReview() {
		return random(goodQualities);
	}

	public String getWeirdFilmReview() {
		return random(weirdQualities);
	}

	public String getVanillaFilmReview() {
		return random(vanillaQualities);
	}
	
	public String getHorrorBadguy() {
		return random(horrorVillain);
	}
	
	public String getComedyGuy() {
		return random(comedyCharacter);
	}
	
	public String getComedyTwist() {
		return random(comedyTwist);
	}

	protected String random(String[] sourceArray) {
		return sourceArray[r.nextInt(sourceArray.length)];
	}

	// and who ------
	private String[] personDescription = { "had a mullet",
			"had a heart of gold", "had an amazing collection of pogs",
			"was a former olympic gymnast", "played a mean ukelele",
			"won the vietnam war for America",
			"had a grudge against the government", "had an eyepatch",
			"had a scar", "looked tough", "had long blonde hair",
			"had an incredible talent for using google", "had enormous feet",
			"was always well dressed", "was an heiress",
			"had connections to the royal family", "yearned to be human",
			"wished upon a star", "wanted to be a real boy", "wanted to rock",
			"had a lovely bottom",
			"had auditioned to be an understudy for the blue man group" };

	private String[] profession = { "basketball player", "novelist",
			"librarian", "fashion designer", "Lawyer", "chef", "waitress",
			"News broadcaster", "journalist", "detective", "cop",
			"travel agent", "hotel receptionist", "dancer",
			"advertising executive", "daytime soap opera actress",
			"Real Estate agent", "Circus performer", "High School teacher",
			"student", "Dentist", "Zoo keeper", "Aquarium cleaner",
			"pool cleaner", "ski instructor", "janitor", "farmer",
			"software developer", "graphic designer", "exotic dancer" };
	
	private String[] comedyCharacter = {
		"class clown", "gangster rapper", "", "", "", "", "", "", "", "", ""	
	};
	
	//they
	private String[] comedyTwist = {
			"have to go on an internship", "have to go undercover as old women", "have to ", "", "", "", "", "", "", "", ""	
		};

	private String[] kidsCharacter = { "friendly dog",
			"inquisitive young girl", "sarcastic cat", "nerdy boy",
			"super rich boy", "silly puppy", "hilarious tiger", "lonely fish",
			"smelly warthog", "funny meercat", "bitter bird", "big lion" };

	private String[] sciFiProfession = { "android", "android hunter", "alien",
			"astronaut", "humanoid", "space cop", "space marine",
			"terraformer", "planet explorer", "starship captain",
			"space ninja", "space pirate", "alien lifeform",
			"alien lifeform hunter", "asteroid miner", "space station janitor",
			"clone", "renegade pilot", "space mutant" };

	private String[] goodGuyJob = { "rookie cop", "US Marine", "fighter pilot",
			"streetwise detective", "helicopter mechanic",
			"illegal street racer", "hot cop", "professional footballer",
			"bomb disposal expert", "police dog trainer", "retired cop",
			"army sniper" };

	private String[] badGuyJob = { "ninja", "drug trafficker", "Mafia don",
			"gang member", "hardened criminal", "football hooligan",
			"cat burglar", "cannibal", "insane doctor", "hells angel" };

	// stop an evil [something] from ....
	private String[] badGoal = { "dumping toxic waste in the sea",
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
	private String[] badOutcome = { "dumping toxic waste in the water supply",
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
	private String[] romanticComplication = {
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

	private String[] dramaTwist = {
			"They must make the choice of which one of their children to give up for adoption",
			"They then find out they have been framed for a crime they didn't commit",
			"They are forced into witness protection after witnessing something no one should ever see",
			"They have to come to terms with their dog getting sick",
			"He must keep the family together while she struggles to get clean",
			"She must take the kids when he becomes an abusive drunk" };
	//
	private String[] scifiTwist = { "Together they joined a futuristic elite fighting force, fighting the corrupt banks and stopping androids from stealing our identities. " };
	
	private String[] horrorVillain = {"vampire", "zombie", "creepy little japanese girl", "g-g-ghost", "axe-murderer", "malevolent slime", "werewolf", "alien", "evil fog", "politician", "fat cat banker", "flock of evil birds", "mad scientist", "ex-lover, risen from the dead", "giant insect", "giant snake", "giant bat", "mutant"};
	
	private String[] kidsGoal = {
			"team up to beat the bullies at their own game", "learn to share",
			"learn what friendship is all about", "find their way back home",
			"help an alien phone home", "survive the whole summer at camp",
			"defend their house from crazy burglars",
			"put their differences aside to win the championship",
			"take the pridelands back from Scar and the hyenas" };

	protected String[] goodQualities = { "excellent", "good", "beautiful",
			"outstanding", "absolutely marvelous", "stylish", "slick",
			"hair-raising", "spine-tingling", "exhilarating",
			"thought-provoking", "fascinating", "magnificent", "emotional",
			"clever",

			"entertaining",

			"smart",

			"funny",

			"the kind of pleasure only my third wife could provide",

			"beautiful, like watching butterfly's make out.",

			// "exciting, like a transsexual hooker from Thailand",

			"brilliant, as if a magician had made my mother in-law disappear",

			"soothing, similar to cuddling a pool of puppies",

			"entertaining, more so then the usual donkey shows I'm partial to",

			"enjoyable",
			"lovable",
			"sensual, like getting stroked by a flamingo",
			"wonderful, it was as if I had parachuted into a flower in a field of dreams. " };

	protected String[] badQualities = {
			"awful",
			"bad",
			"dreadful",
			"utter nonsense",
			"stupid",
			"boring",
			"dull",
			"crap",
			"disasterous",
			"a waste of time",
			"preposterous",
			"absurd",
			"improbable",
			"shallow",
			"superficial",
			"incompetent",
			"as bad as expected",
			"incomprehensible",
			"pretty offensive, blasphemous even",
			"about as real as cher",
			"like a chinese labor camp", "like being violated",
			"the kind of thing that makes me re-think censorship",
			"unsettling",
			"woeful",
			"abysmal",
			"mediocre at best",
			"distressing to say the least",
	};

	private String[] vanillaQualities = { "complete", "mediocre", "nice",
			"interesting", "dark", "watchable", "thoughful", "alright",
			"dramatic" };

	private String[] weirdQualities = { "weird", "bizarre", "confusing",
			"slightly creepy", "silly", };

}
