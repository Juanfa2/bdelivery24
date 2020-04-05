package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {this.repository = repository;}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = repository.createProduct(name, price, weight, supplier);
		return p;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = repository.createSupplier(name,cuil,address,coordX,coordY);
		return s;
	}

	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User u = repository.createUser(email, password,username,name,dateOfBirth);
		return u;
	}

	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		// TODO Auto-generated method stub
		
		Optional<Product> p = this.repository.getProductById(id);
		Price pr = new Price(price, startDate);
		
		return null;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		Optional<User> u = repository.getUserById(id);
		return u;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		Optional<User> u = repository.getUserByEmail(email);
		return u;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		Optional<User> u = repository.getUserByUsername(username);
		return u;
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		Optional<Product> p = repository.getProductById(id);
		return p;
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		Optional<Order> o = repository.getOrderById(id);
		return o;
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = repository.createOrder(dateOfOrder, address, coordX, coordY, client);
		return o;
	}

	@Override
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCancel(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public OrderStatus getActualStatus(Long order) {
		// TODO Auto-generated method stub
		OrderStatus o = null; //= new OrderStatus();
		return o;
	}*/

	@Override
	public List<Product> getProductByName(String name) {
		List<Product> p = repository.getProductByName(name);
		return p;
	}
	
	
	
	
}