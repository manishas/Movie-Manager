package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;
import static ie.simo.movies.util.Consts.NOMINATIONS;

import java.util.List;

import ie.simo.movies.R;
import ie.simo.movies.domain.AwardCategories;
import ie.simo.movies.domain.ProductionCompany;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class OscarsActivity extends ActivityWithMenu {

    private TextView awardName;
    private TextView host;
    private TextView nominees;
    private TextView comment;
    private TextView continueMsg;
    
    private List<String> awardList;

    private int awardsWon = 0;
    private int stageCount = 0;
    private int nominations = 0;

    private AwardCategories categories = new AwardCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscars);
        
        Intent i = getIntent();
		setPc((ProductionCompany) i.getSerializableExtra(COMPANY));
		nominations = i.getIntExtra(NOMINATIONS, 0);
        awardList = getAwardList();
        getAllViews();

        setAwardName();

        showAward();

    }

    private List<String> getAwardList() {
		//TODO get specific awards based on movie
		return categories.getMyAwards(nominations);
	}

	private void showHostIntro() {
        host.setVisibility(View.VISIBLE);
    };

    private void showNominees(){
    	
    	nominees.setText(getNominees());
        nominees.setVisibility(View.VISIBLE);
    };

    private CharSequence getNominees() {
    	//TODO should be a list of nominees to help choosing.
    	
		// TODO Auto-generated method stub
		return "";
	}

	private void andTheWinnerIs(){
        host.setText("And the winner is...");
        nominees.setVisibility(View.INVISIBLE);
    };

    private void announceWinner(){
        nominees.setText("Jimmy from your Movie!!!");

        nominees.setVisibility(View.VISIBLE);
    };

    private void commentToUser(){
        comment.setText("Congratulations, you won!!");

        comment.setVisibility(View.VISIBLE);
    };

    private void setAwardName() {
        awardName.setText(awardList.get(nominations - 1));
    }

    private void getAllViews(){
        awardName = (TextView) this.findViewById(R.id.awardName);
        host = (TextView) this.findViewById(R.id.host);
        nominees = (TextView) this.findViewById(R.id.nominees);
        comment = (TextView) this.findViewById(R.id.comment);
        continueMsg = (TextView) this.findViewById(R.id.continueMsg);
    }

    public void showAward(){
        host.setVisibility(View.INVISIBLE);
        nominees.setVisibility(View.INVISIBLE);
        comment.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        super.onTouchEvent(e);
        if(true){ //if applicable
        	nextStage();
        }
        return false;
    };

    //TODO refactor this properly
    private void nextStage() {
        stageCount++;
        if(stageCount == 1){
            showHostIntro();
        }
        else if(stageCount == 2){
            showNominees();
        }
        else if(stageCount == 3){
            andTheWinnerIs();
        }
        else if(stageCount == 4){
            announceWinner();
        }
        else if(stageCount == 5){
            commentToUser();
        }
        else if(stageCount == 6){
            if(nominations > 0){
                stageCount = 0;
                nominations--;
            }
            else{
                //show summary and button to continue
            }
        }

    };
}
