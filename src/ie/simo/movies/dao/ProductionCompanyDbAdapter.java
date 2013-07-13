package ie.simo.movies.dao;

import ie.simo.movies.domain.ProductionCompany;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import static ie.simo.movies.util.DBConsts.ProductionCompany.*;

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
	
	public boolean previousGames(){
		Cursor c = database.query(productionCompanyTable, null, null, null, null, null, null, null);
		boolean previous = c.getCount() > 8;
		c.close();
		return previous;

	}

	public void addCompany(ProductionCompany pc) {	
		ContentValues values = createFullValues(pc);
		database.insert(productionCompanyTable, null, values);		
	}
	
	public void updateCompanyDetails(ProductionCompany pc){
		String strFilter = String.format("_id='%s'", pc.getUuid().toString());
		ContentValues values = createBasicValues(pc);
		database.update(productionCompanyTable, values, strFilter, null);	
	}
	
	/**
	 * Extract values from object and return in form of ContentValues
	 * @param company a ProductionCompany instance
	 * @return ContentValues object representing values from the param
	 */
	private ContentValues createFullValues(ProductionCompany company) {
		ContentValues values = createBasicValues(company);
		values.put(id, company.getUuid().toString());
		values.put(name, company.getName());
		return values;
	}

	private ContentValues createBasicValues(ProductionCompany company) {
		ContentValues values = new ContentValues();
		values.put(reputation, company.getReputation());
		values.put(budget, company.getBudget());
		values.put(lastModified, company.getLastAccessedDate());
		return values;
	}

	public Cursor getUserGames() {
		
		String sql = "select * from " + productionCompanyTable + " where length(_id) = 36";
		Cursor c = database.rawQuery(sql, null);
		return c;
	}
}