package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.BUDGET;
import static ie.simo.movies.util.Consts.CHOSEN;
import ie.simo.movies.R;
import ie.simo.movies.censor.Censor;
import ie.simo.movies.censor.factory.CensorFactory;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.Rating;
import ie.simo.movies.domain.RatingDetails;
import ie.simo.movies.popup.InfoDialog;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SetContent extends Activity implements OnSeekBarChangeListener{

	private int budget;
	private MovieInfo chosenMovie;
	
	private Censor censor;
	
	private TextView title;
	private TextView violenceDesc;
	private TextView sexDesc;
	private TextView languageDesc;
	private SeekBar violenceLevel;
	private SeekBar sexLevel;
	private SeekBar languageLevel;
	private ImageView ratingImg;
	
	private Button pitchFilm;
	private ImageButton help;
	
	private String [] violence;
	private String [] sex;
	private String [] language;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		
		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		censor = new CensorFactory().getCensor(sharedPrefs.getString(Preferences.RATING_STYLE, "usa"));
		getStrings();
		
		Intent i = getIntent();
		chosenMovie = (MovieInfo) i.getSerializableExtra(CHOSEN);
		budget = i.getIntExtra(BUDGET, 0);
		
		findAllViewsById();
		setListeners();
		
		onProgressChanged(violenceLevel, 0, false);
		onProgressChanged(sexLevel, 0, false);
		onProgressChanged(languageLevel, 0, false);
		
		title.setText(getString(R.string.contentTitle) + chosenMovie.toButtonText());
	}
	
	private void setListeners() {
		violenceLevel.setOnSeekBarChangeListener(this);
		sexLevel.setOnSeekBarChangeListener(this);
		languageLevel.setOnSeekBarChangeListener(this);
		
		pitchFilm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(BUDGET, budget);
				i.putExtra(CHOSEN, chosenMovie);
				i.setClass(SetContent.this, PitchFilm.class);
				startActivity(i);
			}
		});
		
		help.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Activity a = SetContent.this; 
				new InfoDialog(a, a.getString(R.string.contentHelp), a.getString(R.string.contentHelpMessage)).show();
			}
		});
	}

	private void getStrings() {
		String [] vTemp = { 	
		getString(R.string.violence0), 
		getString(R.string.violence1), 
		getString(R.string.violence2), 
		getString(R.string.violence3)
		};
		violence = vTemp;
		
		String [] sTemp = { 
			getString(R.string.sex0), 
			getString(R.string.sex1), 
			getString(R.string.sex2), 
			getString(R.string.sex3)
		};
		sex = sTemp;
		
		String [] lTemp = { 	
			getString(R.string.language0), 
			getString(R.string.language1), 
			getString(R.string.language2), 
			getString(R.string.language3)
		};
		language = lTemp;
		
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		
		switch(seekBar.getId()){
			case R.id.violenceBar: 	violenceDesc.setText(violence[progress]);  
									break;
			case R.id.sexBar:		sexDesc.setText(sex[progress]); 
									break;
			case R.id.rudeBar:		languageDesc.setText(language[progress]);
									break;
		}
		
		setRatingImage();
		
	}
	
	//TODO sanity check levels
	private void setRatingImage() {
		
		RatingDetails thisMovie = new RatingDetails();
		thisMovie.setSex(sexLevel.getProgress());
		thisMovie.setViolence(violenceLevel.getProgress());
		thisMovie.setLanguage(languageLevel.getProgress());

		setRatingImage(thisMovie.getRating());
		
		chosenMovie.setRatingDetails(thisMovie);
	}

	private void setRatingImage(Rating myRating) {

		switch(myRating){
			case GENERAL: 		ratingImg.setImageDrawable(getResources().getDrawable(censor.getGId()));
								break;
			case PG: 			ratingImg.setImageDrawable(getResources().getDrawable(censor.getPGId()));
								break;
			case TWELVES: 		ratingImg.setImageDrawable(getResources().getDrawable(censor.get12Id()));
								break;
			case FIFTEENS: 		ratingImg.setImageDrawable(getResources().getDrawable(censor.get15Id()));
								break;
			case EIGHTEENS: 	ratingImg.setImageDrawable(getResources().getDrawable(censor.get18Id()));
								break;
		}
	}

	private void findAllViewsById() {
		violenceDesc = (TextView) this.findViewById(R.id.violenceDesc);
		sexDesc = (TextView) this.findViewById(R.id.sexDesc);
		languageDesc = (TextView) this.findViewById(R.id.rudeDesc);
		violenceLevel = (SeekBar) this.findViewById(R.id.violenceBar);
		sexLevel = (SeekBar) this.findViewById(R.id.sexBar);
		languageLevel= (SeekBar) this.findViewById(R.id.rudeBar);
		ratingImg = (ImageView) this.findViewById(R.id.ratingImage);
		pitchFilm = (Button) this.findViewById(R.id.pitchButton);
		help = (ImageButton) this.findViewById(R.id.contentHelp);
		title = (TextView) this.findViewById(R.id.setContentActivityTitle);
		
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
		intent.putExtra(BUDGET, budget);
    	
		startActivity(intent);
		
        return false;
    }

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}    
}
