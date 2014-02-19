package ie.simo.movies.domain.award.host;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.Award;

import java.util.List;

public class SoundHost extends Host {

	public SoundHost(Award award) {
		super();
		setAward(award);
	}

	@Override
	protected String chooseWinner(List<MovieInfo> nominees) {
		MovieInfo winner = chooseNominee(nominees);
		return winner.getTitle() + "!";
	}

	@Override
	protected String getNomineeList(List<MovieInfo> nominees, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
