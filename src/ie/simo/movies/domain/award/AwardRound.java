package ie.simo.movies.domain.award;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.host.Host;
import ie.simo.movies.domain.award.host.HostFactory;
import ie.simo.movies.util.MMLogger;

import java.util.List;


public class AwardRound {
	private Award award;
	private List<MovieInfo> nominees;
	private AwardState currentState;
	private boolean userIsWinner;
	private Host billyCrystal;
	
	public AwardRound(Award award){
		try {
			this.award = award;
			billyCrystal = HostFactory.getInstance(award);
			setState(AwardState.SHOW_INTRO);
		}
		catch(AwardTypeNotFoundException e){
			MMLogger.e("Award not found", award.getAwardTitle());
		}
	}

	public void setState(AwardState state) {
		this.currentState = state;
	}

	public List<MovieInfo> getNominees() {
		return nominees;
	}

	public void setNominees(List<MovieInfo> nominees) {
		this.nominees = nominees;
	}

	public AwardState getState() {
		return currentState;
	}

	public String getDisplayText() {
		String comment = billyCrystal.getText(award, currentState, nominees, isUserAWinner());
		return "<b>" + award.getAwardTitle() + "</b> <br> " + comment;
	}

	private boolean isUserAWinner() {
		if(award.getWinner() == null || !award.getWinner().equals(nominees.get(nominees.size() -1)))
		{
			return false;
		}
		else{
			return true;
		}
	}

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}
}
