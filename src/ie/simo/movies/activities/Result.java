package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import static ie.simo.movies.util.Consts.NOMINATIONS;
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
import ie.simo.movies.util.RandomNumberProvider;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.result)
public class Result extends ActivityWithMenu {

	private static final int OLD_NEWSPAPER = 0xCCF8ECC2;

	@ViewById(R.id.ratingBar1)
	protected RatingBar rating;

	@ViewById
	protected Button tryAgain;

	@ViewById(R.id.totalEarnings)
	protected TextView cash;

	@ViewById(R.id.cashmoney)
	protected TextView profitView;

	@ViewById(R.id.budgetValue)
	protected TextView budgetView;

	@ViewById(R.id.companyName)
	protected TextView compName;

	@ViewById
	protected TextView review;

	@ViewById(R.id.moneyexplanation)
	protected TextView explanation;

	private int shareOfEarnings;
	private ReviewGenerator reviewer;

	private EarningsCalculator calculator;
	private RatingCalculator ratingCalc;

	private int money;
	// values used on front end
	private String reviewText;
	private float criticRating;
	private MovieSummary summary;
	private String msg;
	private String profit;

	private int awardsDue;

	private Typeface font;

	@AfterViews
	public void onCreate() {
		init();

		MMLogger.v(getLocalClassName(), "Film Details: "
				+ getPc().getCurrentProject().toString());
		MMLogger.v(getLocalClassName(), "Remaining Budget is: "
				+ getPc().getBudget());

		generateValues();
		updateMovieMetadata();
		displayValues();

		saveToDatabase(summary);
		awardsDue = 5;// calculateAwards(getPc().getCurrentProject());
		if (awardsDue > 0) {
			tryAgain.setText("Go to Awards Ceremony");
		}
		MMLogger.v("AWARDS DUE", "" + awardsDue);

		longToast("Your company earned $" + shareOfEarnings
				+ "M that can be used for your next production");

	}
	
	@Click(R.id.tryAgain)
	public void onTryAgainClick(View v) {
		if (awardsDue == 0) {
			returnToMakeFilmScreen();
		} else {
			goRentYourTux();
		}
	}
	
	private void goRentYourTux() {
		Intent i = prepareToLeaveResultScreen();
		i.setClass(this, OscarsActivity_.class);
		startActivity(i);
	}

	private int calculateAwards(MovieInfo currentProject) {
		// int totalPossibleAwards = AwardCategories.TOTAL_AWARDS;
		int nominations = 0; // guarantee award for testing
		if (criticRating < 3.0) {
			return nominations;
		} else { // has a chance of winning
			int totalConsiderations = (int) ((criticRating - 2.5) / 0.5);
			while (totalConsiderations > 0) {
				if (RandomNumberProvider.getInstance().nextInt(5) == 4) {
					nominations++;
				}
				totalConsiderations--;
			}
		}

		// return nominations;

		return 5; // no awards yet
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
		explanation.setText(String.format(getString(R.string.moneyexplanation),
				shareOfEarnings));

	}

	/**
	 * Update any extra domain object values
	 */
	private void updateMovieMetadata() {
		summary.getMetadata().setStarRating(criticRating);
		summary.getMetadata().setCriticReview(reviewText);
	}

	private void generateValues() {
		criticRating = (float) ratingCalc
				.getRating(getPc().getCurrentProject());
		MMLogger.v(getLocalClassName(), "Critic rating is: " + criticRating);

		money = calculator.calculate(getPc().getCurrentProject(), criticRating);
		MMLogger.v(getLocalClassName(), "Gross Earnings: " + money);

		shareOfEarnings = getShareOfEarnings(money);
		MMLogger.v(getLocalClassName(), "share of earnings: " + shareOfEarnings);

		msg = getString(R.string.totalBoxOffice, "$" + money + "M");
		profit = getString(R.string.totalProfit, "$"
				+ (money - (getPc().getCurrentProject().getTotalCost())) + "M");

		reviewText = getReviewText(criticRating);
		// finally...
		summary = createMovieSummary(getPc().getCurrentProject());
	}

	private String getReviewText(float criticRating) {
		return reviewer.writeReview(getPc().getCurrentProject(), criticRating);
	}

	/**
	 * method to set up instance variables from the onCreate method
	 */
	private void init() {
		font = Typeface.createFromAsset(getAssets(), "OldNewspaperTypes.ttf");

		// get preference for content, default to "PLAIN"
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Boolean reviewType = sharedPrefs.getBoolean(
				Preferences.MENTAL_DESCRIPTIONS, false);
		String value = reviewType ? "MENTAL" : "PLAIN";
		reviewer = new ReviewGenerator(value);
		calculator = new EarningsCalculatorWithActors();
		ratingCalc = new RatingCalculator();

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		setTitleBar();
	}

	private void saveToDatabase(MovieSummary summary) {
		BoxOfficeDbAdapter db = new BoxOfficeDbAdapter(this);
		db.open();
		db.createMovie(summary);
		db.close();

		ProductionCompanyDbAdapter companyDb = new ProductionCompanyDbAdapter(
				this);
		companyDb.openWritable();
		companyDb.updateCompanyDetails(getPc());
		companyDb.close();
	}

	@Override
	public void onBackPressed() {
		this.finish();
		returnToMakeFilmScreen();
	}

	private void returnToMakeFilmScreen() {
		Intent i = prepareToLeaveResultScreen();
		getPc().setCurrentProject(null);
		i.setClass(Result.this, MakeFilmActivity.class);
		startActivity(i);
	}

	private Intent prepareToLeaveResultScreen() {
		setCompanyRep();

		Intent i = new Intent();

		getPc().setBudget(getPc().getBudget() + shareOfEarnings);

		MovieSummary summary = createMovieSummary(getPc().getCurrentProject());
		if (getPc().getBackCatalogue() == null) {
			getPc().setBackCatalogue(new ArrayList<MovieSummary>());
		}
		getPc().getBackCatalogue().add(summary);
		i.putExtra(COMPANY, getPc());
		i.putExtra(NOMINATIONS, awardsDue);
		return i;
	}

	// TODO I have no idea what this means and where these numbers came from
	private void setCompanyRep() {
		int previousLevel = getPc().getReputation() / 15;

		int rep = 1 + (money / 75);
		MMLogger.v("Rep: ", rep + "");
		getPc().setReputation(getPc().getReputation() + rep);

		if (getPc().getReputation() / 15 > previousLevel) {
			// longToast("Your reputation has increased, you will now be able to hire better Actors and Directors!");
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

		int profit = totalEarnings
				- (getPc().getCurrentProject().getTotalCost());
		MMLogger.v(getLocalClassName(), "Profit: " + profit);

		return (profit > 0) ? (profit / 5) : 0;
	}

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	private void setTitleBar() {
		budgetView.setText("$" + getPc().getBudget() + "M");
		compName.setText(getPc().getName());
	}
}