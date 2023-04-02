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

import com.videoclub.pojo.Movie;

@Path("movies")
public class MovieResource {
	protected static final Logger logger = LogManager.getLogger();
	private static List<Movie> movies = new ArrayList<>();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMovies(){
		logger.info(movies);
		return movies;
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(Movie movie) {
		
		movies.add(movie);
		logger.info(movie);
		return Response.ok(new Movie(movie.getTitle())).build();
		
	}
	
	@DELETE
    @Path("/{title}")
    public Response deleteUser(@PathParam("title") String title) {
    	boolean hasTheMovie = false;
    	for(int i = 0; i<movies.size(); i++) {
    		if(title == movies.get(i).getTitle()) {
    			movies.remove(i);
    			hasTheMovie = true;
    		}
    	}
        if (hasTheMovie) {
            logger.info("Deleting movie {} ...", title);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
	
		
}
