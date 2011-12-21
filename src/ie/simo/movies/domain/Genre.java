package ie.simo.movies.domain;

import java.util.Random;
/**
* Enum to represent Genre and box office appeal
* @author Simon Wielens
*
*/
public enum Genre{
Action (1.0), Horror(0.6), Romance(0.7), Comedy(0.8), Drama(0.8);

private final double boxOffice;

Genre(double boxOffice){
this.boxOffice = boxOffice;
}

public double boxOffice() {return boxOffice;}

public static Genre getRandomGenre(){
Genre[] genres = Genre.values();
Random r = new Random();
return genres[r.nextInt(genres.length)];
}
}