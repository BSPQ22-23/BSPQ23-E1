package com.videoclub.dao;

import java.util.List;

import com.example.pojo.User;

public class UserDAO extends DataAccessObjectBase implements IDataAccessObject<User>{

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
		super.saveObject(object);
	}

	@Override
	public void delete(User object) {
		super.deleteObject(object);
		
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User find(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
