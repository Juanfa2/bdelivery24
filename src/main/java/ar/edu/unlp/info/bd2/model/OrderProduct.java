package ar.edu.unlp.info.bd2.model;





import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;



public class OrderProduct {

	private Long cuantity;
	
	private Product producto;
	
	
	@BsonIgnore
	private Order orderP;
	
	public OrderProduct() {
		
	}
	
	public OrderProduct(Long cuantity, Product product) {
		this.setCuantity(cuantity);
		this.setProduct(product);
	}


	public Long getCuantity() {
		return cuantity;
	}

	public void setCuantity(Long cuantity) {
		this.cuantity = cuantity;
	}

	public Product getProduct() {
		return producto;
	}

	public void setProduct(Product product) {
		this.producto = product;
	}

	public Order getOrder() {
		return orderP;
	}

	public void setOrder(Order order) {
		this.orderP = order;
	}

	
	
}
