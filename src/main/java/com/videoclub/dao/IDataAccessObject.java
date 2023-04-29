package com.videoclub.dao;

import java.util.List;

public interface IDataAccessObject<DomainObject>{
	public void save(DomainObject object);
	public void delete(DomainObject object);
	
	public List<DomainObject> getAll(String nameclass);
	public DomainObject find(String param);
	public DomainObject find(int param,String Name);
}
