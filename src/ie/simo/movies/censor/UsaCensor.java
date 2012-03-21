package ie.simo.movies.censor;

import ie.simo.movies.R;

public class UsaCensor implements Censor {

	@Override
	public int getGId() {
		return R.drawable.rated_u;
	}

	@Override
	public int getPGId() {
		return R.drawable.rated_pg;
	}

	@Override
	public int get12Id() {
		return R.drawable.rated_pg13;
	}

	@Override
	public int get15Id() {
		return R.drawable.nc17;
	}

	@Override
	public int get18Id() {
		return R.drawable.rated_r;
	}

}
