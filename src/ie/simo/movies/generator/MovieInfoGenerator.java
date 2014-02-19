package ie.simo.movies.generator;

import android.content.Context;
import ie.simo.movies.dao.ActorDbAdapter;
import ie.simo.movies.dao.DirectorDbAdapter;
import ie.simo.movies.dao.SfxDbAdapter;
import ie.simo.movies.domain.Actor;
import ie.simo.movies.domain.Director;
import ie.simo.movies.domain.Genre;
import ie.simo.movies.domain.MovieInfo;

public class MovieInfoGenerator {
	
	//TODO cache actors and directors
	
	private Context ctx;
	private FilmNameGenerator filmNameGenerator = new FilmNameGenerator();
	
	public MovieInfoGenerator(Context c){
		this.ctx = c;
	}
	
	public MovieInfo createNewMovie(){
		MovieInfo info = new MovieInfo();
		info.setTitle(filmNameGenerator.newFilmTitle());
		info.setDirector(getRandomDirector());
		info.getCast().add(getRandomActor());
		info.getCast().add(getRandomActor());
		info.setGenre(Genre.getRandomGenre());
		info.setSpecialEffectsCompany(getRandomSFXCompany());
		
		return info;
	}

	private String getRandomSFXCompany() {
		SfxDbAdapter dao = new SfxDbAdapter(ctx);
		dao.open();
		String sfxCo = dao.getRandomCompany();
		dao.close();
		return sfxCo;
	}

	private Actor getRandomActor() {
		ActorDbAdapter dao = new ActorDbAdapter(ctx);
		dao.open();
		Actor a = dao.getRandomActor();
		dao.close();
		return a;
	}

	private Director getRandomDirector() {
		DirectorDbAdapter dao = new DirectorDbAdapter(ctx);
		dao.open();
		Director d = dao.getRandomDirector();
		dao.close();
		return d;
	}

}
