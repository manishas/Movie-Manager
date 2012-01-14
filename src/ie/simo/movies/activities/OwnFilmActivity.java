package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.R.id;
import ie.simo.movies.R.layout;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OwnFilmActivity extends Activity {
	
	EditText text;
	Spinner spinner;
	Button director;
	MovieInfo info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makeownfilm);
		
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
					Intent intent = new Intent(getApplicationContext(), GetDirector.class);
					intent.putExtra("chosen", info);
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
}
