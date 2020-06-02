package ar.edu.unlp.info.bd2.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;


public class DBliveryServiceImpl implements DBliveryService{

	private DBliveryMongoRepository repository;

	public DBliveryServiceImpl(DBliveryMongoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product product = new Product(name, price,weight, supplier);
		this.repository.createProduct(product);
		return product;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product product = new Product(name, price,weight, supplier, date);
		this.repository.createProductWithDate(product);
		return product;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY );
		this.repository.createSupplier(supplier);
		
		return supplier;
	}

	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(email, password, username, name, dateOfBirth);
		this.repository.createUser(user);
		return user;
	}

	@Override
	public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
		Product product = this.repository.findProductById(id);
		Price newPrice = new Price(price, startDate);
		product.updatePrice(newPrice);
		this.repository.updateProductPrice(product, id);	
		return product;
	}

	@Override
	public Optional<User> getUserById(ObjectId id) {
		Optional<User> user = this.repository.getUserById(id);
		return user;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		Optional<User> user = this.repository.getUserByEmail(email);
		return user;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		Optional<User> user = this.repository.getUserByUsername(username);
		return user;
	}

	@Override
	public Optional<Order> getOrderById(ObjectId id) {
		Optional<Order> order = this.repository.getOrderById(id);
		return order;
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order order = new Order(dateOfOrder, address, coordX, coordY, client);
		this.repository.createOrder(order);
		return order;
	}

	@Override
	public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		OrderProduct orderProduct = new OrderProduct(quantity, product);
		newOrder.addOrderProduct(orderProduct);
		this.repository.updateOrder(newOrder, order);
		return newOrder;
	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Pending") && newOrder.getProducts().size() > 0){
			newOrder.setDeliveryUser(deliveryUser);
			newOrder.sentOrder();
			this.repository.updateOrder(newOrder,order);
			return newOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(ObjectId order) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Pending")){
			newOrder.cancelOrder();
			this.repository.updateOrder(newOrder,order);
			return newOrder;
		}else {
			throw new DBliveryException("The order can't be cancelled");
		}
	}

	@Override
	public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(ObjectId order) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Sent")){
			newOrder.deliveredOrder();
			this.repository.updateOrder(newOrder,order);
			return newOrder;
		}else {
			throw new DBliveryException("The order can't be finished");
		}
	}

	@Override
	public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCancel(ObjectId order) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Pending") ) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean canFinish(ObjectId id) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(id);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Sent") ) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean canDeliver(ObjectId order) throws DBliveryException {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		if(newOrder.getActualStatus().equals("Pending")  && newOrder.getProducts().size() > 0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public OrderStatus getActualStatus(ObjectId order) {
		Optional<Order> oldOrder = this.repository.getOrderById(order);
		Order newOrder = oldOrder.get();
		List<OrderStatus> or = newOrder.getStatus();
		OrderStatus actualStatus = or.get(or.size()-1);
		return actualStatus;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		FindIterable<Product>  products = this.repository.getProductsByName(name);
		List<Product> productos = new ArrayList<>();
		MongoCursor<Product> prod = products.iterator();
		while(prod.hasNext()) {
			productos.add(prod.next());
		}
		return productos;
	}

	
}