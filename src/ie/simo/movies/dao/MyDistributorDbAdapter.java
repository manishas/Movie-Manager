package ie.simo.movies.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class MyDistributorDbAdapter extends DbAdapter{

	public MyDistributorDbAdapter(Context context) {
		super(context);
	}

	@Override
	public MyDistributorDbAdapter open() throws SQLException {
		return (MyDistributorDbAdapter) super.open();
	}
	
	@Override
	public MyDistributorDbAdapter openWritable() throws SQLException {
		return (MyDistributorDbAdapter) super.openWritable();
	}


	public Cursor selectAll() {	
		String query = "select * from distributor";
		return getDatabase().rawQuery(query, null);
	}
	
}


