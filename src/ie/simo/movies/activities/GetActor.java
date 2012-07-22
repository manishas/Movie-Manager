package ie.simo.movies.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ie.simo.movies.R;
import ie.simo.movies.dao.ActorDbAdapter;
import ie.simo.movies.dao.viewbinder.ActorSpinnerViewBinder;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.DBConsts;

import static ie.simo.movies.util.Consts.COMPANY;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	private TextView chosen;
	private TextView price;
	private TextView budgetView;
	private Spinner spinner;
	private Button produceFilm;
	private Button addActor;
	private ActorDbAdapter db;
	private ProductionCompany pc;
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
		fillSpinner(spinner);
		allSpinners.add(spinner);
		
		pc = (ProductionCompany) i.getSerializableExtra(COMPANY);
		Log.v(getLocalClassName(), "budget before actor: " + pc.getBudget());
		chosen.setText(pc.getCurrentProject().toButtonText());
		String msg = getString(R.string.directorPrice , spinner.getSelectedItem());
		price.setText(msg);
		
		budgetView.setText(pc.getBudget()+"");
		
		spinner.setOnItemSelectedListener(new ActorSelectionListener());
		
		produceFilm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isValid()){
					
					db.close();
					Intent i = new Intent();
					i.setClass(GetActor.this, Result.class);
					Log.v(getLocalClassName(), "Chosen cast: " + pc.getCurrentProject().getCast().toString());
					pc.setBudget(pc.getBudget()  - pc.getCurrentProject().getCast().getCostOfActors());
					i.putExtra(COMPANY, pc);
					Log.v(getLocalClassName(), "budget after cast: " + (pc.getBudget() - pc.getCurrentProject().getCast().getCostOfActors()));
					
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
		tempSet.addAll(pc.getCurrentProject().getCast().getActors());
		//ensure that all actor selections are unique
		return pc.getCurrentProject().getCast().getActors().size() == tempSet.size();
	}

	private boolean isUnderBudget() {
		return pc.getBudget() - pc.getCurrentProject().getCast().getCostOfActors() >= 0;
	}
	
	private void findAllViewsById() {
		chosen = (TextView) this.findViewById(R.id.chosen);
		price = (TextView) this.findViewById(R.id.actorPrice);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		produceFilm = (Button) this.findViewById(R.id.produceFilm);
		addActor = (Button) this.findViewById(R.id.addActor);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		view = findViewById(R.id.actorLayout);
	}
	
	private void fillSpinner(Spinner s){
		Cursor c = db.fetchAllActors();
		startManagingCursor(c);
				
		// create an array to specify which fields we want to display
		String[] from = new String[]{DBConsts.Actor.name, DBConsts.Actor.hire_cost};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{android.R.id.text1};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
		adapter.setViewBinder(new ActorSpinnerViewBinder());
		// get reference to our spinner
		s.setAdapter(adapter);
	}
	
	
	private final class ActorSelectionListener implements OnItemSelectedListener {
				
		@Override
		public void onItemSelected(AdapterView<?> theSpinner, View arg1, int arg2, long arg3) {				
			
			if(theSpinner.getSelectedItem().toString() != null && !theSpinner.getSelectedItem().toString().equals("") )
			{					
				pc.getCurrentProject().getCast().getActors().clear();
				for(Spinner spin : allSpinners){
					Cursor c = (Cursor) spin.getSelectedItem();
					Actor chosenActor = new Actor();
					chosenActor.setName(c.getString(c.getColumnIndex(DBConsts.Actor.name)));
					chosenActor.setPriceToHire(Integer.parseInt(c.getString(c.getColumnIndex(DBConsts.Actor.hire_cost))));
					pc.getCurrentProject().getCast().getActors().add(chosenActor);
				}
				String msg = getString(R.string.actorPrice , "$"+ pc.getCurrentProject().getCast().getCostOfActors() + "M");
				GetActor.this.price.setText(msg);
				Log.v("CAST", pc.getCurrentProject().getCast().toString());
				budgetView.setText("$" + (pc.getBudget() - pc.getCurrentProject().getCast().getCostOfActors()));
			}
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			GetActor.this.spinner.setSelection(0);				
		}
	}
}