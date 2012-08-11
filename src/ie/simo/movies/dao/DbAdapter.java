package ie.simo.movies.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Abstract class for all DB Adapters
 * @author Simon Wielens
 *
 */
public abstract class DbAdapter {
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public DbAdapter(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * @return a read-only DB adapter
	 * @throws SQLException
	 */
	public DbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
		return this;
	}
	/**
	 * 
	 * @return a read/write DB adapter
	 * @throws SQLException
	 */
	public DbAdapter openWritable() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the database
	 */
	public SQLiteDatabase getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	
	public abstract Cursor selectAll();

	/**
	 * @return the dbHelper
	 */
	public DatabaseHelper getDbHelper() {
		return dbHelper;
	}

	/**
	 * @param dbHelper the dbHelper to set
	 */
	public void setDbHelper(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
}
