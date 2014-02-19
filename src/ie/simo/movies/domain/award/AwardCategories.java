package ie.simo.movies.domain.award;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Simon Wielens on 28/07/13.
 */
public class AwardCategories {
	
	public static final int TOTAL_AWARDS = 11;
	
    //Good awards
    private static Award [] goodAwards = {
	    new Award("Best Picture", Award.SCRIPT),
	    new Award("Best Original Screenplay", Award.SCRIPT),
	    new Award("Best Actor in a Leading Role", Award.ACTOR),
	    new Award("Best Actor in a Supporting Role", Award.ACTOR),
	    new Award("Best Cinematography", Award.OTHER),
	    new Award("Best Director", Award.DIRECTOR),
	    new Award("Best Film Editing", Award.OTHER),
	    new Award("Best Production Design", Award.OTHER),
	    new Award("Best Costume Design", Award.OTHER),
	    new Award("Best Makeup and Hairstyling", Award.OTHER),
	    //new Award("Best Original Score", Award.SOUND),
	    //new Award("Best Sound Editing", Award.SOUND),
	    //new Award("Best Sound Mixing", Award.SOUND),
	    new Award("Best Visual Effects", Award.SFX)
	    };

    Random r = new Random();

    public Award award(){
        return goodAwards[r.nextInt(goodAwards.length)];
    }

	public static List<Award> getMyAwards(int nominations) {
		Set<Award> mySet = new HashSet<Award>();
		List<Award> toReturn = new ArrayList<Award>();
		mySet.addAll(Arrays.asList(goodAwards));
		while(nominations > 0){
			toReturn.add(getRandomElement(mySet));
			nominations--;
		}
		assert(nominations == toReturn.size());
		return toReturn;
	}
	
	public static List<Award> getMyAwardsNoSFX(int nominations) {
		Set<Award> mySet = new HashSet<Award>();
		List<Award> toReturn = new ArrayList<Award>();
		mySet.addAll(Arrays.asList(goodAwards));
		while(nominations > 0){
			Award a = getRandomElement(mySet);
			while(a.getAwardType().equals(Award.SFX))
			{
				a = getRandomElement(mySet);
			}
			toReturn.add(a);
			nominations--;
		}
		assert(nominations == toReturn.size());
		return toReturn;
	}

	private static Award getRandomElement(Set<Award> mySet) {
		int chosenIndex = new Random().nextInt(mySet.size());
		int index = 0;
		for(Award award : mySet)
		{
			if(chosenIndex == index){
				return award;
			}
			else{
				index++;
			}
		}
		//MMLogger.wtf("this should never happen", "Statuette missing, Lindsay Lohan's purse looks pretty full...");
		return new Award("Best Missing Award", "wtf");
	}

}
