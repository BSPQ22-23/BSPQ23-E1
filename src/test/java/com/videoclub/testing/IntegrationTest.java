package com.videoclub.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class IntegrationTest {

private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
private static final String USERS_RESOURCE ="users";
private static final String RENTALS_RESOURCE ="rentals";
private static final String MOVIES_RESOURCE ="movies";
private int usercode;
private String movietitle;
private int Rentalid;
Date currentDate = new Date();
User usertest = new User("test",PasswordEncrypt.encryptPassword("test"), "test@gmail.com", "test", "test", typeUser.CLIENT );
Movie movietest = new Movie("test", "test", 100, 2021, "test", 15);
Rental rentaltest = new Rental(movietest, usertest,currentDate , currentDate);
	@Test
	public void UserListResource() {
		List<User> users= new ArrayList<>();
		 Client client = ClientBuilder.newClient();
	        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(USERS_RESOURCE)
	                .request(MediaType.APPLICATION_JSON)
	                .get();

	            // check that the response was HTTP OK
	            if (response.getStatusInfo().toEnum() == Status.OK)
	            {
	                // the response is a generic type (a List<User>)
	                GenericType<List<User>> listType = new GenericType<List<User>>(){};
	                 users = response.readEntity(listType);
	            }
	    List<User> users2 = UserDAO.getInstance().getAll();
	    
	    for(User u : users) {
	    	for(User u1: users2) {
	    		assertEquals(u, u1);
	    	}
	    	
	    }
	}
	
	@Test
	public void AddUserResource() {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		User usertest = new User("test",PasswordEncrypt.encryptPassword("test"), "test@gmail.com", "test", "test", typeUser.CLIENT );
        Response response = appTarget.path(USERS_RESOURCE)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(usertest, MediaType.APPLICATION_JSON)
        );
        
        User userCod = response.readEntity(User.class);
        usercode=userCod.getCode();
        // check if the response was ok
        assertEquals(response.getStatusInfo().toEnum(),Status.OK);
        
	}
	
	
	@Test
	public void DeleteUserResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(USERS_RESOURCE)
	                .path(Integer.toString(usercode))
	                .request()
	                .delete();
		 
		 assertEquals(response.getStatusInfo().toEnum(),Status.OK);
        
	}
	
	
	
	@Test
	public void MovieListResource() {
		List<Movie> movies= new ArrayList<>();
		 Client client = ClientBuilder.newClient();
	        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(MOVIES_RESOURCE)
	                .request(MediaType.APPLICATION_JSON)
	                .get();

	            // check that the response was HTTP OK
	            if (response.getStatusInfo().toEnum() == Status.OK)
	            {
	                // the response is a generic type (a List<User>)
	                GenericType<List<Movie>> listType = new GenericType<List<Movie>>(){};
	                 movies = response.readEntity(listType);
	            }
	    List<Movie> movies2 = MovieDAO.getInstance().getAll();
	    
	    for(Movie m : movies) {
	    	for(Movie m1 : movies2) {
	    	assertEquals(m, m1);
	    	}
	    }
	}
	
	@Test
	public void AddMovieResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
        Response response = appTarget.path(MOVIES_RESOURCE)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(movietest, MediaType.APPLICATION_JSON)
        );
        
        Movie movieTit = response.readEntity(Movie.class);
        movietitle=movieTit.getTitle();
        // check if the response was ok
        assertEquals(response.getStatusInfo().toEnum(),Status.OK);
	}
	
	
	@Test
	public void DeleteMovieResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(MOVIES_RESOURCE)
	                .path(movietitle)
	                .request()
	                .delete();
		 
		 assertEquals(response.getStatusInfo().toEnum(),Status.OK);
	
	}
	
	@Test
	public void RentalListResource() {
		
		List<Rental> rentals= new ArrayList<>();
		 Client client = ClientBuilder.newClient();
	        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(RENTALS_RESOURCE)
	                .request(MediaType.APPLICATION_JSON)
	                .get();

	            // check that the response was HTTP OK
	            if (response.getStatusInfo().toEnum() == Status.OK)
	            {
	                // the response is a generic type (a List<User>)
	                GenericType<List<Rental>> listType = new GenericType<List<Rental>>(){};
	                 rentals = response.readEntity(listType);
	            }
	    List<Rental> rentals2 = RentalDAO.getInstance().getAll();
	    
	    for(Rental r : rentals) {
	    	for(Rental r1 : rentals) {
	    	assertEquals(r, r1);
	    	}
	    }
	}
	
	@Test
	public void AddRentalResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
        Response response = appTarget.path(USERS_RESOURCE)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(rentaltest, MediaType.APPLICATION_JSON)
        );
        
        Rental rentalID = response.readEntity(Rental.class);
        Rentalid=rentalID.getId();
        // check if the response was ok
        assertEquals(response.getStatusInfo().toEnum(),Status.OK);
	}
	
	
	@Test
	public void DeleteRentalResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(USERS_RESOURCE)
	                .path(Integer.toString(Rentalid))
	                .request()
	                .delete();
		 
		 assertEquals(response.getStatusInfo().toEnum(),Status.OK);
	}

}
