package ie.simo.movies.activities;

import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.dao.cursor.BoxOfficeCursorAdapter;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;

public class BoxOffice extends ListActivity {
	private BoxOfficeDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);

		db = new BoxOfficeDbAdapter(this);
		db.open();

		// Read all movies
		Cursor c = db.fetchAllMovies();
				
		ListAdapter adapter = new BoxOfficeCursorAdapter(this,c);
        setListAdapter(adapter);
                
        db.close();
	}

	
	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}
	
	@Override
	protected void onDestroy(){
	
		db.close();
		super.onDestroy();
	}
}
