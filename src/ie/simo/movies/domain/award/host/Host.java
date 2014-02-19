package ie.simo.movies.domain.award.host;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.Award;
import ie.simo.movies.domain.award.AwardState;
import ie.simo.movies.util.RandomNumberProvider;

import java.util.List;
import java.util.Random;

public abstract class Host {
	
	private Award award;

	public String getText(Award thisAward, AwardState currentState, List<MovieInfo> nominees, boolean winner) {
	
		if (currentState == AwardState.SHOW_INTRO) {
			return String.format("And the nominations for %s are...",
					thisAward.getAwardTitle());
		} else if (currentState == AwardState.SHOW_NOMINEES) {
			return getNomineeList(nominees, thisAward.getAwardType());
		} else if (currentState == AwardState.AND_THE_WINNER_IS) {
			return "And the winner is...";
		} else if (currentState == AwardState.SHOW_WINNER) {
			return chooseWinner(nominees);
		} else if (currentState == AwardState.USER_COMMENT) {
			return getComment(winner);
		} else {
			return "";
		}
	}

	private String getComment(boolean winner) {
		if (winner) {
			return "Congratulations, you won!!!";
		} else {
			return "You didn't win... Better luck next time!";
		}
	}

	protected abstract String chooseWinner(List<MovieInfo> nominees);

	protected abstract String getNomineeList(List<MovieInfo> nominees, String type);

	protected boolean isLast(List<MovieInfo> nominees, MovieInfo info) {
		return nominees.indexOf(info) == nominees.size() - 1;
	}
	
	protected MovieInfo chooseNominee(List<MovieInfo> nominees)
	{
		Random r = RandomNumberProvider.getInstance();
		return nominees.get(r.nextInt(nominees.size()));
	}

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

}