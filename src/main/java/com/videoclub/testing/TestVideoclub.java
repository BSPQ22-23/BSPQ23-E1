package com.videoclub.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class TestVideoclub {
	private User u1,u2,u3;
	private Movie m1,m2;
	private Rental r1,r2,r3,r4,r5;
	
	@Before
	public void setUp() {
		u1 = new User("admin", PasswordEncrypt.encryptPassword("admin"), "admin@email.com", "AdminName", "AdminSurname", typeUser.ADMIN);
		u2 = new User("assaz", PasswordEncrypt.encryptPassword("assaz"), "asier.account@email.com", "Asier", "Belloso", typeUser.CLIENT);
		u3 = new User("client", PasswordEncrypt.encryptPassword("client"), "client@email.com", "ClientName", "ClientSurname", typeUser.CLIENT);
		
		UserDAO.getInstance().save(u1);
		UserDAO.getInstance().save(u2);
		UserDAO.getInstance().save(u3);
		
		m1 = new Movie("Movie1", "comedy", 90, 2023, "paco", 19.9);
		m2 = new Movie("Movie2", "drama", 180, 1989, "luis", 5.74);
		
		MovieDAO.getInstance().save(m1);
		MovieDAO.getInstance().save(m2);
		
		r1 = new Rental(m2, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r2 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r3 = new Rental(m1, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r4 = new Rental(m2, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r5 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		RentalDAO.getInstance().save(r1);
		RentalDAO.getInstance().save(r2);
		RentalDAO.getInstance().save(r3);
		RentalDAO.getInstance().save(r4);
		RentalDAO.getInstance().save(r5);
	}
	/**
	 * This test creates different movies,users and  Rentals and inserts them into the database
	 * Then it obtains them back and compares them if they are the same
	 */
	@Test
	public void databaseUserChecking() {
		User u1check= UserDAO.getInstance().find(u1.getEmail(),User.ColumnsNameUser.email);
		
		User u2check= UserDAO.getInstance().find(u2.getEmail(),User.ColumnsNameUser.email);
		
		User u3check= UserDAO.getInstance().find(u3.getEmail(),User.ColumnsNameUser.email);
		
		System.out.println(u1 +" - "+ u1check);
		
		assertEquals(u1, u1check);
		
		assertEquals(u2, u2check);
		
		assertEquals(u3, u3check);	
	}
	
	@Test
	public void databaseMovieChecking() {
		Movie m1check=MovieDAO.getInstance().find(m1.getTitle(), Movie.ColumnsNameMovie.title);
		
		Movie m2check=MovieDAO.getInstance().find(m2.getTitle(), Movie.ColumnsNameMovie.title);
		
		assertEquals(m1, m1check);
		
		assertEquals(m2, m2check);
	}
	
	@Test
	public void databaseRentalChecking() {
		Rental r1check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Rental.ColumnsNameRental.id);
		
		Rental r2check = RentalDAO.getInstance().find(String.valueOf(r2.getId()), Rental.ColumnsNameRental.id);
		
		Rental r3check = RentalDAO.getInstance().find(String.valueOf(r3.getId()), Rental.ColumnsNameRental.id);
		
		Rental r4check = RentalDAO.getInstance().find(String.valueOf(r4.getId()), Rental.ColumnsNameRental.id);
		
		Rental r5check = RentalDAO.getInstance().find(String.valueOf(r5.getId()), Rental.ColumnsNameRental.id);
		
		assertEquals(r1, r1check);
		assertEquals(r2, r2check);
		assertEquals(r3, r3check);
		assertEquals(r4, r4check);
		assertEquals(r5, r5check);
	}

}
