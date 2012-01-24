package ie.simo.movies.dao.cursor;

import ie.simo.movies.activities.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BoxOfficeCursorAdapter extends SimpleCursorAdapter {
	  
	  static final String [] from = {"_id", "movie_name", "earnings"};		
	  static final int [] to = {R.id.rank, R.id.boxofficename, R.id.boxofficeearnings};

	  /** Constructor */
	  public BoxOfficeCursorAdapter(Context context, Cursor c) {
	    super(context, R.layout.boxofficeentry, c, from, to);
	  }

	  /** This is where data is mapped to its view */
	  @Override
	  public void bindView(View row, Context context, Cursor cursor) {
	    super.bindView(row, context, cursor);

	    // Find views by id
	    TextView rank = (TextView) row.findViewById(R.id.rank);
	    TextView name = (TextView) row.findViewById(R.id.boxofficename);
	    TextView earnings = (TextView) row.findViewById(R.id.boxofficeearnings);
	    
	    rank.setText((cursor.getPosition() + 1) + "");
	    
	    name.setText(cursor.getString(cursor.getColumnIndex("movie_name")));
	    earnings.setText(cursor.getString(cursor.getColumnIndex("earnings")));
	  }
}
