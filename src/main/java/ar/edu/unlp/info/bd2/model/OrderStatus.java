package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;


public class OrderStatus {
	private Long id;
	private String status;
	
	public OrderStatus(String status) {
		this.setStatus(status);
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public Long getId() {
		return this.id;
	}
}
