package com.videoclub;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.ClassColumnNames;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class test {
	
	@Test
	public void databaseChecking() {
		
		//Creating the users to insert them into the database
		User u1 = new User("admin", PasswordEncrypt.encryptPassword("admin"), "admin@email.com", "AdminName", "AdminSurname", typeUser.ADMIN);
		User u2 = new User("assaz", PasswordEncrypt.encryptPassword("assaz"), "asier.account@email.com", "Asier", "Belloso", typeUser.CLIENT);
		User u3 = new User("client", PasswordEncrypt.encryptPassword("client"), "client@email.com", "ClientName", "ClientSurname", typeUser.CLIENT);
		
		UserDAO.getInstance().save(u1);
		UserDAO.getInstance().save(u2);
		UserDAO.getInstance().save(u3);
		
		Movie m1 = new Movie("Movie1", "comedy", 90, 2023, "paco", 19.9);
		Movie m2 = new Movie("Movie2", "drama", 180, 1989, "luis", 5.74);
		
		MovieDAO.getInstance().save(m1);
		MovieDAO.getInstance().save(m2);
		
		Rental r1 = new Rental(m2, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r2 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r3 = new Rental(m1, u3, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r4 = new Rental(m2, u1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		Rental r5 = new Rental(m1, u2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		RentalDAO.getInstance().save(r1);
		RentalDAO.getInstance().save(r2);
		RentalDAO.getInstance().save(r3);
		RentalDAO.getInstance().save(r4);
		RentalDAO.getInstance().save(r5);
		
		System.out.println(UserDAO.getInstance().getAll(User.class));
		
		ClassColumnNames<User> Cuser = new ClassColumnNames(User.class, "EMAIL");
		
		ClassColumnNames<Movie> Cmovie = new ClassColumnNames(Movie.class, "TITLE");
		
		ClassColumnNames<Rental> Crental = new ClassColumnNames(Rental.class, "ID");
		
		
		
		
		User u1check= UserDAO.getInstance().find(u1.getEmail(),Cuser);
		
		User u2check= UserDAO.getInstance().find(u2.getEmail(),Cuser);
		
		User u3check= UserDAO.getInstance().find(u3.getEmail(),Cuser);
		
		
		
		Movie m1check=MovieDAO.getInstance().find(m1.getTitle(), Cmovie);
		
		Movie m2check=MovieDAO.getInstance().find(m2.getTitle(), Cmovie);
		
		
		Rental r1check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Crental);
		
		Rental r2check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Crental);
		
		Rental r3check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Crental);
		
		Rental r4check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Crental);
		
		Rental r5check = RentalDAO.getInstance().find(String.valueOf(r1.getId()), Crental);
		
		
		assertEquals(u1, u1check);
		
		assertEquals(u2, u2check);
		
		assertEquals(u3, u3check);
		
		assertEquals(m1, m1check);
		
		assertEquals(m2, m2check);
		
		assertEquals(r1, r1check);
		
		assertEquals(r2, r2check);
		
		assertEquals(r3, r3check);
		
		assertEquals(r4, r4check);
		
		assertEquals(r5, r5check);
		
	}

}
