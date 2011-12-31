package ie.simo.movies.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BoxOfficeDbAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_GENRE = "genre";
	public static final String KEY_DIRECTOR = "director";
	public static final String KEY_EARNINGS = "earnings";
	private static final String DB_TABLE = "movies";
	private Context context;
	private SQLiteDatabase db;
	private BoxOfficeDaoHelper dbHelper;

	public BoxOfficeDbAdapter(Context context) {
		this.context = context;
	}

	public BoxOfficeDbAdapter open() throws SQLException {
		dbHelper = new BoxOfficeDaoHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new Movie. If the movie is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createMovie(String title, String genre, String director, String earnings) {
		ContentValues values = createContentValues(title, genre,
				director, earnings);

		return db.insert(DB_TABLE, null, values);
	}

	/**
	 * Update the movie
	 */
	public boolean updateMovie(long rowId, String title, String genre,String director,
			String earnings) {
		ContentValues values = createContentValues(title, genre, director,
				earnings);

		return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Deletes movie
	 */
	public boolean deleteMovie(long rowId) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllMovies() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_GENRE, KEY_DIRECTOR, KEY_EARNINGS }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined movie
	 */
	public Cursor fetchMovie(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_GENRE, KEY_DIRECTOR, KEY_EARNINGS }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String title, String genre,
			String director, String earnings) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_GENRE, genre);
		values.put(KEY_DIRECTOR, director);
		values.put(KEY_EARNINGS, earnings);
		return values;
	}

}