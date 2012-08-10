package ie.simo.movies.dao;

import ie.simo.movies.util.DBConsts;
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
		String query = String.format("select %s, %s, %s, %s from director order by %s desc", 
				DBConsts.Director.id, DBConsts.Director.name, 
				DBConsts.Director.hire_cost, DBConsts.Director.reputation, DBConsts.Director.hire_cost);
		return database.rawQuery(query, null);
	}
	
	public Cursor getAllDirectorsWithBonuses(){
		String query = "select d.*," +
		"max(case when g._id = 1 then g.genre_name end) as Action," +
        "max(case when g._id = 2 then g.genre_name end) as Horror," +
        "max(case when g._id = 3 then g.genre_name end) as Romance," +
        "max(case when g._id = 4 then g.genre_name end) as Comedy," +
        "max(case when g._id = 5 then g.genre_name end) as Drama," +
        "max(case when g._id = 6 then g.genre_name end) as SciFi," +
        "max(case when g._id = 7 then g.genre_name end) as Kids " +
        "from director d join " +
        "director_bonus db " +
        "on d._id = db.director_id join " +
        "genre g " +
        "on db.genre_id = g._id " 
        + "group by d.director_name "
        + "order by d.hire_cost";
		return database.rawQuery(query, null);
	}
}