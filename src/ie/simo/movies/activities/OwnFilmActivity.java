package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.ProductionCompany;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static ie.simo.movies.util.Consts.COMPANY;

public class OwnFilmActivity extends Activity {
	
	private EditText text;
	private Spinner spinner;
	private Button director;
	private MovieInfo info;
	
	private ProductionCompany pc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makeownfilm);
		
		Intent i = getIntent();
		
		pc = (ProductionCompany) i.getSerializableExtra(COMPANY);
		
		
		findAllViewsById();
		
		Genre [] genres = Genre.values();
		ArrayAdapter<Genre> adapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, genres);
		spinner.setAdapter(adapter);
		
		director.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(text.getText() != null && !("".equals(text.getText().toString()))){
					info = new MovieInfo();
					info.setTitle(text.getText().toString());
					info.setGenre((Genre)spinner.getSelectedItem());
					Intent intent = new Intent(getApplicationContext(), SetContent.class);
					intent.putExtra(COMPANY, pc);
					startActivity(intent);
				}
				else{
					Log.v(getLocalClassName(), "empty title");
					Toast.makeText(getApplicationContext(), "Enter a name for your script!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	private void findAllViewsById() {
		text = (EditText) this.findViewById(R.id.nametextfield);
		spinner = (Spinner) this.findViewById(R.id.genrespinner);
		director = (Button) this.findViewById(R.id.getdirectorbutton);
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
		intent.putExtra(COMPANY, pc);
    	
		startActivity(intent);
		
        return false;
    }    
}
