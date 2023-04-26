package com.videoclub.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		List<Rental> rentals = new ArrayList<>();
		
		try {
			tx.begin();
			
			Extent<Rental> extent = pm.getExtent(Rental.class, true);

			for (Rental category : extent) {
				rentals.add(category);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error retrieving all the Rentals: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return rentals;

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
	public Rental find(int param) {
		// TODO Auto-generated method stub
		return null;
	}

}
