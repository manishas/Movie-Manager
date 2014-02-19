package ie.simo.movies.domain.award.host;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.Award;

import java.util.List;

public class SFXHost extends Host {

	public SFXHost(Award award) {
		super();
		setAward(award);
	}

	@Override
	protected String getNomineeList(List<MovieInfo> nominees, String type) {
		StringBuilder sb = new StringBuilder();
		for (MovieInfo info : nominees) {
			String nominee = info.getSpecialEffectsCompany();
			
			String movie = info.getTitle();
	
			if (!isLast(nominees, info)) {
				// if we have a named nominee
				if (nominee.length() > 0) {
					sb.append(nominee);
					sb.append(" for ");
				}
				sb.append(movie);
				sb.append("<br>");
			} else {
				sb.append("<b>");
				if (nominee.length() > 0) {
					sb.append(nominee);
					sb.append(" for ");
				}
				sb.append(movie);
				sb.append("</b>");
			}
		}
	
		return sb.toString();
	}

	@Override
	protected String chooseWinner(List<MovieInfo> nominees) {
		MovieInfo winner = chooseNominee(nominees);
		return winner.getSpecialEffectsCompany() + " for " + winner.getTitle() + "!";
	}
}
