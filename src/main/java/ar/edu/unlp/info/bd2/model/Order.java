package ar.edu.unlp.info.bd2.model;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
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
	
	@Column(name="client_id")
	private Long clientId;
	
	private User client;
	
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="status")
	private String status;
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		this.setdateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.status = "Pending";
		this.setClientId(this.getDeliveryUser().getId());
		
	}
	public void setClientId (Long id) {
		this.clientId = id;
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
	public void setStatus(String status) {
		this.status = status;
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

	public User getDeliveryUser () {
		return this.client;
	}
	
	public Long getId () {
		return this.id;
	}
	public String getStatus () {
		return this.status;
	}
	public Long getCLientId () {
		return this.clientId;
	}

}
