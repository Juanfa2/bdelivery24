package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.bson.codecs.pojo.annotations.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;



import ar.edu.unlp.info.bd2.config.MongoDBConfiguration;


import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;




public class User implements PersistentObject{
	
	@BsonId
	private String id;
	
	
	private String username;
	
	
	private String name;
	
	
	private String password;
	
	
	private String email;
	
	
	private Date dateOfBirth;
	
	public User() {
		
	}
	
	public User (String email, String password, String username, String name, Date dateOfBirth) {
		this.setEmail(email);
		this.setPassword(password);
		this.setUsername(username);
		this.setName(name);
		this.setDateOfBirth(dateOfBirth);
	}
	

	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getUsername () {
		return this.username;
	}
	public String getEmail () {
		return this.email;
	}
	public String getPassword () {
		return this.password;
	}
	public String getName () {
		return this.name;
	}
	public Date getDateOfBirth () {
		return this.dateOfBirth;
	}
	
	public ObjectId getObjectId() {
		ObjectId id = new ObjectId(this.id);
		return id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id.toString();
	}
	
	
}
