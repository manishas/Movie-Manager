package ie.simo.movies.censor.factory;

import ie.simo.movies.censor.Censor;
import ie.simo.movies.censor.IrishCensor;
import ie.simo.movies.censor.UkCensor;

public class CensorFactory {
	
	public Censor getCensor(String country){
		if("ie".equals(country)){
			return new IrishCensor();
		}
		else if("uk".equals(country)){
			return new UkCensor();
		}
		else{
			return new UsaCensor();
		}
	}

}
