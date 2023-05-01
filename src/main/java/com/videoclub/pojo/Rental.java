package com.videoclub.pojo;

import java.util.Date;

import javax.jdo.annotations.*;

import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.UserDAO;

@PersistenceCapable(detachable="true")
public class Rental {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String title;
	@Transactional
    private Movie movie;
	private int code;
	@Transactional
    private User customer;
    private Date rentalDate;
    private Date returnDate;
    
    public Rental() {
    	
    }
    public Rental(Movie movie, User customer, Date rentalDate, Date returnDate) {
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
			return (movie = MovieDAO.getInstance().find(title,Movie.ColumnsNameMovie.title));
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        this.title = movie.getTitle();
    }

    //TODO check this
    public User getCustomer() {
    	String code1 = Integer.toString(code);
    	if(customer == null && code1 != null)
			return (customer = UserDAO.getInstance().find(code1,User.ColumnsNameUser.code));
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
    
    public enum columnsName{
    	id,title,code,rentalDate,returnDate
    }
    public static class ColumnsNameRental extends ClassColumnNames<Rental>{

		protected ColumnsNameRental(String columnName) {
			super(Rental.class, columnName);
			// TODO Auto-generated constructor stub
		}
		public final static ColumnsNameRental id = new ColumnsNameRental("id");
		public final static ColumnsNameRental title = new ColumnsNameRental("title");
		public final static ColumnsNameRental code = new ColumnsNameRental("code");
		public final static ColumnsNameRental rentalDate = new ColumnsNameRental("rentalDate");
		public final static ColumnsNameRental returnDate = new ColumnsNameRental("returnDate");
	}
    
    @Override
    public boolean equals(Object obj) {
    	// TODO Auto-generated method stub
    	return this.id == (((Rental) obj).getId());
    }
}

