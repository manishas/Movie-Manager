package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import static ie.simo.movies.util.Consts.OFFER;
import ie.simo.movies.R;
import ie.simo.movies.dao.DirectorDbAdapter;
import ie.simo.movies.dao.viewbinder.DirectorSpinnerViewBinder;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.DBConsts;
import ie.simo.movies.util.MMLogger;

import java.util.HashSet;
import java.util.Set;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.director)
public class GetDirector extends ActivityWithMenu {
	
	@ViewById(R.id.chosen)
	protected TextView chosen;
	
	@ViewById(R.id.directorPrice)
	protected TextView price;
	
	@ViewById(R.id.budgetValue)
	protected TextView budgetView;
	
	@ViewById(R.id.companyName)
	protected TextView compName;
	
	@ViewById(R.id.spinner1)
	protected Spinner spinner;
	
	@ViewById(R.id.produceFilm)
	protected Button produceFilm;
	
	private DirectorDbAdapter db;
	private int pitchedBudget;
	
	@AfterViews
	public void onCreate() {
		db = new DirectorDbAdapter(this);
		db.open();
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));		
		pitchedBudget = i.getIntExtra(OFFER, 0);
		getPc().setBudget(getPc().getBudget() + pitchedBudget);
		fillSpinner();
		
		MMLogger.d("budget on starting get Director", "$"+getPc().getBudget());
		
		chosen.setText(getPc().getCurrentProject().toButtonText());
		String msg = "";
		price.setText(msg);
		
		setTitleBar();
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {				
				
				if(spinner.getSelectedItem().toString() != null && !spinner.getSelectedItem().toString().equals("") )
				{					
					Cursor c = (Cursor) spinner.getSelectedItem();
					Director chosenDirector = new Director();
					chosenDirector.setName(c.getString(c.getColumnIndex(DBConsts.Director.name)));
					chosenDirector.setPriceToHire(Integer.parseInt(c.getString(c.getColumnIndex(DBConsts.Director.hire_cost))));
					chosenDirector.setReputation(c.getInt(c.getColumnIndex(DBConsts.Director.reputation)));
					Set <Genre> directorBonuses = createBonusSet(c);
					chosenDirector.setBonuses(directorBonuses);
					
					getPc().setCurrentDirector(chosenDirector);

					String msg = getString(R.string.directorPrice , "$"+chosenDirector.getPriceToHire()+"M");
					GetDirector.this.price.setText(msg);
					
					budgetView.setText(String.format("$%dM", (getPc().getBudget() - chosenDirector.getPriceToHire())));
				}
			}

			private Set<Genre> createBonusSet(Cursor c) {
				Set<Genre> bonusSet = new HashSet<Genre>();
				Genre[] allGenres = {
						Genre.Action, 
						Genre.Horror, 
						Genre.Romance, 
						Genre.Comedy, 
						Genre.Drama, 
						Genre.ScienceFiction,
						Genre.Kids};
				
				for(Genre g : allGenres){
					if(c.getString(c.getColumnIndex(g.name())) != null){
						bonusSet.add(g);
					}
				}
				return bonusSet;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				GetDirector.this.spinner.setSelection(0);				
			}
			
		});
		
		produceFilm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isValid()){
					Intent i = new Intent();
					i.setClass(GetDirector.this, GetActor_.class);
					
					getPc().setBudget(getPc().getBudget() - getPc().getCurrentDirector().getPriceToHire());
					i.putExtra(COMPANY, getPc());
					
					startActivity(i);
				}
				else{
					makeToast();
				}	
			}
		});	
	}
	

	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}
	
	private void  makeToast(){
		Toast.makeText(this, "You can't afford this director! Choose again", Toast.LENGTH_SHORT).show();

	}
	
	private boolean isValid(){	
		return (getPc().getBudget() - getPc().getCurrentDirector().getPriceToHire() >= 0)? true : false;
	}
	
	private void fillSpinner(){
		 
		Cursor c = db.getAllDirectorsFilteredByCost(getPc().getBudget());
		startManagingCursor(c);
				
		// create an array to specify which fields we want to display
		String[] from = new String[]{DBConsts.Director.name, DBConsts.Director.hire_cost, DBConsts.Director.reputation,
				 DBConsts.Genre.action, DBConsts.Genre.horror, 
				 DBConsts.Genre.romance,  DBConsts.Genre.comedy,
				 DBConsts.Genre.drama,  DBConsts.Genre.scifi,  DBConsts.Genre.kids};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{R.id.starname, R.id.starprice, R.id.actionbonus, R.id.romancebonus, R.id.comedybonus,
				R.id.dramabonus, R.id.scifibonus, R.id.horrorbonus, R.id.kidsbonus};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, R.layout.spinner_row, c, from, to );
		adapter.setViewBinder(new DirectorSpinnerViewBinder());
		// get reference to our spinner
		spinner.setAdapter(adapter);
	}  
	
	@Override
	public void onBackPressed(){
		getPc().setBudget(getPc().getBudget() - pitchedBudget);

		MMLogger.d("pitched budget was ", "$"+pitchedBudget);
		MMLogger.d("budget on leaving", "$"+getPc().getBudget());
		super.onBackPressed();
	}
}