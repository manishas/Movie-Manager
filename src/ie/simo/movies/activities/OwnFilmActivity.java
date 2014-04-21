package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.MMLogger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.makeownfilm)
public class OwnFilmActivity extends ActivityWithMenu {
	
	private static final String CONTINUE = "continue";
	
	@ViewById(R.id.nametextfield)
	protected EditText text;
	
	@ViewById(R.id.genrespinner)
	protected Spinner spinner;
	
	@ViewById(R.id.getdirectorbutton)
	protected Button continueButton;
	
	@ViewById(R.id.budgetValue)
	protected TextView budgetView;
	
	@ViewById(R.id.companyName)
	protected TextView compName;
	
	private MovieInfo info;

	@AfterViews
	public void onCreate() {
		
		Intent i = getIntent();
		
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		setTitleBar();
		
		Genre [] genres = Genre.values();
		ArrayAdapter<Genre> adapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, genres);
		spinner.setAdapter(adapter);
		continueButton.setTag(CONTINUE);
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}

	@Click(R.id.getdirectorbutton)
	public void onClick(View target) {
		if(target.getTag().equals(CONTINUE))
		{
			if(text.getText() != null && !("".equals(text.getText().toString()))){
				info = new MovieInfo();
				info.setTitle(text.getText().toString());
				info.setGenre((Genre)spinner.getSelectedItem());
				getPc().setCurrentProject(info);
				
				Intent intent;
				SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
				Boolean contentPref = sharedPrefs.getBoolean(Preferences.SHOW_PLOT, false);
				if(contentPref)
				{
					intent = new Intent(getApplicationContext(), PlotSummary.class);
				}
				else
				{
					getPc().getCurrentProject().setPlot("");
					intent = new Intent(getApplicationContext(), SetContent.class);
				}
				
				intent.putExtra(COMPANY, getPc());
				startActivity(intent);
			}
			else{
				MMLogger.v(getLocalClassName(), "empty title");
				Toast.makeText(getApplicationContext(), "Enter a name for your script!", Toast.LENGTH_LONG).show();
			}
		}
	}  
}
