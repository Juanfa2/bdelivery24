package ar.edu.unlp.info.bd2.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;



public class Supplier implements PersistentObject{
	
	@Id
	private String id;
	
	
	private String name;
	
	
	private String cuil;
	
	
	private String address;
	
	
	private Float coordX;
	
	
	private Float coordY;
	
	/*
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	*/
	private List<Product> products = new ArrayList<>();

	public Supplier() {		
	}
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		this.setName(name);
		this.setCuil(cuil);
		this.setAddress(address);
		this.setCoordY(coordY);
		this.setCoordX(coordX);
	}
	
	public void setName (String name) {
		this.name = name;
	}
	public void setCuil (String cuil) {
		this.cuil = cuil;
	}
	public void setAddress (String address) {
		this.address = address;
	}
	public void setCoordX (Float coordX) {
		this.coordX = coordX;
	}
	public void setCoordY (Float coordY) {
		this.coordY = coordY;
	}
	
	public ObjectId getObjectId() {
		ObjectId id = new ObjectId(this.id);
		return id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id.toString();
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getCuil() {
		return this.cuil;
	}
	public Float getCoordX() {
		return this.coordX;
	}
	public Float getCoordY() {
		return this.coordY;
	}

}
