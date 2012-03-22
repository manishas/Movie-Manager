package ie.simo.movies.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class Credits {
	
	private Activity activity;

	public Credits(Activity context) {
		activity = context;
	}
	
	public void show() {
    
		String title = activity.getString(ie.simo.movies.R.string.credits);
            
        String message = activity.getString(ie.simo.movies.R.string.creditsText);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
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
