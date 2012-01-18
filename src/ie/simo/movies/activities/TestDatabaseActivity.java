package ie.simo.movies.activities;

import ie.simo.movies.dao.BoxOfficeDbAdapter;

import java.util.Random;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class TestDatabaseActivity extends ListActivity {
	private BoxOfficeDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);

		// Define the columns of the table
		// which should be used in the ListView
		String[] from = new String[] { "_id", BoxOfficeDbAdapter.MOVIE_NAME, BoxOfficeDbAdapter.EARNINGS };
		// Define the view elements to which the
		// columns will be mapped
		int[] to = new int[] {R.id.rank, R.id.boxofficename, R.id.boxofficeearnings };
		db = new BoxOfficeDbAdapter(this);
		db.open();

		// Read all movies
		Cursor c = db.fetchAllMovies();
		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		
		ListAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.boxofficeentry, c, from, to);
		
        setListAdapter(adapter);

		
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