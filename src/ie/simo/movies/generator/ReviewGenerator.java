package ie.simo.movies.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import ie.simo.movies.domain.Actor;
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
			"emotional"
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
			"preposterous"
	};
	
	String [] vanillaQualities = {
			"complete", "mediocre", "nice", "interesting", "dark", "watchable", "thoughful"
	};
	
	String [] weirdQualities = {
			"weird", "bizarre", "confusing", "slightly creepy", "silly", 
	};
	
	public String writeReview(MovieInfo info, float rating){
		String actor = info.getCast().getActors().get(0).getName();
		String director = info.getDirector().getName();
		
		List<Sentiment> sentiments = getSentimentsFromRating(rating);
		
		String actorReview = String.format("%s was %s in the lead role, ", actor, getWord(sentiments.get(0)));
		String actorReview2 = String.format(" delivering a %s performance... ", getWord(sentiments.get(1)));
		String plotReview = String.format("The storyline was %s, but definitely became %s towards the end... ", getWord(sentiments.get(2)), getWord(sentiments.get(2)));
		String directorReview = String.format("Director %s has produced a work which can only be described as %s... ", director, getWord(sentiments.get(3)));
		String conclusion = String.format("To sum up, '%s' was %s. ", info.getTitle(), getWord(sentiments.get(4)));
		String reviewer = "- Boris Wackenheim, The Times";
		
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
