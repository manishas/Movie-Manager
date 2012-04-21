package ie.simo.movies.popup;

import android.app.Activity;

public abstract class PopupDialog {

	protected Activity mActivity;

	public PopupDialog() {
		super();
	}

	public abstract void show();

}