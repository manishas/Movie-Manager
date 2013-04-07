package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.domain.ProductionCompany;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static ie.simo.movies.util.Consts.COMPANY;

public class OwnFilmActivity extends ActivityWithMenu {
	
	private EditText text;
	private Spinner spinner;
	private Button director;
	private MovieInfo info;
	private TextView budgetView;
	private TextView compName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makeownfilm);
		
		Intent i = getIntent();
		
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
		
		setTitleBar();
		
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
					getPc().setCurrentProject(info);
					Intent intent = new Intent(getApplicationContext(), PlotSummary.class);
					intent.putExtra(COMPANY, getPc());
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
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView) findViewById(R.id.companyName);
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}  
}
