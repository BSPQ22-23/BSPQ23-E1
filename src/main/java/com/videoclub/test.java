package com.videoclub;

import java.util.Date;

import org.junit.Test;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class test {
	
	@Test
	public void aloja() {
		User u1 = new User(1, "admin", "admin", "admin@email.com", "AdminName", "AdminSurname", typeUser.ADMIN);
		User u2 = new User(2, "assaz", "p4321", "asier.account@email.com", "Asier", "Belloso", typeUser.CLIENT);
		User u3 = new User(3, "client", "1234", "client@email.com", "ClientName", "ClientSurname", typeUser.CLIENT);
		
		UserDAO.getInstance().save(u1);
		UserDAO.getInstance().save(u2);
		UserDAO.getInstance().save(u3);
		
		Movie m1 = MovieDAO.getInstance().find("Movie1");
		Movie m2 = MovieDAO.getInstance().find("Movie2");
		
		Rental r1 = new Rental(103, m2, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r2 = new Rental(104, m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r3 = new Rental(105, m1, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r4 = new Rental(106, m2, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r5 = new Rental(107, m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		RentalDAO.getInstance().save(r1);
		RentalDAO.getInstance().save(r2);
		RentalDAO.getInstance().save(r3);
		RentalDAO.getInstance().save(r4);
		RentalDAO.getInstance().save(r5);
		
	}

}