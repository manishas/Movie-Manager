package ie.simo.movies;

import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
		ArrayAdapter<Genre> adapter = new ArrayAdapter<Genre>(this, R.layout.makeownfilm, genres);
		spinner.setAdapter(adapter);
		
		director.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				info.setTitle(text.getText().toString());
				info.setGenre((Genre)spinner.getSelectedItem());
				Intent intent = new Intent(getApplicationContext(), GetDirector.class);
				intent.putExtra("chosen", info);
			}
		});
		
	}
	
	private void findAllViewsById() {
		text = (EditText) this.findViewById(R.id.nametextfield);
		spinner = (Spinner) this.findViewById(R.id.genrespinner);
		director = (Button) this.findViewById(R.id.getdirectorbutton);
	}
}
