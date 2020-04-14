package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;


public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {this.repository = repository;}

	@Override
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight, supplier);
		repository.save(p);
		return p;
	}

	@Override
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name,cuil,address,coordX,coordY);
		repository.save(s);
		return s;
	}

	@Override
	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User u = new User(email, password,username,name,dateOfBirth);
		repository.save(u);

		return u;
	}

	@Override
	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Optional<Product> p = this.repository.getProductById(id);

		Product pr = p.get();
		Price pc = new Price(pr,price, startDate);

		pr.updatePrice(pc);

		
		return pr;
	}

	@Override
	@Transactional
	public Optional<User> getUserById(Long id) {
		Optional<User> u = repository.getUserById(id);
		return u;
	}

	@Override
	@Transactional
	public Optional<User> getUserByEmail(String email) {
		Optional<User> u = repository.getUserByEmail(email);
		return u;
	}

	@Override
	@Transactional
	public Optional<User> getUserByUsername(String username) {
		Optional<User> u = repository.getUserByUsername(username);
		return u;
	}

	@Override
	@Transactional
	public Optional<Product> getProductById(Long id) {
		Optional<Product> p = repository.getProductById(id);
		return p;
	}

	@Override
	@Transactional
	public Optional<Order> getOrderById(Long id) {
		Optional<Order> o = repository.getOrderById(id);
		return o;
	}

	@Override
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		repository.save(o);
		return o;
	}

	@Override
	@Transactional
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		Order or = o.get();
		OrderProduct op = new OrderProduct(or, quantity, product);
        or.addOrderProduct(op);
		this.repository.save(op);

		/*Optional<Order> o = repository.addProductToOrder(order, quantity, product);
		Order or = o.get();*/
		return or;
	}

	@Override
	@Transactional
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		Order or = o.get();
		or.sentOrder();

		//OrderStatus newStatus = new OrderStatus(or, "Sent");
		//or.updateOrderStatus(newStatus);
		repository.update(or);
		//repository.save(newStatus);

		return null;
	}

	@Override
	@Transactional
	public Order cancelOrder(Long order) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		Order or = o.get();
		or.cancelOrder();

		//OrderStatus newStatus = new OrderStatus(or, "Cancelled");
		//or.updateOrderStatus(newStatus);

		return null;
	}

	@Override
	@Transactional
	public Order finishOrder(Long order) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		Order or = o.get();
		or.deliveredOrder();

        //OrderStatus newStatus = new OrderStatus(or, "Delivered");
		//or.updateOrderStatus(newStatus);

		return null;
	}

	@Override
	@Transactional
	public boolean canCancel(Long order) throws DBliveryException {
		OrderStatus o = repository.getLastStatus(order);
		if (o.getStatus()=="Pending"){
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException {
		OrderStatus o = repository.getLastStatus(id);
		if (o.getStatus()=="Sent"){
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean canDeliver(Long order) throws DBliveryException {
		OrderStatus o = repository.getLastStatus(order);
		if (o.getStatus()=="Pending"){
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public OrderStatus getActualStatus(Long order) {

		OrderStatus actualStatus = repository.getLastStatus(order);

		return actualStatus;
	}

	@Override
	@Transactional
	public List<Product> getProductByName(String name) {
		List<Product> p = repository.getProductByName(name);
		return p;
	}
	
	
	
	
}