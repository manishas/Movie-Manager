package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import static ie.simo.movies.util.Consts.NOMINATIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ie.simo.movies.R;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.domain.Oscars;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.domain.award.Award;
import ie.simo.movies.domain.award.AwardCategories;
import ie.simo.movies.domain.award.AwardRound;
import ie.simo.movies.generator.MovieInfoGenerator;
import ie.simo.movies.util.RandomNumberProvider;
import android.os.Bundle;
import android.content.Intent;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OscarsActivity extends ActivityWithMenu {
	
	private RelativeLayout view;
	private TextView text;
	private Oscars oscars;
	private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscars);
        
        Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		int nominations = i.getIntExtra(NOMINATIONS, 0);
		
		oscars = new Oscars(this.getApplicationContext(), nominations, getPc().getCurrentProject());
		getAllViews();
		button.setVisibility(View.INVISIBLE);
		setOnClick();
		text.setText(Html.fromHtml(oscars.getCurrentRound().getDisplayText()));
        
    }

    private void setOnClick() {
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!oscars.step()) {
					text.setText(Html.fromHtml(oscars.getCurrentRound()
							.getDisplayText()));
				} else {
					text.setText("Congratulations on your awards!");
					button.setVisibility(View.VISIBLE);
				}
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = prepareToLeaveOscarsScreen();
				getPc().setCurrentProject(null);
				i.setClass(OscarsActivity.this, MakeFilmActivity.class);
				startActivity(i);
			}

			private Intent prepareToLeaveOscarsScreen() {
				
				Intent i = new Intent();
				
				getPc().setBudget(getPc().getBudget());
				
				MovieSummary summary = createMovieSummary(getPc().getCurrentProject());
				
				if(getPc().getBackCatalogue() == null){
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
				//summary.setTotalEarnings(money);
				return summary;
			}
		});
	}

	public Oscars getOscars() {
		return oscars;
	}

	public void setOscars(Oscars oscars) {
		this.oscars = oscars;
	}

    private void getAllViews(){
    	view = (RelativeLayout) findViewById(R.id.whole_screen);
		text = (TextView) findViewById(R.id.comment);
		button = (Button) findViewById(R.id.makeNewMovie);
    }
    
    @Override
    public void onBackPressed(){
    	Toast.makeText(getApplicationContext(), "You can't go back until after the ceremony!", Toast.LENGTH_LONG).show();
    }
}
