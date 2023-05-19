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
import com.videoclub.dao.RentalDAO;
import com.videoclub.pojo.Rental;

/**
 * Resource class for rentals.
 * Handles CRUD operations for rentals.
 */
@Path("rentals")
public class RentalResource {
	protected static final Logger logger = LogManager.getLogger();
    //private static List<Rental> rentals = new ArrayList<>();

    /**
     * Retrieves rentals from the database.
     * 
     * @return List of rentals.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rental> getRentals() {
    	List<Rental> rentals = RentalDAO.getInstance().getAll();
    	logger.info(InternationalizationText.getString("retrieve_rentals_db") + rentals);
		return rentals;
    }

    /**
     * Adds a new rental to the database.
     * 
     * @param rental Rental to add.
     * @return OK response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRental(Rental rental) {
        RentalDAO.getInstance().save(rental);
        logger.info(rental + InternationalizationText.getString("add_db_rental"));
        return Response.ok(rental).build();
    }

    /**
     * Deletes a rental from the database.
     * 
     * @param code Code of the rental.
     * @return Status response.
     */
    @DELETE
    @Path("/{code}")
    public Response deleteRental(@PathParam("code") int code) {
    	boolean hasTheRental = false;
    	String newCode = Integer.toString(code);
    	Rental rentalToDelete = RentalDAO.getInstance().find(newCode, Rental.ColumnsNameRental.code);
    	if(rentalToDelete != null)
            hasTheRental = true;
    	
        if (hasTheRental) {
        	RentalDAO.getInstance().delete(rentalToDelete);
            logger.info(InternationalizationText.getString("remove_db_rental") + code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
