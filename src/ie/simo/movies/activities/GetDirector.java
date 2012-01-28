package ie.simo.movies.activities;

import ie.simo.movies.dao.DirectorDbAdapter;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.DBConsts;

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

public class GetDirector extends Activity {

	private MovieInfo chosenFilm;
	private TextView chosen;
	private TextView price;
	private Spinner spinner;
	private Button produceFilm;
	private DirectorDbAdapter db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.director);
		
		db = new DirectorDbAdapter(this);
		db.open();

		findAllViewsById();
		Intent i = getIntent();
		fillSpinner();
		chosenFilm = (MovieInfo) i.getSerializableExtra("chosen");
		chosen.setText(chosenFilm.toButtonText());
		String msg = "$25,000,000";// TODO get this programmatically - getString(R.string.directorPrice , spinner.getSelectedItem());
		price.setText(msg);
		
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
					String msg = getString(R.string.directorPrice , nf.format(chosenDirector.getPriceToHire()*1000000));
					GetDirector.this.price.setText(msg);
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
				Intent i = new Intent();
				i.setClass(GetDirector.this, Result.class);
				i.putExtra("chosen", chosenFilm);
				startActivity(i);
				
			}
		});
		
		
	}
	
	private void findAllViewsById() {
		chosen = (TextView) this.findViewById(R.id.chosen);
		price = (TextView) this.findViewById(R.id.directorPrice);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		produceFilm = (Button) this.findViewById(R.id.produceFilm);
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