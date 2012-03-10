package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.DirectorDbAdapter;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.DBConsts;

import static ie.simo.movies.util.Consts.BUDGET;
import static ie.simo.movies.util.Consts.CHOSEN;

import java.text.NumberFormat;
import java.util.Locale;

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

public class GetDirector extends Activity {

	private MovieInfo chosenFilm;
	private TextView chosen;
	private TextView price;
	private TextView budgetView;
	private Spinner spinner;
	private Button produceFilm;
	private DirectorDbAdapter db;
	private int budget;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.director);
		
		db = new DirectorDbAdapter(this);
		db.open();

		findAllViewsById();
		Intent i = getIntent();
		fillSpinner();
		
		budget = i.getIntExtra(BUDGET, 0);		
		chosenFilm = (MovieInfo) i.getSerializableExtra(CHOSEN);
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
					Director chosenDirector = new Director();
					chosenDirector.setName(c.getString(c.getColumnIndex(DBConsts.Director.name)));
					chosenDirector.setPriceToHire(Integer.parseInt(c.getString(c.getColumnIndex(DBConsts.Director.hire_cost))));
					chosenFilm.setDirector(chosenDirector);
					//format the amount
					NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
					String msg = getString(R.string.directorPrice , "$"+chosenDirector.getPriceToHire()+"M");
					GetDirector.this.price.setText(msg);
					
					budgetView.setText("$" + (budget - chosenDirector.getPriceToHire()));
				}
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
					i.setClass(GetDirector.this, GetActor.class);
					i.putExtra(CHOSEN, chosenFilm);
					i.putExtra(BUDGET, budget - chosenFilm.getDirector().getPriceToHire());
					
					startActivity(i);
				}
				else{
					makeToast();
				}
				
			}
		});
		
		
	}
	
	private void  makeToast(){
		Toast.makeText(this, "You can't afford this director! Choose again", Toast.LENGTH_SHORT).show();

	}
	
	private boolean isValid(){	
		return (this.budget - chosenFilm.getDirector().getPriceToHire() >= 0)? true : false;
	}
	
	
	
	
	private void findAllViewsById() {
		chosen = (TextView) this.findViewById(R.id.chosen);
		price = (TextView) this.findViewById(R.id.directorPrice);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		produceFilm = (Button) this.findViewById(R.id.produceFilm);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
	}
	
	private void fillSpinner(){
		 
		Cursor c = db.fetchAllDirectors();
		startManagingCursor(c);
				
		// create an array to specify which fields we want to display
		String[] from = new String[]{DBConsts.Director.name};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{android.R.id.text1};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
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