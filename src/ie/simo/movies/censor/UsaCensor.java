package ie.simo.movies.censor;

import ie.simo.movies.R;

public class UsaCensor implements Censor {

	@Override
	public int getGId() {
		return R.drawable.g_rating;
	}

	@Override
	public int getPGId() {
		return R.drawable.pg_rating;
	}

	@Override
	public int get12Id() {
		return R.drawable.pg13_rating;
	}

	@Override
	public int get15Id() {
		return R.drawable.nc17_rating;
	}

	@Override
	public int get18Id() {
		return R.drawable.r_rating;
	}

}
