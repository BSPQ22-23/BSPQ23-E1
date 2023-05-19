package com.videoclub.dao;

import java.util.List;

import com.videoclub.pojo.ClassColumnNames;

public interface IDataAccessObject<DomainObject>{
	/** Function to save the Data of an object in the corresponding database. This function is used in the server side.
	 * @param object Object to be saved.
	 */
	public void save(DomainObject object);
	/** Function to delete the Data of an object in the corresponding database. This function is used in the server side.
	 * @param object Object to be deleted.
	 */
	public void delete(DomainObject object);
	/** Function to get all the objects and data of the corresponding database. This function is used in the server side.
	 * @param domainClass The database to retrieve. Matches with the names of the domain classes. 
	 * @return A list with the objects.
	 */
	public List<DomainObject> getAll(Class<DomainObject> domainClass);
	/** Function to find a certain object in database taking into account the filter given as param. This function is used in the server side.
	 * @param param String given to find the desirable object. In SQL, for example, "21-11-2002"
	 * @param filter Filter given to search the object. In SQL, for example, "DATE".
	 * @return The object that fullfils the information provided.
	 */
	public DomainObject find(String param, ClassColumnNames<DomainObject> filter);
	
}
