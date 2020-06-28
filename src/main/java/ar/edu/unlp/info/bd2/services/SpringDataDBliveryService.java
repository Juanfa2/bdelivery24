package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
		Product prod = this.productRepository.findFirstByOrderByWeightDesc();
		return prod;
	}

	@Override
	public List<Order> getAllOrdersMadeByUser(String username) {
		List<Order> orders = this.orderRepository.findByClientUsername(username);
		return orders;
	}

	@Override
	public List<Order> getPendingOrders() {
		List<Order> orders = this.orderRepository.findByActualStatusStatus("Pending");
		return orders;
	}

	@Override
	public List<Order> getSentOrders() {
		List<Order> orders = this.orderRepository.findByActualStatusStatus("Sent");
		return orders;
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		List<Order> orders = this.orderRepository.findByActualStatusStatusAndActualStatusStartDateBetween("Delivered",startDate, endDate);
		return orders;
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		List<Order> orders = this.orderRepository.findByActualStatusStatusAndClientUsername("Delivered",username);
		return orders;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		List<Product> products = this.productRepository.findByOnlyOnePrice();
		return products;
	}

	@Override
	@Transactional
	public List<Product> getSoldProductsOn(Date day) {
		List<Product> products = this.productRepository.findSoldOn(day);
		return products;
	}

	@Override
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product newProduct = new Product(name, price, weight, supplier);
		Product product = this.productRepository.save(newProduct);
		return product;
	}

	@Override
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product newProduct = new Product(name, price, weight, supplier,date);
		Product product = this.productRepository.save(newProduct);
		return product;
	}

	@Override
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier newSupplier =  new Supplier(name, cuil, address, coordX,coordY);
		Supplier supplier = this.supplierRepository.save(newSupplier);
		return supplier;
	}

	@Override
	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User newUser = new User(email,password,username,name,dateOfBirth);
		User user = this.userRepository.save(newUser);
		return user;
	}

	@Override
	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Optional<Product> prod = this.productRepository.findById(id);
		Product product = prod.get();
		Price newPrice = new Price(product,price, startDate);
		
		product.updatePrice(newPrice);
		//this.priceRepository.save(newPrice);
		//this.productRepository.save(product);
		
		return product;
	}

	@Override
	@Transactional
	public Optional<User> getUserById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user;
	}

	@Override
	@Transactional
	public Optional<User> getUserByEmail(String email) {
		Optional<User> user = this.userRepository.findByEmail(email);
		return user;	
	}

	@Override
	@Transactional
	public Optional<User> getUserByUsername(String username) {
		Optional<User> user = this.userRepository.findByUsername(username);
		return user;
	}	

	@Override
	@Transactional
	public Optional<Order> getOrderById(Long id) {
		return this.orderRepository.findById(id);
	}

	@Override
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order order = new Order(dateOfOrder, address, coordX, coordY, client);
		Order newOrder = this.orderRepository.save(order);
		return newOrder;
	}

	@Override
	@Transactional
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		OrderProduct op = new OrderProduct(oldOrder, quantity, product);
		oldOrder.addOrderProduct(op);
		//this.orderProductRepository.save(op);
		//this.orderRepository.save(oldOrder);
		
		return oldOrder;
	}

	@Override
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public OrderStatus getActualStatus(Long order) {
		Optional<Order> ord = this.orderRepository.findById(order);
		Order oldOrder = ord.get();
		List<OrderStatus> or = oldOrder.getStatus();
		OrderStatus actualStatus = or.get(or.size()-1);
		return actualStatus;
	}

	@Override
	@Transactional
	public List<Product> getProductsByName(String name) {
		List<Product> products = this.productRepository.findByNameContaining(name);
		return products;
	}

}
