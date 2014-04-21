package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.censor.Censor;
import ie.simo.movies.censor.factory.CensorFactory;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.domain.Rating;
import ie.simo.movies.domain.RatingDetails;
import ie.simo.movies.popup.InfoDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

@EActivity(R.layout.content)
public class SetContent extends ActivityWithMenu {

	@ViewById(R.id.setContentActivityTitle)
	protected TextView title;

	@ViewById
	protected TextView violenceDesc;

	@ViewById
	protected TextView sexDesc;

	@ViewById(R.id.rudeDesc)
	protected TextView languageDesc;

	@ViewById(R.id.violenceBar)
	protected SeekBar violenceLevel;

	@ViewById(R.id.sexBar)
	protected SeekBar sexLevel;

	@ViewById(R.id.rudeBar)
	protected SeekBar languageLevel;

	@ViewById(R.id.ratingImage)
	protected ImageView ratingImg;

	@ViewById(R.id.pitchButton)
	protected Button pitchFilm;

	@ViewById(R.id.contentHelp)
	protected ImageButton help;

	@ViewById(R.id.budgetValue)
	protected TextView budgetView;

	@ViewById(R.id.companyName)
	protected TextView compName;

	private Censor censor;
	private String[] violence;
	private String[] sex;
	private String[] language;

	@AfterViews
	public void onCreate() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// If preference not set, use USA style ratings
		censor = new CensorFactory().getCensor(sharedPrefs.getString(
				Preferences.RATING_STYLE, "usa"));
		getStrings();

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));

		setTitleBar();

		onProgressChanged(violenceLevel, 0, false);
		onProgressChanged(sexLevel, 0, false);
		onProgressChanged(languageLevel, 0, false);

		title.setText(getString(R.string.contentTitle)
				+ getPc().getCurrentProject().toButtonText());
	}

	@Click(R.id.pitchButton)
	public void onPitchClick() {
		Intent i = new Intent();
		i.putExtra(COMPANY, getPc());
		i.setClass(SetContent.this, PitchFilm.class);
		startActivity(i);
	}

	@Click(R.id.contentHelp)
	public void onHelpClick() {
		Activity a = SetContent.this;
		new InfoDialog(a, a.getString(R.string.contentHelp),
				a.getString(R.string.contentHelpMessage)).show();
	}

	private void getStrings() {
		String[] vTemp = { getString(R.string.violence0),
				getString(R.string.violence1), getString(R.string.violence2),
				getString(R.string.violence3) };
		violence = vTemp;

		String[] sTemp = { getString(R.string.sex0), getString(R.string.sex1),
				getString(R.string.sex2), getString(R.string.sex3) };
		sex = sTemp;

		String[] lTemp = { getString(R.string.language0),
				getString(R.string.language1), getString(R.string.language2),
				getString(R.string.language3) };
		language = lTemp;

	}

	@SeekBarProgressChange({R.id.sexBar, R.id.violenceBar, R.id.rudeBar})
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {

		switch (seekBar.getId()) {
		case R.id.violenceBar:
			violenceDesc.setText(violence[progress]);
			break;
		case R.id.sexBar:
			sexDesc.setText(sex[progress]);
			break;
		case R.id.rudeBar:
			languageDesc.setText(language[progress]);
			break;
		}

		setRatingImage();
	}

	private void setRatingImage() {

		RatingDetails thisMovie = new RatingDetails();
		thisMovie.setSex(sexLevel.getProgress());
		thisMovie.setViolence(violenceLevel.getProgress());
		thisMovie.setLanguage(languageLevel.getProgress());

		setRatingImage(thisMovie.getRating());

		getPc().getCurrentProject().setRatingDetails(thisMovie);
	}

	private void setTitleBar() {
		budgetView.setText("$" + getPc().getBudget() + "M");
		compName.setText(getPc().getName());
	}

	private void setRatingImage(Rating myRating) {

		switch (myRating) {
		case GENERAL:
			ratingImg.setImageDrawable(getResources().getDrawable(
					censor.getGId()));
			break;
		case PG:
			ratingImg.setImageDrawable(getResources().getDrawable(
					censor.getPGId()));
			break;
		case TWELVES:
			ratingImg.setImageDrawable(getResources().getDrawable(
					censor.get12Id()));
			break;
		case FIFTEENS:
			ratingImg.setImageDrawable(getResources().getDrawable(
					censor.get15Id()));
			break;
		case EIGHTEENS:
			ratingImg.setImageDrawable(getResources().getDrawable(
					censor.get18Id()));
			break;
		}
	}
}
