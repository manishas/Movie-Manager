package ie.simo.movies.dao.viewbinder;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public abstract class NamePriceSpinnerViewBinder implements ViewBinder {

	
	private static final int NAME_COLUMN = 1;
	private static final int PRICE_COLUMN = 2;

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

	    TextView textView = (TextView) view;

	    String name = cursor.getString(NAME_COLUMN);
	    String price = "$"+cursor.getInt(PRICE_COLUMN) + "M";
	    
	    textView.setText(name + " - " + price);

	    return true;
	}


}
