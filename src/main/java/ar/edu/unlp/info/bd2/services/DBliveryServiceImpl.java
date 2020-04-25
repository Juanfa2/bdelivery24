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

	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight, supplier);
		repository.save(p);
		return p;
	}

	@Override
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product p = new Product(name, price, weight, supplier, date);
		repository.save(p);
		return p;
	}

	@Override
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name, cuil, address, coordX, coordY);
		repository.save(s);
		return s;
	}

	@Override
	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User u = new User(email, password, username, name, dateOfBirth);
		repository.save(u);

		return u;
	}

	@Override
	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Optional<Product> p = this.repository.getProductById(id);

		Product pr = p.get();
		Price pc = new Price(pr, price, startDate);

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
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending") && o.get().getProducts().size() > 0) {

			Order ord = o.get();
			ord.setDeliveryUser(deliveryUser);
			ord.sentOrder();

			//OrderStatus newStatus = new OrderStatus(or, "Sent");
			//or.updateOrderStatus(newStatus);
			repository.update(ord);
			//repository.save(newStatus);

			return ord;
		} else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	@Transactional
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending") && o.get().getProducts().size() > 0) {

			Order ord = o.get();
			ord.setDeliveryUser(deliveryUser);
			ord.sentOrder(date);

			//OrderStatus newStatus = new OrderStatus(or, "Sent");
			//or.updateOrderStatus(newStatus);
			repository.update(ord);
			//repository.save(newStatus);

			return ord;
		} else {
			throw new DBliveryException("The order can't be delivered");
		}
	}

	@Override
	@Transactional
	public Order cancelOrder(Long order) throws DBliveryException {

		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending")) {
			Order ord = o.get();
			ord.cancelOrder();
			return ord;
		} else {
			throw new DBliveryException("The order can't be cancelled");
		}

	}

	@Override
	@Transactional
	public Order cancelOrder(Long order, Date date) throws DBliveryException {

		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending")) {
			Order ord = o.get();
			ord.cancelOrder(date);
			return ord;
		} else {
			throw new DBliveryException("The order can't be cancelled");
		}

	}

	@Override
	@Transactional
	public Order finishOrder(Long order) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Sent")) {
			Order ord = o.get();
			ord.deliveredOrder();
			return ord;
		} else {
			throw new DBliveryException("The order can't be finished");
		}
	}

	@Override
	@Transactional
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Sent")) {
			Order ord = o.get();
			ord.deliveredOrder(date);
			return ord;
		} else {
			throw new DBliveryException("The order can't be finished");
		}
	}

	@Override
	@Transactional
	public boolean canCancel(Long order) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(id);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Sent")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean canDeliver(Long order) throws DBliveryException {
		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);
		if (orde.getStatus().equals("Pending") && o.get().getProducts().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public OrderStatus getActualStatus(Long order) {

		Optional<Order> o = repository.getOrderById(order);
		List<OrderStatus> or = o.get().getOrderStatus();
		OrderStatus orde = or.get(or.size() - 1);

		return orde;
	}

	@Override
	@Transactional
	public List<Product> getProductByName(String name) {
		List<Product> p = repository.getProductByName(name);
		return p;
	}

	//---------------------------TP2---------------------------------------//

	@Override
	@Transactional
	public List<Order> getAllOrdersMadeByUser(String username) {
		List<Order> orders = repository.getOrderByUser(username);
		return orders;
	}



	@Override
	@Transactional
	public List<User> getUsersSpendingMoreThan(Float amount) {
		return null;
	}

	@Override
	@Transactional
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		List<Supplier> suppliers = repository.suppliersInSentOrders(n);
		return suppliers;
	}

	@Override
	@Transactional
	public List<Product> getTop10MoreExpensiveProducts(){
		List<Product> products = repository.topExpensiveProducts();
		return products;
	}

	@Override
	@Transactional
	public List<User> getTop6UsersMoreOrders(){
		List <User> users = repository.topUsersMoreOrders();
		return users;
	}

	@Override
	@Transactional
	public List<Order> getCancelledOrdersInPeriod(Date startDate, Date endDate){
		List<Order> orders = repository.cancelleddOrdersInPeriod(startDate,endDate);
		return orders;
	}

	@Override
	@Transactional
	public List<Order> getPendingOrders(){
		List<Order> orders = repository.pendingOrders();
		return orders;
	}

	@Override
	@Transactional
	public List<Order> getSentOrders(){
		List<Order> orders = repository.sentOrders();
		return orders;
	}

	@Override
	@Transactional
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
		List<Order> orders = repository.deliveredOrdersInPeriod(startDate,endDate);
		return orders;
	}

	@Override
	@Transactional
	public List<Order> getDeliveredOrdersForUser(String username){
		List<Order> orders = repository.deliveredOrdersUser(username);
		return orders;
	}

	@Override
	@Transactional
	public List<Order> getSentMoreOneHour(){
		return null;
	}

	@Override
	@Transactional
	public List<Order> getDeliveredOrdersSameDay(){
		List<Order> orders = repository.getDeliveredOrdersSameDay();
		return orders;
	}

	@Override
	@Transactional
	public List<User> get5LessDeliveryUsers(){
		List<User> users = repository.lessDeliveryUser();
		return users;
	}

	@Override
	@Transactional
	public Product getBestSellingProduct(){
		Product prod = repository.getBestSellingProduct();

		return prod;
	}

	@Override
	@Transactional
	public List<Product> getProductsOnePrice(){
		List<Product> prod = repository.getProductsOnePrice();
		return prod;
	}

	@Override
	@Transactional
	public List<Product> getProductIncreaseMoreThan100(){
		List<Product> prod = repository.getProductsIncreaseMoreThan100();
		return prod;
	}

	@Override
	@Transactional
	public Supplier getSupplierLessExpensiveProduct(){
		Supplier supplier = repository.supplierLessExpensiveProduct();
		return supplier;
	}

	@Override
	@Transactional
	public List<Supplier> getSuppliersDoNotSellOn(Date day){
		return null;
	}

	@Override
	@Transactional
	public List<Product> getSoldProductsOn(Date day){
		List<Product> prod =  repository.productsSoldOn(day);

		return prod;
	}

	@Override
	@Transactional
	public List<Order> getOrdersCompleteMorethanOneDay(){
		List<Order> orders =  repository.ordersCompleteMorethanOneDay();

		return orders;
	}

	@Override
	@Transactional
	public List<Object[]> getProductsWithPriceAt(Date day){
		List<Object[]> prices =  repository.productsWithPriceAt(day);
		return prices;
	}

	@Override
	@Transactional
	public List <Product> getProductsNotSold(){
		return null;
	}

	@Override
	@Transactional
	public List<Order> getOrderWithMoreQuantityOfProducts(Date day){
		return null;
	}
}
