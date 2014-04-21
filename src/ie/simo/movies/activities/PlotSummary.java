package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.generator.PlotGenerator;
import ie.simo.movies.util.MMLogger;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@EActivity(R.layout.plot)
public class PlotSummary extends ActivityWithMenu {

	@ViewById(R.id.goToContentButton)
	protected Button continueButton;

	@ViewById(R.id.edit_plot)
	protected Button editSummaryButton;

	@ViewById(R.id.plotSummaryEdit)
	protected EditText editablePlotSummary;

	@ViewById(R.id.titleOfFilm)
	protected TextView filmTitle;

	@ViewById(R.id.plotSummary)
	protected TextView plotSummary;

	@ViewById(R.id.budgetValue)
	protected TextView budgetView;

	@ViewById(R.id.companyName)
	protected TextView compName;

	private PlotGenerator plotGenerator;

	@AfterViews
	public void onCreate() {
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));

		// get preference for content, default to "PLAIN"
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Boolean contentPref = sharedPrefs.getBoolean(
				Preferences.MENTAL_DESCRIPTIONS, false);
		String contentType = contentPref ? "MENTAL" : "PLAIN";
		plotGenerator = new PlotGenerator(contentType);

		Genre genre = getPc().getCurrentProject().getGenre();
		String title = getPc().getCurrentProject().getTitle();

		editablePlotSummary.setVisibility(View.INVISIBLE);
		filmTitle.setText(title + " (" + genre.toString() + ")");

		generatePlot(genre, title);

		setTitleBar();
	}

	private void generatePlot(Genre g, String title) {
		String plot = plotGenerator.createPlot(g, title);
		plotSummary.setText(plot);
	}

	@Click(R.id.goToContentButton)
	public void onContinueClicked() {
		getPc().getCurrentProject().setPlot(plotSummary.getText().toString());
		MMLogger.d("Plot: ", plotSummary.getText().toString());
		Intent i = new Intent();
		i.putExtra(COMPANY, getPc());
		i.setClass(getApplicationContext(), SetContent.class);
		startActivity(i);
	}

	@Click(R.id.edit_plot)
	public void onEditClicked() {
		plotSummary.setVisibility(View.GONE);
		editablePlotSummary.setVisibility(View.VISIBLE);
		editablePlotSummary.setText(plotSummary.getText());
	}

	@AfterTextChange(R.id.plotSummaryEdit)
	public void afterTextChanged() {
		plotSummary.setText(editablePlotSummary.getText());
	}

	private void setTitleBar() {
		budgetView.setText("$" + getPc().getBudget() + "M");
		compName.setText(getPc().getName());
	}

}
