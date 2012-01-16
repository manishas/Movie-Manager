package ie.simo.movies.activities;

import ie.simo.movies.domain.MovieInfo;

import java.text.ChoiceFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Result extends Activity {
	
	private MovieInfo finishedFilm;
	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		findAllViewsById();
		
		Intent i = getIntent();
		finishedFilm = (MovieInfo) i.getSerializableExtra("chosen");
		
		Random r = new Random();
		float f = 0.5f * r.nextInt(10);
		rating.setRating(f);
		
		int money = (int) (((20 + r.nextInt(80) * f)*(finishedFilm.getDirector().getPriceToHire()/1000000) * (100000 * finishedFilm.getGenre().boxOffice())));
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String msg = getString(R.string.cashmoney, nf.format(money));
		
		cash.setText(msg);
		
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
}
