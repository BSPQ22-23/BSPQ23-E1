package server.jdo;

import java.util.Date;

public class Rental {

    private Movie movie;
    private User customer;
    private Date rentalDate;
    private Date returnDate;
    private double rentalPrice;

    public Rental(Movie movie, User customer, Date rentalDate, double rentalPrice) {
        this.movie = movie;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.rentalPrice = rentalPrice;
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

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
}

