package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.censor.factory.CensorFactory;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.generator.PlotGenerator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlotSummary extends ActivityWithMenu {

	private Button continueButton;
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
		plotGenerator = new PlotGenerator();
		Genre genre = getPc().getCurrentProject().getGenre();
		String title = getPc().getCurrentProject().getTitle();
		

		findAllViewsById();
		
		filmTitle.setText(title + " ("+ genre.toString() + ")");
		
		generatePlot(genre, title);
		
		setTitleBar();
		setListeners();
	}

	private void generatePlot(Genre g, String title) {
		String plot = plotGenerator.createPlot(g, title);
		plotSummary.setText(plot);
		getPc().getCurrentProject().setPlot(plot);
	}

	private void setListeners() {
		continueButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(COMPANY, getPc());
				i.setClass(getApplicationContext(), SetContent.class);
				startActivity(i);
			}
		});
	}

	private void findAllViewsById() {
		plotSummary = (TextView) this.findViewById(R.id.plotSummary);
		filmTitle = (TextView) this.findViewById(R.id.titleOfFilm);
		continueButton = (Button) this.findViewById(R.id.goToContentButton);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}

}
