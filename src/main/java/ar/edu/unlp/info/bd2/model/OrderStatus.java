package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;

import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;



@BsonDiscriminator
public abstract class OrderStatus {
	
	
	protected String status;
	protected Date startDate;
	
	
	public OrderStatus() {
		
	}
	
	abstract public Date getStartDate();
	abstract public void setStartDate(Date date);
	abstract public String getStatus();
	abstract public void setStatus(String status);
	

	abstract void entregarOrder();
	abstract void cancelarOrder();
	abstract void enviarOrder();
	abstract void enviarOrder(Date date);
	abstract void entregarOrder(Date date);
	abstract void cancelarOrder(Date date);

}
