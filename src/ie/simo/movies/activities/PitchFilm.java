package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.DistributorDBAdapter;
import ie.simo.movies.domain.Distributor;
import ie.simo.movies.util.DBConsts;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PitchFilm extends Activity {
	
	private Button cancel;
	private TableLayout table;
	
	private DistributorDBAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch);
		
		findAllViewsById();
		
		fillTable();
		
	}

	private void fillTable() {
		db = new DistributorDBAdapter(this);
		db.open();
		Cursor c = db.fetchAllDistributors();
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
		tbr.addView(tv);
		tbr.addView(btn);
		PitchFilm.this.table.addView(tbr);
	}
	
}
