package ie.simo.movies.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "moviemanager";
	private static final int DATABASE_VERSION = 1;
	// Database creation sql statement
	
	public static final String inserts = "insert into movie (movie_name, tagline, genre_id, earnings, cost, producer_id, director_id, distributor_id) values ('Avatar', 'Enter the world', 1, 760, 310, 1, 1, 1);"
			+ "insert into director (director_name, director_hire_cost, director_reputation) values ('James Cameron', 25, 80);"
			+ "insert into producer (producer_name, producer_reputation) values ('James Cameron', 80);"
			+ "insert into distributor (distributor_name, distributor_desc) values ('20th Century Fox', 'Lovely chaps');" 
			+ "insert into genre (genre_name) values ('Science Fiction');";
	
	private static final String DATABASE_CREATE = "create table director " +
			"(director_id integer primary key autoincrement, "
			+ "director_name text not null,"
			+ "director_hire_cost integer,"
			+ "director_reputation integer);"
			
			+ "create table actor " 
			+ "(actor integer primary key autoincrement, "
			+ "actor_name text not null,"
			+ "actor_hire_cost integer,"
			+ "actor_reputation integer);"
			
			+ "create table movie "
			+ "(movie_id integer primary key autoincrement, " 
			+ "movie_name text,"
			+ "tagline text,"
			+ "genre_id number,"
			+ "earnings number,"
			+ "cost number,"
			+ "producer_id number,"
			+ "director_id number,"
			+ "distributor_id number);" 
			
			+ "create table genre "
			+ "(genre_id integer primary key autoincrement," 
			+ "genre_name text);" 
			
			+ "create table producer " 
			+ "(producer_id integer primary key autoincrement," 
			+ "producer_name text," 
			+ "producer_reputation integer);" 
			
			+ "create table cast "
			+ "(movie_id number," 
			+ "actor_id number);"
			
			+ "create table distributor " 
			+ "(distributor_id integer primary key autoincrement," 
			+ "distributor_desc text," 
			+ "distributor_name text);" + inserts;

	
									
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Not implemented
	}

}
