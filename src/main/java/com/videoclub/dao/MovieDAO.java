package com.videoclub.dao;

import java.util.List;
import com.videoclub.pojo.Movie;

public class MovieDAO extends DataAccessObjectBase implements IDataAccessObject<Movie>{
	private static MovieDAO instance;
	private MovieDAO() {}
	public static MovieDAO getInstance() {
		if(instance == null) {
			instance = new MovieDAO();
		}
		return instance;
	}
	@Override
	public void save(Movie object) {
		super.saveObject(object);
		
	}

	@Override
	public void delete(Movie object) {
		super.deleteObject(object);
		
	}

	@Override
	public List<Movie> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie find(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
