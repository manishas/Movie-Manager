package ie.simo.movies.domain.award;


public class AwardTypeNotFoundException extends Exception {

	private static final long serialVersionUID = 6097118365047935388L;

	private String message;
	
	public AwardTypeNotFoundException(String msg) {
		message =  msg;
	}
	
	@Override
	public String getMessage(){
		return message;
	}

}
