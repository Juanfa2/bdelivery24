package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.*;

public class SpringDataDBliveryService implements DBliveryService {
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Product getMaxWeigth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrdersMadeByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getPendingOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getSentOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		List<Product> products = this.productRepository.findSoldOn(day);
		return products;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product newProduct = new Product(name, price, weight, supplier);
		Product product = this.productRepository.save(newProduct);
		return product;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product newProduct = new Product(name, price, weight, supplier,date);
		Product product = this.productRepository.save(newProduct);
		return product;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier newSupplier =  new Supplier(name, cuil, address, coordX,coordY);
		Supplier supplier = this.supplierRepository.save(newSupplier);
		return supplier;
	}

	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User newUser = new User(email,password,username,name,dateOfBirth);
		User user = this.userRepository.save(newUser);
		return user;
	}

	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Optional<Product> prod = this.productRepository.findById(id);
		Product product = prod.get();
		Price newPrice = new Price(product,price, startDate);
		product.updatePrice(newPrice);
		this.productRepository.save(product);
		this.priceRepository.save(newPrice);
		return product;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		Optional<User> user = this.userRepository.findByEmail(email);
		return user;	
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		Optional<User> user = this.userRepository.findByUsername(username);
		return user;
	}	

	@Override
	public Optional<Order> getOrderById(Long id) {
		return this.orderRepository.findById(id);
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order order = new Order(dateOfOrder, address, coordX, coordY, client);
		Order newOrder = this.orderRepository.save(order);
		return newOrder;
	}

	@Override
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		OrderProduct op = new OrderProduct(oldOrder, quantity, product);
		oldOrder.addOrderProduct(op);
		this.orderProductRepository.save(op);
		this.orderRepository.save(oldOrder);
		
		return oldOrder;
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")&& oldOrder.getProducts().size()>0) {
			oldOrder.setDeliveryUser(deliveryUser);
			oldOrder.sentOrder();
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")&& oldOrder.getProducts().size()>0) {
			oldOrder.setDeliveryUser(deliveryUser);
			oldOrder.sentOrder(date);
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")) {
			oldOrder.cancelOrder();
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")) {
			oldOrder.cancelOrder(date);
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Sent")) {
			oldOrder.deliveredOrder();
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Sent")) {
			oldOrder.deliveredOrder(date);
			this.orderRepository.save(oldOrder);
			return oldOrder;
		}else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	public boolean canCancel(Long order) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(id);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Sent")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		if(oldOrder.getActualStatus().equals("Pending")&& oldOrder.getProducts().size()>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public OrderStatus getActualStatus(Long order) {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		List<OrderStatus> or = oldOrder.getStatus();
		OrderStatus actualStatus = or.get(or.size()-1);
		return actualStatus;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		List<Product> products = this.productRepository.findAllByName(name);
		return products;
	}

}
