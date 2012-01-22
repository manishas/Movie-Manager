package ie.simo.movies.dao;

import ie.simo.movies.domain.MovieSummary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DirectorDbAdapter {
	
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
		return database.rawQuery( "select _id, director_name, director_hire_cost, director_reputation from director order by director_hire_cost desc", null);
	}
	
}