package ie.simo.movies.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class NewPitchFilm extends ActivityWithMenu implements OnClickListener, OnGestureListener {

    protected GestureDetector gestureScanner;

    private static final int SWIPE_MIN_DISTANCE = 120;

    private static final int SWIPE_MAX_OFF_PATH = 250;

    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private ViewFlipper vf;

    private Animation animFlipInNext,animFlipOutNext, animFlipInPrevious, animFlipOutPrevious;
	
	private Button next;
	private Button previous;
	private ImageView execPic;
	private Button cancel;
	private TextView offer;
	private Button rework;
	private Button choose;
	private Random random = new Random();
	
	private List<Offer> offers;
	
	private TextView budgetView;
	private TextView compName;
	private DistributorDbAdapter db;
	private ArrayList<Distributor> distributorList = new ArrayList<Distributor>();
	
	private List<Integer> images = Arrays.asList(new Integer[]{R.drawable.suits1, R.drawable.suits2});
	
	private void findAllViewsById() {
		next = (Button) this.findViewById(R.id.nextproducer);
		previous = (Button) this.findViewById(R.id.previousproducer);
		execPic = (ImageView) this.findViewById(R.id.producerpic);
		offer = (TextView) this.findViewById(R.id.producercomment);
		rework = (Button) this.findViewById(R.id.reworkscript);
		choose = (Button) this.findViewById(R.id.chooseproducer);
		budgetView = (TextView) this.findViewById(R.id.budgetValue);
		compName = (TextView)this.findViewById(R.id.companyName);
		
        vf=(ViewFlipper)findViewById(R.id.ViewFlipper01);

        animFlipInNext = AnimationUtils.loadAnimation(this, R.anim.flipinnext);
        animFlipOutNext = AnimationUtils.loadAnimation(this, R.anim.flipoutnext);
        animFlipInPrevious = AnimationUtils.loadAnimation(this, R.anim.flipinprevious);
        animFlipOutPrevious = AnimationUtils.loadAnimation(this, R.anim.flipoutprevious);
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pitch2);
		
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		Collections.shuffle(images);
		findAllViewsById();
		setTitleBar();
		
		fetchDistributors();
		getOffers();
		
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
	private void getOffers() {
		assert(null != distributorList);
		
		//TODO increase to 3 when have more images
		for(int i = 0; i< 2; i++){
			Distributor d = distributorList.get(i);
			int money = d.makeOffer(getPc().getCurrentProject().getGenre(), getPc().getCurrentProject().getRatingDetails());
			
			Offer offer = new Offer(d, money, getOfferText(money), images.get(i).intValue());
			offers.add(offer);
		}
		//TODO add self funded
		
	}
	
	
	private void showOffers(){
		//TODO implement
	}
	


	/**
	 * Get unordered collection of distributors
	 */
	public void fetchDistributors() {
		getDistributorList().clear();
		db = new DistributorDbAdapter(this);
		db.open();
		Cursor c = db.selectAll();
		if(c.getCount() > 0) {
			c.moveToFirst();
			while(!c.isAfterLast()){
				Distributor d  = new Distributor();
				d.setName(c.getString(c.getColumnIndex(DBConsts.Distributor.name)));
				d.setDescription(c.getString(c.getColumnIndex(DBConsts.Distributor.desc)));
				getDistributorList().add(d);
				c.moveToNext();
			}
		}
		c.close();
		db.close();
		Collections.shuffle(getDistributorList());
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


	public ArrayList<Distributor> getDistributorList() {
		return distributorList;
	}


	public void setDistributorList(ArrayList<Distributor> distributorList) {
		this.distributorList = distributorList;
	}
	
	public NewPitchFilm(){
		super();
	}
	
	class Offer{
		private Distributor distributor;
		private CharSequence offerText;
		private int offerValue;
		private int pic;
		
		public Offer(){}
		
		public Offer(Distributor distributor, int offerValue, CharSequence offerText, int pic){
			this.setDistributor(distributor);
			this.setOfferText(offerText);
			this.setOfferValue(offerValue);
			this.pic = pic;
		}

		public Distributor getDistributor() {
			return distributor;
		}

		public void setDistributor(Distributor distributor) {
			this.distributor = distributor;
		}

		public CharSequence getOfferText() {
			return offerText;
		}

		public void setOfferText(CharSequence offerText) {
			this.offerText = offerText;
		}

		public int getOfferValue() {
			return offerValue;
		}

		public void setOfferValue(int offerValue) {
			this.offerValue = offerValue;
		}
		
		public int getPic(){
			return pic;
		}
		
		public void setPic(int pic){
			this.pic = pic;
		}
	}
	
	
	
}
