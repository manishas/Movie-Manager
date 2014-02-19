package ie.simo.movies.domain.award;

import ie.simo.movies.domain.MovieInfo;


public class Award {
	
	public final static String DIRECTOR = "DIRECTOR";
	public final static String ACTOR = "ACTOR";
	public final static String SCRIPT = "SCRIPT";
	public final static String SFX = "SFX";
	public static final String OTHER = "OTHER";
	public static final String SOUND = "SOUND";
	
	private String awardType;
	private String awardTitle;
	private MovieInfo winner;
	
	
	
	public Award(String awardTitle, String awardType){
		this.awardTitle = awardTitle;
		this.awardType = awardType;
	}
	
	public String getAwardTitle() {
		return awardTitle;
	}
	public void setAwardTitle(String awardTitle) {
		this.awardTitle = awardTitle;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public MovieInfo getWinner() {
		return winner;
	}

	public void setWinner(MovieInfo winner) {
		this.winner = winner;
	}
}
