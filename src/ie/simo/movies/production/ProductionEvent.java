package ie.simo.movies.production;

public class ProductionEvent{
	//PR
	private int goodBuzz;
	private int badBuzz;
	//speed up or slow down production
	private int productionInfluence;
	//text desc of event
	private String description;
	
	public ProductionEvent(int goodBuzz, int badBuzz, int influence, String description){
		this.setGoodBuzz(goodBuzz);
		this.setBadBuzz(badBuzz);
		this.setProductionInfluence(influence);
		this.setDescription(description);
	}

	public int getGoodBuzz() {
		return goodBuzz;
	}

	public void setGoodBuzz(int goodBuzz) {
		this.goodBuzz = goodBuzz;
	}

	public int getBadBuzz() {
		return badBuzz;
	}

	public void setBadBuzz(int badBuzz) {
		this.badBuzz = badBuzz;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProductionInfluence() {
		return productionInfluence;
	}

	public void setProductionInfluence(int productionInfluence) {
		this.productionInfluence = productionInfluence;
	}
}
