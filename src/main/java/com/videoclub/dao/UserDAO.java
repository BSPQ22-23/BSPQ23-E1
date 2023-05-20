package com.videoclub.dao;


import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.videoclub.pojo.ClassColumnNames;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;

public class UserDAO extends DataAccessObjectBase<User>{

	private static UserDAO instance;
	private UserDAO() {}
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	@Override
	public void save(User object) {
		super.save(object);
	}

	@Override
	public void delete(User object) {
		super.delete(object);
	}

	public List<User> getAll() {
		return super.getAll(User.class);
	}
	
	public User find(String param, ClassColumnNames<User> filter) {
		 PersistenceManager pm = pmf.getPersistenceManager();
		    Transaction tx = pm.currentTransaction();

		    User result = null;
		    User result2 = null;
		    
		    //Tries to convert the param into a number
		    try{
	    	  Double.parseDouble(param);
	    	} catch(NumberFormatException e){
	    	  param = "'" + param.replace("'", "''") + "'";
	    	}

		    try {
		        tx.begin();
		        Query<?> query = pm.newQuery("SELECT FROM " + filter.getClazz().getName() + " WHERE "+ filter.getColumnName() +" == "+param);
		        query.setUnique(true);
		        result = (User) query.execute();
		        if(result!=null) {
			        result2 = pm.detachCopy(result);
			        result2.setFavouriteMovieList((ArrayList<Movie>) pm.detachCopyAll(result.getFavouriteMovieList()));
		        }
		        tx.commit();
		    } finally {
		        if (tx.isActive()) {
		            tx.rollback();
		        }
		        pm.close();
		    }

		    return result2;
	}
}
