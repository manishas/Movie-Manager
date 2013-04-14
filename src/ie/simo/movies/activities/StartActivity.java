package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.popup.InfoDialog;
import ie.simo.movies.popup.NewGame;
import ie.simo.movies.popup.SimpleEula;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static ie.simo.movies.util.Consts.COMPANY;

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
	private Button howToPlay;
	private Button prefs;
	private NewGame dialog;
	private ProductionCompanyDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		db = new ProductionCompanyDbAdapter(this);
		new SimpleEula(this).show();

		findAllViewsById();
		setUpListeners();
		setContinueEnabled();
	}

	private void setContinueEnabled() {
		db.openReadable();
		if (!db.previousGames()) {
			this.continueGame.setEnabled(false);
		}
		db.close();
	}

	private void setUpListeners() {
		newGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				dialog = new NewGame(StartActivity.this);
				dialog.show();
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
				new InfoDialog(a, a.getString(R.string.credits), a
						.getString(R.string.creditsText)).show();
			}
		});

		howToPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Activity a = StartActivity.this;
				new InfoDialog(a, a.getString(R.string.howtoplaytitle), a
						.getString(R.string.howtoplaycontent)).show();
			}
		});
	}

	private void findAllViewsById() {

		newGame = (Button) this.findViewById(R.id.newgameButton);
		continueGame = (Button) this.findViewById(R.id.continuegameButton);
		boxOffice = (Button) this.findViewById(R.id.boxofficeButton);
		prefs = (Button) this.findViewById(R.id.prefsButton);
		howToPlay = (Button) this.findViewById(R.id.howToPlayButton);
		credits = (Button) this.findViewById(R.id.creditsButton);
	}

	public void createNewGame() {
		ProductionCompany pc = new ProductionCompany(dialog.getCompanyName());

		// TODO sort out activity being started before dialog is dismissed
		db.openWritable();
		db.addCompany(pc);
		db.close();

		Intent i = new Intent();
		i.setClass(StartActivity.this, MakeFilmActivity.class);

		i.putExtra(COMPANY, pc);
		startActivity(i);
	}
}
