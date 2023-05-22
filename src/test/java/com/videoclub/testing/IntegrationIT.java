package com.videoclub.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.UserDataHandler;

import com.videoclub.client.ConnectionToServer;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.MovieGenre;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class IntegrationIT {

private static int usercode =0;
private String movietitle = "test";
private int Rentalid =0;
private static ConnectionToServer cts = new ConnectionToServer();
Date currentDate = new Date();
static User usertest = new User("test","test", "test@gmail.com", "test", "test", typeUser.CLIENT );
static User usertest2 = new User("test2","test2", "test2@gmail.com", "test2", "test2", typeUser.ADMIN );
Movie movietest = new Movie("test", MovieGenre.COMEDY, 100, 2021, "test", 15);
Rental rentaltest = new Rental(movietest, usertest,currentDate , currentDate);
	@Test
	public void UserListResource() {
		List<User> users= cts.takeUserListClient();
	    List<User> users2 = UserDAO.getInstance().getAll();
	    for(User u : users) {
	    	assertEquals(users2.contains(u), true );
	    }
	}
	
	@Test
	public void AddUserResource() {
		boolean correct = cts.registerClient(usertest);
	    List<User> users2 = cts.takeUserListClient();
	    for (int i =0; i < users2.size(); i++) {
	    	if(usertest.equals(users2.get(i))) {
	    		usercode = users2.get(i).getCode();
	    	}
	    }
	    assertTrue(correct);
        assertEquals(users2.contains(usertest), true);        
	}
	
	
	@Test
	public void DeleteUserResource() {
		boolean v = cts.registerClient(usertest);
		boolean correct = cts.deleteUserClient(usertest);
		List<User> users = cts.takeUserListClient();
		usertest.setCode(usercode);
		assertTrue(correct);
		assertFalse(users.contains(usertest));   
	}
	
	@Test
	public void WrongDeleteUserResource() {
		boolean v = cts.registerClient(usertest);
		boolean correct = cts.deleteUserClient(usertest2);
		List<User> users = cts.takeUserListClient();
		usertest.setCode(usercode);
		assertFalse(correct);
		assertFalse(users.contains(usertest2));   
	}
	
	@Test
	public void MovieListResource() {
		List<Movie> movies= cts.takeMovieListClient();
	    List<Movie> movies2 = MovieDAO.getInstance().getAll();
	    for(Movie m : movies) {
	    	assertEquals(movies2.contains(m), true );
	    }
	}
	
	@Test
	public void AddMovieResource() {
		cts.addMovieClient(movietest);
		List<Movie> movies = cts.takeMovieListClient();
        assertEquals(movies.contains(movietest),true);
	}
	
	
	@Test
	public void DeleteMovieResource() {
		boolean correct = cts.deleteMovieClient(movietest);
		List<Movie> movies= cts.takeMovieListClient();
		assertEquals(correct, true);
		assertEquals(movies.contains(movietest), false );
	
	}
	
	@Test
	public void RentalListResource() {
		List<Rental> rentals= cts.takeRentalListClient();
	    List<Rental> rentals2 = RentalDAO.getInstance().getAll();
	    for(Rental r : rentals2) {
	    	assertEquals(rentals.contains(r), true );
	    }
	}
	
	@Test
	public void FailLogInFunctionTest() {
		User u = cts.logInClient("eiekjudghv", "wsbeliufa");	
		assertTrue(u==null);
	}
	
	@Test
	public void showMovieTest() {
		cts.addMovieClient(movietest);
		Movie correct = cts.showMovieClient(movietest.getTitle());
		assertEquals(correct, movietest);
	}
	
	@Test
	public void wrongShowMovieTest() {
		Movie correct = cts.showMovieClient("WrongMovie");
		assertEquals(correct, null);
	}
	
	@Test
	public void updateMovieTest() {
		Movie old = new Movie("ja", MovieGenre.HORROR, 6, 4, "ja", 7.9);
		cts.addMovieClient(old);
		Movie newi = MovieDAO.getInstance().find(old.getTitle(), Movie.ColumnsNameMovie.title);
		newi.setDirector("u");
		newi.setGenre(MovieGenre.FAMILY);
		boolean correct = cts.updateMovieClient(newi);
		assertEquals(correct, true);
		cts.deleteMovieClient(old);
	}
	
	@AfterClass
	public static void deleteTest() {
		cts.deleteUserClient(usertest);
	}

}

