package ar.edu.unlp.info.bd2.model;

import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.bson.codecs.pojo.annotations.*;



public class Product implements PersistentObject{
	
	@BsonId
	private ObjectId objectId;

	private String name;
	private List<Price> prices = new ArrayList<>();
	
	@BsonIgnore
	private List<OrderProduct> orderProduct = new ArrayList<>();
	
	private Float price ;
	private Float weight;
	private Date date;
	private Supplier supplier;
	
	
	
	
	public Product() {
		
	}

	public Product (String name, Float price, Float weight, Supplier supplier) {
		Calendar cal = Calendar.getInstance();
		Date startDate = cal.getTime();
		ObjectId id = new ObjectId();
		this.setObjectId(id);
		
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.prices.add(new Price(price,startDate));
	}
	public Product (String name, Float price, Float weight, Supplier supplier, Date date) {
		
		ObjectId id = new ObjectId();
		this.setObjectId(id);
		
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.setDate(date);
		this.prices.add(new Price(price,date));
	}

	public void setDate(Date date){
		this.date = date;
	}
	public Date getDate(){
		return this.date;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public String getName() {
		return this.name;
	}
	public Float getPrice() {
		return this.price;
	}
	public List<Price> getPrices(){
		return this.prices;
	}
	
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	
	public Float getWeight() {
		return this.weight;
	}
	public Supplier getSupplier() {
		return this.supplier;
	}
	
	public ObjectId getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(ObjectId id) {
		this.objectId = id;
	}
	
	public List<OrderProduct> getOrderProduct() {
		return this.orderProduct;
	}

	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}
	public void updatePrice(Price price) {
		this.setPrice(price.getPrice());
		this.prices.add(price);
	}

	public Float getPriceAt(Date date){
		List<Price> p = this.getPrices();
		Date datMax = new Date(0);
		Float price = null;
		for(int i = 0; i < p.size(); i++) {
			if (p.get(i).getStartDate().before(date)) {
				if((p.get(i).getStartDate().after(datMax))){
					datMax = p.get(i).getStartDate();
					price = p.get(i).getPrice();
				}
			}
		}
		return price;
	}

}
