package ie.simo.movies.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class InfoDialog extends PopupDialog {
	
	private String title;
	private String message;
	
	public InfoDialog(Activity context, String title, String message) {
		mActivity = context;
		this.title = title;
		this.message = message;
	}

	@Override
	public void show() {
		
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
	        .setTitle(title)
	        .setMessage(message)
	        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener()
	        {
	        	@Override
	            public void onClick(DialogInterface dialogInterface, int i) {
	                dialogInterface.dismiss();
	            }
	        });
	
        builder.create().show();
	}
	
}
