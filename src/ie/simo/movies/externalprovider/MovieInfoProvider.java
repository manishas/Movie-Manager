package ie.simo.movies.externalprovider;

public interface MovieInfoProvider {
	public String listMoviesForActor(String actorName);
	public String listMoviesForDirector(String directorName);
}