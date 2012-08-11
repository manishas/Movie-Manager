package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import ie.simo.movies.R;
import ie.simo.movies.domain.ProductionCompany;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SpecialEffects extends ActivityWithMenu implements OnSeekBarChangeListener{
	
	private SeekBar effectsLevel;
	private TextView effectsDesc;
	private String [] sfxComments;
	private Spinner sfxCompanies;
	
	private TextView budgetView;
	private TextView compName;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.effects);

		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
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
				getString(R.string.sfx4)
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
	
	/**
	 * On progress changed, update text (and show spinner to select company)
	 */
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		effectsDesc.setText(sfxComments[progress]);
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

}
