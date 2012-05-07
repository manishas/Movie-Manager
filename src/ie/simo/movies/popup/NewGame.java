package ie.simo.movies.popup;

import ie.simo.movies.R;
import ie.simo.movies.activities.StartActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Popup dialog to get name of new production company from user
 * @author Simon Wielens
 *
 */
public class NewGame extends PopupDialog {

	private String title;
	private String message;
	private EditText input;
	private String companyName;
	private boolean opened = false;
	
	public NewGame(Activity context) {
		mActivity = context;
		this.setTitle(context.getString(R.string.newgamedialogtitle));
		this.setMessage(context.getString(R.string.choosecompanyname));
		input = new EditText(context);
		
		InputFilter filter = new InputFilter() { 
		    
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) { 
				    if (!(Character.isLetterOrDigit(source.charAt(i)) || Character.isSpace(source.charAt(i)))) { 
			            return ""; 
			        } 
				}
					
			return null; 
			}
			
		}; 

		input.setFilters(new InputFilter[]{filter}); 
		
	}

	@Override
	public void show() {
		
		setOpened(true);
		
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
	        .setTitle(getTitle())
	        .setMessage(getMessage())
	        .setView(input)
	        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener()
	        {
	        	@Override
	            public void onClick(DialogInterface dialogInterface, int i) {
	        		
	        		if(null != input.getEditableText().toString() && isValid(input.getEditableText().toString())){
		        		
	        			setCompanyName(input.getEditableText().toString());
		        		Log.v("NEW COMPANY", getCompanyName());
		        		setOpened(false);
		                dialogInterface.dismiss();
		                
		                StartActivity parent = (StartActivity) mActivity;
		                parent.createNewGame();
	        		}
	        		else{
	        			Toast.makeText(mActivity, "Invalid company name", Toast.LENGTH_LONG).show();
	        		}
	        	}

				private boolean isValid(String input) {
					
					return !input.equals("");
				}
	        });
	
        builder.create().show();
	}

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
