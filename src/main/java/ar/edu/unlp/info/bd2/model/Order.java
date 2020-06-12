package ar.edu.unlp.info.bd2.model;


import java.util.*;


import org.bson.types.ObjectId;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;
import org.bson.codecs.pojo.annotations.*;



public class Order implements PersistentObject{

	@BsonId
	private ObjectId objectId;
	private Date dateOfOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	private Float totalAmount;
	private Float amount;

	/*NUEVO*/
	private Point position;
	
	private String actualStatus;
	
	
	private User client;
	private User deliveryUser;
	 
	
	private List<OrderProduct> products = new ArrayList<>();

	
	private List<OrderStatus> status = new ArrayList<>();


	public Order() {

	}
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client ) {
		this.setDateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setTotalAmount(0);
		this.setClient(client);
		this.status.add(new Pending(dateOfOrder));
		this.setStatus(this.status);
		OrderStatus status = this.status.get(this.status.size()-1);
		this.setActualStatus(status.getStatus());
		Position pos = new Position(coordX, coordY);
		this.position = new Point(pos);
		

	}

	/* Object Id*/
	public ObjectId getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(ObjectId id) {
		this.objectId = id;
	}
	
	
	
	/* Order Status */
	
	
	public void addStatus(OrderStatus orderStatus) {
		this.status.add(orderStatus);
		this.setActualStatus(orderStatus.getStatus());
	}
	
	public void setStatus(List<OrderStatus> orderStatus) {
		this.status = orderStatus;
	}
	
	public List<OrderStatus>  getStatus () {
		return status;
	}
	

	/* Address */
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	/* Coord */
	
	public Float getCoordX () {
		return this.coordX;
	}

	public Float getCoordY() {
		return this.coordY;
	}
	
	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}
	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}
	
	/* Client */
	
	public void setClient(User client) {
		this.client = client;
	}
	
	public User getClient () {
		return this.client;
	}
	


	/*Date of Order */
	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	
	public Date getDateOfOrder () {
		return this.dateOfOrder;
	}

	/* TotalAmount */
	
	private void setTotalAmount(float amount) {
		this.totalAmount = amount;
	}
	
	public Float getTotalAmount () {
		return this.totalAmount;
	}
	

	/* Delivery User */
	
	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public User getDeliveryUser() {
		return this.deliveryUser;
	}

	public String getActualStatus(){
		return this.actualStatus;
	}
	public void setActualStatus(String actualStatus) {
		this.actualStatus = actualStatus;
	}
	
	
	
	/*Order Product */

	
	public void setProducts(List<OrderProduct> orderProduct) {
		this.products = orderProduct;
	}
	
	
	public List<OrderProduct> getProducts() {
		return products;
	}

	

	public void addOrderProduct(OrderProduct orderProduct) {
		this.products.add(orderProduct);
		this.setAmount(this.obtenerAmount());
	}
	

	
	public void cancelOrder(){
		OrderStatus orderS = new Cancelled();
		this.addStatus(orderS);
	}
	
	
	public void cancelOrder(Date date){
		OrderStatus orderS = new Cancelled(date);
		this.addStatus(orderS);
	}
	

	public void sentOrder(){
		OrderStatus orderS = new Sent();
		this.addStatus(orderS);
	}
	
	public void sentOrder(Date date){
		OrderStatus orderS = new Sent(date);
		this.addStatus(orderS);
	}
	
	public void deliveredOrder(){
		OrderStatus orderS = new Delivered();
		this.addStatus(orderS);
	}
	
	
	public void deliveredOrder(Date date){
		OrderStatus orderS = new Delivered(date);
		this.addStatus(orderS);
	}

	
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Float obtenerAmount() {
		List<OrderProduct> op = this.getProducts();
		float sum = 0;
		for(int i = 0; i < op.size(); i++)
			sum += op.get(i).getCuantity() * (op.get(i).getProduct().getPrice());
			//sum += op.get(i).getCuantity() * (op.get(i).getProduct().getPriceAt(this.getDateOfOrder()));
		return sum;
	}
	public Float getAmount(){
		return this.amount;
	}
/*

	public Float getCurrentAmount(){
		//float amount = (float) 0;
		//this.getProducts().forEach(p -> amount =+ p.getCuantity() * p.getProduct().getPrice());
		List<OrderProduct> op = this.getOrderProduct();
		float sum = 0;
		for(int i = 0; i < op.size(); i++)
			sum += op.get(i).getCuantity() * (op.get(i).getProduct().getPrice());
		return sum;

	}
	
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setOrderStatus(List<OrderStatus> orderStatus) {
		this.orderStatus = orderStatus;
	}
	}
	*/
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	
	

}
