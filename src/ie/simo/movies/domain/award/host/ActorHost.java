package ie.simo.movies.domain.award.host;


import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.award.Award;

import java.util.List;

public class ActorHost extends Host
{
	public ActorHost(Award award) {
		super();
		setAward(award);
	}

	@Override
	protected String getNomineeList(List<MovieInfo> nominees, String type) {
		StringBuilder sb = new StringBuilder();
		for (MovieInfo info : nominees) {
			String nominee = info.getCast().getActors().get(0).getName();
		
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
		//choose nominee
		MovieInfo winner = chooseNominee(nominees);
		getAward().setWinner(winner);
		
		return winner.getCast().getActors().get(0).getName() + " for " + winner.getTitle() + "!";
	}
}
