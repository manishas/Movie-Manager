package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.DistributorDBAdapter;
import ie.simo.movies.domain.Distributor;
import ie.simo.movies.domain.MovieInfo;
import ie.simo.movies.util.DBConsts;
import static ie.simo.movies.util.Consts.*;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PitchFilm extends Activity {
	
	private Button cancel;
	private TableLayout table;
	private TextView budgetView;
	
	private int budget;
	
	private MovieInfo chosenMovie;
	private Random generator;
	
	private DistributorDBAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch);
		generator = new Random();
		findAllViewsById();
		
		Intent i = getIntent();
		chosenMovie = (MovieInfo) i.getSerializableExtra(CHOSEN);
		budget = i.getIntExtra(BUDGET, 0);
		
		budgetView.setText("$" + budget + "M");
		
		fillTable();
		
		cancel.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(BUDGET, budget);
				i.setClass(getApplicationContext(), MakeFilmActivity.class);
				startActivity(i);
			}
			
		});
	}

	private void fillTable() {
		db = new DistributorDBAdapter(this);
		db.open();
		Cursor c = db.fetchAllDistributors();
		c.moveToFirst();
		while(!c.isAfterLast()){
			Distributor d  = new Distributor();
			d.setName(c.getString(c.getColumnIndex(DBConsts.Distributor.name)));
			d.setDescription(c.getString(c.getColumnIndex(DBConsts.Distributor.desc)));
			addNewRow(d);
			c.moveToNext();
		}
		c.close();
		db.close();		
	}
	
	private void findAllViewsById() {
		table = (TableLayout) this.findViewById(R.id.pitchtable);
		cancel = (Button) this.findViewById(R.id.pitchcancel);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
	}
	
	
	private void addNewRow(Distributor d){
		TableRow tbr = new TableRow(this);
		TextView tv = new TextView(this);
		tv.setText(d.getName());
		Button btn = new Button(this);
		btn.setText(R.string.ask);
		btn.setTag("!"+d.getName());
		btn.setGravity(Gravity.FILL_HORIZONTAL);
		btn.setOnClickListener(OnClickDoSomething(btn));
		tbr.addView(tv);
		tbr.addView(btn);
		PitchFilm.this.table.addView(tbr);
	}
	
	private View.OnClickListener OnClickDoSomething(final Button button)  {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	String tag = (String) button.getTag();
	        	if(tag.charAt(0)=='!'){
		        	int offer = new Distributor().makeOffer(chosenMovie.getGenre(), chosenMovie.getRatingDetails());
					button.setText("$" + offer+ "M");
					button.setTag(tag.substring(1));
					toastOffer((String)button.getTag(),offer);
				}
	        	else{
	        		Distributor d = new Distributor();
	        		d.setName(tag);
	        		Intent intent = new Intent(getApplicationContext(), GetDirector.class);
	        		
	        		intent.putExtra(DISTRIBUTOR, d);
	        		intent.putExtra(BUDGET, budget + convertButtonLabelToInt(button.getText().toString()));
	        		intent.putExtra(CHOSEN, chosenMovie);
	        		startActivity(intent);
	        	}
	        }
	    };
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
    	
		startActivity(intent);
		
        return false;
    }
    
    private int convertButtonLabelToInt(String label){
    	label = label.substring(1,label.length()-1);
    	Log.v("offer", label+"");
    	return Integer.parseInt(label);
    }
    
    private void toastOffer(String distributorName, int offer){
    	Toast toast = Toast.makeText(getApplicationContext(),
    			   getOfferText(distributorName, offer), Toast.LENGTH_SHORT);
    			toast.setGravity(Gravity.CENTER, 0, 0);
    			LinearLayout toastView = (LinearLayout) toast.getView();
    			ImageView imageCodeProject = new ImageView(getApplicationContext());
    			imageCodeProject.setImageResource(R.drawable.suits);
    			toastView.addView(imageCodeProject, 0);
    			toast.show();
    	
    }

	private CharSequence getOfferText(String distName, int offer) {
		
		String text = "";
		if(offer == 0){
			text = getString(R.string.noOffer1);
		}
		else if(offer < 3){
			text = getString(R.string.noOffer2);
		}
		else if (offer < 10){
			text = getString(R.string.smallOffer);
		}
		else if (offer < 20){
			text = getString(R.string.mediumOffer);
		}
		else if (offer < 30){
			text = getString(R.string.goodOffer);
		}
		else{
			text = getString(R.string.bestOffer);
		}
				
		return distName + ": " + String.format(text, offer);
		
	}
	
}
