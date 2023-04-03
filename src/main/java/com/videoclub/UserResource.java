package com.videoclub;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

@Path("users")
public class UserResource {

    protected static final Logger logger = LogManager.getLogger();
    private static User userpruebaAdmin = new User(1,"admin","1234","admin@gmail.com","adminName","adminSurname",typeUser.ADMIN);
    private static User userpruebaClient = new User(2,"client","1234","client@gmail.com","clientName","clientSurname",typeUser.CLIENT);
    private static List<User> users = new ArrayList<>(Arrays.asList(userpruebaAdmin,userpruebaClient));
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	logger.info(users);
		return users;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        // here we will process the received user data
        users.add(user);
        logger.info(user);
        // return a response containing a user with only the code for the new user
        return Response.ok(new User(user.getCode())).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUser(@PathParam("code") int code) {
    	boolean hasTheUser = false;
    	for(int i = 0; i<users.size(); i++) {
    		if(code == users.get(i).getCode()) {
    			users.remove(i);
    			hasTheUser = true;
    		}
    	}
        if (hasTheUser) {
            logger.info("Deleting user {} ...", code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}