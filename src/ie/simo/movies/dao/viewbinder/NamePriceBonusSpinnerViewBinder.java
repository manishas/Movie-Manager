package ie.simo.movies.dao.viewbinder;

import ie.simo.movies.R;
import ie.simo.movies.activities.GetDirector;
import ie.simo.movies.activities.StartActivity;
import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public abstract class NamePriceBonusSpinnerViewBinder implements ViewBinder {
	private static final int NAME_COLUMN = 1;
	private static final int PRICE_COLUMN = 2;
	private static final int REP_COLUMN = 3; // not currently displayed
	private static final int ACTION_COLUMN = 4;
	private static final int HORROR_COLUMN = 5;
	private static final int ROMANCE_COLUMN = 6;
	private static final int COMEDY_COLUMN = 7;
	private static final int DRAMA_COLUMN = 8;
	private static final int SCIFI_COLUMN = 9;
	private static final int KIDS_COLUMN = 10;

	//TODO refactor this
	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

		//http://www.androidadb.com/class/si/SimpleCursorAdapter.html
		
		int viewId = view.getId();
		//if(view.get
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
			boolean hasBonus = null != cursor.getString(ACTION_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
        }
        else if(viewId == R.id.actionbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(ACTION_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        else if(viewId == R.id.horrorbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(HORROR_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
        }
        else if(viewId == R.id.comedybonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(COMEDY_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        else if(viewId == R.id.romancebonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(ROMANCE_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        else if(viewId == R.id.dramabonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(DRAMA_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        else if(viewId == R.id.scifibonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(SCIFI_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        else if(viewId == R.id.kidsbonus){
			ImageView imageView = (ImageView) view;
			boolean hasBonus = null != cursor.getString(KIDS_COLUMN);
			if(!hasBonus){
				removeImage(imageView);
			}
			else showImage(imageView);
		    
        }
        return true;
      
	}

	private void removeImage(ImageView view) {
		view.setVisibility(View.GONE);
	}
	
	private void showImage(ImageView view) {
		view.setVisibility(View.VISIBLE);
	}


}
