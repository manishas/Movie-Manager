package ie.simo.movies.dao;

import android.content.Context;
import android.database.Cursor;

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
