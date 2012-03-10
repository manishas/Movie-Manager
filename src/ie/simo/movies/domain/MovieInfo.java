package ie.simo.movies.domain;

import java.io.Serializable;

import ie.simo.movies.generator.FilmNameGenerator;

public class MovieInfo implements Serializable {

	private static final long serialVersionUID = -8148935241980798753L;
	private String title;
	private String tagline;
	private Genre genre;
	private Director director;
	private Cast cast;
	
	/*
	 * Getters & Setters
	 */
	public String getTitle() {
	return title;
	}
	
	public void setTitle(String title) {
	this.title = title;
	}
	
	public String getTagline() {
	return tagline;
	}
	
	public void setTagline(String tagline) {
	this.tagline = tagline;
	}
	
	public Genre getGenre() {
	return genre;
	}
	
	public void setGenre(Genre genre) {
	this.genre = genre;
	}
	
	/*
	* Constructors
	*/
	public MovieInfo(){
		this.cast = new Cast();
	}
	
	public MovieInfo(String title, Genre genre){
		this.title = title;
		this.genre = genre;
		this.tagline = "Film event of the year!";
	}
	
	/*
	* Instance Methods
	*/
	public void newScript() {
		setTagline("Film event of the year!");
		setTitle(new FilmNameGenerator().newFilmTitle());
		setGenre(Genre.getRandomGenre());
	}
	
	public String toButtonText(){
	return String.format("%s (%s)", getTitle(), getGenre());
	}
	
	public Director getDirector() {
		return director;
	}
	
	public void setDirector(Director director) {
		this.director = director;
	}
	
	public Cast getCast() {
		return cast;
	}
	
	public void setCast(Cast cast) {
		this.cast = cast;
	}
	
	public int getTotalCost() {
		int total = director.getPriceToHire();
		if(cast != null){
			total += cast.getCostOfActors();
		}
		return total;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovieInfo [title=");
		builder.append(title);
		builder.append(", tagline=");
		builder.append(tagline);
		builder.append(", genre=");
		builder.append(genre);
		builder.append(", director=");
		builder.append(director);
		builder.append(", cast=");
		builder.append(cast);
		builder.append("]");
		return builder.toString();
	}

}