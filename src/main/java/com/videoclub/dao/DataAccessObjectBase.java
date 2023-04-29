package com.videoclub.dao;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;

//This class defines the basic methods of the DAO pattern.
public abstract class DataAccessObjectBase<DomainObject> implements IDataAccessObject<DomainObject>{	
	protected static PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	
	public void delete(DomainObject object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();			
			pm.deletePersistent(object);			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("$ Error deleting an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
	
	public void save(DomainObject object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			System.out.println("* Storing a "+object.getClass()+" : "+ object);
			pm.makePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("$ Error storing an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
	
	
	public DomainObject find(int param,String Name) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();

	    DomainObject result = null; 

	    try {
	        tx.begin();
	        Query<?> query = pm.newQuery("SELECT FROM " + Name + " WHERE code == '"+param+"'");
	        query.setUnique(true);
	        result = (DomainObject) query.execute();
	        tx.commit();
	    } finally {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        pm.close();
	    }

	    return result;
	}

	@Override
	public List<DomainObject> getAll(Class domainClass) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Query<DomainObject> q = pm.newQuery(domainClass);
	    List<DomainObject> result = q.executeList();

	    // add null check for relevant properties
	    /*for (DomainObject obj : result) {
	        if (obj instanceof User) {
	            User user = (User) obj;
	        } else if (obj instanceof Rental) {
	            Rental rental = (Rental) obj;
	        } else if (obj instanceof Movie) {
	            Movie movie = (Movie) obj;
	        }
	    }
	    */

	    return (List<DomainObject>) result;
	}

	
}