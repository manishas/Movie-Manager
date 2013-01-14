package ie.simo.movies.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

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

public class NewPitchFilm extends ActivityWithMenu {
	
	private Button next;
	private Button previous;
	private ImageView execPic;
	private Button cancel;
	private TextView offer;
	private Button rework;
	private Button choose;
	private Random random = new Random();
	
	private TextView budgetView;
	private TextView compName;
	private DistributorDbAdapter db;
	private ArrayList<Distributor> distributorList = new ArrayList<Distributor>();
	
	private int[] images = new int[]{R.drawable.suits1, R.drawable.suits2};
	
	private void findAllViewsById() {
		next = (Button) this.findViewById(R.id.nextproducer);
		previous = (Button) this.findViewById(R.id.previousproducer);
		execPic = (ImageView) this.findViewById(R.id.producerpic);
		offer = (TextView) this.findViewById(R.id.producercomment);
		rework = (Button) this.findViewById(R.id.reworkscript);
		choose = (Button) this.findViewById(R.id.chooseproducer);
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch2);
		
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		
		findAllViewsById();
		setTitleBar();
		
		fetchDistributors();
		
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
	 * Get unordered collection of distributors
	 */
	private void fetchDistributors() {
		db = new DistributorDbAdapter(this);
		db.open();
		Cursor c = db.selectAll();
		if(c.getCount() > 0) {
			c.moveToFirst();
			while(!c.isAfterLast()){
				Distributor d  = new Distributor();
				d.setName(c.getString(c.getColumnIndex(DBConsts.Distributor.name)));
				d.setDescription(c.getString(c.getColumnIndex(DBConsts.Distributor.desc)));
				distributorList.add(d);
				c.moveToNext();
			}
		}
		c.close();
		db.close();
		Collections.shuffle(distributorList);
	}
	
	private Distributor getADistributor() throws NullPointerException {
		if(null == distributorList || distributorList.size() == 0){
			throw new NullPointerException();
		}
		Distributor d = distributorList.get(0);
		distributorList.remove(0);
		return d;
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
