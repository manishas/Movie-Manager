package ie.simo.movies.dao;

import ie.simo.movies.util.DBConsts;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DistributorDBAdapter {

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public DistributorDBAdapter(Context context) {
		this.context = context;
	}

	public DistributorDBAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public Cursor fetchAllDistributors() {	
		String query = String.format("select * from distributor", 
				DBConsts.Distributor.name, DBConsts.Distributor.desc);
		return database.rawQuery(query, null);
	}
	
}


