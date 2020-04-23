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


	@ManyToOne
	@JoinColumn(name="client_id")
	private User client;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@ManyToOne
	@JoinColumn(name="delivery_id")
	private User deliveryUser;

	@OneToMany( mappedBy = "orderP", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderProduct> orderProduct = new ArrayList<>();


	@OneToMany( targetEntity = OrderStatus.class, mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderStatus> orderStatus = new ArrayList<>();


	public Order() {

	}
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		this.setdateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.setOrderStatus(new Pending(this ,dateOfOrder));

	}

	public List<OrderStatus> getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus.add(orderStatus);
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


	public List<OrderProduct> getProducts() {
		return orderProduct;
	}


	public List<OrderProduct> getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}

	public void addOrderProduct(OrderProduct orderProduct) {
		this.orderProduct.add(orderProduct);
	}

	public void updateOrderStatus(OrderStatus orderStatus) {
		this.orderStatus.add(orderStatus);
	}
	public void setStatus(OrderStatus orderStatus){
		this.orderStatus.add(orderStatus);
	}

	public void cancelOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.cancelarOrder();
	}
	public void cancelOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.cancelarOrder(date);
	}

	public void sentOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.enviarOrder();
	}
	public void sentOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.enviarOrder(date);
	}
	public void deliveredOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.entregarOrder();
	}
	public void deliveredOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.entregarOrder(date);
	}

}
