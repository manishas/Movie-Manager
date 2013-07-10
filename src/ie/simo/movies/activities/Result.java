package ie.simo.movies.activities;

import java.util.ArrayList;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.generator.PlotGenerator;
import ie.simo.movies.generator.ReviewGenerator;
import ie.simo.movies.scoring.earnings.EarningsCalculator;
import ie.simo.movies.scoring.earnings.EarningsCalculatorWithActors;
import ie.simo.movies.scoring.rating.RatingCalculator;
import ie.simo.movies.util.MMLogger;

import static ie.simo.movies.util.Consts.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends ActivityWithMenu {

	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	private TextView profitView;
	private TextView budgetView;
	private TextView compName;
	private TextView review;
	private TextView explanation;

	private int shareOfEarnings;
	private ReviewGenerator reviewer;

	private EarningsCalculator calculator;
	private RatingCalculator ratingCalc;
	
	private int money;
	
	private Typeface font;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.result);
		
		init();
		
		MMLogger.v(getLocalClassName(), "Film Details: "
				+ getPc().getCurrentProject().toString());
		MMLogger.v(getLocalClassName(), "Remaining Budget is: " + getPc().getBudget());

		// HOLY SHIT need to refactor the shit out of this whole method
		float criticRating = (float) ratingCalc.getRating(getPc().getCurrentProject());
		
		MMLogger.v(getLocalClassName(), "Critic rating is: " + criticRating);

		rating.setRating(criticRating);

		money = calculator.calculate(getPc().getCurrentProject(), criticRating);
		MMLogger.v(getLocalClassName(), "Gross Earnings: " + money);

		shareOfEarnings = getShareOfEarnings(money);
		MMLogger.v(getLocalClassName(), "share of earnings: " + shareOfEarnings);
		String msg = getString(R.string.totalBoxOffice, "$" + money + "M");
		String profit = getString(R.string.totalProfit,
				"$" + (money - (getPc().getCurrentProject().getTotalCost())) + "M");

		budgetView.setText("$" + (getPc().getBudget() + shareOfEarnings) + "M");

		review.setText(reviewer.writeReview(getPc().getCurrentProject(),
				criticRating));
		review.setTextColor(getResources().getColor(android.R.color.black));
		review.setTypeface(font);
		review.setBackgroundColor(0xCCF8ECC2);

		cash.setText(msg);
		profitView.setText(profit);
		explanation.setText(String.format(getString(R.string.moneyexplanation), shareOfEarnings));

		MovieSummary summary = createMovieSummary(getPc().getCurrentProject());

		saveToDatabase(summary);

		tryAgain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				returnToMakeFilmScreen();
			}
		});

		longToast("Your company earned $" + shareOfEarnings
				+ "M that can be used for your next production");

	}

	/**
	 * method to set up instance variables from the onCreate method
	 */
	private void init() {
		font = Typeface.createFromAsset(getAssets(), "OldNewspaperTypes.ttf");
		
		//get preference for content, default to "PLAIN"
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		Boolean reviewType = sharedPrefs.getBoolean(Preferences.MENTAL_DESCRIPTIONS, false);
		String value = reviewType ? "MENTAL" : "PLAIN";
		reviewer = new ReviewGenerator(value);
		calculator = new EarningsCalculatorWithActors();
		ratingCalc = new RatingCalculator();

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
		
		setTitleBar();
	}

	private void saveToDatabase(MovieSummary summary) {
		BoxOfficeDbAdapter db = new BoxOfficeDbAdapter(this);
		db.open();
		db.createMovie(summary);
		db.close();
		
		ProductionCompanyDbAdapter companyDb = new ProductionCompanyDbAdapter(this);
		companyDb.openWritable();
		companyDb.updateCompanyDetails(getPc());
		companyDb.close();
	}

	private void findAllViewsById() {
		tryAgain = (Button) this.findViewById(R.id.tryAgain);
		rating = (RatingBar) this.findViewById(R.id.ratingBar1);
		cash = (TextView) this.findViewById(R.id.totalEarnings);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
		profitView = (TextView) this.findViewById(R.id.cashmoney);
		review = (TextView) this.findViewById(R.id.review);
		explanation = (TextView) this.findViewById(R.id.moneyexplanation);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		returnToMakeFilmScreen();
	}

	private void returnToMakeFilmScreen() {
		
		setCompanyRep();
		
		Intent i = new Intent();
		i.setClass(Result.this, MakeFilmActivity.class);
		getPc().setBudget(getPc().getBudget() + shareOfEarnings);
		
		MovieSummary summary = createMovieSummary(getPc().getCurrentProject());
		if(getPc().getBackCatalogue() == null){
			getPc().setBackCatalogue(new ArrayList<MovieSummary>());
		}
		getPc().getBackCatalogue().add(summary);
		getPc().setCurrentProject(null);
		i.putExtra(COMPANY, getPc());

		startActivity(i);
	}

	private void setCompanyRep() {
		int previousLevel = getPc().getReputation()/15;
		
		int rep = 1 + (money/75);
		MMLogger.v("Rep: ", rep+"");
		getPc().setReputation(getPc().getReputation() + rep);
		
		if(getPc().getReputation()/15 > previousLevel){
	//		longToast("Your reputation has increased, you will now be able to hire better Actors and Directors!");
		}
	}

	private MovieSummary createMovieSummary(MovieInfo info) {
		MovieSummary summary = new MovieSummary();
		summary.setInfo(info);
		summary.setTotalCost(info.getTotalCost());
		summary.setTotalEarnings(money);
		return summary;
	}

	// TODO will have to change how this works
	private int getShareOfEarnings(int totalEarnings) {

		int profit = totalEarnings - (getPc().getCurrentProject().getTotalCost());
		MMLogger.v(getLocalClassName(), "Profit: " + profit);

		return (profit > 0) ? (profit / 5) : 0;
	}

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}
}