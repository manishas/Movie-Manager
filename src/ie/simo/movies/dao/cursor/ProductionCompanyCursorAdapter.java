package ie.simo.movies.dao.cursor;

import ie.simo.movies.R;
import ie.simo.movies.util.DBConsts.ProductionCompany;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ProductionCompanyCursorAdapter extends SimpleCursorAdapter {
	  
	  static final String [] from = {ProductionCompany.name, ProductionCompany.lastModified};		
	  static final int [] to = {R.id.listText1, R.id.listText2};

	  /** Constructor */
	  public ProductionCompanyCursorAdapter(Context context, Cursor c) {
	    super(context, R.layout.savedgameentry, c, from, to);
	  }

	  /** This is where data is mapped to its view */
	  @Override
	  public void bindView(View row, Context context, Cursor cursor) {
	    super.bindView(row, context, cursor);

	    // Find views by id
	    TextView name = (TextView) row.findViewById(R.id.listText1);
	    TextView date = (TextView) row.findViewById(R.id.listText2);
	    
	    name.setText(cursor.getString(cursor.getColumnIndex(ProductionCompany.name)));
	    date.setText(cursor.getString(cursor.getColumnIndex(ProductionCompany.lastModified)));
	  }
}
