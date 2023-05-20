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
	//TODO
	public List<Movie> takeMovieListClient(){
		return null;
		
	}
	
}
