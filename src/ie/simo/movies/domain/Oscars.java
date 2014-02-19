package ie.simo.movies.domain;

import ie.simo.movies.domain.award.Award;
import ie.simo.movies.domain.award.AwardCategories;
import ie.simo.movies.domain.award.AwardRound;
import ie.simo.movies.domain.award.AwardState;
import ie.simo.movies.generator.MovieInfoGenerator;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class Oscars {

	private int INDEX;
	private List<AwardRound> rounds = new ArrayList<AwardRound>();
	private AwardRound currentRound;
	private MovieInfoGenerator movieInfoGenerator;

	public Oscars(Context context, int nominations, MovieInfo myMovie) {
		INDEX = 0;
		List<Award> myAwards;

		if (myMovie.getSpecialEffectsCompany() == null) {
			myAwards = AwardCategories.getMyAwardsNoSFX(nominations);
		} else {
			myAwards = AwardCategories.getMyAwards(nominations);
		}

		movieInfoGenerator = new MovieInfoGenerator(context);

		for (Award award : myAwards) {
			AwardRound round = new AwardRound(award);
			List<MovieInfo> contenders = generateNominees();
			contenders.add(myMovie);
			round.setNominees(contenders);
			rounds.add(round);
		}

		setCurrentRound(rounds.get(INDEX));
	}

	public Oscars(List<AwardRound> rounds) {
		this.rounds = rounds;
	}

	private List<MovieInfo> generateNominees() {
		List<MovieInfo> nominees = new ArrayList<MovieInfo>();
		for (int i = 0; i < 5; i++) {
			nominees.add(movieInfoGenerator.createNewMovie());
		}

		return nominees;
	}

	public boolean step() {
		currentRound.setState(AwardState.getNextState(currentRound.getState()));
		if (currentRound.getState() == AwardState.END) {
			INDEX += 1;
			Log.d("INDEX: ", INDEX + "");
			if (rounds.size() > INDEX) {
				currentRound = rounds.get(INDEX);
			} else {
				return true;
			}
		}

		return false;
	}

	public AwardRound getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(AwardRound currentRound) {
		this.currentRound = currentRound;
	}

}
