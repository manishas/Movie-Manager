package ie.simo.movies.dao;


import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.util.DBConsts;
import ie.simo.movies.util.MMLogger;
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
	public static final String PLOT = "description";
	
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
		
		//TODO hardcoded director + distributor
		ContentValues values = new ContentValues();
		values.put(MOVIE_NAME, movie.getInfo().getTitle());
		values.put(EARNINGS, movie.getTotalEarnings());
		values.put(GENRE, movie.getInfo().getGenre().ordinal());
		values.put(TAGLINE, movie.getInfo().getTagline());
		values.put(DIRECTOR, getDirectorIdFromName(movie.getInfo().getDirector().getName()));
		values.put(COST, movie.getInfo().getDirector().getPriceToHire());
		// where the fuck does distributor get saved???
		//values.put(DISTRIBUTOR, getDistributorIdFromName(movie.getInfo().));
		values.put(PLOT, movie.getInfo().getPlot());
		
		return database.insert(DATABASE_TABLE, null, values);
	}
	
	private int getDirectorIdFromName(String name){
		Cursor c = database.rawQuery("SELECT d._id from director d where d.director_name = '"+name+"'", null);
		return c.getInt(c.getColumnIndex(DBConsts.Director.id));
	}
	
	private int getDistributorIdFromName(String name){
		Cursor c = database.rawQuery("SELECT d._id from distributor d where d.distributor_name = '"+name+"'", null);
		return c.getInt(c.getColumnIndex(DBConsts.Distributor.id));
	}

	public Cursor fetchAllMovies() {	//need to order
		return database.rawQuery( "select rowid _id, movie_name, earnings from movie order by earnings desc limit 10", null);
	}
	
	public Cursor fetchUserMovies() {	//need to order
		return database.rawQuery( "select rowid _id, movie_name, earnings from movie where _id > 10 order by earnings desc", null);
	}
	
	public void getMovieByName(String name){
		Cursor c = database.rawQuery( "select movie_name, director_name, earnings from movie, director where movie_name='"+"' and director._id = movie.director_id", null);
		MMLogger.v("DIRECTORQUERY", c.getString(c.getColumnIndex(DIRECTOR)));	
	}
}