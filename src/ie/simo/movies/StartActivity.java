package ie.simo.movies;

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
		
		findAllViewsById();
		
		startGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, MakeFilmActivity.class);
				startActivity(i);
			}
		});
		
		boxOffice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, BoxOffice.class);
				startActivity(i);
			}
		});
	}
	
	private void findAllViewsById() {
		startGame = (Button) this.findViewById(R.id.startgameButton);
		boxOffice = (Button) this.findViewById(R.id.boxofficeButton);
	}
}
