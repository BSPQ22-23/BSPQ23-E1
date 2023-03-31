package com.example.pojo;

import java.util.Date;

import javax.jdo.annotations.*;


@PersistenceCapable
public class Rental {
	
	@PrimaryKey
	private int id;
    private Movie movie;
    private User customer;
    private Date rentalDate;
    private Date returnDate;

    public Rental(Movie movie, User customer, Date rentalDate) {
        this.movie = movie;
        this.customer = customer;
        this.rentalDate = rentalDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
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

