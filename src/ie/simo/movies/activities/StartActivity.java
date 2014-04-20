package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.popup.InfoDialog;
import ie.simo.movies.popup.NewGame;
import ie.simo.movies.popup.SimpleEula;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * First Activity which is opened when the game is started
 * 
 * @author Simon
 * 
 */

@EActivity(R.layout.splash)
public class StartActivity extends Activity {

	@ViewById(R.id.newgameButton)
	protected Button newGame;
	
	@ViewById(R.id.continuegameButton)
	protected Button continueGame;
	
	@ViewById(R.id.boxofficeButton)
	protected Button boxOffice;
	
	@ViewById(R.id.creditsButton)
	protected Button credits;
	
	@ViewById(R.id.howToPlayButton)
	protected Button howToPlay;
	
	@ViewById(R.id.prefsButton)
	protected Button prefs;
	
	private NewGame dialog;
	private ProductionCompanyDbAdapter db;

	@AfterViews
	public void onCreate() {
		db = new ProductionCompanyDbAdapter(this);
		new SimpleEula(this).show();
		setContinueEnabled();
	}

	private void setContinueEnabled() {
		db.openReadable();
		if (!db.previousGames()) {
			this.continueGame.setEnabled(false);
		}
		db.close();
	}

	@Click(R.id.newgameButton)
	protected void newGameClick() {
		dialog = new NewGame(this);
		dialog.show();
	}
	
	@Click(R.id.continuegameButton)
	protected void continueClick() {
		Intent i = new Intent();
		i.setClass(this, ContinueGameActivity_.class);
		startActivity(i);
	}
	
	@Click(R.id.boxofficeButton)
	protected void boxOfficeClick(){
		Intent i = new Intent();
		i.setClass(this, BoxOffice.class);
		startActivity(i);
	}
	
	@Click(R.id.prefsButton)
	protected void prefsClick() {
		Intent i = new Intent();
		i.setClass(this, Preferences.class);
		startActivity(i);
	}
	
	@Click(R.id.creditsButton)
	protected void creditsClick() {
		Activity a = this;
		new InfoDialog(a, a.getString(R.string.credits), a
				.getString(R.string.creditsText)).show();
	}
	
	@Click (R.id.howToPlayButton)
	protected void howToPlayClick() {
		Activity a = this;
		new InfoDialog(a, a.getString(R.string.howtoplaytitle), a
				.getString(R.string.howtoplaycontent)).show();

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
