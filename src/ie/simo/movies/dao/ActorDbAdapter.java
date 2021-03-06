package ie.simo.movies.dao;

import ie.simo.movies.domain.Actor;
import ie.simo.movies.util.DBConsts;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ActorDbAdapter {
	
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public ActorDbAdapter(Context context) {
		this.context = context;
	}

	public ActorDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public Cursor fetchAllActors() {	
		String query = String.format("select %s, %s, %s, %s from actor order by %s desc", 
				DBConsts.Actor.id, DBConsts.Actor.name, 
				DBConsts.Actor.hire_cost, DBConsts.Actor.reputation, DBConsts.Actor.hire_cost);
		return database.rawQuery(query, null);
	}
	
	
	public Cursor getAllActorsWithBonuses(){
		String query = "select a._id, a.actor_name, a.actor_hire_cost, a.actor_reputation," +
        "max(case when g._id = 1 then g.genre_name end) as Action, " +
        "max(case when g._id = 2 then g.genre_name end) as Horror, " +
        "max(case when g._id = 3 then g.genre_name end) as Romance, " +
        "max(case when g._id = 4 then g.genre_name end) as Comedy, " +
        "max(case when g._id = 5 then g.genre_name end) as Drama, " +
        "max(case when g._id = 6 then g.genre_name end) as ScienceFiction, " +
        "max(case when g._id = 7 then g.genre_name end) as Kids, " +
        "a.gender " +
        "from actor a left outer join " +
        "actor_bonus ab " +
        "on a._id = ab.actor_id left outer join " +
        "genre g " +
        "on ab.genre_id = g._id " +
        "group by a.actor_name " +
        "order by a.actor_reputation desc";
		
		return database.rawQuery(query, null);
	}
	
	public Cursor getAllActorsFilteredByCost(int remainingBudget){
		String query = "select a._id, a.actor_name, a.actor_hire_cost, a.actor_reputation," +
        "max(case when g._id = 1 then g.genre_name end) as Action, " +
        "max(case when g._id = 2 then g.genre_name end) as Horror, " +
        "max(case when g._id = 3 then g.genre_name end) as Romance, " +
        "max(case when g._id = 4 then g.genre_name end) as Comedy, " +
        "max(case when g._id = 5 then g.genre_name end) as Drama, " +
        "max(case when g._id = 6 then g.genre_name end) as ScienceFiction, " +
        "max(case when g._id = 7 then g.genre_name end) as Kids, " +
        "a.gender " +
        "from actor a left outer join " +
        "actor_bonus ab " +
        "on a._id = ab.actor_id left outer join " +
        "genre g " +
        "on ab.genre_id = g._id " +
        "where a.actor_hire_cost <= " + remainingBudget +
        " group by a.actor_name " +
        "order by a.actor_reputation desc";
		
		return database.rawQuery(query, null);
	}

	public Actor getRandomActor() {
		Cursor c = database.rawQuery("SELECT * from Actor ORDER BY Random() limit 1", null);
		c.moveToFirst();
		
		Actor actor = createActorFromCursor(c);
		
		return actor;
	}

	private Actor createActorFromCursor(Cursor c) {
		Actor actor = new Actor();
		actor.setName(c.getString(c.getColumnIndex(DBConsts.Actor.name)));
		actor.setGender(c.getString(c.getColumnIndex(DBConsts.Actor.gender)));
		return actor;
	}
}