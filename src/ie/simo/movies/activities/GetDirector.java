package ie.simo.movies.activities;

import java.text.NumberFormat;
import java.util.Locale;

import ie.simo.movies.dao.DirectorDbAdapter;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.MovieInfo;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.director);

		findAllViewsById();
		Intent i = getIntent();
		fillSpinner();
		chosenFilm = (MovieInfo) i.getSerializableExtra("chosen");
		chosen.setText(chosenFilm.toButtonText());
		String msg = getString(R.string.directorPrice , 20000000/(spinner.getSelectedItemPosition() + 1));
		price.setText(msg);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Director chosenDirector = new Director();
				
				chosenDirector.setName(spinner.getSelectedItem().toString());
				chosenDirector.setPriceToHire(20000000/(spinner.getSelectedItemPosition() + 1));
				
				chosenFilm.setDirector(chosenDirector);
				//format the amount
				NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
				String msg = getString(R.string.directorPrice , nf.format(20000000/(spinner.getSelectedItemPosition() + 1)));
				GetDirector.this.price.setText(msg);				
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
		 DirectorDbAdapter db = new DirectorDbAdapter(this);
		 db.open();
		Cursor c = db.fetchAllDirectors();
		startManagingCursor(c);
		
		
		// create an array to specify which fields we want to display
		String[] from = new String[]{"director_name"};
		// create an array of the display item we want to bind our data to
		int[] to = new int[]{android.R.id.text1};
		// create simple cursor adapter
		SimpleCursorAdapter adapter =
		  new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		// get reference to our spinner
		spinner.setAdapter(adapter);
	}
}