package ie.simo.movies.popup;

import ie.simo.movies.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

/**
 * Popup dialog to get name of new production company from user
 * @author Simon Wielens
 *
 */
public class HowToPlay extends PopupDialog {

	private String title;
	private String message;
	private String companyName;
	private boolean opened = false;
	
	public HowToPlay(Activity context) {
		mActivity = context;
		//this.setTitle(context.getString(R.string.));
		this.setMessage(context.getString(R.string.howtoplaycontent));
	}

	@Override
	public void show() {
		
		setOpened(true);
		
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
	        .setTitle(getTitle())
	        .setMessage(getMessage())
	        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener()
	        {
	        	@Override
	            public void onClick(DialogInterface dialogInterface, int i) {
		        		setOpened(false);
		                dialogInterface.dismiss();
	        	}

	        });
	
        builder.create().show();
	}

	/*
	 * Getters & Setters
	 */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	

}
