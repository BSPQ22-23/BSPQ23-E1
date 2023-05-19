package com.videoclub;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;

@Path("movies")
public class MovieResource {
	protected static final Logger logger = LogManager.getLogger();
	//private static List<Movie> movies;
	
	/** Function to retrieve movies from the database.
	 * @return List of movies.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMovies(){
		List<Movie> movies = MovieDAO.getInstance().getAll(Movie.class);
		logger.info(InternationalizationText.getString("retrieve_movies_db")+movies);
		return movies;
	}
	
	/**Function to add new movie to the database.
	 * @param movie Movie to add.
	 * @return OK response. 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(Movie movie) {
		MovieDAO.getInstance().save(movie);
		logger.info(movie.getTitle()+" "+InternationalizationText.getString("add_db_movie"));
		return Response.ok(new Movie(movie.getTitle())).build();
		
	}
	
	/**Function to delete a movie of the database.
	 * @param title Title of the movie.
	 * @return Status Response.
	 */
	@DELETE
    @Path("/{title}")
    public Response deleteMovie(@PathParam("title") String title) {
    	boolean hasTheMovie = false;
    	Movie movieToDelete = MovieDAO.getInstance().find(title, Movie.ColumnsNameMovie.title);
    	
    	if(movieToDelete!=null) hasTheMovie=true;
    	
        if (hasTheMovie) {
        	MovieDAO.getInstance().delete(movieToDelete);
            logger.info(InternationalizationText.getString("remove_db_movie")+title);
            return Response.status(Response.Status.OK).build();
        } else {
        	logger.info("No movies found with -"+title+"- title");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
	
		
}
