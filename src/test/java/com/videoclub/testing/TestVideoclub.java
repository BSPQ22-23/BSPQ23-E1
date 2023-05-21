package com.videoclub.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
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
	private static User u1,u2,u3;
	private static Movie m1,m2,m3,m4,m5,m6,m7;
	private static Rental r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17,r18,r19,r20;
	
	@BeforeClass
	public static void setUp() {
		u1 = new User("admin", PasswordEncrypt.encryptPassword("admin"), "admin@email.com", "AdminName", "AdminSurname", typeUser.ADMIN);
		u2 = new User("assaz", PasswordEncrypt.encryptPassword("assaz"), "asier.account@email.com", "Asier", "Belloso", typeUser.CLIENT);
		u3 = new User("client", PasswordEncrypt.encryptPassword("client"), "client@email.com", "ClientName", "ClientSurname", typeUser.CLIENT);
		
		UserDAO.getInstance().save(u1);
		UserDAO.getInstance().save(u2);
		UserDAO.getInstance().save(u3);
		
		m1 = new Movie("Movie1", "comedy", 90, 2023, "paco", 19.9);
		m2 = new Movie("Movie2", "drama", 180, 1989, "luis", 5.74);
		m3 = new Movie("Movie3", "drama", 90, 2012, "luis", 4.25);
		m4 = new Movie("Movie4", "horror", 160, 2004, "juana", 14.34);
		m5 = new Movie("Movie5", "comedy", 90, 1997, "paco", 9.25);
		m6 = new Movie("Movie6", "horror", 120, 2015, "juana", 6.74);
		m7 = new Movie("Movie7", "comedy", 90, 1990, "paco", 6.75);
		
		MovieDAO.getInstance().save(m1);
		MovieDAO.getInstance().save(m2);
		MovieDAO.getInstance().save(m3);
		MovieDAO.getInstance().save(m4);
		MovieDAO.getInstance().save(m5);
		MovieDAO.getInstance().save(m6);
		MovieDAO.getInstance().save(m7);
		
		r1 = new Rental(m2, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r2 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r3 = new Rental(m1, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r4 = new Rental(m2, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r5 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		r6 = new Rental(m3, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r7 = new Rental(m4, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r8 = new Rental(m5, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r9 = new Rental(m6, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r10 = new Rental(m7, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		r11 = new Rental(m3, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r12 = new Rental(m4, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r13 = new Rental(m5, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r14 = new Rental(m6, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r15 = new Rental(m7, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		r16 = new Rental(m3, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r17 = new Rental(m4, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r18 = new Rental(m5, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r19 = new Rental(m6, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		r20 = new Rental(m7, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		RentalDAO.getInstance().save(r1);
		RentalDAO.getInstance().save(r2);
		RentalDAO.getInstance().save(r3);
		RentalDAO.getInstance().save(r4);
		RentalDAO.getInstance().save(r5);
		
		RentalDAO.getInstance().save(r6);
		RentalDAO.getInstance().save(r7);
		RentalDAO.getInstance().save(r8);
		RentalDAO.getInstance().save(r9);
		RentalDAO.getInstance().save(r10);
		
		RentalDAO.getInstance().save(r11);
		RentalDAO.getInstance().save(r12);
		RentalDAO.getInstance().save(r13);
		RentalDAO.getInstance().save(r14);
		RentalDAO.getInstance().save(r15);
		
		RentalDAO.getInstance().save(r16);
		RentalDAO.getInstance().save(r17);
		RentalDAO.getInstance().save(r18);
		RentalDAO.getInstance().save(r19);
		RentalDAO.getInstance().save(r20);
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
