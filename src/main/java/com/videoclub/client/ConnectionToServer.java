package com.videoclub.client;

import java.util.List;

import javax.swing.JOptionPane;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;

public class ConnectionToServer {
    private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE ="users";
    private static final String MOVIES_RESOURCE ="movies";
    private static final String RENTALS_RESOURCE ="rentals";
    protected static final Logger logger = LogManager.getLogger();

    
	/** Function that Client uses to request the server the LogIn
	 * @param username Username written.
	 * @param password Password written.
	 * @return User object with which we have logged in.
	 */
	public User logInClient(String username, String password) {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
        User user;
        try {
            Response response = appTarget.path(USERS_RESOURCE)
            	.path(username+"/"+password)
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.ACCEPTED) {
                // the response is a generic type (a List<User>)
                GenericType<User> listType = new GenericType<User>(){};
                user = response.readEntity(listType);
                logger.info("Log In accepted. Welcome, "+user.getName()+" "+user.getSurname());
                return user;
            } else if (response.getStatusInfo().toEnum() == Status.NOT_ACCEPTABLE){
            	logger.info("Error - Password does not match.");
            } else if (response.getStatusInfo().toEnum() == Status.NOT_FOUND){
            	logger.info("Error - User not found.");
            }else {
            	logger.info("Unknown Error - " + response.getStatusInfo().toEnum());
            }
        } catch (ProcessingException o) {
           	logger.info("Error in LogIn. " + o.getMessage());
        }
		return null;
	}
	/** Function that Client uses to request the server the register of a new User.
	 * @param user User to add.
	 * @return True = Registered, False = Not Registered because of a problem.
	 */
	public boolean registerClient(User user) {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
        try {
            Response response = appTarget.path(USERS_RESOURCE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

            // check that the response was HTTP ACCEPTED
            if (response.getStatusInfo().toEnum() == Status.ACCEPTED) {
            	logger.info("User succesfully registered.");
            	return true;
            } else if(response.getStatusInfo().toEnum() == Status.NOT_ACCEPTABLE){
            	JOptionPane.showMessageDialog(null, "Username or email already taken.");
                logger.info("Error - Username or email already taken.");
            } else if(response.getStatusInfo().toEnum() == Status.BAD_REQUEST) {
            	JOptionPane.showMessageDialog(null, "Some of the field are empty. BE CAREFUL!");
            	logger.info("Error - Some of the field are empty. BE CAREFUL!");
            }
        } catch (ProcessingException o) {
            logger.info("Unexpected Error - " + o.getMessage());
        }
		return false;
	}
	//TODO
	public boolean saveRentalClient(Rental rental) {
		return false;
		
	}
	public List<Movie> takeMovieListClient(){
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		List<Movie> listMovies = null;
		try {
            Response response = appTarget.path(MOVIES_RESOURCE)
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<Movie>> listType = new GenericType<List<Movie>>(){};
                listMovies = response.readEntity(listType);
                logger.info("List of movies retrieved correctly from database.");
                
            } else {
                logger.error("Error - ", response);
            }
        } catch (ProcessingException o) {
            logger.error("Error - ", o.getMessage());
        }
		return listMovies;
		
	}
	
	public List<User> takeUserListClient(){
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		List<User> listUser = null;
		try {
            Response response = appTarget.path(USERS_RESOURCE)
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<User>> listType = new GenericType<List<User>>(){};
                listUser = response.readEntity(listType);
                logger.info("List of users retrieved correctly from database.");
                
            } else {
                logger.error("Error - ", response);
            }
        } catch (ProcessingException o) {
            logger.error("Error - ", o.getMessage());
        }
		return listUser;
		
	}
	
	public boolean updateMovieClient(Movie movie) {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		try {
            Response response = appTarget.path(MOVIES_RESOURCE)
            	.path("/updatemovie")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(movie, MediaType.APPLICATION_JSON));

            // check if the response was ok
            if (response.getStatusInfo().toEnum() == Status.ACCEPTED) {
                Movie movieObtained = response.readEntity(Movie.class);
                logger.info("The movie with code: "+movieObtained.getCode()+" has been updated correctly.");
                return true;
            } else if(response.getStatusInfo().toEnum() == Status.NOT_FOUND){
                logger.error("Error - Movie not found in database.");
            }else {
            	logger.error("Error - Unknown Error");
            }
        } catch (ProcessingException e) {
            logger.info("Error - " + e.getMessage());
        }
		return false;
	}
	
	public boolean deleteMovieClient(Movie m) {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		try {
            Response response = appTarget.path(MOVIES_RESOURCE)
                .path(m.getTitle())
                .request()
                .delete();

            // check if the response was ok
            if (response.getStatusInfo().toEnum() == Status.OK) {
                logger.info("Movie correctly deleted from server");
                return true;
            } else if(response.getStatusInfo().toEnum() == Status.NOT_FOUND){
                logger.error("Error - No movies found in the database");
            }
        } catch (ProcessingException o) {
            logger.error("Error" + o.getMessage());
        }
		return false;
	}
	
	public boolean deleteUserClient(User u) {
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		try {
            Response response = appTarget.path(USERS_RESOURCE)
                .path(u.getUsername())
                .request()
                .delete();

            // check if the response was ok
            if (response.getStatusInfo().toEnum() == Status.OK) {
                logger.info("User correctly deleted from server");
                return true;
            } else if(response.getStatusInfo().toEnum() == Status.NOT_FOUND){
                logger.error("Error - No Users found in the database");
            }
        } catch (ProcessingException o) {
            logger.error("Error" + o.getMessage());
        }
		return false;
	}
	public boolean changePasswordUserClient(User u) {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SERVER_ENDPOINT);

        Response response = target.path(USERS_RESOURCE)
        		.path("/updateuser")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(u, MediaType.APPLICATION_JSON));

        if (response.getStatusInfo().toEnum() == Status.ACCEPTED) {
            logger.info(InternationalizationText.getString("user_upd_correct"));
            return true;
        } else if(response.getStatusInfo().toEnum() == Status.NOT_FOUND){
            logger.error("Error - User not found in the database");
        }else {
            logger.info(InternationalizationText.getString("user_upd_fail"));
        }
		return false;
	
	}
}
