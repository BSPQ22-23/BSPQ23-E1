package com.videoclub;

import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.User;

@Path("users")
public class UserResource {

    protected static final Logger logger = LogManager.getLogger();
   // private static List<User> users;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	JOptionPane.showMessageDialog(null, "Getting all users");
    	List<User> users = UserDAO.getInstance().getAll(User.class.getName());
    	
    	logger.info(users);
		return users;
    }
    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseBuilder find(int param,String Name) {
    	return Response.ok(UserDAO.getInstance().find(param,Name));
    }
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
    	UserDAO.getInstance().save(user);
        logger.info(user+" added.");
        // return a response containing a user with only the code for the new user
        return Response.ok(new User(user.getCode())).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUser(@PathParam("code") String code) {
    	boolean hasTheUser = false;
    	User userToDelete = UserDAO.getInstance().find(code);
    	if(userToDelete!=null) {
    		hasTheUser=true;
    	}
    	
    	UserDAO.getInstance().delete(userToDelete);
    	
        if (hasTheUser) {
            logger.info("Deleting user {} ...", code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}