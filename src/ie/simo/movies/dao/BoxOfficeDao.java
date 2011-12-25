package ie.simo.movies.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BoxOfficeDao {
	
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table movies"
			+ "(_id integer primary key autoincrement, "
			+ "title text not null, " + "genre text not null,"
			+ "director text not null," + "earnings text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BoxOfficeDao.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}
}
