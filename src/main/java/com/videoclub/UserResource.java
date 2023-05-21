package com.videoclub;

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
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;


/**
 * Resource class for users.
 * Handles CRUD operations for users.
 */
@Path("users")
public class UserResource {
    protected static final Logger logger = LogManager.getLogger();
    //private static List<User> users;

    /**
     * Retrieves users from the database.
     * 
     * @return List of users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	List<User> users = UserDAO.getInstance().getAll();
    	logger.info(InternationalizationText.getString("retrieve_users_db") + users);
		return users;
    }

    /**
     * Adds a new user to the database.
     * 
     * @param user User to add.
     * @return OK response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
    	if(!user.getEmail().strip().equals("") && !user.getName().strip().equals("") && !user.getPassword().strip().equals("") && !user.getUsername().strip().equals("") && !user.getSurname().strip().equals("")) {
	    	user.setPassword(PasswordEncrypt.encryptPassword(user.getPassword()));
    		if(UserDAO.getInstance().find(user.getEmail(),User.ColumnsNameUser.email) != null || UserDAO.getInstance().find(user.getUsername(),User.ColumnsNameUser.username) != null) {
	    		return Response.status(Status.NOT_ACCEPTABLE).build();
	    	}else {
	        	UserDAO.getInstance().save(user);
		        logger.info(user + " " + InternationalizationText.getString("add_db_user"));
		        return Response.status(Status.ACCEPTED).build();
	    	}
    	}else {
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    }

    /**
     * Deletes a user from the database.
     * 
     * @param code Code of the user.
     * @return Status response.
     */
    @DELETE
    @Path("/{code}")
    public Response deleteUser(@PathParam("code") String code) {
    	boolean hasTheUser = false;
    	User userToDelete = UserDAO.getInstance().find(code, User.ColumnsNameUser.username);
    	if(userToDelete != null)
            hasTheUser = true;
    	
        if (hasTheUser) {
        	UserDAO.getInstance().delete(userToDelete);
            logger.info(InternationalizationText.getString("remove_db_user") + code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    /**
     * Log in of the user.
     * @param usernameTC Username of the user.
     * @param passTC	Password of the user.
     * @return Response with the corresponding Status depending of the result obtained. Also, sends the user.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/{pass}")
    public Response logInUser(@PathParam("username") String usernameTC, @PathParam("pass")String passTC){
    	User userToCheck = UserDAO.getInstance().find(usernameTC,User.ColumnsNameUser.username);
    	if(userToCheck==null) {
    		return Response.status(Status.NOT_FOUND).build();
    	}else {
    		if(userToCheck.getPassword().equals(PasswordEncrypt.encryptPassword(passTC))) {
    			return Response.status(Status.ACCEPTED).entity(userToCheck).build();
    		}else {
    			return Response.status(Status.NOT_ACCEPTABLE).build();
    		}
    	}
    }
    /**Update the user.
     * 
     * @param user User with the new parameters to change.
     * @return Response with the corresponding Status depending of the result obtained. Also, sends the user.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateuser")
    public Response updateMovie(User user) {
    	User userFromDB = UserDAO.getInstance().find(Integer.toString(user.getCode()), User.ColumnsNameUser.code);
    	logger.info(user.getCode());
    	if(userFromDB != null) {
    		userFromDB.setEmail(user.getEmail());
    		userFromDB.setFavouriteMovieList(user.getFavouriteMovieList());
    		userFromDB.setName(user.getName());
    		userFromDB.setPassword(user.getPassword());
    		userFromDB.setSurname(user.getSurname());
    		userFromDB.setType(user.getType());
    		userFromDB.setUsername(user.getUsername());
        	UserDAO.getInstance().save(userFromDB);
        	logger.info("User updated correctly.");
        	return Response.accepted(userFromDB).build();
    	}
    	return Response.status(Status.NOT_FOUND).build();
    }
    
    /*
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
    
        UserDAO.getInstance().update(user); 
        logger.info("Updating user {} ...", user.getCode());
        
        return Response.status(Response.Status.OK).build();
    }
    */
}
