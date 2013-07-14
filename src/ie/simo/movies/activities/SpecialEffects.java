package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.dao.SfxDbAdapter;
import ie.simo.movies.dao.viewbinder.SfxViewBinder;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.MMLogger;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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
	
	private TextView budgetView;
	private TextView compName;
	private TextView knownFor;
	private TextView sfxCost;
	
	private LinearLayout layout;
	private Button done;
	
	private int totalEffectsCost = 0;
	private int sfxStudioRep = 0;
	
	//TODO on back button it forgets which actors were selected
	
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
		
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isValid()){
					Intent i = new Intent();
					MMLogger.v("SFX: ", "before budget = " + getPc().getBudget() + ", sfx cost = " + totalEffectsCost);
					i.setClass(SpecialEffects.this, Result.class);
					getPc().getCurrentProject().setSpecialEffectsCompany(sfxCompanies.getSelectedItem().toString());
					getPc().setBudget(getPc().getBudget() - totalEffectsCost);
					
					MMLogger.v("SFX: ", "after budget = " + getPc().getBudget());
					
					i.putExtra(COMPANY, getPc());
					
					startActivity(i);
				}
				else{
					Toast.makeText(getApplicationContext(), "You can't afford these effects", Toast.LENGTH_LONG).show();
				}
				
			}
			
			private boolean isValid() {
				int budget = getPc().getBudget();
				return (budget - totalEffectsCost >= 0) ? true : false;
			}
		});
		
		
		
		sfxCompanies.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {				
				
				if(sfxCompanies.getSelectedItem().toString() != null && !sfxCompanies.getSelectedItem().toString().equals("") )
				{					
					Cursor c = (Cursor) sfxCompanies.getSelectedItem();
					knownFor.setText(c.getString(c.getColumnIndex("sfx_credits")));
					sfxStudioRep = c.getInt(c.getColumnIndex("sfx_reputation"));
					setEffectsCost(sfxStudioRep);
				}
			}

			public void setEffectsCost(int sfxRep) {
				totalEffectsCost = effectsLevel.getProgress() * (sfxRep/10);
				SpecialEffects.this.updateEffectsCost(effectsLevel.getProgress(), sfxRep);
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
	
	
	public void updateEffectsCost(int progress, int sfxStudioRep){
		sfxCost.setText(String.format("$%dM", progress * sfxStudioRep/10));
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
		effectsDesc 	= (TextView) this.findViewById(R.id.sfxDesc);
		effectsLevel	= (SeekBar) this.findViewById(R.id.sfx);
		sfxCompanies 	= (Spinner) this.findViewById(R.id.companiesSpinner);
		budgetView 		= (TextView) this.findViewById(R.id.budgetValue);
		compName 		= (TextView)this.findViewById(R.id.companyName);
		knownFor 		= (TextView)this.findViewById(R.id.knownfor);
		layout 			= (LinearLayout) this.findViewById(R.id.knownforlayout);
		sfxCost 		= (TextView) this.findViewById(R.id.sfxpricevalue);
		done 			= (Button) this.findViewById(R.id.sfxbutton);
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
		updateEffectsCost(progress, sfxStudioRep);
		totalEffectsCost = progress * sfxStudioRep/10;
		
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
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}
	
	private void showCompanies(){
		sfxCompanies.setVisibility(View.VISIBLE);
		layout.setVisibility(View.VISIBLE);
	}
	
	private void hideCompanies(){
		sfxCompanies.setVisibility(View.INVISIBLE);
		layout.setVisibility(View.INVISIBLE);
		
	}
}
