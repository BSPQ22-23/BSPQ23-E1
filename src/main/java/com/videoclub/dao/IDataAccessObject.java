package com.videoclub.dao;

import java.util.List;

import com.videoclub.pojo.ClassColumnNames;

public interface IDataAccessObject<DomainObject>{
	public void save(DomainObject object);
	public void delete(DomainObject object);
	public List<DomainObject> getAll(Class<DomainObject> domainClass);
	public DomainObject find(String param, ClassColumnNames<DomainObject> filter);
	
}
