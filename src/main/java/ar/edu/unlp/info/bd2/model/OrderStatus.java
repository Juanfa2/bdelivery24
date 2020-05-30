package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;

import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;


/*
@Entity
@Table(name = "orderStatus")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
*/
@BsonDiscriminator
public abstract class OrderStatus implements PersistentObject{
	
	@BsonId
	protected String id;

	
	
	protected String status;

	
	protected Date startDate;
	
	/*
	@ManyToOne
	@JoinColumn(name= "order_id")
	*/
	protected Order order;
	
	public OrderStatus() {
		
	}
	
	public OrderStatus(Order order) {
		Calendar cal = Calendar.getInstance();
		this.setOrder(order);
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	abstract public String getStatus();
	abstract void setStatus(String status);
	
	public ObjectId getObjectId() {
		ObjectId id = new ObjectId(this.id);
		return id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id.toString();
	}

	abstract void entregarOrder();
	abstract void cancelarOrder();
	abstract void enviarOrder();
	abstract void enviarOrder(Date date);
	abstract void entregarOrder(Date date);
	abstract void cancelarOrder(Date date);

}
