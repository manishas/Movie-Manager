package ie.simo.movies.domain;

import ie.simo.movies.util.RandomNumberProvider;

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
	
	public static final int TOTAL_AWARDS = 14;
	
    //Good awards
    String [] goodAwards = {
    "Best Picture",
    "Best Original Screenplay",
    "Best Actor in a Leading Role",
    "Best Actor in a Supporting Role",
    "Best Cinematography",
    "Best Director",
    "Best Film Editing",
    "Best Production Design",
    "Best Costume Design",
    "Best Makeup and Hairstyling",
    "Best Original Score",
    "Best Sound Editing",
    "Best Sound Mixing",
    "Best Visual Effects"};

    Random r = new Random();

    public String award(){
        return goodAwards[r.nextInt(goodAwards.length)];
    }

	public List<String> getMyAwards(int nominations) {
		Set<String> mySet = new HashSet<String>();
		List<String> toReturn = new ArrayList<String>();
		mySet.addAll(Arrays.asList(goodAwards));
		while(nominations > 0){
			toReturn.add(getRandomElement(mySet));
			nominations--;
		}
		assert(nominations == toReturn.size());
		return toReturn;
	}

	private String getRandomElement(Set<String> mySet) {
		int chosenIndex = RandomNumberProvider.getInstance().nextInt(mySet.size());
		int index = 0;
		for(String str : mySet)
		{
			System.out.println(str);
			if(chosenIndex == index){
				return str;
			}
			else{
				index++;
			}
		}
		return null;
	}

}
