package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;



public class Price {
	
	@BsonIgnore
	private String id;
	

	private Float price;
	
	
	private Date startDate;
	
	/*
	@ManyToOne
	@JoinColumn(name="product_id")
	*/
	@BsonIgnore
	private Product products;
	
	
	public Price() {
		
	}
	
	public Price(Float price, Date startDate) {
		this.setPrice(price);
		this.setStartDate(startDate);
	}
	public Price (Product product, Float price, Date startDate) {
		/*
		ObjectId id = new ObjectId();
		this.setObjectId(id);
		*/
		this.setPrice(price);
		this.setStartDate(startDate);
		this.setProduct(product);
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Float getPrice() {
		return this.price;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	/*
	public ObjectId getObjectId() {
		ObjectId id = new ObjectId(this.id);
		return id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id.toString();
	}
	*/
	
	public Product getProduct() {
		return products;
	}

	public void setProduct(Product product) {
		this.products = product;
	}

	
}
