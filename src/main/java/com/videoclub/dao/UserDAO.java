package com.videoclub.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll(String nameclass) {
		return super.getAll(nameclass);
	}

	@Override
	public User find(String param) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		User result = null; 

		try {
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE username == '"+param+"'");
			query.setUnique(true);
			result = (User) query.execute();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying an User: " + ex.getMessage());
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
	public User find(int param) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		User result = null; 

		try {
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE code == '"+param+"'");
			query.setUnique(true);
			result = (User) query.execute();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying an User: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return result;
	}
	*/
	
	 @Override
	public User find(int param, String Name) {
		return super.find(param, Name);
	}

}
