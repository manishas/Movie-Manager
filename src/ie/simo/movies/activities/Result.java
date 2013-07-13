package ie.simo.movies.activities;

import java.util.ArrayList;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.domain.ProductionCompany;
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
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends ActivityWithMenu {

	private static final int OLD_NEWSPAPER = 0xCCF8ECC2;
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
	//values used on front end
	private String reviewText;
	private float criticRating;
	private MovieSummary summary;
	private String msg;
	private String profit;
	
	private Typeface font;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.result);
		
		init();
		
		MMLogger.v(getLocalClassName(), "Film Details: " + getPc().getCurrentProject().toString());
		MMLogger.v(getLocalClassName(), "Remaining Budget is: " + getPc().getBudget());
			
		generateValues();
		updateMovieMetadata();
		displayValues();

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
	 * set the values of UI elements
	 */
	private void displayValues() {
		rating.setRating(criticRating);
		budgetView.setText("$" + (getPc().getBudget() + shareOfEarnings) + "M");
		
		review.setText(reviewText);
		review.setTextColor(getResources().getColor(android.R.color.black));
		review.setTypeface(font);
		review.setBackgroundColor(OLD_NEWSPAPER);

		cash.setText(msg);
		profitView.setText(profit);
		explanation.setText(String.format(getString(R.string.moneyexplanation), shareOfEarnings));
		
	}
	
	/**
	 * Update any extra domain object values
	 */
	private void updateMovieMetadata() {
		summary.getMetadata().setStarRating(criticRating);
		summary.getMetadata().setCriticReview(reviewText);
	}

	private void generateValues() {
		criticRating = (float) ratingCalc.getRating(getPc().getCurrentProject());
		MMLogger.v(getLocalClassName(), "Critic rating is: " + criticRating);
		
		money = calculator.calculate(getPc().getCurrentProject(), criticRating);
		MMLogger.v(getLocalClassName(), "Gross Earnings: " + money);
		
		shareOfEarnings = getShareOfEarnings(money);
		MMLogger.v(getLocalClassName(), "share of earnings: " + shareOfEarnings);
		
		msg = getString(R.string.totalBoxOffice, "$" + money + "M");
		profit = getString(R.string.totalProfit, "$" + (money - (getPc().getCurrentProject().getTotalCost())) + "M");
		
		reviewText = getReviewText(criticRating);
		//finally...
		summary = createMovieSummary(getPc().getCurrentProject());
	}

	private String getReviewText(float criticRating) {
		return reviewer.writeReview(getPc().getCurrentProject(),
				criticRating);
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