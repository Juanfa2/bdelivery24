package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;

import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

@Entity
@Table(name="orders")
public class Order {
	
	@Column(name="dateOfOrder")
	private Date dateOfOrder;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="client_id")
	private User client;
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="delivery_id")
	private User deliveryUser;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> orderProduct = new ArrayList<>();
	

	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "order", cascade = CascadeType.ALL)
	@Column(name="status")
	private List<OrderStatus> orderStatus = new ArrayList<>();
	
	public Order() {
		
	}
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		this.setdateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.setStatus("Pending");
		
	}
	public void setStatus(String status) {
		OrderStatus st = new OrderStatus(status);
		this.orderStatus.add(st);
	}
	
	public void setdateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}
	public void setCoordY(Float coordY) {
		this.coordY = coordY; 
	}
	public void setClient(User client) {
		this.client = client;
	}
	
	
	
	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public void setOrderStatus(List<OrderStatus> orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	
	public Date getDateOfOrder () {
		return this.dateOfOrder;
	}
	public String getAddress() {
		return this.address;
	}

	public Float getCoordX () {
		return this.coordX;
	}

	public Float getCoordY() {
		return this.coordY;
	}

	public User getClient () {
		return this.client;
	}
	
	public Long getId () {
		return this.id;
	}
	public List<OrderStatus>  getStatus () {
		return this.orderStatus;
	}
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	

	public List<OrderStatus> getProducts() {
		return orderStatus;
	}
	

	

}
