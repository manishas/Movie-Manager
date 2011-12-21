package ie.simo.movies;

import ie.simo.movies.domain.MovieMetadata;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MakeFilmActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button script1;
	private Button script2;
	private Button script3;
	private Button makeOwn;
	private MovieMetadata meta1;
	private MovieMetadata meta2;
	private MovieMetadata meta3;
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

		makeOwn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MakeFilmActivity.this.generateScripts();
				
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
		meta1 = new MovieMetadata();
		meta2 = new MovieMetadata();
		meta3 = new MovieMetadata();
		changeButtonText(script1, meta1);
		changeButtonText(script2, meta2);
		changeButtonText(script3, meta3);
	}

	private void findAllViewsById() {
		script1 = (Button) this.findViewById(R.id.script1);
		script2 = (Button) this.findViewById(R.id.script2);
		script3 = (Button) this.findViewById(R.id.script3);

		makeOwn = (Button) this.findViewById(R.id.makeOwn);
	}

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	private void changeButtonText(Button myButton, MovieMetadata meta) {
		meta.newScript();
		myButton.setText(meta.toButtonText());
	}

}