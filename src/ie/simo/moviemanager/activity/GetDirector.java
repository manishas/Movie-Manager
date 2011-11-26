package ie.simo.moviemanager.activity;

import ie.simo.moviemanager.R;
import ie.simo.moviemanager.domain.MovieMetadata;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GetDirector extends Activity {
	
	private MovieMetadata chosenFilm;
	private TextView chosen;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.director);
	        
	        findAllViewsById();
	        chosenFilm = (MovieMetadata)savedInstanceState.getSerializable("chosen");
	        chosen.setText(chosenFilm.toButtonText());
	 }
	 
	 private void findAllViewsById(){
	    	chosen = (TextView)this.findViewById(R.id.chosen);   
	    }
}
