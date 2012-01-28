package ie.simo.movies.dao;

import ie.simo.movies.domain.Director;
import ie.simo.movies.util.DBConsts;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
		String query = String.format("select %s, %s, %s, %s from director order by %s desc", 
				DBConsts.Director.id, DBConsts.Director.id, DBConsts.Director.name, 
				DBConsts.Director.hire_cost, DBConsts.Director.reputation, DBConsts.Director.hire_cost);
		return database.rawQuery(query, null);
	}
	
}