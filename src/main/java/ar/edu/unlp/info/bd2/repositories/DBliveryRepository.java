package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;


public class DBliveryRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

    private Session session = sessionFactory.openSession();

    public Optional<User> getUserById(long id) {
    	Optional<User> u=Optional.ofNullable(session.get(User.class,id));
    	return u;
    }

	public Optional<User> getUserByEmail(String email) {
	
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where email = :email ");
        query.setParameter("email", email);
		List<User> users = query.getResultList();
        Optional<User> u=Optional.ofNullable(users.get(0));
        return (users != null && !users.isEmpty()) ? u : null;
		
	}

	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		this.session.persist(o);
		return o;
	}

	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight, supplier);
		this.session.persist(p);
		return p;
	}

	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name,cuil,address,coordX,coordY);
        this.session.persist(s);
		return s;
	}

	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User u = new User(email, password,username,name,dateOfBirth);
        this.session.persist(u);
		return u;
	}

	public Optional<User> getUserByUsername(String username) {
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from User where username = :username ");
        query.setParameter("username", username);
		List<User> users = query.getResultList();
        Optional<User> u=Optional.ofNullable(users.get(0));
        return (users != null && !users.isEmpty()) ? u : null;
	}

	public Optional<Product> getProductById(Long id) {
    	Optional<Product> p=Optional.ofNullable(session.get(Product.class,id));
    	return p;
	}

	public Optional<Order> getOrderById(Long id) {
    	Optional<Order> o=Optional.ofNullable(session.get(Order.class,id));
    	return o;
	}

	public List<Product> getProductByName(String name) {
		@SuppressWarnings("unchecked")
		Query<Product> query = session.createQuery("from Product where name = :name ");
        query.setParameter("name", name);
		List<Product> products = query.getResultList();
        //Optional<Product> u=Optional.ofNullable(products.get(0));
        return products;
	}


}

