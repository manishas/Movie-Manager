package ie.simo.movies.dao;

import java.util.ArrayList;
import java.util.List;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.util.DBConsts;
import ie.simo.movies.util.MMLogger;
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
	public static final String DATABASE_TABLE = "movie";
	public static final String TAGLINE = "tagline";
	public static final String COST = "cost";
	public static final String PRODUCER = "producer_id";
	public static final String DIRECTOR = "director_id";
	public static final String DISTRIBUTOR = "distributor_id";
	public static final String PLOT = "description";
	public static final String CAST = "cast";
	public static final String STARS = "star_rating";
	public static final String REVIEW = "review";
	public static final String CENSOR = "censor_rating";
	public static final String COMPOSER = "composer_id";
	public static final String SFX = "sfx_id";
	public static final String SOUND = "sound_id";

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public BoxOfficeDbAdapter(Context context) {
		this.context = context;
	}

	public boolean isOpen() {
		return database.isOpen();
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
		values.put(DIRECTOR, getDirectorIdFromName(movie.getInfo()
				.getDirector().getName()));
		values.put(COST, movie.getInfo().getDirector().getPriceToHire());
		values.put(DISTRIBUTOR, getDistributorIdFromName(movie.getInfo()
				.getDistributor().getName()));
		values.put(PLOT, movie.getInfo().getPlot());
		//stars saved as integer, so double it to keep it a whole number
		values.put(STARS, (int) movie.getMetadata().getStarRating() * 2);
		values.put(REVIEW, movie.getMetadata().getCriticReview());
		long id = database.insert(DATABASE_TABLE, null, values);
		saveCast(id, movie.getInfo().getCast());
		return id;
	}

	public void saveCast(long movieId, Cast cast) {
		for (Actor a : cast.getActors()) {
			ContentValues values = new ContentValues();
			values.put("movie_id", movieId);
			values.put("actor_id", getActorIdFromName(a.getName()));
			database.insert(CAST, null, values);
		}
	}

	public int getDirectorIdFromName(String name) {
		String query = "SELECT d.* from director d where d.director_name = ?";
		MMLogger.v("SQL for Director", query);
		Cursor c = database.rawQuery(query, new String[] { name });
		c.moveToFirst();
		return c.getInt(c.getColumnIndex(DBConsts.Director.id));
	}

	public int getActorIdFromName(String name) {
		String query = "SELECT * from actor where actor_name = ?";
		MMLogger.v("SQL for Actor", query);
		Cursor c = database.rawQuery(query, new String[] { name });
		c.moveToFirst();
		int actorId = c.getInt(c.getColumnIndex(DBConsts.Actor.id));
		return actorId;
	}

	public Director getDirectorFromId(int id) {
		String query = "SELECT * from director where _id=?";
		Director d = new Director();
		Cursor c = database.rawQuery(query, new String[] { id + "" });
		while (c.moveToNext()) {
			d.setName(c.getString(c.getColumnIndex(DBConsts.Director.name)));
			d.setPriceToHire(c.getInt(c
					.getColumnIndex(DBConsts.Director.hire_cost)));
			d.setReputation(c.getInt(c
					.getColumnIndex(DBConsts.Director.reputation)));
		}

		return d;
	}

	public int getDistributorIdFromName(String name) {
		String query = "SELECT * from distributor where distributor_name = ?";
		MMLogger.v("SQL for Director", query);
		Cursor c = database.rawQuery(query, new String[] { name });
		c.moveToFirst();
		return c.getInt(c.getColumnIndex(DBConsts.Distributor.id));
	}

	public Cursor fetchAllMovies() {
		return database.rawQuery(
				"select * from movie order by earnings desc limit 10", null);
	}

	public Cursor fetchUserMovies() {
		return database.rawQuery(
				"select * from movie where _id > 10 order by earnings desc",
				null);
	}

	public Cursor getMovieByName(String name) {
		Cursor c = database
				.rawQuery(
						"select movie_name, director_name, earnings from movie, director where movie_name='"
								+ "' and director._id = movie.director_id",
						null);
		MMLogger.v("DIRECTORQUERY", c.getString(c.getColumnIndex(DIRECTOR)));
		return c;
	}

	public List<Actor> getCast(int movieId) {
		String query = "select a.* from actor a, cast c where a._id=c.actor_id and c.movie_id='"
				+ movieId + "'";
		Cursor c = database.rawQuery(query, null);
		ArrayList<Actor> lads = new ArrayList<Actor>();
		while (c.moveToNext()) {
			Actor a = new Actor();
			a.setName(c.getString(c.getColumnIndex(DBConsts.Actor.name)));
			a.setGender(c.getString(c.getColumnIndex(DBConsts.Actor.gender)));
			a.setPriceToHire(c.getInt(c
					.getColumnIndex(DBConsts.Actor.hire_cost)));
			a.setReputation(c.getInt(c
					.getColumnIndex(DBConsts.Actor.reputation)));
			lads.add(a);
		}

		c.close();
		return lads;
	}

	public String getGenreNameFromId(int scifiId) {
		
		MMLogger.v("Getting Genre name of", scifiId + "");
		String query = "SELECT * from genre where _id=?";
		Cursor c = database.rawQuery(query, new String[] { scifiId + "" });
		String genreName = "";
		while (c.moveToNext()) {
			genreName = c.getString(c.getColumnIndex("genre_name"));
		}
		MMLogger.v("Genre of " + scifiId + " is", genreName);
		return genreName;
	}

}