package ie.simo.movies.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class DistributorDbAdapter extends DbAdapter{

	public DistributorDbAdapter(Context context) {
		super(context);
	}

	@Override
	public DistributorDbAdapter open() throws SQLException {
		return (DistributorDbAdapter) super.open();
	}
	
	@Override
	public DistributorDbAdapter openWritable() throws SQLException {
		return (DistributorDbAdapter) super.openWritable();
	}


	public Cursor selectAll() {	
		String query = "select * from distributor";
		return getDatabase().rawQuery(query, null);
	}
	
}


