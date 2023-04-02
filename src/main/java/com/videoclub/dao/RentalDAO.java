package com.videoclub.dao;

import java.util.List;

import com.videoclub.pojo.Rental;

public class RentalDAO extends DataAccessObjectBase implements IDataAccessObject<Rental>{
	private static RentalDAO instance;
	private RentalDAO() {}
	public static RentalDAO getInstance() {
		if(instance == null) {
			instance = new RentalDAO();
		}
		return instance;
	}

	@Override
	public void save(Rental object) {
		super.saveObject(object);
		
	}

	@Override
	public void delete(Rental object) {
		super.deleteObject(object);
		
	}

	@Override
	public List<Rental> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rental find(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
