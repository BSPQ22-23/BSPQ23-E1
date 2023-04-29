package com.videoclub.dao;

import java.util.List;

import com.videoclub.pojo.ClassColumnNames;
import com.videoclub.pojo.Rental;

public class RentalDAO extends DataAccessObjectBase<Rental>{
	
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
		super.save(object);
	}

	@Override
	public void delete(Rental object) {
		super.delete(object);
	}

	public List<Rental> getAll() {
        return super.getAll(Rental.class);
	}
	
	public Rental find(String param, ClassColumnNames<Rental> filter) {
		return super.find(param, filter);
	}

}
