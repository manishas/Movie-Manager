package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import static ie.simo.movies.util.Consts.NOMINATIONS;
import ie.simo.movies.R;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.domain.Oscars;
import ie.simo.movies.domain.ProductionCompany;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.oscars)
public class OscarsActivity extends ActivityWithMenu {

	@ViewById(R.id.whole_screen)
	protected RelativeLayout view;

	@ViewById(R.id.comment)
	protected TextView text;

	@ViewById(R.id.makeNewMovie)
	protected Button button;

	private Oscars oscars;

	@AfterViews
	protected void onCreate() {
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		int nominations = i.getIntExtra(NOMINATIONS, 0);

		oscars = new Oscars(this.getApplicationContext(), nominations, getPc()
				.getCurrentProject());
		button.setVisibility(View.INVISIBLE);
		text.setText(Html.fromHtml(oscars.getCurrentRound().getDisplayText()));

	}

	@Click(R.id.whole_screen)
	public void onScreenClick() {
		if (!oscars.step()) {
			text.setText(Html.fromHtml(oscars.getCurrentRound()
					.getDisplayText()));
		} else {
			text.setText("Congratulations on your awards!");
			button.setVisibility(View.VISIBLE);
		}
	}

	@Click(R.id.makeNewMovie)
	public void onButtonClick() {
		Intent i = prepareToLeaveOscarsScreen();
		getPc().setCurrentProject(null);
		i.setClass(this, MakeFilmActivity_.class);
		startActivity(i);
	}

	public Oscars getOscars() {
		return oscars;
	}

	public void setOscars(Oscars oscars) {
		this.oscars = oscars;
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(),
				"You can't go back until after the ceremony!",
				Toast.LENGTH_LONG).show();
	}

	private Intent prepareToLeaveOscarsScreen() {

		Intent i = new Intent();

		getPc().setBudget(getPc().getBudget());

		MovieSummary summary = createMovieSummary(getPc().getCurrentProject());

		if (getPc().getBackCatalogue() == null) {
			getPc().setBackCatalogue(new ArrayList<MovieSummary>());
		}

		getPc().getBackCatalogue().add(summary);
		i.putExtra(COMPANY, getPc());
		return i;
	}

	private MovieSummary createMovieSummary(MovieInfo currentProject) {
		MovieSummary summary = new MovieSummary();
		summary.setInfo(currentProject);
		summary.setTotalCost(currentProject.getTotalCost());
		// summary.setTotalEarnings(money);
		return summary;
	}
}
