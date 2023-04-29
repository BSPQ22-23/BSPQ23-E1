package com.videoclub.dao;

import java.util.List;

import com.videoclub.pojo.ClassColumnNames;
import com.videoclub.pojo.Movie;

public class MovieDAO extends DataAccessObjectBase<Movie> {
	
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
		super.save(object);
	}
	
	@Override
	public void delete(Movie object) {
		super.delete(object);	
	}
	
	public Movie find(String param,ClassColumnNames<Movie> filter) {
		return super.find(param, filter);
	}

	public List<Movie> getAll() {
        return super.getAll(Movie.class);
	}

}
