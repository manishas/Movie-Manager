package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.ActorDbAdapter;
import ie.simo.movies.dao.viewbinder.ActorSpinnerViewBinder;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Cast;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.DBConsts;

import static ie.simo.movies.util.Consts.*;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GetActor extends Activity {

	private MovieInfo chosenFilm;
	private TextView chosen;
	private TextView price;
	private TextView budgetView;
	private Spinner spinner;
	private Button produceFilm;
	private ActorDbAdapter db;
	private int budget;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actors);
		
		db = new ActorDbAdapter(this);
		db.open();

		findAllViewsById();
		Intent i = getIntent();
		fillSpinner();
		
		chosenFilm = (MovieInfo) i.getSerializableExtra(CHOSEN);
		Log.v(getLocalClassName(), "film details: " + chosenFilm.toString());
		budget = i.getIntExtra(BUDGET, 0);
		Log.v(getLocalClassName(), "budget before actor: " +budget);
		chosen.setText(chosenFilm.toButtonText());
		String msg = "$25,000,000";// TODO get this programmatically - getString(R.string.directorPrice , spinner.getSelectedItem());
		price.setText(msg);
		
		budgetView.setText(budget+"");
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {				
				
				if(spinner.getSelectedItem().toString() != null && !spinner.getSelectedItem().toString().equals("") )
				{					
					Cursor c = (Cursor) spinner.getSelectedItem();
					Actor chosenActor = new Actor();
					chosenActor.setName(c.getString(c.getColumnIndex(DBConsts.Actor.name)));
					chosenActor.setPriceToHire(Integer.parseInt(c.getString(c.getColumnIndex(DBConsts.Actor.hire_cost))));
					
					Cast cast = new Cast();
					cast.getActors().add(chosenActor);
					chosenFilm.setCast(cast);
					//TODO handle this better
					//chosenFilm.getCast().getActors().add(chosenActor);
					String msg = getString(R.string.actorPrice , "$"+ chosenActor.getPriceToHire() + "M");
					GetActor.this.price.setText(msg);
					
					budgetView.setText("$" + (budget - chosenActor.getPriceToHire()));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				GetActor.this.spinner.setSelection(0);				
			}
			
		});
		
		produceFilm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isValid()){
					Intent i = new Intent();
					i.setClass(GetActor.this, Result.class);
					i.putExtra(CHOSEN, chosenFilm);
					Log.v(getLocalClassName(), "Chosen cast: " + chosenFilm.getCast().toString());
					i.putExtra(BUDGET, budget - chosenFilm.getCast().getCostOfActors());
					Log.v(getLocalClassName(), "budget after cast: " + (budget - chosenFilm.getCast().getCostOfActors()));
					
					startActivity(i);
				}
				else{
					makeToast();
				}
				
			}
		});
		
		
	}
	
	private void  makeToast(){
		Toast.makeText(this, "You can't afford this Actor! Choose again", Toast.LENGTH_SHORT).show();

	}
	
	private boolean isValid(){	
		return (this.budget - chosenFilm.getCast().getCostOfActors() >= 0)? true : false;
	}
	
	
	
	
	private void findAllViewsById() {
		chosen = (TextView) this.findViewById(R.id.chosen);
		price = (TextView) this.findViewById(R.id.actorPrice);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		produceFilm = (Button) this.findViewById(R.id.produceFilm);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
	}
	
	private void fillSpinner(){
		 
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
		spinner.setAdapter(adapter);
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
    	
        return true;
    }    
}