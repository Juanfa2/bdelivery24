package ar.edu.unlp.info.bd2.model;


import java.util.*;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;



public class Order implements PersistentObject{

	
	private Date dateOfOrder;

	
	private String address;

	
	private Float coordX;

	
	private Float coordY;

	
	private Float totalAmount;
	
	/*
	@ManyToOne
	@JoinColumn(name="actual_status")*/
	
	private OrderStatus actualStatus;

	/*
	@ManyToOne
	@JoinColumn(name="client_id")
	*/
	private User client;

	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	*/
	@BsonId
	private String id;

	/*
	@ManyToOne
	@JoinColumn(name="delivery_id")
	*/
	
	private User deliveryUser;

	/*
	@OneToMany( mappedBy = "orderP", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	*/
	private List<OrderProduct> orderProduct = new ArrayList<>();

	/*
	@OneToMany( targetEntity = OrderStatus.class, mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	*/
	private List<OrderStatus> orderStatus = new ArrayList<>();


	public Order() {

	}
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		ObjectId id = new ObjectId();
		this.setObjectId(id);
		this.setdateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.setOrderStatus(new Pending(this ,dateOfOrder));
		this.setActualStatus();
		this.setTotalAmount(0);

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
	

	private void setTotalAmount(float amount) {
		this.totalAmount = amount;
	}


	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public Date getDateOfOrder () {
		return this.dateOfOrder;
	}

	public Float getTotalAmount () {
		return this.totalAmount;
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
		this.setTotalAmount(getAmount());
	}

	public void updateOrderStatus(OrderStatus orderStatus) {
		this.orderStatus.add(orderStatus);
	}
	public void setStatus(OrderStatus orderStatus){
		this.orderStatus.add(orderStatus);
	}

	public void setActualStatus(){
		this.actualStatus = this.orderStatus.get(orderStatus.size()-1);

	}

	public OrderStatus getActualStatus(){
		return this.actualStatus;
	}


	public void cancelOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.cancelarOrder();
		this.setActualStatus();
	}
	public void cancelOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.cancelarOrder(date);
		this.setActualStatus();
	}

	public void sentOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.enviarOrder();
		this.setActualStatus();
	}
	public void sentOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.enviarOrder(date);
		this.setActualStatus();
	}
	public void deliveredOrder(){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.entregarOrder();
		this.setActualStatus();
	}
	public void deliveredOrder(Date date){
		OrderStatus orderS = this.orderStatus.get(orderStatus.size()-1);
		orderS.entregarOrder(date);
		this.setActualStatus();
	}

	public Float getAmount(){
		//float amount = (float) 0;
		//this.getProducts().forEach(p -> amount =+ p.getCuantity() * p.getProduct().getPrice());
		List<OrderProduct> op = this.getProducts();
		float sum = 0;
		for(int i = 0; i < op.size(); i++)
			sum += op.get(i).getCuantity() * (op.get(i).getProduct().getPriceAt(this.getDateOfOrder()));
		return sum;

	}

	public Float getCurrentAmount(){
		//float amount = (float) 0;
		//this.getProducts().forEach(p -> amount =+ p.getCuantity() * p.getProduct().getPrice());
		List<OrderProduct> op = this.getProducts();
		float sum = 0;
		for(int i = 0; i < op.size(); i++)
			sum += op.get(i).getCuantity() * (op.get(i).getProduct().getPrice());
		return sum;

	}
	
	public ObjectId getObjectId() {
		ObjectId id = new ObjectId(this.id);
		return id;
	}
	
	public void setObjectId(ObjectId id) {
		this.id = id.toString();
	}

}
