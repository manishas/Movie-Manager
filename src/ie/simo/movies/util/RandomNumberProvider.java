package ie.simo.movies.util;

import java.util.Random;

public class RandomNumberProvider {

	private static Random r;
	
	public static Random getInstance(){
		if(r != null){
			return r;
		}
		else{
			return new Random();
		}
	}
}
