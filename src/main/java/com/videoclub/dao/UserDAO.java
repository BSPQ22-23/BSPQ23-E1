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

		    return super.find(param, filter);
	}
}
