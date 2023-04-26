package com.videoclub.pojo;

import java.util.Date;

import javax.jdo.annotations.*;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.UserDAO;


@PersistenceCapable
public class Rental {
	
	@PrimaryKey
	private int id;
	@Unique
	private String title;
	@Transactional
    private Movie movie;
	@Unique
	private int code;
	@Transactional
    private User customer;
    private Date rentalDate;
    private Date returnDate;
    
    public Rental() {
    	
    }
    public Rental(int id) {
    	this.id = id;
    }
    public Rental(int id, Movie movie, User customer, Date rentalDate, Date returnDate) {
    	this.id = id;
    	this.title = movie.getTitle();
        this.movie = movie;
        this.code = customer.getCode();
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Movie getMovie() {
		if(movie == null && title != null)
			return (movie = MovieDAO.getInstance().find(title));
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        this.title = movie.getTitle();
    }

    public User getCustomer() {
    	if(customer == null && code != 0)
			return (customer = UserDAO.getInstance().find(code));
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
        this.code = customer.getCode();
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

