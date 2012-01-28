package ie.simo.movies.dao;


import ie.simo.movies.domain.MovieSummary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BoxOfficeDbAdapter {

	// Database fields
	public static final String MOVIE_NAME = "movie_name";
	public static final String EARNINGS = "earnings";
	public static final String GENRE = "genre_id";
	public static final String DATABASE_TABLE = "movie";
	public static final String TAGLINE = "tagline";
	public static final String COST = "cost";
	public static final String PRODUCER = "producer_id";
	public static final String DIRECTOR = "director_id";
	public static final String DISTRIBUTOR ="distributor_id";
	
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
		values.put(TAGLINE, movie.getInfo().getTagline());
		values.put(DIRECTOR,1);
		values.put(COST, movie.getInfo().getDirector().getPriceToHire());
		values.put(DISTRIBUTOR, 1);
		
		return database.insert(DATABASE_TABLE, null, values);
	}

	public Cursor fetchAllMovies() {	//need to order
		return database.rawQuery( "select rowid _id, movie_name, earnings from movie order by earnings desc limit 10", null);
	}
	
	public void getMovieByName(String name){
		Cursor c = database.rawQuery( "select movie_name, director_name, earnings from movie, director where movie_name='"+"' and director._id = movie.director_id", null);
		Log.v("DIRECTORQUERY", c.getString(c.getColumnIndex(DIRECTOR)));	
	}
	
}