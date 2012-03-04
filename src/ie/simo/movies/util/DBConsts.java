package ie.simo.movies.util;

public class DBConsts {
	
	public static class Actor{
		public static final String id = "_id";
		public static final String name = "actor_name";
		public static final String hire_cost = "actor_hire_cost";
		public static final String reputation = "actor_reputation";	
	}
	
	public static class Director{
		public static final String id = "_id";
		public static final String name = "director_name";
		public static final String hire_cost = "director_hire_cost";
		public static final String reputation = "director_reputation";	
	}
	
	public static class Movie{
		public static final String id = "_id";
		public static final String name = "movie_name";
		public static final String tagline = "tagline";
		public static final String desc = "description";
		public static final String genre = "genre_id";
		public static final String earnings = "earnings";
		public static final String cost = "cost";
		public static final String producer = "producer_id";
		public static final String director = "director_id";
		public static final String distributor = "distributor_id";
	}
	
	public static class Distributor{
		public static final String id = "_id"; 
		public static final String desc = "distributor_desc";
		public static final String name = "distributor_name";
	}

}
