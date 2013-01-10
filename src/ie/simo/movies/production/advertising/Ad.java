package ie.simo.movies.production.advertising;

public abstract class Ad {
	private int cost;
	private int effect;
	
	public Ad(){}
	
	public Ad(int cost, int effect){
		this.cost = cost;
		this.effect = effect;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getEffect() {
		return effect;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
}
