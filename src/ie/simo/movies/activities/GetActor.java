package ie.simo.movies.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.List;
import java.util.Set;

import ie.simo.movies.R;
import ie.simo.movies.dao.ActorDbAdapter;
import ie.simo.movies.dao.viewbinder.ActorSpinnerViewBinder;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.DBConsts;
import ie.simo.movies.util.MMLogger;

import static ie.simo.movies.util.Consts.COMPANY;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GetActor extends ActivityWithMenu {

	private int initialBudget;
	private TextView chosen;
	private TextView price;
	private TextView budgetView;
	private TextView compName;
	private Spinner spinner;
	private Button produceFilm;
	private Button addActor;
	private ActorDbAdapter db;
	private View view;
	private final String NO_CASH = "You cannot afford this cast! Choose again";
	private final String SAME_ACTOR = "You cannot hire the same actor twice! Choose again";
	private int id = 1;
	
	private List<Spinner> allSpinners = new ArrayList<Spinner>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actors);
		
		db = new ActorDbAdapter(this);
		db.open();

		findAllViewsById();
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		initialBudget = getPc().getBudget();
		
		fillSpinner(spinner);
		allSpinners.add(spinner);
		
		
		MMLogger.v(getLocalClassName(), "budget before actor: " + getPc().getBudget());
		chosen.setText(getPc().getCurrentProject().toButtonText());
		String msg = getString(R.string.directorPrice , spinner.getSelectedItem());
		price.setText(msg);
		
		setTitleBar();
		
		spinner.setOnItemSelectedListener(new ActorSelectionListener());
		
		produceFilm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isValid()){
					
					db.close();
					Intent i = new Intent();
					i.setClass(GetActor.this, SpecialEffects.class);

					MMLogger.v(getLocalClassName(), "Chosen cast: " + getPc().getCurrentCast().toString());
					getPc().setBudget(initialBudget - getPc().getCurrentCast().getCostOfActors());
					i.putExtra(COMPANY, getPc());
					MMLogger.v(getLocalClassName(), "budget after cast: " + (getPc().getBudget() - getPc().getCurrentCast().getCostOfActors()));
					
					startActivity(i);
				}
				else{
					if(!isPossibleSelection()){
						makeToast(SAME_ACTOR);
					}
					if(!isUnderBudget()){
						makeToast(NO_CASH);
					}
				}
			}
		});
		
		addActor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Spinner extraSpinner = new Spinner(v.getContext());
				allSpinners.add(extraSpinner);
				extraSpinner.setId(nextID());
				fillSpinner(extraSpinner);
				//remove already hired
				extraSpinner.setOnItemSelectedListener(new ActorSelectionListener());
				//add to view
				((LinearLayout) view).addView(extraSpinner);	
			}

			private int nextID() {
				id += 1;
				return id;
			}
		});	
	}
	
	private void  makeToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private boolean isValid(){	
		return isUnderBudget() && isPossibleSelection();
	}

	private boolean isPossibleSelection() {
		HashSet<Actor> tempSet = new HashSet<Actor>();
		tempSet.addAll(getPc().getCurrentCast().getActors());
		//ensure that all actor selections are unique
		return getPc().getCurrentCast().size() == tempSet.size();
	}

	private boolean isUnderBudget() {
		return getPc().getBudget() - getPc().getCurrentCast().getCostOfActors() >= 0;
	}
	
	private void findAllViewsById() {
		chosen = (TextView) this.findViewById(R.id.chosen);
		price = (TextView) this.findViewById(R.id.actorPrice);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		produceFilm = (Button) this.findViewById(R.id.produceFilm);
		addActor = (Button) this.findViewById(R.id.addActor);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		view = findViewById(R.id.actorLayout);
		compName = (TextView) findViewById(R.id.companyName);
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}
	
	private void fillSpinner(Spinner s){
		Cursor c = db.getAllActorsFilteredByCost(getPc().getBudget() - getPc().getCurrentCast().getCostOfActors());
		startManagingCursor(c);
				
		String[] from = new String[]{DBConsts.Actor.name, DBConsts.Actor.hire_cost, DBConsts.Actor.reputation,
				 DBConsts.Genre.action, DBConsts.Genre.horror, 
				 DBConsts.Genre.romance,  DBConsts.Genre.comedy,
				 DBConsts.Genre.drama,  DBConsts.Genre.scifi,  DBConsts.Genre.kids};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{R.id.starname, R.id.starprice, R.id.actionbonus, R.id.romancebonus, R.id.comedybonus,
				R.id.dramabonus, R.id.scifibonus, R.id.horrorbonus, R.id.kidsbonus};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, R.layout.spinner_row, c, from, to );
		
		adapter.setViewBinder(new ActorSpinnerViewBinder());
		// get reference to our spinner
		s.setAdapter(adapter);
	}
	
	
	private final class ActorSelectionListener implements OnItemSelectedListener {
				
		@Override
		public void onItemSelected(AdapterView<?> theSpinner, View arg1, int arg2, long arg3) {				
			
			if(theSpinner.getSelectedItem().toString() != null && !theSpinner.getSelectedItem().toString().equals("") )
			{					
				getPc().getCurrentCast().clear();
				for(Spinner spin : allSpinners){
					Cursor c = (Cursor) spin.getSelectedItem();
					Actor chosenActor = new Actor();
					chosenActor.setName(c.getString(c.getColumnIndex(DBConsts.Actor.name)));
					chosenActor.setPriceToHire(Integer.parseInt(c.getString(c.getColumnIndex(DBConsts.Actor.hire_cost))));
					chosenActor.setReputation(c.getInt(c.getColumnIndex(DBConsts.Actor.reputation)));
					chosenActor.setGender(c.getString(c.getColumnIndex(DBConsts.Actor.gender)));
					Set <Genre> actorBonuses = createBonusSet(c);
					chosenActor.setBonuses(actorBonuses);
					getPc().getCurrentCast().add(chosenActor);
				}
				String msg = getString(R.string.actorPrice , "$"+ getPc().getCurrentCast().getCostOfActors() + "M");
				GetActor.this.price.setText(msg);
				MMLogger.v("CAST", getPc().getCurrentCast().toString());
				budgetView.setText(String.format("$%dM", (getPc().getBudget() - getPc().getCurrentCast().getCostOfActors())));
				
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
			GetActor.this.spinner.setSelection(0);				
		}
	}
}
