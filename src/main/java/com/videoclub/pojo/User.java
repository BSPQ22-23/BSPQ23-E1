package com.videoclub.pojo;

import java.util.ArrayList;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class User {
    
    // Attributes of the User class
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int code;
	@Unique
    private String username;
    private typeUser type;
    private String password;
    private String email;
    private String name;
    private String surname;
    @Join
    private ArrayList<Movie> favouriteMovieList;
    
	public User() {
    	
    }
    public User(int code) {
    	this.code = code;
    }
    
    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
    	
    }
    
    // Constructor of the User class
    public User(String username, String password, String email, String name, String surname, typeUser type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.favouriteMovieList = new ArrayList<>();
    }
     
    public typeUser getType() {
		return type;
	}
	public void setType(typeUser type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	// Getter and setter methods for the attributes of the User class
    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
    
    public String getUsername() {
        return username;
    }
    
	public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public ArrayList<Movie> getFavouriteMovieList() {
		return favouriteMovieList;
	}
	public void setFavouriteMovieList(ArrayList<Movie> favouriteMovieList) {
		this.favouriteMovieList = favouriteMovieList;
	}
	
	public static class ColumnsNameUser extends ClassColumnNames<User>{

		protected ColumnsNameUser(String columnName) {
			super(User.class, columnName);
			// TODO Auto-generated constructor stub
		}
		public final static ColumnsNameUser code = new ColumnsNameUser("code");
		public final static ColumnsNameUser username = new ColumnsNameUser("username");
		public final static ColumnsNameUser type = new ColumnsNameUser("type");
		public final static ColumnsNameUser password = new ColumnsNameUser("password");
		public final static ColumnsNameUser email = new ColumnsNameUser("email");
		public final static ColumnsNameUser name = new ColumnsNameUser("name");
		public final static ColumnsNameUser surname = new ColumnsNameUser("surname");
		public final static ColumnsNameUser favouriteMovieList = new ColumnsNameUser("favouriteMovieList");
	}
	@Override
	public String toString() {
		return "User [code=" + code + ", username=" + username + ",type="+ type +", password=" + password + ", email=" + email
				+ ", name=" + name + ", surname=" + surname + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.username.equals(((User)obj).getUsername());
	}

}
