package ie.simo.movies.activities;

import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.MovieSummary;
import ie.simo.movies.scoring.earnings.EarningsCalculator;
import ie.simo.movies.scoring.earnings.EarningsCalculatorFirstImpl;
import ie.simo.movies.scoring.rating.RatingCalculator;

import java.text.ChoiceFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Result extends Activity {
	
	private MovieInfo finishedFilm;
	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	
	private EarningsCalculator calculator;
	private RatingCalculator ratingCalc;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		calculator = new EarningsCalculatorFirstImpl();
		ratingCalc = new RatingCalculator();
		
		findAllViewsById();
		
		Intent i = getIntent();
		finishedFilm = (MovieInfo) i.getSerializableExtra("chosen");
		
		float criticRating = (float) ratingCalc.getRating(finishedFilm.getDirector().getReputation());
		
		rating.setRating(criticRating);
		
		int money = calculator.calculate(finishedFilm, criticRating);
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String msg = getString(R.string.cashmoney, nf.format(money));
		
		cash.setText(msg);
		
		MovieSummary summary = new MovieSummary();
		summary.setTotalEarnings(money/1000000);
		summary.setInfo(finishedFilm);
		
		BoxOfficeDbAdapter db = new BoxOfficeDbAdapter(this);
		db.open();
		db.createMovie(summary);
		db.close();
		
		tryAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				
				i.setClass(Result.this, MakeFilmActivity.class);
				
				startActivity(i);
			}
		});
		
	}
	
	private void findAllViewsById() {
		tryAgain = (Button) this.findViewById(R.id.tryAgain);
		rating = (RatingBar) this.findViewById(R.id.ratingBar1);
		cash = (TextView) this.findViewById(R.id.cashmoney);
		
	}
	
	 // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        //Doesn't check to see if different button selected
    	Intent intent = new Intent();

		intent.setClass(this, BoxOffice.class);
    	
		startActivity(intent);
		
        return false;
    }    
}
