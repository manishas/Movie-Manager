package ie.simo.movies.activities;

import ie.simo.movies.util.SimpleEula;
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
				i.setClass(StartActivity.this, TestDatabaseActivity.class);
				startActivity(i);
			}
		});
	}
	
	private void findAllViewsById() {
		startGame = (Button) this.findViewById(R.id.startgameButton);
		boxOffice = (Button) this.findViewById(R.id.boxofficeButton);
	}
}
