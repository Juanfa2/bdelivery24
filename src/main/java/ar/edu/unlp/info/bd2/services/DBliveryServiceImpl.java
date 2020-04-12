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
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight, supplier);
		repository.save(p);
		return p;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name,cuil,address,coordX,coordY);
		repository.save(s);
		return s;
	}

	@Override
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
		/**
		 * Registra la entrega de un pedido.
		 * @param order pedido a finalizar
		 * @return el pedido modificado
		 * @throws DBliveryException en caso que no exista el pedido o si el mismo no esta en estado Send
		 */
		//Order o = this.getOrderById(order).get();
		//String status = repository.getCurrentStatus(order);

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