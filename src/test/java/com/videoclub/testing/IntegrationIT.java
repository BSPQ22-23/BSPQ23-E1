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

public class IntegrationIT {

private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
private static final String USERS_RESOURCE ="users";
private static final String RENTALS_RESOURCE ="rentals";
private static final String MOVIES_RESOURCE ="movies";
private int usercode =0;
private String movietitle = "test";
private int Rentalid =0;
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
	    	//boolean contains = users2.contains(u);
	    	assertEquals(users2.contains(u), true );
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
	    List<User> users2 = UserDAO.getInstance().getAll();
	    for (int i =0; i < users2.size(); i++) {
	    	if(usertest.equals(users2.get(i))) {
	    		usercode = users2.get(i).getCode();
	    	}
	    }
        assertEquals(users2.contains(usertest), true);        
	}
	
	
	@Test
	public void DeleteUserResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(USERS_RESOURCE)
	                .path(Integer.toString(usercode))
	                .request()
	                .delete();
		 List<User> users = UserDAO.getInstance().getAll();
		 usertest.setCode(usercode);
		 assertFalse(users.contains(usertest));   
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
        //movietitle=movieTit.getTitle();
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
	    
	    for(Rental r : rentals2) {
	    	//boolean contains = users2.contains(u);
	    	assertEquals(rentals.contains(r), true );
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
       
        // check if the response was ok
        List<Rental> rentals = RentalDAO.getInstance().getAll();
	    for (int i =0; i < rentals.size(); i++) {
	    	if(rentaltest.equals(rentals.get(i))) {
	    		Rentalid = rentals.get(i).getId();
	    		rentaltest.setId(Rentalid);
	    	}
	    }
	}
	
	
	@Test
	public void DeleteRentalResource() {
		
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		 Response response = appTarget.path(USERS_RESOURCE)
	                .path(Integer.toString(Rentalid))
	                .request()
	                .delete();
		 List<Rental> rentals = RentalDAO.getInstance().getAll();
		 rentaltest.setId(Rentalid);
		 assertFalse(rentals.contains(rentaltest)); 		 
	}

}

