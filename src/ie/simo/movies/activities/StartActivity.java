package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.popup.InfoDialog;
import ie.simo.movies.popup.NewGame;
import ie.simo.movies.popup.SimpleEula;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static ie.simo.movies.util.Consts.COMPANY_NAME;

/**
 * First Activity which is opened when the game is started
 * 
 * @author Simon
 *
 */
public class StartActivity extends Activity {
	
	private Button newGame;
	private Button continueGame;
	private Button boxOffice;
	private Button credits;
	private Button prefs;
	private NewGame dialog;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new SimpleEula(this).show(); 
		
		findAllViewsById();	
		setUpListeners();
		setContinueEnabled();
	}

	private void setContinueEnabled() {
		//TODO add db query to see if this should be enabled or not
		this.continueGame.setEnabled(false);
	}

	private void setUpListeners() {
		newGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				dialog = new NewGame(StartActivity.this);
				dialog.show();
				//TODO sort out activity being started before dialog is dismissed
				//TODO Add to database
				
				Intent i = new Intent();
				i.setClass(StartActivity.this, MakeFilmActivity.class);
				
				i.putExtra(COMPANY_NAME, dialog.getCompanyName());
				startActivity(i);
			}
		});

		continueGame.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, ContinueGameActivity.class);
				startActivity(i);
			}
		});
		
		boxOffice.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(StartActivity.this, BoxOffice.class);
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

		newGame = (Button) this.findViewById(R.id.newgameButton);
		continueGame = (Button) this.findViewById(R.id.continuegameButton);
		boxOffice = (Button) this.findViewById(R.id.boxofficeButton);
		prefs = (Button) this.findViewById(R.id.prefsButton);
		credits = (Button) this.findViewById(R.id.creditsButton);
	}
}
