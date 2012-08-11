package ie.simo.movies.dao;

import ie.simo.movies.util.DBConsts;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SfxDbAdapter extends DbAdapter{
	
	public SfxDbAdapter(Context context) {
		super(context);
	}
	
	@Override
	public SfxDbAdapter open() {
		
		return (SfxDbAdapter) super.open();
	}

	public Cursor selectAll() {
		String query = "select * from sfx";
		return getDatabase().rawQuery(query, null);
	}

}
