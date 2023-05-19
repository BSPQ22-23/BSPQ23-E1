package com.videoclub;

import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.User;


@Path("users")
public class UserResource {
    protected static final Logger logger = LogManager.getLogger();
    //private static List<User> users;
    
    /** Function to retrieve users from the database. 
     * @return List of users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
    	List<User> users = UserDAO.getInstance().getAll(User.class);
    	logger.info(InternationalizationText.getString("retrieve_users_db")+users);
		return users;
    }
    
    /**Function to add new user to the database.
     * @param user User to add.
     * @return OK response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
    	UserDAO.getInstance().save(user);
        logger.info(user+" "+InternationalizationText.getString("add_db_user"));
        // return a response containing a user with only the code for the new user
        return Response.ok(new User(user.getCode())).build();
    }

    /** Function to delete a movie of the database.
     * @param code Code of the rental.
     * @return Status response.
     */
    @DELETE
    @Path("/{code}")
    public Response deleteUser(@PathParam("code") String code) {
    	boolean hasTheUser = false;
    	User userToDelete = UserDAO.getInstance().find(code,User.ColumnsNameUser.code);
    	if(userToDelete!=null) hasTheUser=true;
    	
        if (hasTheUser) {
        	UserDAO.getInstance().delete(userToDelete);
            logger.info(InternationalizationText.getString("remove_db_user")+code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    /**@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
    
        UserDAO.getInstance().update(user); 
        logger.info("Updating user {} ...", user.getCode());
        
        return Response.status(Response.Status.OK).build();
    }
*/
}