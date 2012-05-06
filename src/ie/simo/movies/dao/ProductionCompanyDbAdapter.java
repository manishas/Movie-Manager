package ie.simo.movies.dao;

import ie.simo.movies.util.DBConsts;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProductionCompanyDbAdapter {
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public ProductionCompanyDbAdapter(Context context) {
		this.context = context;
	}

	public ProductionCompanyDbAdapter openReadable() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
		return this;
	}
	
	public ProductionCompanyDbAdapter openWritable() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/*
	public Cursor fetchAllActors() {	
		String query = String.format("select %s, %s, %s, %s from actor order by %s desc", 
				DBConsts.Actor.id, DBConsts.Actor.name, 
				DBConsts.Actor.hire_cost, DBConsts.Actor.reputation, DBConsts.Actor.hire_cost);
		return database.rawQuery(query, null);
	}
	*/
	
	public void addNewCompany(String name){
		
	}
	
	
}