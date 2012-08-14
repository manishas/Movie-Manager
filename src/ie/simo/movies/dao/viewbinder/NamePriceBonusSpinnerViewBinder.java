package ie.simo.movies.dao.viewbinder;

import android.database.Cursor;
import android.view.View;
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

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

		//http://www.androidadb.com/class/si/SimpleCursorAdapter.html
		TextView textView = (TextView) view;

	    String name = cursor.getString(NAME_COLUMN);
	    String price = "$"+cursor.getInt(PRICE_COLUMN) + "M";
	    
	    String bonuses = getBonuses(cursor);
	    
	    textView.setText(name + " - " + price +'\t'+ bonuses);

	    return true;
	}

	private String getBonuses(Cursor cursor) {
		
		String bonus = (cursor.getString(ACTION_COLUMN) == null) ? "" : "A";
		bonus += (cursor.getString(HORROR_COLUMN) == null) ? "" : "H";
		bonus += (cursor.getString(ROMANCE_COLUMN) == null) ? "" : "R";
		bonus += (cursor.getString(COMEDY_COLUMN) == null) ? "" : "C";
		bonus += (cursor.getString(DRAMA_COLUMN) == null) ? "" : "D";
		bonus += (cursor.getString(SCIFI_COLUMN) == null) ? "" : "S";
		bonus += (cursor.getString(KIDS_COLUMN) == null) ? "" : "K";
		
		return bonus;
	}

}
