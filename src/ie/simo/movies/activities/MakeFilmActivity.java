package ie.simo.movies.activities;

import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.GetImage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MakeFilmActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button script1;
	private Button script2;
	private Button script3;
	private Button getMore;
	private Button makeOwn;
	private MovieInfo meta1;
	private MovieInfo meta2;
	private MovieInfo meta3;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findAllViewsById();

		generateScripts();
		
		OnClickListener notImplYetListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				longToast("Not implemented yet...");

			}

		};

		script1.setOnClickListener(this);
		script2.setOnClickListener(this);
		script3.setOnClickListener(this);

		getMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MakeFilmActivity.this.generateScripts();
				
			}
		});
		
		makeOwn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), OwnFilmActivity.class);		
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View target) {

		intent = new Intent(getApplicationContext(), GetDirector.class);
		if (target == script1)
			intent.putExtra("chosen", meta1);
		else if (target == script2)
			intent.putExtra("chosen", meta2);
		else if (target == script3)
			intent.putExtra("chosen", meta3);

		startActivity(intent);

	}

	private void generateScripts() {
		meta1 = new MovieInfo();
		meta2 = new MovieInfo();
		meta3 = new MovieInfo();
		changeButtonText(script1, meta1);
		changeButtonText(script2, meta2);
		changeButtonText(script3, meta3);
	}

	private void findAllViewsById() {
		script1 = (Button) this.findViewById(R.id.script1);
		script2 = (Button) this.findViewById(R.id.script2);
		script3 = (Button) this.findViewById(R.id.script3);
		getMore = (Button) this.findViewById(R.id.makeOwn);
		makeOwn = (Button) this.findViewById(R.id.makeownfilm);
	}

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	private void changeButtonText(Button myButton, MovieInfo meta) {
		meta.newScript();
		myButton.setText(meta.toButtonText());
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

		intent.setClass(MakeFilmActivity.this, BoxOffice.class);
    	
        return true;
    }    

}