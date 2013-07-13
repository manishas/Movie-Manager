package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Director;
import ie.simo.movies.util.DBConsts;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public abstract class ClickableHighScoreList extends ListActivity {

	public static final String USER_MOVIES = "user";
	public static final String ALL_MOVIES = "all";
	protected BoxOfficeDbAdapter db;

	public ClickableHighScoreList() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);
		db = new BoxOfficeDbAdapter(this);
		db.open();

	}

	protected abstract void populateList();

	public void onResume() {
		populateList();
		super.onResume();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = (Cursor) getListView().getItemAtPosition(position);
		Dialog dialog = setUpDialog(c);
		dialog.show();
	}

	private Dialog setUpDialog(Cursor c) {
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.movieinfodialog);
		dialog.setCancelable(true);

		int movieId = c.getInt(c.getColumnIndex(DBConsts.Movie.id));
		List<Actor> actors = db.getCast(movieId);

		TextView movieTitle = (TextView) dialog
				.findViewById(R.id.movieinfodialog_movietitle);
		RatingBar reviewedRating = (RatingBar) dialog
				.findViewById(R.id.movieinfodialog_reviewedRating);
		TextView plot = (TextView) dialog
				.findViewById(R.id.movieinfodialog_plot);
		TextView movieDirector = (TextView) dialog
				.findViewById(R.id.movieinfodialog_director);
		TextView movieActors = (TextView) dialog
				.findViewById(R.id.movieinfodialog_actors);
		TextView totalMovieBudget = (TextView) dialog
				.findViewById(R.id.movieinfodialog_budget);
		TextView totalMovieGross = (TextView) dialog
				.findViewById(R.id.movieinfodialog_gross);

		Director director = db.getDirectorFromId(c.getInt(c
				.getColumnIndex(DBConsts.Movie.director)));

		movieTitle.setText(c.getString(c.getColumnIndex(DBConsts.Movie.name)) + " (" + c.getString(c.getColumnIndex(DBConsts.Movie.genre)) + ")");
		plot.setText(c.getString(c.getColumnIndex(DBConsts.Movie.desc)));
		movieDirector.setText(director.getName());
		totalMovieBudget.setText("$"
				+ c.getInt(c.getColumnIndex(DBConsts.Movie.cost)) + ",000,000");
		totalMovieGross.setText("$"
				+ c.getInt(c.getColumnIndex(DBConsts.Movie.earnings))
				+ ",000,000");
		reviewedRating.setRating(getAsFloat(c.getInt(c.getColumnIndex(DBConsts.Movie.stars))));
		c.close();

		movieActors.setText(makeActorList(actors));
		dialog.setCanceledOnTouchOutside(true);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				populateList();
			}
		});
		return dialog;
	}

	public float getAsFloat(int savedValue) {
		float f = (float) savedValue;
		return (f > 0.0f) ? f/2.f : f;
	}

	//changed access modifier for testing
	public String makeActorList(List<Actor> actors) {
		StringBuilder buf = new StringBuilder();
		for (Actor a : actors) {
			buf.append(a.getName() + ", ");
		}
		// get rid of last comma
		if (buf.length() > 0) {
			buf.delete(buf.length() - 2, buf.length());
		}
		return buf.toString();
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}