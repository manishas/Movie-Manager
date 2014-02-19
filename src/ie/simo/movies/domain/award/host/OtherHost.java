package ie.simo.movies.domain.award.host;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.Award;

import java.util.List;

public class OtherHost extends Host {

	public OtherHost(Award award) {
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
		StringBuilder sb = new StringBuilder();
		for (MovieInfo info : nominees) {
			
			String movie = info.getTitle();
	
			if (!isLast(nominees, info)) {
				sb.append(movie);
				sb.append("<br>");
			} else {
				sb.append("<b>");
				sb.append(movie);
				sb.append("</b>");
			}
		}
	
		return sb.toString();
	}

}
