package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.dao.ActorDbAdapter;
import ie.simo.movies.dao.SfxDbAdapter;
import ie.simo.movies.dao.viewbinder.ActorSpinnerViewBinder;
import ie.simo.movies.dao.viewbinder.SfxViewBinder;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.DBConsts;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SpecialEffects extends ActivityWithMenu implements OnSeekBarChangeListener{
	
	private SeekBar effectsLevel;
	private TextView effectsDesc;
	private String [] sfxComments;
	private Spinner sfxCompanies;
	private SfxDbAdapter db;
	
	//TODO add other textviews
	private TextView budgetView;
	private TextView compName;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.effects);
		
		db = new SfxDbAdapter(this);
		db.open();

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
		sfxCompanies.setVisibility(View.INVISIBLE);
		fillSpinner(sfxCompanies);
		
		sfxCompanies.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {				
				
				if(sfxCompanies.getSelectedItem().toString() != null && !sfxCompanies.getSelectedItem().toString().equals("") )
				{					
					Cursor c = (Cursor) sfxCompanies.getSelectedItem();
					Toast.makeText(SpecialEffects.this, c.getString(c.getColumnIndex("sfx_credits")), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				SpecialEffects.this.sfxCompanies.setSelection(0);				
			}
			
		});
		
		setTitleBar();
		setComments();
		effectsLevel.setOnSeekBarChangeListener(this);
		onProgressChanged(effectsLevel, 0, false);
	}
	/**
	 * Load strings into array
	 */
	private void setComments() {
		String [] comments = { 	
				getString(R.string.sfx0), 
				getString(R.string.sfx1), 
				getString(R.string.sfx2), 
				getString(R.string.sfx3),
				getString(R.string.sfx4),
				getString(R.string.sfx5)

			};
			sfxComments = comments;
	}

	/**
	 * Get all views
	 */
	private void findAllViewsById() {
		effectsDesc = (TextView) this.findViewById(R.id.sfxDesc);
		effectsLevel= (SeekBar) this.findViewById(R.id.sfx);
		sfxCompanies = (Spinner) this.findViewById(R.id.companiesSpinner);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
	}
	
	private void fillSpinner(Spinner s){
		Cursor c = db.selectAll();
		startManagingCursor(c);
				
		// create an array to specify which fields we want to display
		String[] from = new String[]{"sfx_name", "sfx_hire_cost", "sfx_reputation", "sfx_credits"};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{android.R.id.text1};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
		adapter.setViewBinder(new SfxViewBinder());
		// get reference to our spinner
		s.setAdapter(adapter);
	}
	
	
	/**
	 * On progress changed, update text (and show spinner to select company)
	 */
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		effectsDesc.setText(sfxComments[progress]);
		if(progress > 0){
			showCompanies();
		}
		else{
			hideCompanies();
		}
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	} 
	/**
	 * set contents of title bar
	 */
	private void setTitleBar() {
		budgetView.setText(getPc().getBudget()+"");
		compName.setText(getPc().getName());
	}
	
	private void showCompanies(){
		sfxCompanies.setVisibility(View.VISIBLE);
		
	}
	
	private void hideCompanies(){
		sfxCompanies.setVisibility(View.INVISIBLE);
		
	}
	
	

}
