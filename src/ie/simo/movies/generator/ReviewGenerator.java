package ie.simo.movies.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ie.simo.movies.domain.MovieInfo;

/**
 * Class to generate newspaper reviews
 * 
 * @author Simon
 *
 */
public class ReviewGenerator {
	
	enum Sentiment {
		GOOD, BAD, INDIFFERENT
	}
	
	Random randomGenerator = new Random();
	
	String [] goodQualities = {
			"excellent", "good", 
			"beautiful", "outstanding",
			"absolutely marvelous",
			"stylish", "slick",
			"hair-raising",
			"spine-tingling",
			"exhilarating",
			"thought-provoking",
			"fascinating",
			"magnificent",
			"emotional",
			"clever",

			"entertaining",

			"smart",

			"funny",

			"the kind of pleasure only my third wife could provide",

			"beautiful, like watching butterfly's make out.",

			//"exciting, like a transsexual hooker from Thailand",

			"brilliant, as if a magician had made my mother in-law disappear",

			"soothing, similar to cuddling a pool of puppies",

			"entertaining, more so then the usual donkey shows I'm partial to",

			"enjoyable",

			"lovable",

			"sensual, like getting stroked by a flamingo",

			"wonderful, it was as if I had parachuted into a flower in a field of dreams. "
	};
	
	String [] badQualities = {
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

			"...well,  all I can say is during the screening, several people projectile vomited.",

			"pretty offensive, blasphemous even",

			"bad, there was a moment were I could see my soul leaving my body and flipping me off for putting it through that.",

			"like being be tied to a chair and forced to listen to Barry Manilow tunes while having my tonsils removed with a rusty spoon.",

			"reminded me of one time in korea, I ate a bee's nest.",
			//"Sure its all fun and games until a colony of bees wake up in their hydrochloric crypt.",
			"almost identical to wrestling a turkish truck driver",
			"about as real as cher",
			//"seriously the strangest shit i have ever seen and i once saw a man eat his own face.",

			//"very gay, i saw four dudes going down on each other ass to face in a circle, that was the gayest thing id ever seen until this",
			"like a chinese labor camp",
			"like being violated",
			//"a close second to having a porcupine inserted violently into my rectum...",
			"the kind of thing that makes me re-think censorship",
			"very similar to having my toes chewed off by a bear",

			"unsettling",

			//"like watching Adele and Rosie O'Donnell mud wrestle naked",
			//"worse then drinking a tramps piss",

			"similar to cutting off the wings of a pigeon then feeding them to another pigeon, you're left wondering, 'is this good for any party involved here?'",

			"woeful",

			"abysmal",

			"mediocre at best",

			"like dragging my face over broken glass",

			//"like licking a taxi drivers ass crack",

			"distressing to say the least",

			//"comparable to rats fighting in a ditch over a streak of piss",

			//"comparable to punching a dwarf for stealing your shoes",

			//"comparable to taking every sharp from a needle exchange clinic and sticking them into your skin untill you resemble a hedgehog",

			"comparable to eating my own arm",
			//"comparable to eating an egg and realising that it was actually a lightbulb and now your mouth is full of blood and broken glass.",
			"if vomit could vomit",
			//"like watching my first child being born and not in a nice metaphoric way, i mean that literally. there were complications the misses had a c-section and refused meds she gave birth to several large antelope."
			
	};
	
	String [] vanillaQualities = {
			"complete", "mediocre", "nice", 
			"interesting", "dark", "watchable", 
			"thoughful", "alright", "dramatic"
	};
	
	String [] weirdQualities = {
			"weird", "bizarre", "confusing", "slightly creepy", "silly", 
	};
	
	public String writeReview(MovieInfo info, float rating){
		String actor = info.getCast().getActors().get(0).getName();
		String director = info.getDirector().getName();
		
		List<Sentiment> sentiments = getSentimentsFromRating(rating);
		
		String actorReview = String.format("\"...%s was %s in the lead role, ", actor, getWord(sentiments.get(0)));
		String actorReview2 = String.format(" delivering a %s performance... ", getWord(sentiments.get(1)));
		String plotReview = String.format("The storyline was %s, but definitely became %s towards the end... ", getWord(sentiments.get(2)), getWord(sentiments.get(2)));
		String directorReview = String.format("Director %s has produced a work which can only be described as %s... ", director, getWord(sentiments.get(3)));
		String conclusion = String.format("To sum up, '%s' was %s.\" ", info.getTitle(), getWord(sentiments.get(4)));
		String reviewer = String.format("- %s, The Times", new PersonNameGenerator().getMaleName());
		
		return actorReview + actorReview2 + plotReview + directorReview + conclusion + reviewer;
	}
	
	private static List<Sentiment> getSentimentsFromRating(float rating){
		//String review = "%s was %s in the lead role.";
		//review = String.format(review, actor.getName(), getWord(sentiment));
		//return review;
		List<Sentiment> list = new LinkedList<Sentiment>(); 
		if(rating <= 1.0){
			list.add(Sentiment.BAD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.BAD);
		}
		else if(rating <= 2.0){
			list.add(Sentiment.BAD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.INDIFFERENT);
			list.add(Sentiment.INDIFFERENT);
			list.add(Sentiment.INDIFFERENT);
		}
		else if(rating <= 2.5){ 
			list.add(Sentiment.BAD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.INDIFFERENT);
			list.add(Sentiment.INDIFFERENT);
		}
		else if(rating <= 3.0){
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.BAD);
			list.add(Sentiment.INDIFFERENT);
			list.add(Sentiment.INDIFFERENT);
		}
		else if(rating <= 4.0){
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.INDIFFERENT);
			list.add(Sentiment.INDIFFERENT);
		}
		else if(rating <= 4.5){
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.INDIFFERENT);
		}
		else{
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
			list.add(Sentiment.GOOD);
		}
		Collections.shuffle(list);
		
		return list;
		
	}
	
	private String getWord(Sentiment s) {
		String [] toBeUsed;
		
		switch (s) {
			case GOOD: 			toBeUsed = goodQualities; break;
			case INDIFFERENT:	toBeUsed = randomGenerator.nextBoolean() ? vanillaQualities : weirdQualities; break;
			case BAD:			toBeUsed = badQualities; break;
			default:			toBeUsed = new String[1];
		}
		
		return toBeUsed[randomGenerator.nextInt(toBeUsed.length-1)];
	}

}
