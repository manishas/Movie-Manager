package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.generator.ReviewGenerator;
import ie.simo.movies.scoring.earnings.EarningsCalculator;
import ie.simo.movies.scoring.earnings.EarningsCalculatorFirstImpl;
import ie.simo.movies.scoring.rating.RatingCalculator;

import static ie.simo.movies.util.Consts.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends Activity {

	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	private TextView profitView;
	private TextView budgetView;
	private TextView review;

	private ProductionCompany pc;
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
		Log.v(getLocalClassName(), "Film Details: " + pc.getCurrentProject().toString());

		pc = (ProductionCompany) i.getSerializableExtra(COMPANY);
		Log.v(getLocalClassName(), "Remaining Budget is: " + pc.getBudget());

		//HOLY SHIT need to refactor the shit out of this method
		float criticRating = (float) ratingCalc.getRating(pc.getCurrentProject()
				.getDirector().getReputation(), pc.getCurrentProject().getCast());
		Log.v(getLocalClassName(), "Critic rating is: " + criticRating);

		rating.setRating(criticRating);

		int money = calculator.calculate(pc.getCurrentProject(), criticRating);
		Log.v(getLocalClassName(), "Gross Earnings: " + money);

		shareOfEarnings = getShareOfEarnings(money);
		Log.v(getLocalClassName(), "share of earnings: " + shareOfEarnings);
		String msg = getString(R.string.totalBoxOffice, "$" + money + "M");
		String profit = getString(R.string.totalProfit, "$"
				+ (money - (pc.getCurrentProject().getTotalCost())) + "M");

		budgetView.setText("$" + (pc.getBudget() + shareOfEarnings) + "M");
		
		Typeface font = Typeface.createFromAsset(getAssets(), "OldNewspaperTypes.ttf"); 
		
		review.setText(reviewer.writeReview(pc.getCurrentProject(), criticRating));
		review.setTypeface(font);

		cash.setText(msg);
		profitView.setText(profit);

		MovieSummary summary = new MovieSummary();
		summary.setTotalEarnings(money);
		summary.setInfo(pc.getCurrentProject());

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
		
		
		longToast("Your company earned $" + shareOfEarnings + "M that can be used for your next production");

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
		pc.setBudget(pc.getBudget() + shareOfEarnings);
		pc.getBackCatalogue().add(pc.getCurrentProject());
		pc.setCurrentProject(null);
		i.putExtra(COMPANY, pc);

		startActivity(i);
	}

	// TODO will have to change how this works
	private int getShareOfEarnings(int totalEarnings) {

		int profit = totalEarnings - (pc.getCurrentProject().getTotalCost());
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
	
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
