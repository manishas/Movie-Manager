package ie.simo.movies.domain;

import java.io.Serializable;

import ie.simo.movies.generator.FilmNameGenerator;

public class MovieInfo implements Serializable {

	private static final long serialVersionUID = -8148935241980798753L;
	private String title;
	private String tagline;
	private String plot;
	private Genre genre;
	private Director director;
	private Cast cast;
	private RatingDetails ratingDetails;
	private Distributor distributor;
	private String specialEffectsCompany;
	private int sfxCost;
	
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
	/**
	 * Return movie in the form of name (genre)
	 * @return string
	 */
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

	public RatingDetails getRatingDetails() {
		return ratingDetails;
	}

	public void setRatingDetails(RatingDetails ratingDetails) {
		this.ratingDetails = ratingDetails;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getSpecialEffectsCompany() {
		return specialEffectsCompany;
	}

	public void setSpecialEffectsCompany(String specialEffectsCompany) {
		this.specialEffectsCompany = specialEffectsCompany;
	}

	public int getSfxCost() {
		return sfxCost;
	}

	public void setSfxCost(int sfxCost) {
		this.sfxCost = sfxCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MovieInfo)) {
			return false;
		}
		MovieInfo other = (MovieInfo) obj;
		if (genre != other.genre) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
	
}