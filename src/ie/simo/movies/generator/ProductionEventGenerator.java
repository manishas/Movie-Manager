package ie.simo.movies.generator;

import java.util.Random;

import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.production.AdType;
import ie.simo.movies.production.ProductionEvent;
import ie.simo.movies.util.RandomNumberProvider;

public class ProductionEventGenerator {
	private Random rng = RandomNumberProvider.getInstance();
	
	public ProductionEvent runAd(AdType type){
		return null;
	}
	
	public ProductionEvent communityChest(MovieInfo info){
		return null;
	}
	
	public ProductionEvent nextEvent(){
		//TODO make real random events
		return new ProductionEvent(1, 5, -2, "Your sound engineer has accidentally appeared on 'To catch a predator'. Production has been delayed while you get a replacement");
	}
	
	//5% chance of getting an event
	public boolean isDueEvent(){
		return (rng.nextInt(100) <= 5);
	}

}
