package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.BUDGET;
import ie.simo.movies.R;
import ie.simo.movies.censor.Censor;
import ie.simo.movies.censor.IrishCensor;
import ie.simo.movies.censor.factory.CensorFactory;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SetContent extends Activity implements OnSeekBarChangeListener{

	private int budget;
	
	private Censor censor;
	
	private TextView contentDesc;
	private TextView violenceDesc;
	private TextView sexDesc;
	private TextView languageDesc;
	private SeekBar violenceLevel;
	private SeekBar sexLevel;
	private SeekBar languageLevel;
	private ImageView rating;
	
	private Button pitchFilm;
	
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
		budget = i.getIntExtra(BUDGET, 0);
		findAllViewsById();
		
		setListeners();
		setRatingImage();
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
				i.setClass(SetContent.this, PitchFilm.class);
				startActivity(i);
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
	//TODO fix levels
	private void setRatingImage() {
		
		int total = sexLevel.getProgress() + violenceLevel.getProgress() + languageLevel.getProgress();
		
		if(total == 0){
			rating.setImageDrawable(getResources().getDrawable(censor.getGId()));
		}
		else if(total == 1){
			rating.setImageDrawable(getResources().getDrawable(censor.getPGId()));
		}
		else if(sexLevel.getProgress() < 2 && violenceLevel.getProgress() < 2 && languageLevel.getProgress() < 2){
			rating.setImageDrawable(getResources().getDrawable(censor.get12Id()));
		}
		else if(total >= 7)
		{
			rating.setImageDrawable(getResources().getDrawable(censor.get18Id()));
		}
		else{
			rating.setImageDrawable(getResources().getDrawable(censor.get15Id()));
		}
	}

	private void findAllViewsById() {
		
		contentDesc = (TextView) this.findViewById(R.id.contentDesc);
		violenceDesc = (TextView) this.findViewById(R.id.violenceDesc);
		sexDesc = (TextView) this.findViewById(R.id.sexDesc);
		languageDesc = (TextView) this.findViewById(R.id.rudeDesc);
		violenceLevel = (SeekBar) this.findViewById(R.id.violenceBar);
		sexLevel = (SeekBar) this.findViewById(R.id.sexBar);
		languageLevel= (SeekBar) this.findViewById(R.id.rudeBar);
		rating = (ImageView) this.findViewById(R.id.ratingImage);
		pitchFilm = (Button) this.findViewById(R.id.pitchButton);
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
