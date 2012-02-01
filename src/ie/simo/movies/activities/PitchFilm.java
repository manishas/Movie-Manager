package ie.simo.movies.activities;

import java.util.Random;

import ie.simo.movies.R;
import ie.simo.movies.dao.DistributorDBAdapter;
import ie.simo.movies.domain.Distributor;
import ie.simo.movies.util.DBConsts;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PitchFilm extends Activity {
	
	private Button cancel;
	private TableLayout table;
	Random generator;
	
	private DistributorDBAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch);
		generator = new Random();
		findAllViewsById();
		
		fillTable();
		
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
		
	}
	
	private void findAllViewsById() {
		table = (TableLayout) this.findViewById(R.id.pitchtable);
		cancel = (Button) this.findViewById(R.id.pitchcancel);
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
		        	int offer = generator.nextInt(40);
					button.setText("$" + offer+ "M");
					button.setTag(tag.substring(1));
				}
	        	else{
	        		Distributor d = new Distributor();
	        		d.setName(tag);
	        		Intent intent = new Intent(getApplicationContext(), GetDirector.class);
	        		
	        		intent.putExtra("ie.simo.movies.distributor", d);
	        		intent.putExtra("ie.simo.movies.budget", button.getText());
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
	
}
