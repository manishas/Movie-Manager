package ie.simo.movies.ui.component;

import ie.simo.movies.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PitchFilmRow extends LinearLayout {
	
	private TextView distributorName;
	private Button distributorOffer;
	private TextView distributorComment;

	public PitchFilmRow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pitchfilmrow, this);
        
        setDistributorName((TextView)findViewById(R.id.distname));
		setDistributorOffer((Button)findViewById(R.id.distoffer));
		setDistributorComment((TextView)findViewById(R.id.distcomment));
		
		distributorOffer.setMinEms(4);
		distributorName.setMinEms(7);
		distributorComment.setMinEms(4);
    }
	
	/* Getters & Setters */

	public TextView getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(TextView distributorName) {
		this.distributorName = distributorName;
	}

	public Button getDistributorOffer() {
		return distributorOffer;
	}

	public void setDistributorOffer(Button distributorOffer) {
		this.distributorOffer = distributorOffer;
	}

	public TextView getDistributorComment() {
		return distributorComment;
	}

	public void setDistributorComment(TextView distributorComment) {
		this.distributorComment = distributorComment;
	}

}
