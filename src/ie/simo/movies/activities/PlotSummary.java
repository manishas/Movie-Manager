package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.generator.PlotGenerator;
import ie.simo.movies.util.MMLogger;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class PlotSummary extends ActivityWithMenu {

	private Button continueButton;
	private Button editSummaryButton;
	private EditText editablePlotSummary;
	private TextView filmTitle;
	private TextView plotSummary;
	private PlotGenerator plotGenerator;
	private TextView budgetView;
	private TextView compName;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.plot);

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		//get preference for content, default to "PLAIN"
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
		Boolean contentPref = sharedPrefs.getBoolean(Preferences.MENTAL_DESCRIPTIONS, false);
		String contentType = contentPref ? "MENTAL" : "PLAIN"; 
		plotGenerator = new PlotGenerator(contentType);
		
		Genre genre = getPc().getCurrentProject().getGenre();
		String title = getPc().getCurrentProject().getTitle();
		

		findAllViewsById();
		
		editablePlotSummary.setVisibility(View.INVISIBLE);
		filmTitle.setText(title + " ("+ genre.toString() + ")");
		
		generatePlot(genre, title);
		
		setTitleBar();
		setListeners();
	}

	private void generatePlot(Genre g, String title) {
		String plot = plotGenerator.createPlot(g, title);
		plotSummary.setText(plot);
	}

	private void setListeners() {
		continueButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				getPc().getCurrentProject().setPlot(plotSummary.getText().toString());
				MMLogger.d("Plot: ", plotSummary.getText().toString());
				Intent i = new Intent();
				i.putExtra(COMPANY, getPc());
				i.setClass(getApplicationContext(), SetContent.class);
				startActivity(i);
			}
		});
		
		editSummaryButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				plotSummary.setVisibility(View.GONE);
				editablePlotSummary.setVisibility(View.VISIBLE);
				editablePlotSummary.setText(plotSummary.getText());
			}
		});
		
		editablePlotSummary.addTextChangedListener(new TextWatcher() {

	          public void afterTextChanged(Editable s) {
	        	  plotSummary.setText(editablePlotSummary.getText());
	          }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void findAllViewsById() {
		plotSummary = (TextView) this.findViewById(R.id.plotSummary);
		filmTitle = (TextView) this.findViewById(R.id.titleOfFilm);
		continueButton = (Button) this.findViewById(R.id.goToContentButton);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
		editSummaryButton = (Button) this.findViewById(R.id.edit_plot);
		editablePlotSummary = (EditText) this.findViewById(R.id.plotSummaryEdit);
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}

}
