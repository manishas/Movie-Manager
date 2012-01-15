package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;

import java.util.Random;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TestDatabaseActivity extends ListActivity {
	private BoxOfficeDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);

		// Define the columns of the table
		// which should be used in the ListView
		String[] from = new String[] { BoxOfficeDbAdapter.MOVIE_NAME, BoxOfficeDbAdapter.EARNINGS };
		// Define the view elements to which the
		// columns will be mapped
		int[] to = new int[] { android.R.id.text1 };
		db = new BoxOfficeDbAdapter(this);
		db.open();

		// Create a new comment every time the activity is started
		//String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
		//int nextInt = new Random().nextInt(3);
		// Save the new comment to the database
		//db.createCommen(comments[nextInt]);

		// Read all comments
		Cursor c = db.fetchAllMovies();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, c, from, to);
		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}

}