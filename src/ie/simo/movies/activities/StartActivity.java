package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.popup.InfoDialog;
import ie.simo.movies.popup.SimpleEula;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * First Activity which is opened when the game is started
 * 
 * @author Simon
 *
 */
public class StartActivity extends Activity {
	
	private Button startGame;
	private Button boxOffice;
	private Button credits;
	private Button prefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new SimpleEula(this).show(); 
		
		findAllViewsById();
		
		setUpListeners();
	}

	private void setUpListeners() {
		startGame.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, MakeFilmActivity.class);
				startActivity(i);
			}
		});
		
		boxOffice.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, BoxOfficeTab.class);
				startActivity(i);
			}
		});
		
		prefs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, Preferences.class);
				startActivity(i);
			}
		});
		
		credits.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Activity a = StartActivity.this;
				new InfoDialog(a, a.getString(ie.simo.movies.R.string.credits), a.getString(ie.simo.movies.R.string.creditsText)).show();
			}
		});
	}

	private void findAllViewsById() {
		startGame = (Button) this.findViewById(R.id.startgameButton);
		boxOffice = (Button) this.findViewById(R.id.boxofficeButton);
		prefs = (Button) this.findViewById(R.id.prefsButton);
		credits = (Button) this.findViewById(R.id.creditsButton);
	}
}
