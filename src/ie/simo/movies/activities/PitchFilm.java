package ie.simo.movies.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import ie.simo.movies.R;
import ie.simo.movies.dao.MyDistributorDbAdapter;
import ie.simo.movies.domain.Distributor;
import ie.simo.movies.domain.ProductionCompany;
import ie.simo.movies.util.DBConsts;
import ie.simo.movies.util.MMLogger;
import static ie.simo.movies.util.Consts.*;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class PitchFilm extends ActivityWithMenu implements OnGestureListener {

    protected GestureDetector gestureScanner;
    private static final int SWIPE_MIN_DISTANCE = 120;
    //private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper vf;
    private static final int NUMBER_OF_PICS = 3;
    private Animation animFlipInNext,animFlipOutNext, animFlipInPrevious, animFlipOutPrevious;
    private int ARRAY_LAST_INDEX = 2; 
	private ImageView producerpic1, producerpic2, producerpic3;
	private Button cancel;
	private TextView producercomment1, producercomment2, producercomment3;
	//private Button rework;
	private Button choose;
	//private Random random = RandomNumberProvider.getInstance();
	private int currentIndex = 0;	
	private ArrayList<Offer> offers = new ArrayList<Offer>();
	private TextView budgetView;
	private TextView compName;
	private MyDistributorDbAdapter db;
	private ArrayList<Distributor> distributorList = new ArrayList<Distributor>();
	private List<Integer> images = Arrays.asList(new Integer[]{R.drawable.char1, R.drawable.char2, R.drawable.char3, R.drawable.char4,  R.drawable.char5});

	private void findAllViewsById() {
		
		producerpic1 = (ImageView) this.findViewById(R.id.pitchimage1);
		producerpic2 = (ImageView) this.findViewById(R.id.pitchimage2);
		producerpic3 = (ImageView) this.findViewById(R.id.pitchimage3);

		producercomment1 = (TextView) this.findViewById(R.id.pitchtext1);
		producercomment2 = (TextView) this.findViewById(R.id.pitchtext2);
		producercomment3 = (TextView) this.findViewById(R.id.pitchtext3);
		
		cancel = (Button) this.findViewById(R.id.reworkscript);
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
		gestureScanner = new GestureDetector(this);
		
		Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		Collections.shuffle(images);
		findAllViewsById();
		setTitleBar();
		
		fetchDistributors();
		getOffers();
		showOffers();
		
		cancel.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra(COMPANY, getPc());
				i.setClass(getApplicationContext(), MakeFilmActivity.class);
				startActivity(i);
			}
		});
		
		choose.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Offer o = offers.get(currentIndex);
				Distributor d = o.getDistributor();
				Intent intent = new Intent(getApplicationContext(), GetDirector.class);
        		MMLogger.v(getLocalClassName(), offerString(o));

        		MMLogger.d("budget on leaving pitch", "$"+getPc().getBudget());
        		getPc().getCurrentProject().setDistributor(d);
        		intent.putExtra(COMPANY, getPc());
        		intent.putExtra(OFFER, o.getOfferValue());
        		startActivity(intent);
			}
		});
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		MMLogger.d("budget re-starting", "$"+getPc().getBudget());
	}
	
	private void getOffers() {
		assert(null != distributorList);
		//TODO increase to 3 when have more images
		for(int i = 0; i < NUMBER_OF_PICS; i++){
			Distributor d = distributorList.get(i);
			int money = d.makeOffer(getPc().getCurrentProject().getGenre(), getPc().getCurrentProject().getRatingDetails());
			
			Offer offer = new Offer(d, money, getOfferText(money), images.get(i).intValue());
			offers.add(offer);
		}
		//TODO add self funded option for pitch film
	}
	
	
	private void showOffers(){
		
		int width = (getWindowManager().getDefaultDisplay().getWidth() / 4) * 3;
		//TODO refactor with array...
		producerpic1.setImageResource(offers.get(0).getPic());
		producerpic1.getLayoutParams().width = width;
		producerpic1.getLayoutParams().height = width;
		//producerpic1.getLayoutParams().gravity = Gravity.CENTER;
		producercomment1.setText(Html.fromHtml(offerString(offers.get(0))));
		
		producerpic2.setImageResource(offers.get(1).getPic());
		producerpic2.getLayoutParams().width = width;
		producerpic2.getLayoutParams().height = width;
		producercomment2.setText(Html.fromHtml(offerString(offers.get(1))));

		producerpic3.setImageResource(offers.get(2).getPic());
		producerpic3.getLayoutParams().width = width;
		producerpic3.getLayoutParams().height = width;
		producercomment3.setText(Html.fromHtml(offerString(offers.get(2))));

	}
	
	private String offerString(Offer offer){
		return String.format(Locale.UK, "<b>%s: $%dM</b><br/>%s", offer.getDistributor().getName(), offer.getOfferValue(), offer.getOfferText());
	}

	/**
	 * Get unordered collection of distributors
	 */
	public void fetchDistributors() {
		getDistributorList().clear();
		db = new MyDistributorDbAdapter(this);
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
	
	public PitchFilm(){
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
	
    @Override
    public boolean onTouchEvent(MotionEvent me){
        return gestureScanner.onTouchEvent(me);
    }

    public boolean onDown(MotionEvent e){
        return true;
    }

    public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){

        try {

            if(e1.getX() > e2.getX() && Math.abs(e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                vf.setInAnimation(animFlipInPrevious);
                vf.setOutAnimation(animFlipOutPrevious);
                vf.showPrevious();
                currentIndex--;
                if(currentIndex < 0) currentIndex = ARRAY_LAST_INDEX;
                
                MMLogger.v(getLocalClassName(), "current index: "+ currentIndex);

            }else if (e1.getX() < e2.getX() && e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                vf.setInAnimation(animFlipInNext);
                vf.setOutAnimation(animFlipOutNext);
                vf.showNext();
                
                currentIndex ++;
                if(currentIndex > ARRAY_LAST_INDEX) currentIndex = 0;
                
                MMLogger.v(getLocalClassName(), "current index: "+ currentIndex);
                
            }

        } catch (Exception e) {
        	//TODO handle this exception
        }
        return true;
    }

    public boolean onScroll(MotionEvent e1,MotionEvent e2,float distanceX,float distanceY){
        return true;
    }
    
    public void onLongPress(MotionEvent e){}

    public void onShowPress(MotionEvent e){}

    public boolean onSingleTapUp(MotionEvent e){ return true;}
}
