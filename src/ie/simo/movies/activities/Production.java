package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Production extends ActivityWithMenu {
	
	private ProgressBar productionProgress;
	private TextView progressText;
	private ListView productionNews;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.production);
	}
}
