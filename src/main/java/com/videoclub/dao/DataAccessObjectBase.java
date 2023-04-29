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
	public List<DomainObject> getAll(String nameclass) {
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    List<DomainObject> result = null;
	    try {
	        tx.begin();
	        Query<DomainObject> query = pm.newQuery("SELECT  FROM " + nameclass);
	        result = (List<DomainObject>) query.execute();
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        pm.close();
	    }
	    return result;
	}

	
}