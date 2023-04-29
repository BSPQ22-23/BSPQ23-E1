package com.videoclub.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Rental> getAll(Class nameclass) {

        return super.getAll(nameclass);

	}

	@Override
	public Rental find(String param) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		Rental result = null; 

		try {
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + Rental.class.getName() + " WHERE id == '"+param+"'");
			query.setUnique(true);
			result = (Rental) query.execute();
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying an Rental: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return result;
	}
	
	@Override
	public Rental find(int param,String Name) {
		return super.find(param, Name);
	}

}
