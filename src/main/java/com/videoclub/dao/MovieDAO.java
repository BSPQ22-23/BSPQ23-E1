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
	
	@Override
	public Movie find(int param,String Name) {
		return super.find(param, Name);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> getAll(String nameclass) {
        return super.getAll(nameclass);
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
	/*
	@Override
	public Movie find(int param) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();

	    Movie result = null; 

	    try {
	        tx.begin();
	        Query<?> query = pm.newQuery("SELECT FROM " + Movie.class.getName() + " WHERE code == '"+param+"'");
	        query.setUnique(true);
	        result = (Movie) query.execute();
	        tx.commit();
	    } finally {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        pm.close();
	    }

	    return result;
	}
*/

}
