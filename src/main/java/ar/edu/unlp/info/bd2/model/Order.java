package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.*;
import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;

@Entity
@Table(name="productOrders")
public class Order {
	
	@Column(name="dateOfOrder")
	private Date dateOfOrder;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="client_id")
	private User client;
	
	@Id
	@GeneratedValue
	private Long id;
	
	
	private Long quantity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="delivery_id")
	private User deliveryUser;
	
	private ArrayList<Product> products = new ArrayList<Product>();
	
	@Column(name="status")
	private ArrayList<String> status = new ArrayList<String>();
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		this.setdateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.setStatus("Pending");
		
	}
	public void setStatus(String status) {
		this.status.add("status");
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
	
	public void setProducts(Product product) {
		this.products.add(product);
	}
	
	public void sumCuantity(Long cuantity) {
		this.quantity += quantity;
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
	public ArrayList<String>  getStatus () {
		return this.status;
	}
	
	public ArrayList<Product> getProducts() {
		return this.products;
	}
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	
	

}
