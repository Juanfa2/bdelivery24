package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

public class Price {
	
	private Long id;
	private Float price;
	private Date startDate;
	
	public Price (Float price, Date startDate) {
		this.setPrice(price);
		this.setStartDate(startDate);
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
	
	public Long getId() {
		return this.id;
	}
	
}
