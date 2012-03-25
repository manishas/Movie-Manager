package ie.simo.movies.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;

public abstract class PopupDialog {

	protected Activity mActivity;

	public PopupDialog() {
		super();
	}

	public abstract void show();

}