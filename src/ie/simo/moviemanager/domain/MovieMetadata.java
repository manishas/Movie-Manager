package ie.simo.moviemanager.domain;

import java.io.Serializable;

import ie.simo.moviemanager.generator.FilmNameGenerator;

public class MovieMetadata implements Serializable {
	
	private static final long serialVersionUID = -8148935241980798753L;
	private String title;
	private String tagline;
	private Genre genre;
	
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
	public MovieMetadata(){}
	
	public MovieMetadata(String title, Genre genre){
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

}
