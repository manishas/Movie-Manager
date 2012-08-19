package ie.simo.movies.dao.viewbinder;

import ie.simo.movies.R;
import ie.simo.movies.activities.GetDirector;
import ie.simo.movies.activities.StartActivity;
import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public abstract class NamePriceBonusSpinnerViewBinder implements ViewBinder {
	private static final int NAME_COLUMN = 1;
	private static final int PRICE_COLUMN = 2;
	private static final int ACTION_COLUMN = 3;
	private static final int HORROR_COLUMN = 4;
	private static final int ROMANCE_COLUMN = 5;
	private static final int COMEDY_COLUMN = 6;
	private static final int DRAMA_COLUMN = 7;
	private static final int SCIFI_COLUMN = 8;
	private static final int KIDS_COLUMN = 9;

	//TODO refactor this
	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

		//http://www.androidadb.com/class/si/SimpleCursorAdapter.html
		
		int viewId = view.getId();
        if(viewId == R.id.starname){
        	TextView textView = (TextView) view;
        	textView.setText(cursor.getString(NAME_COLUMN));
        }
        else if(viewId == R.id.starprice){
			TextView textView = (TextView) view;
		    String price = "$"+cursor.getInt(PRICE_COLUMN) + "M";
		    
		    textView.setText( price);
        }
        else if(viewId == R.id.actionbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(ACTION_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.actionbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(ACTION_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.horrorbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(HORROR_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.comedybonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(COMEDY_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.romancebonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(ROMANCE_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.dramabonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(DRAMA_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.scifibonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(SCIFI_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        else if(viewId == R.id.kidsbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = cursor.getString(KIDS_COLUMN).length() > 0;
			Drawable d = new Activity().getResources().getDrawable(setImage(hasBonus));
			
			imageView.setImageDrawable(d);
		    
        }
        

	    return true;
      
	}

	private int setImage(boolean hasBonus){
		return hasBonus? android.R.drawable.presence_online : android.R.drawable.presence_online;
	}

}
