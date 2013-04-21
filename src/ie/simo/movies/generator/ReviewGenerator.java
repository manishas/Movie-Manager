package ie.simo.movies.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.generator.util.KindOfExplicitPhraseProvider;
import ie.simo.movies.generator.util.PhraseProvider;
import ie.simo.movies.util.RandomNumberProvider;

/**
 * Class to generate newspaper reviews
 * 
 * @author Simon
 *
 */
public class ReviewGenerator {
	
	public ReviewGenerator(String contentType){
		if(contentType != "PLAIN"){
			pp = new KindOfExplicitPhraseProvider();
		}
		else {
			pp = new PhraseProvider();
		}
	}
	
	enum Sentiment {
		GOOD, BAD, INDIFFERENT
	}
	
	Random randomGenerator = RandomNumberProvider.getInstance();
	PhraseProvider pp;
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
		switch (s) {
			case GOOD: 			return pp.getGoodFilmReview();
			case INDIFFERENT:	return (randomGenerator.nextBoolean() ? pp.getVanillaFilmReview() : pp.getWeirdFilmReview());
			case BAD:			return pp.getBadFilmReview();
			default:			return pp.getGoodFilmReview();
		}
	}

}
