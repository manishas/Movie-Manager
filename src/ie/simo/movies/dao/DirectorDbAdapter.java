package ie.simo.movies.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DirectorDbAdapter {

	// Database fields
	public static final String DIRECTOR_NAME = "director_name";
	public static final String HIRE_COST = "director_hire_cost";
	public static final String REPUTATION = "director_reputation";
	private static final String DATABASE_TABLE = "director";
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public DirectorDbAdapter(Context context) {
		this.context = context;
	}

	public DirectorDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public Cursor fetchAllDirectors() {
		return database.rawQuery( "select rowid _id," + DIRECTOR_NAME + " ," + 
									HIRE_COST + ", "+ REPUTATION +" from " +
									DATABASE_TABLE, null);
	}
	
}