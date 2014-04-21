package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.ProductionCompany;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.main)
public class MakeFilmActivity extends ActivityWithMenu implements OnClickListener {
	/** Called when the activity is first created. */
	
	@ViewById
	protected Button script1;
	
	@ViewById
	protected Button script2;
	
	@ViewById
	protected Button script3;
	
	@ViewById(R.id.makeOwn)
	protected Button getMore;
	
	@ViewById(R.id.makeownfilm)
	protected Button makeOwn;
	
	@ViewById(R.id.budgetValue)
	protected TextView budgetView;
	
	@ViewById(R.id.companyName)
	protected TextView compName;
	
	protected MovieInfo meta1;
	protected MovieInfo meta2;
	protected MovieInfo meta3;
	protected Intent intent;

	@AfterViews
	public void onCreate() {
		
		Intent i = getIntent();
		
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		setTitleBar();
		generateScripts();	
	}
	
	@Click(R.id.makeOwn)
	protected void getMoreClicked() {
		MakeFilmActivity.this.generateScripts();
	}
	
	@Click(R.id.makeownfilm)
	protected void makeOwnClicked() {
		intent = new Intent(getApplicationContext(), OwnFilmActivity_.class);
		intent.putExtra(COMPANY, getPc());
		startActivity(intent);
	}

	@Click({R.id.script1, R.id.script2, R.id.script3})
	public void onClick(View target) {

		if (target == script1)
			getPc().setCurrentProject(meta1);
		else if (target == script2)
			getPc().setCurrentProject(meta2);
		else if (target == script3)
			getPc().setCurrentProject(meta3);
		
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
		Boolean contentPref = sharedPrefs.getBoolean(Preferences.SHOW_PLOT, false);
		if(contentPref)
		{
			intent = new Intent(getApplicationContext(), PlotSummary_.class);
		}
		else
		{
			intent = new Intent(getApplicationContext(), SetContent_.class);
			getPc().getCurrentProject().setPlot("");
		}
		
		intent.putExtra(COMPANY, getPc());
		startActivity(intent);

	}

	private void generateScripts() {
		meta1 = new MovieInfo();
		meta2 = new MovieInfo();
		meta3 = new MovieInfo();
		changeButtonText(script1, meta1);
		changeButtonText(script2, meta2);
		changeButtonText(script3, meta3);
	}

	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	private void changeButtonText(Button myButton, MovieInfo meta) {
		meta.newScript();
		myButton.setText(meta.toButtonText());
	}
}