package ie.simo.movies.handler;

import ie.simo.movies.production.ProductionEvent;

import java.util.List;

public class AdHandler {

	private List<ProductionEvent> events;
	
	public AdHandler(List<ProductionEvent> events)
	{
		this.events = events;
	}
}
