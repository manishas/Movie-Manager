package ie.simo.movies.censor;

import ie.simo.movies.R;

public class IrishCensor implements Censor {

	@Override
	public int getGId() {
		return R.drawable.irish_g;
	}

	@Override
	public int getPGId() {
		return R.drawable.irish_pg;
	}

	@Override
	public int get12Id() {
		return R.drawable.irish_g;
	}

	@Override
	public int get15Id() {
		return R.drawable.irish_15a;
	}

	@Override
	public int get18Id() {
		return R.drawable.irish_18;
	}


}
