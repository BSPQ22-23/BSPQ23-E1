package com.videoclub.pojo;

import javax.jdo.annotations.*;



@PersistenceCapable(detachable="true")
public class Movie {
    
    // Attributes of the Movie class
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int code;
	@Unique
    private String title;
    private MovieGenre genre;
    private int duration;
    private int year;
    private String director;
    private double rentalPrice;
    
    public Movie() {
    	
    }
    public Movie(int code) {
    	this.code=code;
    }
    // Constructor of the Movie class
    public Movie(String title, MovieGenre genre, int duration, int year, String director, double rentalPrice) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.year = year;
        this.director = director;
        this.rentalPrice = rentalPrice;
    }
    
    // Getter and setter methods for the attributes of the Movie class
    public int getCode() {
		return code;
	}
    
	public void setCode(int code) {
		this.code = code;
	}
    
    public int getDuration() {
		return duration;
	}
    
	public void setDuration(int duration) {
		this.duration = duration;
	}
    
    public String getTitle() {
        return title;
    }
    
	public void setTitle(String title) {
        this.title = title;
    }
    
    public MovieGenre getGenre() {
        return genre;
    }
    
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public double getRentalPrice() {
        return rentalPrice;
    }
    
    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
    public static class ColumnsNameMovie extends ClassColumnNames<Movie>{

		protected ColumnsNameMovie(String columnName) {
			super(Movie.class, columnName);
			// TODO Auto-generated constructor stub
		}
		public final static ColumnsNameMovie code = new ColumnsNameMovie("code");
		public final static ColumnsNameMovie title = new ColumnsNameMovie("title");
		public final static ColumnsNameMovie genre = new ColumnsNameMovie("genre");
		public final static ColumnsNameMovie duration = new ColumnsNameMovie("duration");
		public final static ColumnsNameMovie year = new ColumnsNameMovie("year");
		public final static ColumnsNameMovie director = new ColumnsNameMovie("director");
		public final static ColumnsNameMovie rentalPrice = new ColumnsNameMovie("rentalPrice");	
		
    }
    
    @Override
    public boolean equals(Object obj) {
    	// TODO Auto-generated method stub
    	return this.title.equals(((Movie) obj).getTitle());
    }
	@Override
	public String toString() {
		return "Movie [title=" + title + ", genre=" + genre + ", duration=" + duration + ", year=" + year
				+ ", director=" + director + ", rentalPrice=" + rentalPrice + "]";
	}
    
}

