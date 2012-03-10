package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.generator.ReviewGenerator;
import ie.simo.movies.scoring.earnings.EarningsCalculator;
import ie.simo.movies.scoring.earnings.EarningsCalculatorFirstImpl;
import ie.simo.movies.scoring.rating.RatingCalculator;

import java.text.NumberFormat;
import java.util.Locale;

import static ie.simo.movies.util.Consts.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Result extends Activity {

	private MovieInfo finishedFilm;
	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	private TextView profitView;
	private TextView budgetView;
	private TextView review;

	private int budget;
	private int shareOfEarnings;
	private ReviewGenerator reviewer;

	private EarningsCalculator calculator;
	private RatingCalculator ratingCalc;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		reviewer = new ReviewGenerator();

		calculator = new EarningsCalculatorFirstImpl();
		ratingCalc = new RatingCalculator();

		findAllViewsById();

		Intent i = getIntent();
		finishedFilm = (MovieInfo) i.getSerializableExtra(CHOSEN);
		Log.v(getLocalClassName(), "Film Details: " + finishedFilm.toString());

		budget = i.getIntExtra(BUDGET, 0);
		Log.v(getLocalClassName(), "Remaining Budget is: " + budget);

		float criticRating = (float) ratingCalc.getRating(finishedFilm
				.getDirector().getReputation(), finishedFilm.getCast());
		Log.v(getLocalClassName(), "Critic rating is: " + criticRating);

		rating.setRating(criticRating);

		int money = calculator.calculate(finishedFilm, criticRating);
		Log.v(getLocalClassName(), "Gross Earnings: " + money);

		shareOfEarnings = getShareOfEarnings(money);
		Log.v(getLocalClassName(), "share of earnings: " + shareOfEarnings);
		String msg = getString(R.string.totalBoxOffice, "$" + money + "M");
		String profit = getString(R.string.totalProfit, "$"
				+ (money - (finishedFilm.getTotalCost())) + "M");

		budgetView.setText(budget + shareOfEarnings + "M");
		
		review.setText(reviewer.writeReview(finishedFilm, criticRating));

		cash.setText(msg);
		profitView.setText(profit);

		MovieSummary summary = new MovieSummary();
		summary.setTotalEarnings(money);
		summary.setInfo(finishedFilm);

		BoxOfficeDbAdapter db = new BoxOfficeDbAdapter(this);
		db.open();
		db.createMovie(summary);
		db.close();

		tryAgain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				returnToMakeFilmScreen();
			}
		});

	}

	private void findAllViewsById() {
		tryAgain = (Button) this.findViewById(R.id.tryAgain);
		rating = (RatingBar) this.findViewById(R.id.ratingBar1);
		cash = (TextView) this.findViewById(R.id.totalEarnings);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		profitView = (TextView) this.findViewById(R.id.cashmoney);
		review = (TextView) this.findViewById(R.id.review);
		
	}

	@Override
	public void onBackPressed() {
		this.finish();
		returnToMakeFilmScreen();
	}

	private void returnToMakeFilmScreen() {

		Intent i = new Intent();
		i.setClass(Result.this, MakeFilmActivity.class);
		i.putExtra(BUDGET, budget + shareOfEarnings);

		startActivity(i);
	}

	// TODO will have to change how this works
	private int getShareOfEarnings(int totalEarnings) {

		int profit = totalEarnings - (finishedFilm.getTotalCost());
		Log.v(getLocalClassName(), "Profit: " + profit);

		return (profit > 0) ? (profit / 5) : 0;
	}

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	/**
	 * 50104 Event Handling for Individual menu item selected Identify single
	 * menu item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Doesn't check to see if different button selected
		Intent intent = new Intent();

		intent.setClass(this, BoxOffice.class);

		startActivity(intent);

		return false;
	}
}
