package ie.simo.movies;

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
	
	private RatingBar rating;
	private Button tryAgain;
	private TextView cash;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		findAllViewsById();
		Random r = new Random();
		float f = 0.5f * r.nextInt(10);
		rating.setRating(f);
		
		int money = (int)(20 + r.nextInt(80) * f) * 1000000;
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
