package ie.simo.movies.domain.award.host;

import ie.simo.movies.domain.award.Award;
import ie.simo.movies.domain.award.AwardTypeNotFoundException;


public class HostFactory {
	
	public static Host getInstance(Award award) throws AwardTypeNotFoundException
	{	
		String type = award.getAwardType();
		
		if(type.equals(Award.ACTOR))
		{
			return new ActorHost(award);
		}
		else if(type.equals(Award.DIRECTOR))
		{
			return new DirectorHost(award);
		}
		else if(type.equals(Award.SFX))
		{
			return new SFXHost(award);
		}
		else if(type.equals(Award.SCRIPT))
		{
			return new ScriptHost(award);
		}
		else if(type.equals(Award.SOUND))
		{
			return new SoundHost(award);
		}
		else if(type.equals(Award.OTHER))
		{		
			return new OtherHost(award);
		}
		
		throw new AwardTypeNotFoundException("no award found for type " + type);
	}
}
