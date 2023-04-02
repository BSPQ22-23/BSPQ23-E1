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

import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;

@Path("rentals")
public class RentalResource {
	protected static final Logger logger = LogManager.getLogger();
    private static List<Rental> rentals = new ArrayList<>();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rental> getRentals() {
    	logger.info("List of Rentals --> {}", rentals);
		return rentals;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addrental(Rental rental) {
        rentals.add(rental);
        logger.info("Add a new rental succesfully {} ...", rental.getId());
        return Response.ok(new User(rental.getId())).build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteUser(@PathParam("code") int code) {
    	boolean hasTheRental = false;
    	for(int i = 0; i<rentals.size(); i++) {
    		if(code == rentals.get(i).getId()) {
    			rentals.remove(i);
    			hasTheRental = true;
    		}
    	}
        if (hasTheRental) {
            logger.info("Deleting rental {} ...", code);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
