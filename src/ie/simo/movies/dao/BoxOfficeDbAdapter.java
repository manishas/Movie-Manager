package ie.simo.movies.dao;

import ie.simo.movies.domain.MovieSummary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BoxOfficeDbAdapter {

	// Database fields
	public static final String MOVIE_NAME = "movie_name";
	public static final String EARNINGS = "earnings";
	public static final String GENRE = "genre_id";
	private static final String DATABASE_TABLE = "movie";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public BoxOfficeDbAdapter(Context context) {
		this.context = context;
	}

	public BoxOfficeDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createMovie(MovieSummary movie) {
		ContentValues values = new ContentValues();
		values.put(MOVIE_NAME, movie.getInfo().getTitle());
		values.put(EARNINGS, movie.getTotalEarnings());
		values.put(GENRE, movie.getInfo().getGenre().ordinal());
		return database.insert(DATABASE_TABLE, null, values);
	}

	public Cursor fetchAllMovies() {
		return database.rawQuery( "select rowid _id,movie_name, earnings from movie order by earnings", null);
	}
	
}