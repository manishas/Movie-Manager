package ie.simo.movies.generator.util;

public class KindOfExplicitPhraseProvider extends PhraseProvider {

	@Override
	public String getBadFilmReview(){
		return random(concat(badQualities, reallyBadQualities));
	}
	
	private String [] reallyBadQualities = {
			"...well,  all I can say is during the screening, several people projectile vomited.",
			"bad, there was a moment were I could see my soul leaving my body and flipping me off for putting it through that.",
			"like being be tied to a chair and forced to listen to Barry Manilow tunes while having my tonsils removed with a rusty spoon.",
			"reminded me of one time in korea, I ate a bee's nest, its all fun and games until a colony of bees wake up in their hydrochloric crypt.",
			"almost identical to wrestling a turkish truck driver",
			"seriously the strangest shit i have ever seen and i once saw a man eat his own face.",
			"very similar to having my toes chewed off by a bear",
			"like watching Adele and Rosie O'Donnell mud wrestle naked",
			"worse then drinking a tramps piss",
			"similar to cutting off the wings of a pigeon then feeding them to another pigeon, you're left wondering, 'is this good for any party involved here?'",
			"like dragging my face over broken glass",
			"like licking a taxi drivers ass crack",
			"comparable to rats fighting in a ditch over a streak of piss",
			"comparable to punching a dwarf for stealing your shoes",
			"comparable to taking every sharp from a needle exchange clinic and sticking them into your skin untill you resemble a hedgehog",
			"comparable to eating my own arm",
			"comparable to eating an egg and realising that it was actually a lightbulb and now your mouth is full of blood and broken glass.",
			"if vomit could vomit",
			"like watching my first child being born, and not in a nice metaphoric way, i mean that literally. It was awful, 8 hours of screaming."
	};
	
	private String[] concat(String [] first, String [] second) {
		   int aLen = first.length;
		   int bLen = second.length;
		   String[] result= new String[aLen+bLen];
		   System.arraycopy(first, 0, result, 0, aLen);
		   System.arraycopy(second, 0, result, aLen, bLen);
		   return result;
		}
	
}
