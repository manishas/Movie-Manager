package ie.simo.movies.activities;

import ie.simo.movies.dao.cursor.BoxOfficeCursorAdapter;
import android.database.Cursor;
import android.widget.ListAdapter;

public class PersonalBest extends ClickableHighScoreList {
	
	@Override
	protected void populateList() {
		// Get users movies
		Cursor c = db.fetchUserMovies();
		startManagingCursor(c);

		ListAdapter adapter = new BoxOfficeCursorAdapter(this, c);
		setListAdapter(adapter);
	}
}
