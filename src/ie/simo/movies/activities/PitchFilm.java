package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.DistributorDbAdapter;
import ie.simo.movies.domain.Distributor;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.ui.component.PitchFilmRow;
import ie.simo.movies.util.DBConsts;
import static ie.simo.movies.util.Consts.*;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PitchFilm extends ActivityWithMenu {
	
	private Button cancel;
	private LinearLayout table;
	private TextView budgetView;
	private TextView compName;
	private DistributorDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch);
		
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
		setTitleBar();
		fillTable();
		
		cancel.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(COMPANY, getPc());
				i.setClass(getApplicationContext(), MakeFilmActivity.class);
				startActivity(i);
			}
			
		});
	}
	/**
	 * Get Distributor data and add rows
	 */
	private void fillTable() {
		db = new DistributorDbAdapter(this);
		db.open();
		Cursor c = db.selectAll();
		if(c.getCount() > 0) {
			c.moveToFirst();
			while(!c.isAfterLast()){
				Distributor d  = new Distributor();
				d.setName(c.getString(c.getColumnIndex(DBConsts.Distributor.name)));
				d.setDescription(c.getString(c.getColumnIndex(DBConsts.Distributor.desc)));
				addNewRow(d);
				c.moveToNext();
			}
		}
		c.close();
		db.close();		
	}
	
	private void findAllViewsById() {
		table = (LinearLayout) this.findViewById(R.id.pitchtable);
		cancel = (Button) this.findViewById(R.id.pitchcancel);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
	}
	
	
	private void addNewRow(Distributor d){
		
		PitchFilmRow row = new PitchFilmRow(getApplicationContext());
        
		row.getDistributorName().setText(d.getName());
		row.getDistributorOffer().setText(R.string.ask);
		row.getDistributorOffer().setTag("!"+d.getName());
		row.getDistributorOffer().setOnClickListener(OnClickDoSomething(row.getDistributorOffer()));
		
		table.addView(row);

	}
	
	private View.OnClickListener OnClickDoSomething(final Button button)  {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	String tag = (String) button.getTag();
	        	//hacky...
	        	if(tag.charAt(0)=='!'){
		        	int offer = new Distributor().makeOffer(getPc().getCurrentProject().getGenre(), getPc().getCurrentProject().getRatingDetails());
					button.setText("$" + offer+ "M");
					button.setTag(tag.substring(1));
					PitchFilmRow row = (PitchFilmRow) button.getParent();

					makeOffer(row, (String)button.getTag(),offer);
				}
	        	else{
	        		Distributor d = new Distributor();
	        		d.setName(tag);
	        		Intent intent = new Intent(getApplicationContext(), GetDirector.class);
	        		
	        		intent.putExtra(DISTRIBUTOR, d);
	        		getPc().setBudget(getPc().getBudget() + convertButtonLabelToInt(button.getText().toString()));
	        		intent.putExtra(COMPANY, getPc());
	        		startActivity(intent);
	        	}
	        }
	    };
	}
    
    private int convertButtonLabelToInt(String label){
    	label = label.substring(1,label.length()-1);
    	Log.v("offer", label+"");
    	return Integer.parseInt(label);
    }
    
    private void makeOffer(PitchFilmRow row, String distributorName, int offer){
    	row.getDistributorComment().setText(getOfferText(offer));
    }
    
	private CharSequence getOfferText(int offer) {
		
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
				
		return String.format(text, offer);
		
	}
	
	private void setTitleBar() {
		budgetView.setText("$"+getPc().getBudget()+"M");
		compName.setText(getPc().getName());
	}
	
}
