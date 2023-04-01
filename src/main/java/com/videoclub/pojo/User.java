package com.videoclub.pojo;

public class User {
    
    // Attributes of the User class
	private int code;
    private String username;
    private typeUser type;
    private String password;
    private String email;
    private String name;
    private String surname;
   // private ArrayList<Rental>
    
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
    public User(int code, String username, String password, String email, String name, String surname, typeUser type) {
    	this.code = code;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.type = type;
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
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.name + " " + this.surname;
    }
}
