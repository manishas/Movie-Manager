package ie.simo.movies.censor;

import ie.simo.movies.R;

public class UkCensor implements Censor {

	@Override
	public int getGId() {
		return R.drawable.rated_g;
	}

	@Override
	public int getPGId() {
		return R.drawable.uk_pg;
	}

	@Override
	public int get12Id() {
		return R.drawable.uk_12;
	}

	@Override
	public int get15Id() {
		return R.drawable.uk_15;
	}

	@Override
	public int get18Id() {
		return R.drawable.uk_18;
	}

}
