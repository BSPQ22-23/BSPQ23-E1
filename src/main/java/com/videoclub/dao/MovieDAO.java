package com.videoclub.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.videoclub.pojo.Movie;

public class MovieDAO extends DataAccessObjectBase<Movie> {
	
	private static MovieDAO instance;
	private MovieDAO() {}
	public static MovieDAO getInstance() {
		if(instance == null) {
			instance = new MovieDAO();
		}
		return instance;
	}
	@Override
	public void save(Movie object) {
		super.save(object);
		
	}
	@Override
	public void delete(Movie object) {
		super.delete(object);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> getAll() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Query<Movie> q = pm.newQuery(Movie.class);

        return (List<Movie>)q.execute(20);
	}

	@Override
	public Movie find(String param) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		Movie result = null; 

		try {
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + Movie.class.getName() + " WHERE title == '"+param+"'");
			query.setUnique(true);
			result = (Movie) query.execute();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying an Movie: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return result;
	}
	@Override
	public Movie find(int param) {
		// TODO Auto-generated method stub
		return null;
	}

}
